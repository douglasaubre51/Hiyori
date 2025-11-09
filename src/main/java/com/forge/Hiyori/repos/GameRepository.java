package com.forge.Hiyori.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forge.Hiyori.entities.Game;

public interface GameRepository extends JpaRepository<Game,Long> {

}
