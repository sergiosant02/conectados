import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import {
  NoteDTO,
  ProductCategoryDTO,
  ProductDTO,
  RoomDTO,
  ShoppingItemDTO,
  ShoppingListDTO,
  UserDTO,
} from '../dtos/typesDtos';
import {
  RoomExtDTO,
  ShoppingListDataExtDTO,
  ShoppingListExtDTO,
} from '../dtos/typesExtDtos';
import { HttpResponse, JwtRequest, ResponseEntity } from '../models/responses';
import { WsAbstractService } from './ws-astract.service';

@Injectable({
  providedIn: 'root',
})
export class RestService extends WsAbstractService {
  serverUrl = environment.restUrl;
  authPath = `${this.serverUrl}${environment.authEndpoint}`;
  userPath = `${this.serverUrl}${environment.usersEndpoint}`;
  roomPath = `${this.serverUrl}${environment.roomsEndpoint}`;
  productPath = `${this.serverUrl}${environment.productsEnpoint}`;
  notePath = `${this.serverUrl}${environment.notesEndpoint}`;
  shoppingItemPath = `${this.serverUrl}${environment.shoppingItemEndpoint}`;
  shoppingListPath = `${this.serverUrl}${environment.shoppingListEndpoint}`;

  // Authenticate

  async login(user: JwtRequest): Promise<ResponseEntity<UserDTO>> {
    return await this.makePostRequest(`${this.authPath}/login`, user);
  }

  async register(user: UserDTO): Promise<ResponseEntity<UserDTO>> {
    return await this.makePostRequest(`${this.authPath}/register`, user);
  }

  // User

  async roomsOwner(): Promise<RoomDTO[]> {
    return await this.makePostRequest(`${this.userPath}/roomsOwner`);
  }

  async roomsGuest(): Promise<RoomDTO[]> {
    return await this.makePostRequest(`${this.userPath}/roomsGuest`);
  }

  async roomsMember(): Promise<RoomDTO[]> {
    return await this.makeGetRequest(`${this.userPath}/roomsMember`);
  }

  // Rooms

  async createRoom(room: RoomDTO): Promise<HttpResponse<RoomDTO>> {
    return await this.makePostRequest(`${this.roomPath}/create`, room);
  }

  async joinToRoom(code: string): Promise<HttpResponse<RoomDTO>> {
    return await this.makePostRequest(`${this.roomPath}/join`, { code: code });
  }

  async leaveRoom(id: number): Promise<HttpResponse<RoomDTO>> {
    return await this.makePostRequest(`${this.roomPath}/leave`, { id: id });
  }

  async deleteRoom(id: number): Promise<HttpResponse<RoomDTO>> {
    return await this.makePostRequest(`${this.roomPath}/delete`, { id: id });
  }

  async getMembersOfRoom(id: number): Promise<UserDTO[]> {
    return await this.makeGetRequest(`${this.roomPath}/listMembers/`, {
      id: id,
    });
  }

  async getRoom(id: number): Promise<HttpResponse<RoomExtDTO>> {
    return await this.makeGetRequest(`${this.roomPath}/allRoomInfo`, {
      id: id,
    });
  }

  async fireMember(roomDTO: RoomDTO): Promise<HttpResponse<RoomDTO>> {
    return await this.makePostRequest(`${this.roomPath}/fireMember`, {
      roomDTO: roomDTO,
    });
  }

  async listProductOfRoom(id: number): Promise<HttpResponse<RoomDTO>> {
    return await this.makeGetRequest(`${this.roomPath}/listProducts/${id}`);
  }

  async createCategoryIntoRoom(
    categoryDTO: ProductCategoryDTO
  ): Promise<HttpResponse<ProductCategoryDTO>> {
    return await this.makePostRequest(
      `${this.roomPath}/createCatgeory`,
      categoryDTO
    );
  }

  async deleteCategory(id: number): Promise<HttpResponse<ProductCategoryDTO>> {
    return await this.makePostRequest(`${this.roomPath}/deleteCategory`, {
      id: id,
    });
  }

  async createProductIntoRoom(
    productDTO: ProductDTO
  ): Promise<HttpResponse<ProductDTO>> {
    return await this.makePostRequest(`${this.productPath}/create`, productDTO);
  }

  async deleteProductIntoRoom(id: number): Promise<HttpResponse<ProductDTO>> {
    return await this.makePostRequest(`${this.productPath}/delete`, {
      id: id,
    });
  }

  // Notes

  async createNote(note: NoteDTO): Promise<HttpResponse<NoteDTO>> {
    return await this.makePostRequest(`${this.notePath}/create`, note);
  }

  async getNotesRoom(roomId: number): Promise<HttpResponse<NoteDTO[]>> {
    return await this.makeGetRequest(`${this.notePath}/notes`, {
      id: roomId,
    });
  }

  async deleteNote(id: number): Promise<HttpResponse<NoteDTO>> {
    return await this.makePostRequest(`${this.notePath}/delete`, { id: id });
  }

  // Shopping

  async getShoppingListOfRoom(
    roomId: number
  ): Promise<HttpResponse<ShoppingListDataExtDTO[]>> {
    return await this.makeGetRequest(`${this.shoppingListPath}/list`, {
      id: roomId,
    });
  }

  async createShoppingList(
    shoppingList: ShoppingListDTO
  ): Promise<HttpResponse<ShoppingListExtDTO>> {
    return await this.makePostRequest(
      `${this.shoppingListPath}/create`,
      shoppingList
    );
  }

  async deleteShoppingList(
    shoppingListDTO: ShoppingListDTO
  ): Promise<HttpResponse<ShoppingListDTO>> {
    return await this.makePostRequest(
      `${this.shoppingListPath}/delete`,
      shoppingListDTO
    );
  }

  async changeStatusShoppingList(
    shoppingListDTO: ShoppingListDTO
  ): Promise<HttpResponse<ShoppingListDTO>> {
    return await this.makePostRequest(
      `${this.shoppingListPath}/changeStatus`,
      shoppingListDTO
    );
  }

  async deleteShoppingItem(
    item: ShoppingItemDTO
  ): Promise<HttpResponse<RoomDTO>> {
    return await this.makePostRequest(`${this.shoppingItemPath}/delete`, item);
  }

  async createShoppingItem(
    shoppingItem: RoomDTO
  ): Promise<HttpResponse<RoomDTO>> {
    return await this.makePostRequest(
      `${this.shoppingItemPath}/create`,
      shoppingItem
    );
  }
}
