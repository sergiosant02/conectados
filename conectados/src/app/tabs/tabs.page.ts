import { Component } from '@angular/core';
import { constants } from 'src/app/constants.ts';
import { RoomDTO } from '../dtos/typesDtos';
import { DataManagementService } from '../services/data-management.service';
import { PersistenceService } from '../services/persistence.service';

@Component({
  selector: 'app-tabs',
  templateUrl: 'tabs.page.html',
  styleUrls: ['tabs.page.scss'],
})
export class TabsPage {
  constants = constants;
  room: RoomDTO;

  constructor(
    private datamanagement: DataManagementService,
    private persistence: PersistenceService
  ) {
    this.room = this.persistence.getValue('selectedRoom');
    this.datamanagement.getRoom(this.room.id).then((roomData) => {
      persistence.setValue(constants.SELECTED_ROOM, roomData.data.roomDTO);
    });
  }
}
