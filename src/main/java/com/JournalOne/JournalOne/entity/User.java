package com.JournalOne.JournalOne.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Document(collection = "Users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private ObjectId id;
    // need to set a property in application.properties file for this to be active
    @Indexed(unique = true)
    // lombok annotations
    @NonNull
    private String userName;
    @NonNull
    private String password;
    private String email;
    private boolean sentimentAnalysis;
    // creates many-to-one relationship between Users collection and JournalCollections collection
    @DBRef
    private List<JournalOneEntries> journalOneEntriesList = new ArrayList<>();
    private List<String> roles;

}
