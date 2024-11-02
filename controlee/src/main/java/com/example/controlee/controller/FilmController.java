package com.example.controlee.controller;

import com.example.controlee.entities.Film;
import com.example.controlee.entities.FilmRealisateur;
import com.example.controlee.entities.Realisateur;
import com.example.controlee.service.FilmRealisateurService;
import com.example.controlee.service.FilmService;
import com.example.controlee.service.RealisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/films")  // Définit le point de base "/films" pour toutes les requêtes de ce contrôleur
public class FilmController {

    // Injection des services nécessaires pour la gestion des films, réalisateurs et relations entre eux
    @Autowired
    private FilmService filmService;

    @Autowired
    private RealisateurService realisateurService;

    @Autowired
    private FilmRealisateurService filmRealisateurService;

    @GetMapping  // Mapping pour l'affichage de la liste de tous les films
    public String listFilms(Model model) {
        List<Film> films = filmService.findAll();  // Récupération de tous les films

        // Associer chaque film à ses réalisateurs respectifs
        for (Film film : films) {
            List<Realisateur> realisateurs = filmRealisateurService.findRealisateursByFilm(film);
            film.setRealisateurs(realisateurs);  // Mise à jour de la liste des réalisateurs pour chaque film
        }

        model.addAttribute("films", films);  // Ajout de la liste de films au modèle pour affichage
        return "film-list";  // Retourne la vue pour afficher la liste des films
    }

    @GetMapping("/add")  // Mapping pour afficher le formulaire d'ajout de film
    public String showAddFilmForm(Film film, Model model) {
        model.addAttribute("realisateurs", realisateurService.findAll());  // Ajoute la liste de réalisateurs au modèle
        return "add-film";  // Retourne la vue du formulaire d'ajout de film
    }

    @PostMapping("/add")  // Mapping pour gérer la soumission du formulaire d'ajout de film
    public String addFilm(@Valid Film film, BindingResult result,
                          @RequestParam List<String> realisateurNoms, // Liste des noms des réalisateurs sélectionnés
                          Model model) {
        if (result.hasErrors()) {  // Vérifie si le formulaire contient des erreurs
            model.addAttribute("realisateurs", realisateurService.findAll());
            return "add-film";  // Retourne le formulaire avec les erreurs
        }

        Film savedFilm = filmService.save(film);  // Sauvegarde le nouveau film

        // Associer les réalisateurs existants ou créer de nouveaux réalisateurs selon les noms fournis
        for (String realisateurNom : realisateurNoms) {
            // Rechercher ou créer un réalisateur
            Realisateur realisateur = realisateurService.findByName(realisateurNom)
                    .orElseGet(() -> {
                        Realisateur newRealisateur = new Realisateur();
                        newRealisateur.setNom(realisateurNom);
                        return realisateurService.save(newRealisateur);  // Enregistre un réalisateur si nouveau
                    });
            FilmRealisateur filmRealisateur = new FilmRealisateur(savedFilm, realisateur);
            filmRealisateurService.save(filmRealisateur);  // Sauvegarde l'association entre le film et le réalisateur
        }

        return "redirect:/films";  // Redirige vers la liste des films après ajout
    }

    @GetMapping("/edit/{id}")  // Mapping pour afficher le formulaire de modification d'un film existant
    public String showUpdateFilmForm(@PathVariable("id") long id, Model model) {
        Film film = filmService.findById(id)
                .orElseThrow(() -> new RuntimeException("Film non trouvé"));  // Récupère le film à modifier
        model.addAttribute("film", film);
        model.addAttribute("realisateurs", realisateurService.findAll());  // Ajoute la liste des réalisateurs au modèle
        return "update-film";  // Retourne la vue du formulaire de modification
    }

    @PostMapping("/update/{id}")  // Mapping pour gérer la soumission du formulaire de modification de film
    public String updateFilm(@PathVariable("id") long id, @Valid Film film, BindingResult result,
                             @RequestParam(required = false) List<Long> realisateurIds, // IDs des réalisateurs existants
                             @RequestParam(required = false) List<String> realisateurNoms, // Noms des réalisateurs
                             Model model) {
        if (result.hasErrors()) {  // Vérifie si le formulaire contient des erreurs
            model.addAttribute("realisateurs", realisateurService.findAll());
            return "update-film";  // Retourne le formulaire avec les erreurs
        }

        Film updatedFilm = filmService.save(film);  // Sauvegarde les modifications du film

        // Met à jour les réalisateurs si des informations existantes sont fournies
        if (realisateurIds != null && realisateurNoms != null) {
            for (int i = 0; i < realisateurIds.size(); i++) {
                Long realisateurId = realisateurIds.get(i);
                String realisateurNom = realisateurNoms.get(i);
                Realisateur realisateur = realisateurService.findById(realisateurId)
                        .orElseThrow(() -> new RuntimeException("Realisateur non trouvé"));
                realisateur.setNom(realisateurNom);  // Met à jour le nom du réalisateur
                realisateurService.save(realisateur);  // Sauvegarde les modifications du réalisateur
            }
        }

        return "redirect:/films";  // Redirige vers la liste des films après modification
    }

    @GetMapping("/delete/{id}")  // Mapping pour gérer la suppression d'un film
    public String deleteFilm(@PathVariable("id") long id) {
        Film film = filmService.findById(id)
                .orElseThrow(() -> new RuntimeException("Film non trouvé"));  // Récupère le film à supprimer
        filmRealisateurService.deleteByFilm(film);  // Supprime les associations entre le film et ses réalisateurs
        filmService.delete(id);  // Supprime le film
        return "redirect:/films";  // Redirige vers la liste des films après suppression
    }
    @GetMapping("/details/{id}")
    public String getFilmDetails(@PathVariable("id") Long id, Model model) {
        // Récupérer le film par son ID
        Film film = filmService.findById(id)
                .orElseThrow(() -> new RuntimeException("Film non trouvé"));

        // Charger les réalisateurs associés au film
        List<Realisateur> realisateurs = filmRealisateurService.findRealisateursByFilm(film);

        // Ajouter le film et ses réalisateurs au modèle
        model.addAttribute("film", film);
        model.addAttribute("realisateurs", realisateurs);

        return "film-detail"; // Retourne la vue film-detail.html
    }

}
