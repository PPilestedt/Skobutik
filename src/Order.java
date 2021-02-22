public class Order {

    protected int id;
    protected String createdDate;
    protected Customer customer;
    protected Shoppinglist shoppinglist;

    public Order(int id, String createdDate, Customer customer, Shoppinglist shoppinglist){

        this.id = id;
        this.createdDate = createdDate;
        this.customer = customer;
        this.shoppinglist = shoppinglist;

    }

    public int getId() {
        return id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Shoppinglist getShoppinglist() {
        return shoppinglist;
    }
}
