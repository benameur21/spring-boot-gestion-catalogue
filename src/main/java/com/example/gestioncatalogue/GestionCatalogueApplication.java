
package com.example.gestioncatalogue;

import com.example.gestioncatalogue.entities.*;
import com.example.gestioncatalogue.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GestionCatalogueApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionCatalogueApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(CategorieRepository catRepo,
							   ProduitRepository prodRepo) {

		return args -> {

			// Avoid inserting duplicate data on every restart
			if (catRepo.count() == 0) {

				// 1. Create categories
				Categorie info = catRepo.save(
						Categorie.builder()
								.nom("Informatique")
								.description("Materiel informatique")
								.build()
				);

				Categorie elec = catRepo.save(
						Categorie.builder()
								.nom("Electronique")
								.description("Appareils electroniques")
								.build()
				);

				Categorie bureau = catRepo.save(
						Categorie.builder()
								.nom("Bureau")
								.description("Fournitures de bureau")
								.build()
				);

				// 2. Create products
				prodRepo.save(Produit.builder()
						.nom("MacBook Pro 14")
						.prix(2499.99)
						.quantite(10)
						.description("Ordinateur portable Apple M3")
						.categorie(info)
						.build());

				prodRepo.save(Produit.builder()
						.nom("Dell XPS 15")
						.prix(1899.99)
						.quantite(15)
						.description("Ordinateur portable Dell")
						.categorie(info)
						.build());

				prodRepo.save(Produit.builder()
						.nom("Samsung Galaxy S24")
						.prix(999.99)
						.quantite(25)
						.description("Smartphone Samsung")
						.categorie(elec)
						.build());

				prodRepo.save(Produit.builder()
						.nom("AirPods Pro")
						.prix(279.99)
						.quantite(50)
						.description("Ecouteurs sans fil Apple")
						.categorie(elec)
						.build());

				prodRepo.save(Produit.builder()
						.nom("Chaise ergonomique")
						.prix(349.99)
						.quantite(20)
						.description("Chaise de bureau ergonomique")
						.categorie(bureau)
						.build());

				System.out.println("=== Initial data loaded ===");

			} else {
				System.out.println("=== Initial data already exists ===");
			}

			System.out.println("Categories: " + catRepo.count());
			System.out.println("Products: " + prodRepo.count());
		};
	}
}
