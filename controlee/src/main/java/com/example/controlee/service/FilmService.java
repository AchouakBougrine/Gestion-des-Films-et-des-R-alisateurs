package com.example.controlee.service;

import com.example.controlee.entities.Film;
import com.example.controlee.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service  // Indique que cette classe est un service Spring, géré par le conteneur
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;  // Dépendance pour interagir avec la base de données des films

    @Autowired
    private FilmRealisateurService filmRealisateurService;  // Dépendance pour gérer les relations entre films et réalisateurs

    // Méthode pour sauvegarder (créer ou mettre à jour) un film
    public Film save(Film film) {
        return filmRepository.save(film);  // Utilise le repository pour sauvegarder le film
    }

    // Méthode pour récupérer la liste de tous les films
    public List<Film> findAll() {
        return filmRepository.findAll();  // Utilise le repository pour obtenir tous les films
    }

    // Méthode pour récupérer un film par son ID
    public Optional<Film> findById(Long id) {
        return filmRepository.findById(id);  // Retourne un Optional contenant le film ou vide si non trouvé
    }

    // Méthode pour supprimer un film par son ID
    public void delete(Long id) {
        Optional<Film> film = filmRepository.findById(id);  // Cherche le film par ID
        film.ifPresent(f -> {
            // Supprime d'abord les associations du film avec les réalisateurs
            filmRealisateurService.deleteByFilm(f);
            filmRepository.delete(f);  // Supprime ensuite le film de la base de données
        });
    }

}
