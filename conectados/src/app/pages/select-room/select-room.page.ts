import { Component, OnInit } from '@angular/core';
import { Clipboard } from '@capacitor/clipboard';
import { ModalController } from '@ionic/angular';
import { RoomDTO } from 'src/app/dtos/typesDtos';
import { RoomCreatorComponent } from './../../components/room-creator/room-creator.component';
import { constants } from './../../constants.ts';
import { DataManagementService } from './../../services/data-management.service';

@Component({
  selector: 'app-select-room',
  templateUrl: './select-room.page.html',
  styleUrls: ['./select-room.page.scss'],
})
export class SelectRoomPage implements OnInit {
  constants = constants;

  rooms: RoomDTO[] = [];

  constructor(
    private dataManagementService: DataManagementService,
    private modalCtrl: ModalController
  ) {}

  ngOnInit() {
    this.dataManagementService.getRoomsMember().then((data) => {
      this.rooms = data.sort((a, b) =>
        a.name.replace(/ /g, '').localeCompare(b.name.replace(/ /g, ''))
      );
    });
  }

  async copyToClipboard(roomIndex: number) {
    await Clipboard.write({
      string: this.rooms[roomIndex].code,
    });
  }

  async createRoom() {
    const modal = await this.modalCtrl.create({
      component: RoomCreatorComponent,
      initialBreakpoint: 0.8,
    });
    modal.onDidDismiss().then((dataReturned) => {
      if (dataReturned.data.newRoom) {
        this.rooms.push(dataReturned.data.newRoom);
        this.rooms.sort((a, b) =>
          a.name.replace(/ /g, '').localeCompare(b.name.replace(/ /g, ''))
        );
      }
    });
    modal.present();
  }
}
