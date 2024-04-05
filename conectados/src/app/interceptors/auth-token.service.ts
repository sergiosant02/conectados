import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PersistenceService } from '../services/persistence.service';

@Injectable({
  providedIn: 'root',
})
export class AuthTokenService implements HttpInterceptor {
  constructor(private persistenceService: PersistenceService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const token = this.persistenceService.getToken();
    if (token) {
      const auth = req.clone({
        setHeaders: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Headers': 'Content-Type',
          'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT',
          Authorization: `Bearer ${token}`,
        },
      });
      return next.handle(auth);
    }
    return next.handle(req);
  }
}
