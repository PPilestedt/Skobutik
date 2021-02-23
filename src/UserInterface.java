import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserInterface {

    private Scanner scan = new Scanner(System.in);
    private Repository repo = new Repository();

    private int userID = -1;

    public UserInterface(){
        userLogin();
        showMainMenu();
    }

    public void userLogin(){

        while(true) {
            System.out.println("skriv in användarnamn och lösenord... i klartext ;) (lovar att inte läcka ut info på nätet)");
            System.out.println("Användarnamn: ");
            String username = scan.nextLine().trim();
            System.out.println("Lösenord: ");
            String password = scan.nextLine().trim();

            userID = repo.validateLogin(username, password);

            if(userID != -1){
                break;
            }
        }
    }

    private void showMainMenu() {
        System.out.println("Hej och välkommen till den makalöst bra skobutiken!");
        System.out.println("1. Skobutik");
        System.out.println("2. Visa Varukorg");
        System.out.println("3. Betala och Avsluta");

        System.out.println("Gör ditt val: ");
        String userinput = scan.nextLine();
        if(userinput.equalsIgnoreCase("1")){
            showShoeListMenu();
        }else if(userinput.equalsIgnoreCase("2")){
            showShoppinglist();
        }else if(userinput.equalsIgnoreCase("3")){
            completePurchase();
        }
    }

    private void showShoeListMenu() {
        List<Shoe> shoeList = repo.getShoeList();
        for (int i = 1; i <= shoeList.size(); i++) {
            System.out.println(i + ". " + shoeList.get(i-1));
        }

        System.out.println("Gör ditt val: ");
        int userinput = Integer.parseInt(scan.nextLine());

        showShoeDetails(shoeList.get(userinput-1));

    }

    private void showShoeDetails(Shoe shoe) {

        System.out.println("--- " + shoe.toString() + " ---");
        System.out.println("Pris: " + shoe.getPrice());
        System.out.println();
        System.out.println("1. Lägg till i varukorg");
        System.out.println("2. Visa omdöme");
        System.out.println("3. Lämna omdöme");
        System.out.println("4. Backa");

        System.out.println("Gör ditt val: ");
        String userinput = scan.nextLine();
        if(userinput.equalsIgnoreCase("1")){
            repo.addToCart(userID,shoe);
            System.out.println("Tillagt i varukorgen");
            showMainMenu();
        }else if(userinput.equalsIgnoreCase("2")){
            //TODO: visa omdöme
        }else if(userinput.equalsIgnoreCase("3")){
            //TODO: adda omdöme
        }else if(userinput.equalsIgnoreCase("4")){
            showShoeListMenu();
        }
    }

    private void showShoppinglist() {
        System.out.println("Varukorgen: ");
        Map<Shoe, Integer> shoppingList = repo.getShoppingList();
        for (Shoe shoe : shoppingList.keySet()) {
            System.out.println("Sko: " + shoe + ", antal: " + shoppingList.get(shoe));
        }
        showMainMenu();
        //TODO: Kunna ta bort från varukorgen om tid finns
    }

    private void completePurchase() {
        int sum = 0;
        Map<Shoe, Integer> shoppinglist = repo.getShoppingList();
        for (Shoe shoe : shoppinglist.keySet()) {
            sum += shoe.getPrice();
        }
        System.out.println("Summan blir: " + sum);
        repo.payOrder(userID);
        System.out.println("Varukorgen är betald.");
        System.exit(0);
    }
}
