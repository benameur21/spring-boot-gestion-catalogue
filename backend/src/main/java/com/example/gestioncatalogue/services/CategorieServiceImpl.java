package com.example.gestioncatalogue.services;

import com.example.gestioncatalogue.entities.Categorie;
import com.example.gestioncatalogue.repository.CategorieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategorieServiceImpl implements ICategorieService {

    private final CategorieRepository categorieRepository;

    @Override
    public Categorie saveCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    public Categorie getCategorieById(Long id) {
        return categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Categorie non trouvee avec l'id : " + id));
    }

    @Override
    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    @Override
    public Categorie updateCategorie(Long id, Categorie categorie) {
        Categorie existing = getCategorieById(id);

        existing.setNom(categorie.getNom());
        existing.setDescription(categorie.getDescription());

        return categorieRepository.save(existing);
    }

    @Override
    public void deleteCategorie(Long id) {
        categorieRepository.deleteById(id);
    }
}
