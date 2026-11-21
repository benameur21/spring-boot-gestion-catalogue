
package com.example.gestioncatalogue.controller;

import com.example.gestioncatalogue.entities.Categorie;
import com.example.gestioncatalogue.services.ICategorieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategorieController {

    private final ICategorieService categorieService;

    // GET: List all categories
    @GetMapping
    public List<Categorie> getAllCategories() {
        return categorieService.getAllCategories();
    }

    // GET: Get a category by ID
    @GetMapping("/{id}")
    public Categorie getCategorieById(@PathVariable Long id) {
        return categorieService.getCategorieById(id);
    }

    // POST: Create a category
    @PostMapping
    public Categorie saveCategorie(@RequestBody Categorie categorie) {
        return categorieService.saveCategorie(categorie);
    }

    // PUT: Update a category
    @PutMapping("/{id}")
    public Categorie updateCategorie(
            @PathVariable Long id,
            @RequestBody Categorie categorie) {
        return categorieService.updateCategorie(id, categorie);
    }

    // DELETE: Delete a category
    @DeleteMapping("/{id}")
    public void deleteCategorie(@PathVariable Long id) {
        categorieService.deleteCategorie(id);
    }
}