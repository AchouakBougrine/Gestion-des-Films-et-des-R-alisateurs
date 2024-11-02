package com.example.controlee.repository;

import com.example.controlee.entities.Realisateur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RealisateurRepository extends CrudRepository<Realisateur, Long> {
    // Recherche un réalisateur par nom
    Optional<Realisateur> findByNom(String nom);
}
