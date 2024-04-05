import { User } from "./authentication";

export interface Note{
  id: number;
  title: string;
  body: string;
  writer: User;
  writeIn: Room;

}

export interface Product{
  id: number;
  name: string;
  registeredBy: User;
  room: Room;
  categories: ProdcutCategory[];
  shoppingItems: ShoppingItem[];
  pricture: string;

}

export interface ProdcutCategory{
  id: number;
  name: string;
  products: Product[];
  registeredBy: User;
  room: Room;
}

export interface ShoppingItem{
  id: number;
  quantity: number;
  shoppingList: ShoppingItem;
  product: Product;
  value: number;
}

export interface ShoppingList{
  id: number;
  name: string;
  completed: boolean;
  items: ShoppingItem[];
  room: Room;
}

export interface Room{
  id: number;
  name: string;
  code: string;
  description: string;
  owner: User;
  belongTo: User;
  products: Product[];
  notes: Note[];
  shoppingLists: ShoppingList[];
  productCategories: ProdcutCategory[];
}
