package com.forge.Hiyori.dtos;

import java.util.List;

import lombok.Data;

@Data
public class GameDto {

    private String title;

    private String author;

    private String details;

    private String thumbnail;

    private List<String> tags;

    private String sourceURI;
}
