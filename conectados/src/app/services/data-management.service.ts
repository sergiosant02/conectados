import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { constants } from '../constants.ts';
import {
  NoteDTO,
  ProductCategoryDTO,
  ProductDTO,
  RoomDTO,
  ShoppingItemDTO,
  ShoppingListDTO,
  UserDTO,
} from '../dtos/typesDtos';
import { RoomExtDTO } from '../dtos/typesExtDtos.js';
import { HttpResponse, JwtRequest, ResponseEntity } from '../models/responses';
import {
  ShoppingListDataExtDTO,
  ShoppingListExtDTO,
} from './../dtos/typesExtDtos';
import { PersistenceService } from './persistence.service';
import { RestService } from './rest.service';

@Injectable({
  providedIn: 'root',
})
export class DataManagementService {
  constructor(
    private rest: RestService,
    private persistenceService: PersistenceService
  ) {}

  async login(user: JwtRequest): Promise<ResponseEntity<UserDTO>> {
    return this.rest
      .login(user)
      .then(async (data) => {
        this.persistenceService.setValue(constants.USER, data.data);
        this.persistenceService.setToken(data.jwttoken);
        return data;
      })
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async register(user: UserDTO): Promise<ResponseEntity<UserDTO>> {
    return this.rest
      .register(user)
      .then(async (data) => {
        this.persistenceService.setValue(constants.USER, data.data);
        this.persistenceService.setValue(constants.TOKEN, data.jwttoken);
        return data;
      })
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async logout(): Promise<void> {
    this.persistenceService.removeValue(constants.TOKEN);
    this.persistenceService.removeValue(constants.USER);
  }

  async getRoomsMember(): Promise<RoomDTO[]> {
    return this.rest
      .roomsMember()
      .then((data) => data)
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async getRoomsOwner(): Promise<RoomDTO[]> {
    return this.rest
      .roomsOwner()
      .then((data) => data)
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async getRoomsGuest(): Promise<RoomDTO[]> {
    return this.rest
      .roomsGuest()
      .then((data) => data)
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async createRoom(room: RoomDTO): Promise<HttpResponse<RoomDTO>> {
    return this.rest
      .createRoom(room)
      .then((data) => data)
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async joinToRoom(code: string): Promise<HttpResponse<RoomDTO>> {
    return this.rest
      .joinToRoom(code)
      .then((data) => data)
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async leaveRoom(id: number): Promise<HttpResponse<RoomDTO>> {
    return this.rest
      .leaveRoom(id)
      .then((data) => data)
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async getMembersOfRoom(roomId: number): Promise<UserDTO[]> {
    return this.rest
      .getMembersOfRoom(roomId)
      .then((data) => data)
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async deleteRoom(id: number): Promise<HttpResponse<RoomDTO>> {
    return this.rest
      .deleteRoom(id)
      .then((data) => data)
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async getRoom(id: number): Promise<HttpResponse<RoomExtDTO>> {
    return this.rest
      .getRoom(id)
      .then((data) => data)
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async createCategory(
    category: ProductCategoryDTO
  ): Promise<HttpResponse<ProductCategoryDTO>> {
    return this.rest
      .createCategoryIntoRoom(category)
      .then((data) => data)
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async deleteCategory(id: number): Promise<HttpResponse<ProductCategoryDTO>> {
    return this.rest
      .deleteCategory(id)
      .then((data) => data)
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async createProduct(product: ProductDTO): Promise<HttpResponse<ProductDTO>> {
    return this.rest
      .createProductIntoRoom(product)
      .then((data) => data)
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async deleteProduct(id: number): Promise<HttpResponse<ProductDTO>> {
    return this.rest
      .deleteProductIntoRoom(id)
      .then((data) => data)
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async fireMember(room: RoomDTO): Promise<HttpResponse<RoomDTO>> {
    return this.rest
      .fireMember(room)
      .then((data) => data)
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async createNote(noteDTO: NoteDTO): Promise<HttpResponse<NoteDTO>> {
    return this.rest
      .createNote(noteDTO)
      .then((data) => data)
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async getNotesOfRoom(roomId: number): Promise<HttpResponse<NoteDTO[]>> {
    return this.rest
      .getNotesRoom(roomId)
      .then((data) => data)
      .catch((err) => {
        throw err;
      });
  }

  async deleteNote(noteId: number): Promise<HttpResponse<NoteDTO>> {
    return this.rest
      .deleteNote(noteId)
      .then((data) => data)
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async getShoppingListOfRoom(
    roomId: number
  ): Promise<HttpResponse<ShoppingListDataExtDTO[]>> {
    return this.rest
      .getShoppingListOfRoom(roomId)
      .then((data) => data)
      .catch((err) => {
        throw err;
      });
  }

  async createShoppingList(
    shoppingList: ShoppingListDTO
  ): Promise<HttpResponse<ShoppingListExtDTO>> {
    return this.rest
      .createShoppingList(shoppingList)
      .then((data) => data)
      .catch((err) => {
        throw err;
      });
  }

  async deleteShoppingList(
    shoppingList: ShoppingListDTO
  ): Promise<HttpResponse<ShoppingListDTO>> {
    return this.rest
      .deleteShoppingList(shoppingList)
      .then((data) => data)
      .catch((err) => {
        throw err;
      });
  }

  async changeStatusShoppingList(
    shoppingList: ShoppingListDTO
  ): Promise<HttpResponse<ShoppingListDTO>> {
    return this.rest
      .changeStatusShoppingList(shoppingList)
      .then((data) => data)
      .catch((err) => {
        throw err;
      });
  }

  async deleteShoppingItem(
    item: ShoppingItemDTO
  ): Promise<HttpResponse<RoomDTO>> {
    return this.rest
      .deleteShoppingItem(item)
      .then((data) => data)
      .catch((err) => {
        throw err;
      });
  }

  async createShoppingItem(
    shoppingItem: ShoppingItemDTO
  ): Promise<HttpResponse<RoomDTO>> {
    return this.rest
      .createShoppingItem(shoppingItem)
      .then((data) => data)
      .catch((err) => {
        throw err;
      });
  }
}
