import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
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

    public String completePurchase() throws SQLException {

        String query = "CALL addToCart";
        String errormessage = "";
        ResultSet rs = null;
        int slutsumma = 0;

        try (Connection con = DriverManager.getConnection(database, username, password)) {
            PreparedStatement addOrderPricesStmt = con.prepareStatement("SELECT pris from sko(beställning)");
            //stmt.setString(1, beställning); -- hämtar vi in beställning
            rs = addOrderPricesStmt.executeQuery();
            while (rs.next()) {
                //slutsumma = rs.getInt();
                if (slutsumma <= 0) {
                    System.out.println("Det finns inget i din varukorg av värde");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            return "Kan inte visa beställning: " ; //beställning;
        }
        return " Den totala summan i din beställning är " + slutsumma;
    }





}
