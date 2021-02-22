/**
 * Created by: Ulf Nyberg
 * Date: 2021-02-22
 * Time: 10:55
 * Project: Skobutik
 * Copyright: MIT
 */
public class Rating {

    private int id;
    private int score;
    private String comment;
    private Shoe shoe;
    private Customer customerId;

    public Rating () {}

    public Rating(int id, int score, String comment, Shoe shoe, Customer customerId) {
        this.id = id;
        this.score = score;
        this.comment = comment;
        this.shoe = shoe;
        this.customerId = customerId;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public void setShoe(Shoe shoe) {
        this.shoe = shoe;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }
}



