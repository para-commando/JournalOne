package com.JournalOne.JournalOne.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TasksApiResponse{
    @JsonProperty("userId")
    private int userIdentifier;
    @JsonProperty("id")
    private int uniqueId;
    @JsonProperty("title")
    private String taskName;
    @JsonProperty("completed")
    private boolean taskStatus;
}

