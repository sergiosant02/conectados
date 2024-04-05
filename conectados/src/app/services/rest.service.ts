import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.prod';
import { ProductCategoryDTO, RoomDTO, UserDTO } from '../dtos/typesDtos';
import { JwtRequest, ResponseEntity } from '../models/responses';
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

  async createRoom(room: RoomDTO): Promise<ResponseEntity<RoomDTO>> {
    return await this.makePostRequest(`${this.roomPath}/create`, room);
  }

  async joinToRoom(code: string): Promise<ResponseEntity<RoomDTO>> {
    return await this.makePostRequest(`${this.roomPath}/join`, { code: code });
  }

  async leaveRoom(id: number): Promise<ResponseEntity<RoomDTO>> {
    return await this.makePostRequest(`${this.roomPath}/leave`, { id: id });
  }

  async deleteRoom(id: number): Promise<ResponseEntity<RoomDTO>> {
    return await this.makePostRequest(`${this.roomPath}/delete`, { id: id });
  }

  async getRoom(id: number): Promise<ResponseEntity<RoomDTO>> {
    return await this.makeGetRequest(`${this.roomPath}/allRoomInfo`, {
      id: id,
    });
  }

  async fireMember(roomDTO: RoomDTO): Promise<ResponseEntity<RoomDTO>> {
    return await this.makePostRequest(`${this.roomPath}/fireMember`, {
      roomDTO: roomDTO,
    });
  }

  async listProductOfRoom(id: number): Promise<ResponseEntity<RoomDTO>> {
    return await this.makeGetRequest(`${this.roomPath}/listProducts/${id}`);
  }

  async createCategoryIntoroom(
    categoryDTO: ProductCategoryDTO
  ): Promise<ResponseEntity<RoomDTO>> {
    return await this.makePostRequest(`${this.roomPath}/createCatgeory`, {
      categoryDTO: categoryDTO,
    });
  }
}
