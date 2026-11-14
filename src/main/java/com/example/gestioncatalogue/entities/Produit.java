
package com.example.gestioncatalogue.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "produits")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private double prix;

    private int quantite;

    private String description;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;
}
