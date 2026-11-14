
package com.example.gestioncatalogue.repository;

import com.example.gestioncatalogue.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

    // ================================================================
    // 1. DERIVED QUERIES (Queries derived from method names)
    // ================================================================

    // Search products by name containing a keyword
    List<Produit> findByNomContaining(String keyword);

    // Search products by category ID
    List<Produit> findByCategorieId(Long categorieId);

    // Search products by category name
    List<Produit> findByCategorieNom(String nomCategorie);

    // Get the three most expensive products
    List<Produit> findTop3ByOrderByPrixDesc();


    // ================================================================
    // 2. JPQL QUERIES (Java Persistence Query Language)
    // ================================================================

    // Search products by keyword in name or description
    @Query("SELECT p FROM Produit p WHERE p.nom LIKE %:motCle% OR p.description LIKE %:motCle%")
    List<Produit> rechercherParMotCle(@Param("motCle") String motCle);

    // Search products by category ID
    @Query("SELECT p FROM Produit p WHERE p.categorie.id = :idCat")
    List<Produit> getProduitsByCatId(@Param("idCat") Long idCat);

}
