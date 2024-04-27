import { Component, OnInit } from '@angular/core';
import { Clipboard } from '@capacitor/clipboard';
import { ModalController, NavController } from '@ionic/angular';
import { SearchRoomComponent } from 'src/app/components/search-room/search-room.component';
import { RoomDTO } from 'src/app/dtos/typesDtos';
import { PersistenceService } from 'src/app/services/persistence.service';
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
    private modalCtrl: ModalController,
    private persistanceService: PersistenceService,
    private navCtrl: NavController
  ) {}

  ngOnInit() {
    this.dataManagementService.getRoomsMember().then((data) => {
      this.rooms = data.sort((a, b) =>
        a.name!.replace(/ /g, '').localeCompare(b.name!.replace(/ /g, ''))
      );
    });
  }

  async copyToClipboard(event: Event, roomIndex: number) {
    event.stopPropagation();
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
          a.name!.replace(/ /g, '').localeCompare(b.name!.replace(/ /g, ''))
        );
      }
    });
    modal.present();
  }

  public async seacrhRoom() {
    const modal = await this.modalCtrl.create({
      component: SearchRoomComponent,
      initialBreakpoint: 0.8,
    });
    modal.onDidDismiss().then((dataReturned) => {
      if (dataReturned.data.newRoom && dataReturned.data.newRoom != null) {
        this.rooms.push(dataReturned.data.newRoom);
        this.rooms.sort((a, b) =>
          a.name!.replace(/ /g, '').localeCompare(b.name!.replace(/ /g, ''))
        );
      }
    });
    modal.present();
  }

  onRoomSelected(index: number) {
    this.persistanceService.setValue(
      constants.SELECTED_ROOM,
      this.rooms[index]
    );
    this.navCtrl.navigateForward('/room', {
      queryParams: { id: this.rooms[index].id },
    });
  }
}
