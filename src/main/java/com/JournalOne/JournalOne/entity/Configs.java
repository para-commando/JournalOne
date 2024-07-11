package com.JournalOne.JournalOne.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "configs")
@Data
@NoArgsConstructor
public class Configs {
    @Id
    private ObjectId id;
    // need to set a property in application.properties file for this to be active
    @Indexed(unique = true)
    // lombok annotations
    @NonNull
    private String key;
    @NonNull
    private String value;
}




