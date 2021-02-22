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



        try (Connection con = DriverManager.getConnection(username, password, database)) {
            PreparedStatement statement = con.prepareStatement("SELECT förnamn,efternamn,lösenord FROM kund WHERE förnamn like ? AND efternamn like ?");
            statement.setString(1, userLogin.substring(0, dividerIndex));
            statement.setString(2, userLogin.substring(dividerIndex + 1));
            ResultSet res = statement.executeQuery();
            realPassword = res.getString(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (userPassword.equals(realPassword)) {
            return true;
        } else {
            return false;
        }
    }
}
