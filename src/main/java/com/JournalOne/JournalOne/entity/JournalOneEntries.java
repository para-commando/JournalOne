package com.JournalOne.JournalOne.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
@Document(collection = "JournalCollections")
public class JournalOneEntries {
    // let's say in the payload value for id was passed then mongodb wont create a unique id and use it as a value for the below field else it will create its own id. so lets say we again hit the create api with same id value as before, then in that case the values provided will be updated as id value remained same
    @Id
    private String id;
    private String title;

    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
