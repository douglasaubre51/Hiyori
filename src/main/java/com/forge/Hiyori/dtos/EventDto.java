package com.forge.Hiyori.dtos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.Data;

@Data
public class EventDto {

    private long id;

    private String title;

    private String author;

    private String shortDesc;

    private String details;

    private LocalDate eventDate;
    private LocalTime eventTime;

    private String thumbnail;

    private List<String> tags;

}
