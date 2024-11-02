package com.example.controlee.entities;

import jakarta.persistence.*; // Importation des annotations JPA pour la gestion des entités et des relations
import java.util.List;

@Entity // Indique que cette classe est une entité JPA et sera persistée dans la base de données
public class Film {

    @Id // Indique que ce champ est la clé primaire de l'entité
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Génération automatique de l'ID unique pour chaque enregistrement
    private Long id;

    private String titre; // Champ pour le titre du film
    private String genre; // Champ pour le genre du film
    private int anneeSortie; // Champ pour l'année de sortie du film

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL) // Relation un-à-plusieurs avec l'entité FilmRealisateur
    private List<FilmRealisateur> filmRealisateurs; // Liste des réalisateurs associés au film

    // Propriété pour afficher les réalisateurs avec Thymeleaf
    @Transient // Indique que cette propriété n'est pas persistée dans la base de données
    private List<Realisateur> realisateurs;

    // Getters et Setters pour accéder aux propriétés privées

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAnneeSortie() {
        return anneeSortie;
    }

    public void setAnneeSortie(int anneeSortie) {
        this.anneeSortie = anneeSortie;
    }

    public List<FilmRealisateur> getFilmRealisateurs() {
        return filmRealisateurs;
    }

    public void setFilmRealisateurs(List<FilmRealisateur> filmRealisateurs) {
        this.filmRealisateurs = filmRealisateurs;
    }

    public List<Realisateur> getRealisateurs() {
        return realisateurs;
    }

    public void setRealisateurs(List<Realisateur> realisateurs) {
        this.realisateurs = realisateurs;
    }
}
