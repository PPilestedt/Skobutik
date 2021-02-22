import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private Scanner scan = new Scanner(System.in);
    private Repository repo = new Repository();

    private int userID = -1;
    private int menuPage = 0;

    public UserInterface(){

        userLogin();

        // menu page 0
        showMainMenu();


        // menu page 1
        System.out.println("--- Skobutiken ---");
        System.out.println("radda med skor visas ...");
        System.out.println("välj sko 1.2.3.4.5.6");
        System.out.println("backa");

        // menu page 2


        System.out.println("--- Visa varukorg ---");
        System.out.println("1.Radera en post");
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
        System.out.println("");
        System.out.println("1. Lägg till i varukorg");
        System.out.println("2. Visa omdöme");
        System.out.println("3. Lämna omdöme");
        System.out.println("4. Backa");

        System.out.println("Gör ditt val: ");
        String userinput = scan.nextLine();
        if(userinput.equalsIgnoreCase("1")){
            repo.addToCart(userID,shoe);
            System.out.println("Tillagt i varukorgen");
        }else if(userinput.equalsIgnoreCase("2")){
            //visa omdöme
        }else if(userinput.equalsIgnoreCase("3")){
            //adda omdöme
        }else if(userinput.equalsIgnoreCase("4")){
            showShoeListMenu();
        }
    }

    private void showShoppinglist() {
    }

    private void completePurchase() {
        //TODO: visa totalsumman och sätt betald till true
    }
}
