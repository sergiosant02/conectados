import { Component, Input } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { LoadingController, ModalController } from '@ionic/angular';
import { DataManagementService } from './../../services/data-management.service';

@Component({
  selector: 'app-category-creator',
  templateUrl: './category-creator.component.html',
  styleUrls: ['./category-creator.component.scss'],
})
export class CategoryCreatorComponent {
  @Input() roomId: number = 0;
  categpryForm: FormGroup;

  constructor(
    private modalCtrl: ModalController,
    private dataManagement: DataManagementService,
    private loadingCtrl: LoadingController
  ) {
    this.categpryForm = new FormGroup({
      roomId: new FormControl(this.roomId),
      name: new FormControl('', Validators.required),
    });
  }

  async closeModal() {
    return await this.modalCtrl.dismiss();
  }

  async submit() {
    if (this.categpryForm.valid) {
      const loading = await this.loadingCtrl.create({});
      loading.present();
      this.categpryForm.value.roomId = this.roomId;
      console.log(this.roomId);
      console.log(this.categpryForm.value.roomId);
      this.dataManagement
        .createCategory(this.categpryForm.value)
        .then(async (data) => {
          loading.dismiss();
          console.dir(data);
          await this.modalCtrl.dismiss({ newCategory: data.data });
        });
    }
  }
}
