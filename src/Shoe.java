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

    public int getId() {
        return id;
    }

    public Shoe(int id, String color, int size, int price, Producer producer, int amount) {
        this.color = color;
        this.size = size;
        this.price = price;
        this.producer = producer;
        this.model = new ArrayList<>();
        this.amount = amount;
        this.id = id;
    }

    public Shoe(String color, int size, int price, Producer producer) {
        this.color = color;
        this.size = size;
        this.price = price;
        this.producer = producer;
        this.model = new ArrayList<>();
    }

    public String getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public int getPrice() {
        return price;
    }

    public Producer getProducer() {
        return producer;
    }

    public int getAmount() {
        return amount;
    }

    public void addModel(Model model) {
        this.model.add(model);
    }

    @Override
    public String toString() {
        return producer + ", " + color + ", " + size + ", " + model;
    }
}
