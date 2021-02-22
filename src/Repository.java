import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {

    private String username;
    private String password;
    private String database;
    private String propertiesPath = "src/settings.properties";

    public Repository() {

        try {
            loadProperties();
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void loadProperties() throws IOException {

        FileReader reader = new FileReader(propertiesPath);

        Properties prop = new Properties();
        prop.load(reader);

        username = prop.getProperty("username", "admin");
        password = prop.getProperty("password", "password");
        database = prop.getProperty("database", "localhost");

        System.out.println(username + " " + password + " " + database);
    }

    public boolean validateLogin(String userLogin, String userPassword) {

        int dividerIndex = userLogin.indexOf(".");
        String realPassword = null;

        try (Connection con = DriverManager.getConnection(database, username, password)) {
            PreparedStatement statement = con.prepareStatement("SELECT förnamn,efternamn,lösenord FROM kund WHERE förnamn like ? AND efternamn like ?");
            statement.setString(1, userLogin.substring(0, dividerIndex));
            statement.setString(2, userLogin.substring(dividerIndex + 1));
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                realPassword = res.getString(3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (userPassword.equals(realPassword)) {
            return true;
        } else {
            return false;
        }
    }

    public List<Shoe> getShoeList() {
        List<Shoe> listOfShoes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(database, username, password)) {
            PreparedStatement statement = con.prepareStatement("SELECT färg, storlek, pris, märke.namn, lager.antal FROM sko INNER JOIN märke ON märke.id = sko.märkesid INNER JOIN lager ON lager.skoid = sko.id");
            ResultSet res = statement.executeQuery();
            while(res.next()) {
                int amount = res.getInt(5);
                if (amount > 0) {
                    listOfShoes.add(new Shoe(res.getString(1), res.getInt(2), res.getInt(3), new Producer(res.getString(4)), amount));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfShoes;
    }
}
