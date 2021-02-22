public class Shoe {
    private int id;
    private String color;
    private int size;
    private int price;
    private Producer producer;

    public Shoe(int id, String color, int size, int price, Producer producer) {
        this.id = id;
        this.color = color;
        this.size = size;
        this.price = price;
        this.producer = producer;
    }

    public int getId() {
        return id;
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
}
