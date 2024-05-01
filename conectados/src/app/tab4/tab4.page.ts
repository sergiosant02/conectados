import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoadingController, NavController } from '@ionic/angular';
import { RoomDTO, UserDTO } from 'src/app/dtos/typesDtos';
import { constants } from '../constants.ts';
import { DataManagementService } from './../services/data-management.service';
import { PersistenceService } from './../services/persistence.service';

@Component({
  selector: 'app-tab4',
  templateUrl: './tab4.page.html',
  styleUrls: ['./tab4.page.scss'],
})
export class Tab4Page implements OnInit {
  constants = constants;

  members: UserDTO[] = [];
  room: RoomDTO;
  isOwner: boolean = false;
  user: UserDTO;
  loading: HTMLIonLoadingElement | undefined;

  constructor(
    private datamangement: DataManagementService,
    private loadingCtrl: LoadingController,
    private navController: NavController,
    private persistenceService: PersistenceService,
    private router: Router
  ) {
    this.room = this.persistenceService.getValue(constants.SELECTED_ROOM);
    this.user = this.persistenceService.getValue(constants.USER);
    this.isOwner = this.room.ownerId == this.user.id;
    this.datamangement.getMembersOfRoom(this.room.id).then((res) => {
      this.members = res;
    });
  }

  ngOnInit() {
    this.createLoading();
  }

  async createLoading() {
    this.loading = await this.loadingCtrl.create();
    return this.loading;
  }

  async leaveRoom() {
    this.loading?.present();
    this.datamangement.leaveRoom(this.room.id).then((res) => {
      this.navController.navigateRoot('/select-room');
      this.loading?.dismiss();
    });
  }

  async fireMember(userId: number) {
    const roomDTO: RoomDTO = this.room;
    roomDTO.belongToUserIds = [userId];
    this.datamangement.fireMember(roomDTO).then((res) => {
      this.members = this.members.filter((m) => m.id !== userId);
    });
  }

  async deleteRoom() {
    this.loading?.present();
    this.datamangement.deleteRoom(this.room.id).then((res) => {
      this.loading?.dismiss();
      this.navController.navigateRoot('/select-room');
    });
  }
}
