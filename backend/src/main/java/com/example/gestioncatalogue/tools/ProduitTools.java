
package com.example.gestioncatalogue.tools;

import com.example.gestioncatalogue.entities.Produit;
import com.example.gestioncatalogue.repository.ProduitRepository;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

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
}
