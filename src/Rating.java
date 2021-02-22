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
    private Sko shoeId;
    private Customer customerId;

    public Rating () {}

    public Rating(int id, int score, String comment, Sko shoeId, Customer customerId) {
        this.id = id;
        this.score = score;
        this.comment = comment;
        this.shoeId = shoeId;
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

    public Sko getShoeId() {
        return shoeId;
    }

    public void setShoeId(Sko shoeId) {
        this.shoeId = shoeId;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }
}



