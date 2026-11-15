
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { Product } from '../../models/product';
import { Category } from '../../models/category';
import { ProductService } from '../../services/product.service';
import { CategoryService } from '../../services/category.service';

@Component({
  selector: 'app-product-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-form.component.html',
  styleUrl: './product-form.component.css'
})
export class ProductFormComponent implements OnInit {

  product: Product = {
    nom: '',
    prix: 0,
    quantite: 0,
    description: ''
  };

  categories: Category[] = [];
  isEditMode = false;
  productId?: number;
  errorMessage = '';

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadCategories();

    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      this.isEditMode = true;
      this.productId = Number(id);
      this.loadProduct(this.productId);
    }
  }

  loadCategories(): void {
    this.categoryService.getAllCategories().subscribe({
      next: (data) => {
        this.categories = data;
      },
      error: (error) => {
        console.error('Error loading categories:', error);
        this.errorMessage = 'Unable to load categories.';
      }
    });
  }

  loadProduct(id: number): void {
    this.productService.getProductById(id).subscribe({
      next: (data) => {
        this.product = {
          ...data,
          categorie: data.categorie
        };
      },
      error: (error) => {
        console.error('Error loading product:', error);
        this.errorMessage = 'Unable to load product.';
      }
    });
  }

  onCategoryChange(categoryId: number): void {
    const selectedCategory = this.categories.find(
      category => category.id === Number(categoryId)
    );

    this.product.categorie = selectedCategory
      ? { id: selectedCategory.id!, nom: selectedCategory.nom }
      : undefined;
  }

  saveProduct(): void {
    if (!this.product.categorie?.id) {
      this.errorMessage = 'Please select a category.';
      return;
    }

    this.errorMessage = '';

    if (this.isEditMode && this.productId) {
      this.productService.updateProduct(this.productId, this.product).subscribe({
        next: () => {
          this.router.navigate(['/products']);
        },
        error: (error) => {
          console.error('Error updating product:', error);
          this.errorMessage = 'Unable to update product.';
        }
      });
    } else {
      this.productService.addProduct(this.product).subscribe({
        next: () => {
          this.router.navigate(['/products']);
        },
        error: (error) => {
          console.error('Error adding product:', error);
          this.errorMessage = 'Unable to add product.';
        }
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/products']);
  }
}
