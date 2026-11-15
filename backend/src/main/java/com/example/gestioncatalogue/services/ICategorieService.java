package com.example.gestioncatalogue.services;

import com.example.gestioncatalogue.entities.Categorie;
import java.util.List;

public interface ICategorieService {

    Categorie saveCategorie(Categorie categorie);

    Categorie getCategorieById(Long id);

    List<Categorie> getAllCategories();

    Categorie updateCategorie(Long id, Categorie categorie);

    void deleteCategorie(Long id);
}
