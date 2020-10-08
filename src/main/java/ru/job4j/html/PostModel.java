package ru.job4j.html;

import java.util.Date;

public class PostModel {
    private int id;
    private String thema;
    private String author;
    private int answerQuantity;
    private int viewingQuantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThema() {
        return thema;
    }

    public void setThema(String thema) {
        this.thema = thema;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAnswerQuantity() {
        return answerQuantity;
    }

    public void setAnswerQuantity(int answerQuantity) {
        this.answerQuantity = answerQuantity;
    }

    public int getViewingQuantity() {
        return viewingQuantity;
    }

    public void setViewingQuantity(int viewingQuantity) {
        this.viewingQuantity = viewingQuantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private Date date;
}
