
package com.example.gestioncatalogue.controller;

import com.example.gestioncatalogue.entities.Produit;
import com.example.gestioncatalogue.services.IProduitService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ProduitController {

    private final IProduitService produitService;

    // GET: Retrieve all products
    @GetMapping
    public List<Produit> getAll() {
        return produitService.getAllProduits();
    }

    // GET: Retrieve a product by ID
    @GetMapping("/{id}")
    public Produit getById(@PathVariable Long id) {
        return produitService.getProduitById(id);
    }

    // POST: Create a product
    @PostMapping
    public Produit create(@RequestBody Produit produit) {
        return produitService.saveProduit(produit);
    }

    // PUT: Update a product
    @PutMapping("/{id}")
    public Produit update(
            @PathVariable Long id,
            @RequestBody Produit produit) {
        return produitService.updateProduit(id, produit);
    }

    // DELETE: Delete a product
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        produitService.deleteProduit(id);
    }

    // GET: Search products by name
    @GetMapping("/search")
    public List<Produit> search(@RequestParam String keyword) {
        return produitService.searchByNom(keyword);
    }

    // GET: Retrieve products by category
    @GetMapping("/categorie/{catId}")
    public List<Produit> byCategorie(@PathVariable Long catId) {
        return produitService.getByCategorie(catId);
    }
}
