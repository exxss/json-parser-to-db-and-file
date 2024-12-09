package com.mycompany.interviewtask.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Status {

    @JsonProperty("gold")
    GOLD(100),
    @JsonProperty("platinum")
    PLATINUM(1000),
    @JsonProperty("silver")
    SILVER(10);

    private final Integer statusAsNumb;

    Status(Integer statusAsNumb) {
        this.statusAsNumb = statusAsNumb;
    }
}
