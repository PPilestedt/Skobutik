import java.util.ArrayList;
import java.util.List;

public class Shoe {
    private String color;
    private int size;
    private int price;
    private Producer producer;
    private List<Model> model;
    private int amount = 1;
    private int id;
    private List<Rating> ratingList;
    private String averageRating;

    public Shoe(int id, String color, int size, int price, Producer producer, int amount) {
        this.color = color;
        this.size = size;
        this.price = price;
        this.producer = producer;
        this.amount = amount;
        this.id = id;
        this.model = new ArrayList<>();
        this.ratingList = new ArrayList<>();
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating){
        this.averageRating = averageRating;
    }


    public void addRating(Rating rating){
        ratingList.add(rating);
    }

    public List<Rating> getRatings(){
        return ratingList;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public void addModel(Model model) {
        this.model.add(model);
    }

    @Override
    public String toString() {
        return producer + ", " + color + ", " + size + ", " + model;
    }
}
