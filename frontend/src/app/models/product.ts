
export interface Product {
  id?: number;
  nom: string;
  prix: number;
  quantite: number;
  description?: string;
  categorie?: {
    id: number;
    nom?: string;
  };
}
