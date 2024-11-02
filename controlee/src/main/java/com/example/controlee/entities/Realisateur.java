package com.example.controlee.entities;

import jakarta.persistence.*; // Importation des annotations JPA pour les entités
import java.util.List;

@Entity // Indique que cette classe est une entité JPA qui sera persistée dans la base de données
public class Realisateur {

    @Id // Indique que ce champ est la clé primaire de l'entité
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Génère automatiquement un ID unique pour chaque enregistrement
    private Long id;

    private String nom; // Champ pour le nom du réalisateur

    @Column(length = 500) // Définit une limite de longueur de 500 caractères pour le champ biographie
    private String biographie; // Champ pour la biographie du réalisateur

    @Transient // Indique que cette liste n'est pas persistée dans la base de données
    private List<com.example.controlee.entities.Film> films; // Liste des films associés au réalisateur, remplie dans le service

    // Getters et Setters pour accéder et modifier les champs privés

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getBiographie() {
        return biographie;
    }

    public void setBiographie(String biographie) {
        this.biographie = biographie;
    }

    public List<com.example.controlee.entities.Film> getFilms() {
        return films;
    }

    public void setFilms(List<com.example.controlee.entities.Film> films) {
        this.films = films;
    }
}
