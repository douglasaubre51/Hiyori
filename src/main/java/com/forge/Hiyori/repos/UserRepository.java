package com.forge.Hiyori.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forge.Hiyori.entities.User;

public interface UserRepository extends JpaRepository<User,Long> {

}
