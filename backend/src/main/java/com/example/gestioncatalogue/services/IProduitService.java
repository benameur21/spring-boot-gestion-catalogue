package com.example.gestioncatalogue.services;

import com.example.gestioncatalogue.entities.Produit;
import java.util.List;

public interface IProduitService {

    Produit saveProduit(Produit produit);

    Produit getProduitById(Long id);

    List<Produit> getAllProduits();

    Produit updateProduit(Long id, Produit produit);

    void deleteProduit(Long id);

    List<Produit> searchByNom(String keyword);

    List<Produit> getByCategorie(Long categorieId);
}
