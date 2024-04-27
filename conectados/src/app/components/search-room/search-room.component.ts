import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {
  LoadingController,
  ModalController,
  ToastController,
} from '@ionic/angular';
import { DataManagementService } from 'src/app/services/data-management.service';

@Component({
  selector: 'app-search-room',
  templateUrl: './search-room.component.html',
  styleUrls: ['./search-room.component.scss'],
})
export class SearchRoomComponent {
  searchRoomForm: FormGroup;

  constructor(
    private modalCtrl: ModalController,
    private dataManagement: DataManagementService,
    private loadingCtrl: LoadingController,
    private toastController: ToastController
  ) {
    this.searchRoomForm = new FormGroup({
      code: new FormControl('', Validators.required),
    });
  }

  async closeModal() {
    return await this.modalCtrl.dismiss();
  }

  async submit() {
    if (this.searchRoomForm.valid) {
      const loading = await this.loadingCtrl.create({});
      loading.present();
      this.dataManagement
        .joinToRoom(this.searchRoomForm.value.code)
        .then(async (data) => {
          if (data.code >= 400) {
            const toast = await this.toastController.create({
              message: data.message,
              duration: 2000,
              color: 'danger',
            });
            toast.present();
          }
          loading.dismiss();
          await this.modalCtrl.dismiss({ newRoom: data.data });
        });
    }
  }
}
