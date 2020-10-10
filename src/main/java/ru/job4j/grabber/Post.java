package ru.job4j.grabber;

import java.util.Date;

public class Post {
    private int id;
    private String link;
    private String thema;
    private String announcementText;
    private String author;
    private Date date;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAnnouncementText() {
        return announcementText;
    }

    public void setAnnouncementText(String announcementText) {
        this.announcementText = announcementText;
    }

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


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
