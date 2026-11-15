package com.example.gestioncatalogue.tools;

import com.example.gestioncatalogue.entities.Produit;
import com.example.gestioncatalogue.repository.ProduitRepository;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;

import java.util.List;

@Component
public class ProduitTools {

    private final ProduitRepository produitRepository;

    // Injection directe du repository JPA du catalogue
    public ProduitTools(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @Tool(
            name = "getProducts",
            description = "Recupere la liste complete des produits directement depuis la base MySQL"
    )
    public List<Produit> getProducts() {
        return this.produitRepository.findAll();
    }

    @Tool(
            name = "getProduct",
            description = "Recupere les informations detaillees d'un produit par son identifiant unique"
    )
    public Produit getProduct(
            @ToolParam(description = "Identifiant numerique du produit", required = true)
            Long id
    ) {
        return this.produitRepository.findById(id).orElse(null);
    }

    @Tool(
            name = "searchProducts",
            description = "Recherche des produits dont le nom contient un mot-cle donne"
    )
    public List<Produit> searchProducts(
            @ToolParam(description = "Mot-cle de recherche", required = true)
            String keyword
    ) {
        return this.produitRepository.findByNomContaining(keyword);
    }

    @Tool(
            name = "getProductsByCategory",
            description = "Recupere les produits d'une categorie donnee par son ID"
    )
    public List<Produit> getProductsByCategory(
            @ToolParam(description = "Identifiant numerique de la categorie", required = true)
            Long catId
    ) {
        return this.produitRepository.findByCategorieId(catId);
    }



    @Tool(
            name = "getCatalogueStatistics",
            description = "Fournit des statistiques cles sur le catalogue : nombre total de produits, prix moyen, produit le plus cher et produit le moins cher"
    )
    public Map<String, Object> getCatalogueStatistics() {

        // 1. Recuperer tous les produits depuis MySQL
        List<Produit> produits = produitRepository.findAll();

        Map<String, Object> statistics = new HashMap<>();

        // 2. Verifier si le catalogue est vide
        if (produits.isEmpty()) {
            statistics.put("totalArticles", 0);
            statistics.put("prixMoyen", 0.0);
            statistics.put("produitPlusCher", "Aucun produit");
            statistics.put("produitMoinsCher", "Aucun produit");

            return statistics;
        }

        // 3. Calculer le nombre total de produits
        long totalArticles = produits.size();

        // 4. Calculer le prix moyen
        double prixMoyen = produits.stream()
                .mapToDouble(produit -> produit.getPrix())
                .average()
                .orElse(0.0);

        prixMoyen = Math.round(prixMoyen * 100.0) / 100.0;

        // 5. Trouver le produit le plus cher
        Produit produitPlusCher = produits.stream()
                .max(Comparator.comparingDouble(produit -> produit.getPrix()))
                .orElse(null);

        // 6. Trouver le produit le moins cher
        Produit produitMoinsCher = produits.stream()
                .min(Comparator.comparingDouble(produit -> produit.getPrix()))
                .orElse(null);

        // 7. Ajouter les resultats dans la Map
        statistics.put("totalArticles", totalArticles);
        statistics.put("prixMoyen", prixMoyen);

        statistics.put("produitPlusCher",
                produitPlusCher.getNom() + " - " + produitPlusCher.getPrix() + " TND");

        statistics.put("produitMoinsCher",
                produitMoinsCher.getNom() + " - " + produitMoinsCher.getPrix() + " TND");

        return statistics;
    }


}
