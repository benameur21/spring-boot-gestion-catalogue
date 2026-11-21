
import { Routes } from '@angular/router';

import { ProductListComponent } from './components/product-list/product-list.component';
import { ProductFormComponent } from './components/product-form/product-form.component';
import { ChatComponent } from './components/chat/chat.component';

export const routes: Routes = [

  {
    path: 'products',
    component: ProductListComponent
  },

  {
    path: 'products/new',
    component: ProductFormComponent
  },

  {
    path: 'products/edit/:id',
    component: ProductFormComponent
  },

  {
    path: 'chat',
    component: ChatComponent
  },

  {
    path: '',
    redirectTo: 'products',
    pathMatch: 'full'
  }

];
