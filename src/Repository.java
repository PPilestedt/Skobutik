import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Repository {

    private String username;
    private String password;
    private String database;
    private String propertiesPath = "src/settings.properties";

    public Repository(){

        try {
            loadProperties();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

    private void loadProperties() throws IOException {

        FileReader reader = new FileReader(propertiesPath);

        Properties prop = new Properties();
        prop.load(reader);

        username = prop.getProperty("username","admin");
        password = prop.getProperty("password","password");
        database = prop.getProperty("database","localhost");

        System.out.println(username + " " + password + " " + database);

    }
}
