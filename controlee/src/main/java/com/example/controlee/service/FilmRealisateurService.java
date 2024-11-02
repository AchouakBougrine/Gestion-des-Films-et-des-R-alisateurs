package com.example.controlee.service;

import com.example.controlee.entities.Film; // Changer le package selon votre structure
import com.example.controlee.entities.Realisateur;
import com.example.controlee.entities.FilmRealisateur; // Changer le package selon votre structure
import com.example.controlee.repository.FilmRealisateurRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service  // Indique que cette classe est un service Spring géré par le conteneur
public class FilmRealisateurService {

    @Autowired
    private FilmRealisateurRepository filmRealisateurRepository;  // Injection du dépôt pour gérer les opérations de FilmRealisateur

    // Méthode pour sauvegarder une association entre un film et un réalisateur
    public void save(FilmRealisateur filmRealisateur) {
        filmRealisateurRepository.save(filmRealisateur);  // Sauvegarde l'entité dans la base de données
    }

    // Méthode pour récupérer tous les films associés à un réalisateur spécifique
    public List<Film> findFilmsByRealisateur(Realisateur realisateur) {
        // Récupère toutes les associations FilmRealisateur pour le réalisateur, puis extrait les films associés
        return filmRealisateurRepository.findByRealisateur(realisateur).stream()
                .map(FilmRealisateur::getFilm)  // Utilise un stream pour extraire l'objet Film de chaque association
                .collect(Collectors.toList());  // Convertit le stream en liste
    }

    // Méthode pour récupérer tous les réalisateurs associés à un film spécifique
    public List<Realisateur> findRealisateursByFilm(Film film) {
        // Récupère toutes les associations FilmRealisateur pour le film, puis extrait les réalisateurs associés
        return filmRealisateurRepository.findByFilm(film).stream()
                .map(FilmRealisateur::getRealisateur)  // Utilise un stream pour extraire l'objet Realisateur de chaque association
                .collect(Collectors.toList());  // Convertit le stream en liste
    }

    @Transactional  // Assure que toutes les opérations de suppression sont traitées dans une seule transaction
    public void deleteByFilm(Film film) {
        // Supprime toutes les associations FilmRealisateur liées au film donné
        filmRealisateurRepository.deleteByFilm(film);
    }

    @Transactional  // Assure que toutes les opérations de suppression sont traitées dans une seule transaction
    public void deleteByRealisateur(Realisateur realisateur) {
        // Supprime toutes les associations FilmRealisateur liées au réalisateur donné
        filmRealisateurRepository.deleteByRealisateur(realisateur);
    }
}
