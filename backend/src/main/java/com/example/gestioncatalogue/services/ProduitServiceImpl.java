package com.example.gestioncatalogue.services;

import com.example.gestioncatalogue.entities.Produit;
import com.example.gestioncatalogue.repository.ProduitRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProduitServiceImpl implements IProduitService {

    private final ProduitRepository produitRepository;

    @Override
    public Produit saveProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    @Override
    public Produit getProduitById(Long id) {
        return produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Produit non trouve avec l'id : " + id));
    }

    @Override
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    @Override
    public Produit updateProduit(Long id, Produit produit) {
        Produit existing = getProduitById(id);

        existing.setNom(produit.getNom());
        existing.setPrix(produit.getPrix());
        existing.setQuantite(produit.getQuantite());
        existing.setDescription(produit.getDescription());
        existing.setCategorie(produit.getCategorie());

        return produitRepository.save(existing);
    }

    @Override
    public void deleteProduit(Long id) {
        produitRepository.deleteById(id);
    }

    @Override
    public List<Produit> searchByNom(String keyword) {
        return produitRepository.findByNomContaining(keyword);
    }

    @Override
    public List<Produit> getByCategorie(Long categorieId) {
        return produitRepository.findByCategorieId(categorieId);
    }
}
