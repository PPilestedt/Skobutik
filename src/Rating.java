/**
 * Created by: Ulf Nyberg
 * Date: 2021-02-22
 * Time: 10:55
 * Project: Skobutik
 * Copyright: MIT
 */
public class Rating {

    private int score;
    private String comment;
    private Customer customer;

    public Rating(int score, String comment, Customer customer) {
        this.score = score;
        this.comment = comment;
        this.customer = customer;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "score= " + score +
                ", comment='" + comment + '\'' +
                ", customer=" + customer +
                '}';
    }
}



