package com.example.mydreams.model;

import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;

public class Dreams implements Serializable {

    @DocumentId
    private String id;
    private String title;
    private int evaluation;
    private String content;
    private String tag;
    private long date;

    public Dreams() {}

    public Dreams(String title, int evaluation, String content, String tag, long date) {
        this.title = title;
        this.evaluation = evaluation;
        this.content = content;
        this.tag = tag;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
