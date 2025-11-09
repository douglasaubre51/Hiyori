package com.forge.Hiyori.dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class PictureDto {

    private String title;

    private String details;

    private LocalDate takenDate;

    private List<String> tags;

    private String imageURI;
}
