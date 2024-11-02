package com.example.controlee.service;

import com.example.controlee.entities.Realisateur;
import com.example.controlee.repository.RealisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service  // Indique que cette classe est un service Spring, géré par le conteneur
public class RealisateurService {

    @Autowired
    private RealisateurRepository realisateurRepository;  // Dépendance pour interagir avec la base de données des réalisateurs

    @Autowired
    private FilmRealisateurService filmRealisateurService;  // Dépendance pour gérer les relations entre films et réalisateurs

    // Méthode pour sauvegarder (créer ou mettre à jour) un réalisateur
    public Realisateur save(Realisateur realisateur) {
        return realisateurRepository.save(realisateur);  // Utilise le repository pour sauvegarder le réalisateur
    }

    // Méthode pour récupérer la liste de tous les réalisateurs
    public List<Realisateur> findAll() {
        return (List<Realisateur>) realisateurRepository.findAll();  // Utilise le repository pour obtenir tous les réalisateurs
    }

    // Méthode pour récupérer un réalisateur par son ID
    public Optional<Realisateur> findById(Long id) {
        return realisateurRepository.findById(id);  // Retourne un Optional contenant le réalisateur ou vide si non trouvé
    }

    // Méthode pour supprimer un réalisateur par son ID
    public void delete(Long id) {
        Optional<Realisateur> realisateur = realisateurRepository.findById(id);  // Cherche le réalisateur par ID
        realisateur.ifPresent(r -> {
            // Supprime d'abord les associations du réalisateur avec les films
            filmRealisateurService.deleteByRealisateur(r);
            realisateurRepository.delete(r);  // Supprime ensuite le réalisateur de la base de données
        });
    }

    // Méthode pour rechercher un réalisateur par nom
    public Optional<Realisateur> findByName(String nom) {
        return realisateurRepository.findByNom(nom);  // Utilise le repository pour trouver un réalisateur par nom
    }
}
