package com.example.controlee.repository;

import com.example.controlee.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {


}
