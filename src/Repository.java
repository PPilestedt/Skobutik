import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;

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

    public int validateLogin(String userLogin, String userPassword) {

        int dividerIndex = userLogin.indexOf(".");
        String realPassword = null;
        int userID = -1;

        try (Connection con = DriverManager.getConnection(database, username, password)) {
            PreparedStatement statement = con.prepareStatement("SELECT id,förnamn,efternamn,lösenord FROM kund WHERE förnamn like ? AND efternamn like ?");
            statement.setString(1, userLogin.substring(0, dividerIndex));
            statement.setString(2, userLogin.substring(dividerIndex + 1));
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                userID = res.getInt(1);
                realPassword = res.getString(4);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (userPassword.equals(realPassword)) {
            return userID;
        } else {
            return -1;
        }
    }

    private List<Model> getModels(int shoeId) {
        List<Model> models = new ArrayList<>();
        List<Model> shoeModels = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(database, username, password)) {
            PreparedStatement statement = con.prepareStatement("SELECT namn FROM modell");
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                models.add(new Model(res.getString(1)));
            }
            PreparedStatement statement1 = con.prepareStatement("SELECT modellid FROM typ WHERE skoid = ?");
            statement1.setInt(1, shoeId);
            res = statement1.executeQuery();
            while (res.next()) {
                switch (res.getInt("modellid")) {
                    case 1 -> shoeModels.add(models.get(0));
                    case 2 -> shoeModels.add(models.get(1));
                    case 3 -> shoeModels.add(models.get(2));
                    case 4 -> shoeModels.add(models.get(3));
                    case 5 -> shoeModels.add(models.get(4));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shoeModels;
    }

    public List<Shoe> getShoeList() {
        List<Shoe> listOfShoes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(database, username, password)) {
            PreparedStatement statement = con.prepareStatement(
                    "SELECT färg, storlek, pris, märke.namn, lager.antal, sko.id " +
                            "FROM sko " +
                            "INNER JOIN märke ON märke.id = sko.märkesid " +
                            "INNER JOIN lager ON lager.skoid = sko.id");
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                int amount = res.getInt(5);
                if (amount > 0) {
                    Shoe tempShoe = new Shoe(res.getInt(6), res.getString(1), res.getInt(2), res.getInt(3), new Producer(res.getString(4)), amount);
                    for (Model model : getModels(tempShoe.getId())) {
                        tempShoe.addModel(model);
                    }
                    listOfShoes.add(tempShoe);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfShoes;
    }

    public void addToCart(int userID, Shoe shoe) {

        int orderID = 0;

        try (Connection con = DriverManager.getConnection(database, username, password)) {

            PreparedStatement stmt = con.prepareStatement("SELECT id FROM beställning where kundid = ? AND betald IS FALSE");
            stmt.setInt(1, userID);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                orderID = res.getInt(1);
            }
            CallableStatement statement = con.prepareCall("CALL addToCart(?,?,?)");
            statement.setInt(1, userID);
            if (orderID == 0) {
                statement.setNull(2, Types.INTEGER);
            } else {
                statement.setInt(2, orderID);
            }
            statement.setInt(3, shoe.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO: Felhantering om vi hinner
        }
    }

    private Shoe getShoe(int shoeID) {
        Shoe newShoe = null;
        try (Connection con = DriverManager.getConnection(database, username, password)) {
            PreparedStatement statement = con.prepareStatement("SELECT färg, storlek, pris, märke.namn, lager.antal " +
                    "FROM sko " +
                    "INNER JOIN märke ON märke.id = sko.märkesid " +
                    "INNER JOIN lager ON lager.skoid = sko.id " +
                    "WHERE sko.id = ?");
            statement.setInt(1, shoeID);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                newShoe = new Shoe(shoeID, res.getString(1), res.getInt(2), res.getInt(3), new Producer(res.getString(4)), res.getInt(5));
                for (Model model : getModels(shoeID)) {
                    newShoe.addModel(model);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newShoe;
    }

    public Map<Shoe, Integer> getShoppingList() {
        Map<Shoe, Integer> shoeMap = new HashMap<>();
        try (Connection con = DriverManager.getConnection(database, username, password)) {
            PreparedStatement statement = con.prepareStatement("SELECT skoid, antal FROM shoppinglista " +
                    "INNER JOIN beställning " +
                    "ON shoppinglista.beställningsid = beställning.id " +
                    "WHERE beställning.betald is false;");
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                shoeMap.put(getShoe(res.getInt(1)), res.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shoeMap;
    }

    public void payOrder(int userID) {
        Connection con = null;
        try {
            con = DriverManager.getConnection(database, username, password);
            PreparedStatement statement = con.prepareStatement("UPDATE beställning SET betald = 1 WHERE kundid = ?");
            statement.setInt(1, userID);
            statement.executeUpdate();
        } catch (SQLException e) {
            try {
                assert con != null;
                con.rollback();
            } catch (SQLException ae) {
                e.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                assert con != null;
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
