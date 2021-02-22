import java.util.List;

public class Shoe {
    private String color;
    private int size;
    private int price;
    private Producer producer;
    private List<Model> model;

    public Shoe(String color, int size, int price, Producer producer) {
        this.color = color;
        this.size = size;
        this.price = price;
        this.producer = producer;
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

    public void addModel(Model model) {
        this.model.add(model);
    }


}
