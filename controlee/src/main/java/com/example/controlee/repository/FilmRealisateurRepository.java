package com.example.controlee.repository;

import com.example.controlee.entities.FilmRealisateur;
import com.example.controlee.entities.Realisateur;
import com.example.controlee.entities.Film;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface FilmRealisateurRepository extends CrudRepository<FilmRealisateur, Long> {

    // Méthode pour trouver tous les FilmRealisateur par réalisateur
    List<FilmRealisateur> findByRealisateur(Realisateur realisateur);

    // Méthode pour trouver tous les FilmRealisateur par film
    List<FilmRealisateur> findByFilm(Film film);

    // Méthode pour supprimer tous les FilmRealisateur par réalisateur
    void deleteByRealisateur(Realisateur realisateur);

    // Méthode pour supprimer tous les FilmRealisateur par film
    void deleteByFilm(Film film);
}
