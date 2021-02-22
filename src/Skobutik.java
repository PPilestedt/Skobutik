public class Skobutik {

    public static void main(String[] args) {
        System.out.println("skor till alla!");
        System.out.println("Loading properties");
        Repository repo = new Repository();
        System.out.println("Properties Loaded");

        System.out.println("starting user interface");
        new UserInterface();
    }
}
