import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserInterface {

    private Scanner scan = new Scanner(System.in);
    private Repository repo = new Repository();

    private Customer user = null;

    public UserInterface() {
        userLogin();
        showMainMenu();
    }

    public void userLogin() {

        while (user == null) {
            System.out.println("skriv in användarnamn och lösenord... i klartext ;) (lovar att inte läcka ut info på nätet)");
            System.out.println("Användarnamn: ");
            String username = scan.nextLine().trim();
            System.out.println("Lösenord: ");
            String password = scan.nextLine().trim();

            user = repo.validateLogin(username, password);

        }
    }

    private void showMainMenu() {
        System.out.println("Hej och välkommen till den makalöst bra skobutiken!");
        System.out.println("1. Skobutik");
        System.out.println("2. Visa Varukorg");
        System.out.println("3. Betala och Avsluta");

        System.out.println("Gör ditt val: ");
        String userinput = scan.nextLine();
        if (userinput.equalsIgnoreCase("1")) {
            showShoeListMenu();
        } else if (userinput.equalsIgnoreCase("2")) {
            showShoppinglist();
        } else if (userinput.equalsIgnoreCase("3")) {
            completePurchase();
        }
    }

    private void showShoeListMenu() {
        List<Shoe> shoeList = repo.getShoeList();
        for (int i = 1; i <= shoeList.size(); i++) {
            System.out.println(i + ". " + shoeList.get(i - 1));
        }

        System.out.println("Gör ditt val: ");
        int userinput = Integer.parseInt(scan.nextLine());

        showShoeDetails(shoeList.get(userinput - 1));

    }

    private void showShoeDetails(Shoe shoe) {


        System.out.println("--- " + shoe.toString() + " ---");
        System.out.println("Pris: " + shoe.getPrice());
        System.out.println("Medelbetyg: " + shoe.getAverageRating());
        System.out.println();
        System.out.println("1. Lägg till i varukorg");
        System.out.println("2. Visa omdöme");
        System.out.println("3. Lämna omdöme");
        System.out.println("4. Backa");

        System.out.println("Gör ditt val: ");
        String userinput = scan.nextLine();
        if (userinput.equalsIgnoreCase("1")) {
            repo.addToCart(user.getId(), shoe);
            System.out.println("Tillagt i varukorgen");
            showMainMenu();
        } else if (userinput.equalsIgnoreCase("2")) {
            showAllRatings(shoe);
        } else if (userinput.equalsIgnoreCase("3")) {
            rateShoe(shoe);
        } else if (userinput.equalsIgnoreCase("4")) {
            showShoeListMenu();
        }
    }

    private void showAllRatings(Shoe shoe) {
        String ratingInText;
        for (Rating rating :
                shoe.getRatings()) {

            switch (rating.getScore()) {
                case 10 -> ratingInText = "Missnöjd";
                case 20 -> ratingInText = "Ganska nöjd";
                case 30 -> ratingInText = "Nöjd";
                case 40 -> ratingInText = "Mycket nöjd";
                default -> ratingInText = "Betyg Saknas";
            }

            System.out.println("----------");
            System.out.println("Betyg: " + ratingInText);
            System.out.println("Kommentar: " + rating.getComment());
            System.out.println("Kund: " + rating.getCustomer().getFirstName() + " " + rating.getCustomer().getLastName());
            System.out.println("----------");
            System.out.println();

            showMainMenu();
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
        repo.payOrder(user.getId());
        System.out.println("Varukorgen är betald.");
        System.exit(0);
    }

    private void rateShoe(Shoe shoe) {

        int ratingInDigit;
        int points = 0;
        String currentRatingScale = "1-4";
        String commentYesOrNo = null;
        String ratingInComment = null;

        System.out.println("Vad vill du ge den för betyg?");
        System.out.println("1. Missnöjd");
        System.out.println("2. Ganska nöjd");
        System.out.println("3. Nöjd");
        System.out.println("4. Mycket nöjd");
        System.out.println("Gör ditt val:");


        while (true) {
            ratingInDigit = scan.nextInt();
            switch (ratingInDigit) {
                case 1 -> points = 10;
                case 2 -> points = 20;
                case 3 -> points = 30;
                case 4 -> points = 40;
                default -> System.out.println("Du har inte gett ett betyg mellan " + currentRatingScale);
            }
            if (points > 0) {
                break;
            } scan.nextLine();
        }
        System.out.println("Tack! Vill du även kommentera skon? [y/n]");
        while (true) {
            commentYesOrNo = scan.nextLine();
            if (commentYesOrNo.equalsIgnoreCase("y")) {
                System.out.println("Skriv din kommentar: ");
                ratingInComment = scan.nextLine();
                break;
            } else if (commentYesOrNo.equalsIgnoreCase("n")) {
                ratingInComment = null;
                break;
            } else{
                System.out.println("Skriv vänligen Y eller N");
            }
        }
        System.out.println("Tack för ditt omdöme! Ditt engagemang betyder mycket för oss.");
        repo.addRating(new Rating(points, ratingInComment, user),shoe.getId());
    }


}
