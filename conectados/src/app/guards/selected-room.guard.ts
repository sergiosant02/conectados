import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { NavController } from '@ionic/angular';
import { Observable } from 'rxjs';
import { constants } from '../constants.ts';
import { PersistenceService } from '../services/persistence.service';

@Injectable({
  providedIn: 'root',
})
export class SelectedRoomGuard implements CanActivate {
  constructor(
    private persistenceService: PersistenceService,
    private navCtr: NavController
  ) {}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    const isAnyRoomSelected = this.persistenceService.checkValue(
      constants.SELECTED_ROOM_ID
    );
    if (!isAnyRoomSelected) {
      this.navCtr.navigateRoot('/select-room');
      return false;
    }

    return true;
  }
}
