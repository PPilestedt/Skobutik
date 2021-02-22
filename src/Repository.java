import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Repository {

    private String username;
    private String password;
    private String database;
    private String propertiesPath = "settings.properties";

    public Repository(){

        try {
            loadProperties();
        } catch (FileNotFoundException e) {
            System.out.println("Error loading proerties file");
            System.exit(0);
        }

    }

    private void loadProperties() throws FileNotFoundException {

        Properties prop = new Properties();
        InputStream inputStream = Repository.class.getClassLoader().getResourceAsStream(propertiesPath);

        if(inputStream != null){

            try {
                prop.load(inputStream);
                username = prop.getProperty("username","admin");
                password = prop.getProperty("password","password");
                database = prop.getProperty("database","localhost");
            }catch (IOException e){
                System.out.println("proplem med properties-filen");
            }

        }else{
            throw new FileNotFoundException("Could not find properties file " + propertiesPath);
        }


    }
}
