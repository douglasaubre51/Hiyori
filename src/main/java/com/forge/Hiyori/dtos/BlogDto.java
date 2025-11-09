package com.forge.Hiyori.dtos;

import java.util.List;

import lombok.Data;

@Data
public class BlogDto {

    private String title;

    private String author;

    private String thumbnail;

    private List<String> tags;

    private String content;
}
