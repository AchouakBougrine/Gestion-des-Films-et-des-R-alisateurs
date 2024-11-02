package com.example.controlee.controller;

import com.example.controlee.entities.Film;
import com.example.controlee.entities.Realisateur;
import com.example.controlee.service.FilmRealisateurService;
import com.example.controlee.service.RealisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/realisateurs")  // Définit le point de base "/realisateurs" pour toutes les requêtes de ce contrôleur
public class RealisateurController {

    // Injection des services pour gérer les réalisateurs et leurs relations avec les films
    @Autowired
    private RealisateurService realisateurService;

    @Autowired
    private FilmRealisateurService filmRealisateurService;

    @GetMapping  // Mapping pour afficher la liste de tous les réalisateurs
    public String listRealisateurs(Model model) {
        List<Realisateur> realisateurs = realisateurService.findAll();  // Récupère tous les réalisateurs

        // Associer chaque réalisateur à sa liste de films
        for (Realisateur realisateur : realisateurs) {
            realisateur.setFilms(filmRealisateurService.findFilmsByRealisateur(realisateur));
        }

        model.addAttribute("realisateurs", realisateurs);  // Ajoute la liste des réalisateurs au modèle pour affichage
        return "realisateur-list";  // Retourne la vue pour afficher la liste des réalisateurs
    }

    @GetMapping("/add")  // Mapping pour afficher le formulaire d'ajout de réalisateur
    public String showAddRealisateurForm(Realisateur realisateur) {
        return "add-realisateur";  // Retourne la vue pour le formulaire d'ajout de réalisateur
    }

    @PostMapping("/add")  // Mapping pour gérer la soumission du formulaire d'ajout de réalisateur
    public String addRealisateur(@ModelAttribute Realisateur realisateur) {
        realisateurService.save(realisateur);  // Sauvegarde le nouveau réalisateur
        return "redirect:/realisateurs";  // Redirige vers la liste des réalisateurs après ajout
    }

    @GetMapping("/edit/{id}")  // Mapping pour afficher le formulaire de modification d'un réalisateur existant
    public String showUpdateRealisateurForm(@PathVariable("id") long id, Model model) {
        Realisateur realisateur = realisateurService.findById(id)
                .orElseThrow(() -> new RuntimeException("Realisateur non trouvé"));  // Récupère le réalisateur à modifier
        model.addAttribute("realisateur", realisateur);  // Ajoute le réalisateur au modèle
        return "update-realisateur";  // Retourne la vue du formulaire de modification
    }

    @PostMapping("/update/{id}")  // Mapping pour gérer la soumission du formulaire de modification de réalisateur
    public String updateRealisateur(@PathVariable("id") long id, @ModelAttribute Realisateur realisateur) {
        realisateur.setId(id);  // Définit l'ID du réalisateur pour qu'il soit mis à jour
        realisateurService.save(realisateur);  // Sauvegarde les modifications du réalisateur
        return "redirect:/realisateurs";  // Redirige vers la liste des réalisateurs après modification
    }

    @GetMapping("/delete/{id}")  // Mapping pour gérer la suppression d'un réalisateur
    public String deleteRealisateur(@PathVariable("id") long id) {
        Realisateur realisateur = realisateurService.findById(id)
                .orElseThrow(() -> new RuntimeException("Realisateur non trouvé"));  // Récupère le réalisateur à supprimer
        filmRealisateurService.deleteByRealisateur(realisateur);  // Supprime les relations entre le réalisateur et ses films
        realisateurService.delete(id);  // Supprime le réalisateur
        return "redirect:/realisateurs";  // Redirige vers la liste des réalisateurs après suppression
    }

    @GetMapping("/{id}")  // Mapping pour afficher les détails d'un réalisateur spécifique
    public String getRealisateurDetails(@PathVariable Long id, Model model) {
        Realisateur realisateur = realisateurService.findById(id)
                .orElseThrow(() -> new RuntimeException("Realisateur non trouvé"));  // Récupère le réalisateur demandé

        // Charger la liste des films associés au réalisateur
        List<Film> films = filmRealisateurService.findFilmsByRealisateur(realisateur);

        model.addAttribute("realisateur", realisateur);  // Ajoute le réalisateur au modèle
        model.addAttribute("films", films);  // Ajoute la liste des films au modèle
        return "realisateur-detail";  // Retourne la vue des détails du réalisateur
    }
    @GetMapping("/search")
    public String searchRealisateurByName(@RequestParam("nom") String nom, Model model) {
        Optional<Realisateur> realisateur = realisateurService.findByName(nom);
        if (realisateur.isPresent()) {
            model.addAttribute("realisateurs", List.of(realisateur.get())); // Convertit en liste pour le modèle
        } else {
            model.addAttribute("realisateurs", List.of()); // Liste vide si aucun réalisateur n'est trouvé
        }
        return "realisateur-list"; // Vue affichant les résultats
    }


}
