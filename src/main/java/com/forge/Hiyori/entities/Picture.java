package com.forge.Hiyori.entities;


import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Picture {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    private String details;

    private LocalDate takenDate;
    @CreationTimestamp
    private LocalDate uploadDate;

    private List<String> tags;

    private String imageURI;
}
