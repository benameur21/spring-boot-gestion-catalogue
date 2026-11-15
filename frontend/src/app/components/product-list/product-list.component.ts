import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Product } from '../../models/product';
import { ProductService } from '../../services/product.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule,RouterLink],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent implements OnInit {

  products: Product[] = [];
  errorMessage = '';

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  // Retrieve all products from the API
  loadProducts(): void {
    this.productService.getAllProducts().subscribe({
      next: (data) => {
        this.products = data;
        this.errorMessage = '';
      },
      error: (error) => {
        console.error('Error loading products:', error);
        this.errorMessage = 'Unable to load products.';
      }
    });
  }

  // Delete a product
  deleteProduct(id: number): void {
    if (confirm('Are you sure you want to delete this product?')) {
      this.productService.deleteProduct(id).subscribe({
        next: () => {
          this.loadProducts();
        },
        error: (error) => {
          console.error('Error deleting product:', error);
          this.errorMessage = 'Unable to delete product.';
        }
      });
    }
  }
}
