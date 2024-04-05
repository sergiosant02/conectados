export interface UserDTO {
  id: number;
  name: string;
  lastName: string;
  picture: string;
  email: string;
  password: string;
  role: string;
  enabled: boolean;
  roomInIds: number[];
  roomOwnerIds: number[];
  noteIds: number[];
  productIds: number[];
  productCategoryIds: number[];
}

export interface ShoppingListDTO {
  id: number;
  name: string;
  completed: boolean;
  roomId: number;
  itemIds: number[];
}

export interface ShoppingItemDTO {
  id: number;
  name: string;
  quantity: number;
  shoppingListId: number;
  productId: number;
  value: number;
}

export interface RoomDTO {
  id: number;
  name: string;
  code: string;
  description: string;
  ownerId: number;
  belongToUserIds: number[];
  productIds: number[];
  noteIds: number[];
  shoppingListIds: number[];
  productCategoryIds: number[];
}

export interface ProductDTO {
  id: number;
  name: string;
  userId: number;
  roomId: number;
  categoryIds: number[];
  shoppingItemIds: number[];
  picture: string;
}

export interface ProductCategoryDTO {
  id: number;
  name: string;
  productIds: number[];
  userId: number;
  roomId: number;
}

export interface NoteDTO {
  id: number;
  title: string;
  body: string;
  userId: number;
  roomId: number;
}
