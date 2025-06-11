package com.allwyn.tool.data.responses;

import com.allwyn.tool.data.HasId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book implements HasId {

    @JsonProperty("id")
    private int id;

    @JsonProperty("title")
    private String title;
}
