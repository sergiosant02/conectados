import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { Token, User } from '../models/authentication';
import { PersistenceService } from './persistence.service';
import { RestService } from './rest.service';
import { constants } from '../constants.ts';

@Injectable({
  providedIn: 'root',
})
export class DataManagementService {
  constructor(
    private rest: RestService,
    private router: Router,
    private persistenceService: PersistenceService
  ) {}
  public userLogged: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(
    false
  );

  public email: BehaviorSubject<string> = new BehaviorSubject<string>('');

  public role: BehaviorSubject<string> = new BehaviorSubject<string>('');

  public userId: BehaviorSubject<string> = new BehaviorSubject<string>('');

  formCorreo!: FormGroup;

  async postLogin(user: User): Promise<Token> {
    return this.rest
      .postLogin(user)
      .then(async (data) => {
        this.persistenceService.setToken(data);
        return data;
      })
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async postVerifyEmail(code: string): Promise<Token> {
    return this.rest
      .postVerifyEmail(
        code,
        this.persistenceService.getValue(constants.PROVISIONAL_TOKEN)
      )
      .then(async (data: Token) => {
        this.persistenceService.setToken(data);
        this.persistenceService.removeValue(constants.PROVISIONAL_TOKEN);
        return data;
      })
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async postRegister(user: User): Promise<Token> {
    return this.rest
      .postRegister(user)
      .then(async (data) => {
        this.persistenceService.setValue(
          constants.PROVISIONAL_TOKEN,
          data.token
        );
        return data;
      })
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async postDeleteAccount(): Promise<void> {
    return this.rest
      .postDeleteAccount(
        this.persistenceService.getValue(constants.PROVISIONAL_TOKEN)
      )
      .then((_) => {
        this.persistenceService.removeValue(constants.TOKEN);
        this.persistenceService.removeValue(constants.PROVISIONAL_TOKEN);
      })
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }

  async postLogout(): Promise<void> {
    return this.rest
      .logout()
      .then((_) => {
        this.persistenceService.removeValue(constants.TOKEN);
        this.persistenceService.removeValue(constants.PROVISIONAL_TOKEN);
      })
      .catch((err: HttpErrorResponse) => {
        throw err;
      });
  }
}
