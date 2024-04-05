import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {
  LoadingController,
  ModalController,
  NavController,
} from '@ionic/angular';
import { DataManagementService } from './../../services/data-management.service';

@Component({
  selector: 'app-room-creator',
  templateUrl: './room-creator.component.html',
  styleUrls: ['./room-creator.component.scss'],
})
export class RoomCreatorComponent implements OnInit {
  roomForm: FormGroup;

  constructor(
    private modalCtrl: ModalController,
    private loadingCtrl: LoadingController,
    private datamanagement: DataManagementService,
    private navController: NavController
  ) {
    this.roomForm = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.minLength(3)]),
      description: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
      ]),
    });
  }

  ngOnInit() {
    console.log('Hello RoomCreatorComponent Component');
  }

  async closeModal() {
    return await this.modalCtrl.dismiss();
  }

  async submit() {
    if (this.roomForm.valid) {
      const loading = await this.loadingCtrl.create({});
      loading.present();
      this.datamanagement.createRoom(this.roomForm.value).then(async (data) => {
        this.modalCtrl.dismiss({ newRoom: data });
        loading.dismiss();
      });
    }
  }
}
