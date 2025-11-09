package com.forge.Hiyori.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forge.Hiyori.entities.Blog;

public interface BlogRepository extends JpaRepository<Blog,Long> {

}
