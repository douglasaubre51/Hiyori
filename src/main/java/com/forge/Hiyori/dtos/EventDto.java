package com.forge.Hiyori.dtos;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import lombok.Data;

@Data
public class EventDto {

    private String title;

    private String author;

    private String shortDesc;

    private String details;

    private Date eventDate;
    private Time eventTime;

    private String thumbnail;

    private List<String> tags;

}
