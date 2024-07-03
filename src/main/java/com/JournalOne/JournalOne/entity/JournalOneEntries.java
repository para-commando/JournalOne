package com.JournalOne.JournalOne.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.StringOperators;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "JournalCollections")
@Data
public class JournalOneEntries {
    // let's say in the payload value for id was passed then mongodb wont create a unique id and use it as a value for the below field else it will create its own id. so lets say we again hit the create api with same id value as before, then in that case the values provided will be updated as id value remained same
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;


}
