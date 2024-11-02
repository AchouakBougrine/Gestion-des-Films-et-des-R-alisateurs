package com.example.controlee.entities;

import jakarta.persistence.*; // Importation des annotations JPA pour la gestion des entités
import java.util.Objects;

@Entity // Indique que cette classe est une entité JPA qui sera persistée dans la base de données
public class FilmRealisateur {

    @Id // Indique que ce champ est la clé primaire de l'entité
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Génère automatiquement un ID unique pour chaque enregistrement
    private Long id;

    @ManyToOne // Relation plusieurs-à-un avec l'entité Film
    @JoinColumn(name = "film_id", nullable = false) // Définit la colonne de jointure pour la relation avec Film
    private com.example.controlee.entities.Film film; // Référence au film associé

    @ManyToOne // Relation plusieurs-à-un avec l'entité Realisateur
    @JoinColumn(name = "realisateur_id", nullable = false) // Définit la colonne de jointure pour la relation avec Realisateur
    private com.example.controlee.entities.Realisateur realisateur; // Référence au réalisateur associé

    // Constructeur par défaut requis par JPA
    public FilmRealisateur() {
    }

    // Constructeur avec paramètres pour faciliter la création de l'entité
    public FilmRealisateur(com.example.controlee.entities.Film film, com.example.controlee.entities.Realisateur realisateur) {
        this.film = film;
        this.realisateur = realisateur;
    }

    // Getters et Setters pour accéder et modifier les champs privés

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public com.example.controlee.entities.Film getFilm() {
        return film;
    }

    public void setFilm(com.example.controlee.entities.Film film) {
        this.film = film;
    }

    public com.example.controlee.entities.Realisateur getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(com.example.controlee.entities.Realisateur realisateur) {
        this.realisateur = realisateur;
    }

    // Méthode equals pour comparer les objets FilmRealisateur basés sur leur ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmRealisateur that = (FilmRealisateur) o;
        return Objects.equals(id, that.id);
    }

    // Méthode hashCode pour générer un code de hachage basé sur l'ID
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
