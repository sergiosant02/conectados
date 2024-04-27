import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {
  LoadingController,
  ModalController,
  NavController,
} from '@ionic/angular';
import { constants } from 'src/app/constants.ts';
import { RoomDTO } from 'src/app/dtos/typesDtos';
import { DataManagementService } from 'src/app/services/data-management.service';
import { PersistenceService } from 'src/app/services/persistence.service';

@Component({
  selector: 'app-note-creator',
  templateUrl: './note-creator.component.html',
  styleUrls: ['./note-creator.component.scss'],
})
export class NoteCreatorComponent implements OnInit {
  noteForm: FormGroup;
  room: RoomDTO;

  constructor(
    private modalCtrl: ModalController,
    private loadingCtrl: LoadingController,
    private datamanagement: DataManagementService,
    private navController: NavController,
    private persistenceService: PersistenceService
  ) {
    this.room = this.persistenceService.getValue(constants.SELECTED_ROOM);
    this.noteForm = new FormGroup({
      title: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
      ]),
      body: new FormControl('', [Validators.required, Validators.minLength(3)]),
    });
  }

  ngOnInit() {
    console.log('Hello RoomCreatorComponent Component');
  }

  async closeModal() {
    return await this.modalCtrl.dismiss();
  }

  async submit() {
    if (this.noteForm.valid) {
      this.noteForm.value.roomId = this.room.id;
      const loading = await this.loadingCtrl.create({});
      loading.present();
      this.datamanagement.createNote(this.noteForm.value).then(async (data) => {
        console.log(data.data);
        this.modalCtrl.dismiss({ newNote: data.data });
        loading.dismiss();
      });
    }
  }
}
