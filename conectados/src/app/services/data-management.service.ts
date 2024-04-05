import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { constants } from '../constants.ts';
import { RoomDTO, UserDTO } from '../dtos/typesDtos';
import { JwtRequest, ResponseEntity } from '../models/responses';
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
        console.log(data);
        this.persistenceService.setValue(constants.TOKEN, data.jwttoken);
        return data;
      })
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async logout(): Promise<void> {
    this.persistenceService.removeValue(constants.TOKEN);
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

  async createRoom(room: RoomDTO): Promise<ResponseEntity<RoomDTO>> {
    return this.rest
      .createRoom(room)
      .then((data) => data)
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async joinToRoom(code: string): Promise<ResponseEntity<RoomDTO>> {
    return this.rest
      .joinToRoom(code)
      .then((data) => data)
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }
}
