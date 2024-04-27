import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {
  LoadingController,
  ModalController,
  NavController,
} from '@ionic/angular';
import { constants } from '../constants.ts';
import { NoteDTO, RoomDTO } from '../dtos/typesDtos.js';
import { NoteCreatorComponent } from './../components/note-creator/note-creator.component';
import { DataManagementService } from './../services/data-management.service';
import { PersistenceService } from './../services/persistence.service';

@Component({
  selector: 'app-tab2',
  templateUrl: 'tab2.page.html',
  styleUrls: ['tab2.page.scss'],
})
export class Tab2Page {
  constants = constants;
  roomDTO: RoomDTO;
  notes: NoteDTO[] = [];
  onEdition: boolean = false;

  constructor(
    private datamangement: DataManagementService,
    private loadingCtrl: LoadingController,
    private persistenceService: PersistenceService,
    private navController: NavController,
    private modalCtrl: ModalController,
    private router: Router
  ) {
    this.roomDTO = this.persistenceService.getValue(constants.SELECTED_ROOM);
    this.datamangement.getNotesOfRoom(this.roomDTO.id).then((res) => {
      this.notes = res.data;
    });
  }

  async createNote() {
    const modal = await this.modalCtrl.create({
      component: NoteCreatorComponent,
      initialBreakpoint: 0.8,
    });
    modal.onDidDismiss().then((dataReturned) => {
      if (dataReturned.data.newNote) {
        console.log();
        this.notes.push(dataReturned.data.newNote);
        this.notes.sort((a, b) =>
          a.title.replace(/ /g, '').localeCompare(b.title.replace(/ /g, ''))
        );
      }
    });
    modal.present();
  }

  changeEditionStatus() {
    this.onEdition = !this.onEdition;
  }

  deleteNote(note: NoteDTO) {
    this.datamangement.deleteNote(note.id);
    this.notes = this.notes.filter((n) => n.id !== note.id);
  }
}
