import { Component, Input } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {
  LoadingController,
  ModalController,
  ToastController,
} from '@ionic/angular';
import { ProductDTO } from 'src/app/dtos/typesDtos';
import { DataManagementService } from 'src/app/services/data-management.service';

@Component({
  selector: 'app-add-shopping-item',
  templateUrl: './add-shopping-item.component.html',
  styleUrls: ['./add-shopping-item.component.scss'],
})
export class AddShoppingItemComponent {
  @Input({ required: true }) shoppingListId: number = 0;
  @Input({ required: true }) products: ProductDTO[] = [];
  addShoppingItemForm: FormGroup;

  constructor(
    private datamanagement: DataManagementService,
    private toastCtrl: ToastController,
    private modalCtrl: ModalController,
    private loadingCtrl: LoadingController
  ) {
    this.addShoppingItemForm = new FormGroup({
      productId: new FormControl('', [Validators.required]),
      quantity: new FormControl('', [Validators.required, Validators.min(0)]),
      value: new FormControl('', [Validators.required, Validators.min(0)]),
    });
  }

  async closeModal() {
    return await this.modalCtrl.dismiss();
  }

  public addShoppingItemToList() {}

  public async submit() {
    const loading = await this.loadingCtrl.create();
    loading.present();
    if (this.addShoppingItemForm.valid) {
      console.log(this.addShoppingItemForm.value);
      this.addShoppingItemForm.value.shoppingListId = this.shoppingListId;
      this.datamanagement
        .createShoppingItem(this.addShoppingItemForm.value)
        .then(async (res) => {
          if (res.code >= 400) {
            const toast = await this.toastCtrl.create({
              message: res.message,
              duration: 2000,
            });
            toast.present();
          } else {
            this.modalCtrl.dismiss({ newArticle: res.data });
          }
          loading.dismiss();
        });
    }
  }
}
