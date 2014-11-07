package com.prikhodko.yasha.assignment2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yasha on 14-11-07.
 */
public class Note {

    private String updatedDate;
    private String updatedTime;
    private String noteText;
    private long Id;

    public Note() {}

    public Note(String text) {
        this.noteText = text;
    }

    public void setText(String text) {
        this.noteText = text;
    }

    public String getText() {
        return noteText;
    }

    public String getTime() {
        return updatedTime;
    }

    public String getDate() {
        return updatedDate;
    }

    public void setTime(String time) { this.updatedTime = time; }

    public void setDate(String date) {
        this.updatedDate = date;
    }

    public void updateTime() {
        DateFormat date = new SimpleDateFormat("MMM dd, yyyy");
        DateFormat time = new SimpleDateFormat("HH:mm");
        this.updatedDate = date.format(new Date());
        this.updatedTime = time.format(new Date());
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        this.Id = id;
    }
}
