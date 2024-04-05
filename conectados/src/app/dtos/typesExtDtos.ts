import { ProductCategoryDTO, ProductDTO, RoomDTO, ShoppingItemDTO, ShoppingListDTO } from "./typesDtos";

export interface ProductExtDTO {
  products: ProductDTO[];
  categories: ProductCategoryDTO[];
}

export interface RoomExtDTO {
  productCategoryDTOs: ProductCategoryDTO[];
  productDTOs: ProductDTO[];
  roomDTO: RoomDTO;
}

export interface ShoppingListExtDTO {
  shoppingListDTO: ShoppingListDTO;
  shoppingItemDTOs: ShoppingItemDTO[];
}
