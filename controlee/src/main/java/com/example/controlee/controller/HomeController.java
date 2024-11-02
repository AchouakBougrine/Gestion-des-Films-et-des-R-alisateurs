package com.example.controlee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  // Indique que cette classe est un contrôleur Spring MVC
public class HomeController {

    @GetMapping("/")  // Associe la racine du site ("/") à cette méthode
    public String home() {
        // Retourne le nom de la vue "index" qui sera rendu
        return "index";
    }
}
