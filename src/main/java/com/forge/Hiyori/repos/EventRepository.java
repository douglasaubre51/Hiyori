package com.forge.Hiyori.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forge.Hiyori.entities.Event;

public interface EventRepository extends JpaRepository<Event,Long> {

}
