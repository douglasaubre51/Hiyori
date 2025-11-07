package com.forge.Hiyori.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forge.Hiyori.entities.Picture;

public interface PictureRepository extends JpaRepository<Picture,Long> {

}
