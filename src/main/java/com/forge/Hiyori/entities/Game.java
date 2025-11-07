package com.forge.Hiyori.entities;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    private String author;

    private String details;

    @CreationTimestamp
    private LocalDate createdDate;
    @CreationTimestamp
    private LocalTime createdTime;

    private String thumbnail;

    private List<String> tags;

    private String sourceURI;

}
