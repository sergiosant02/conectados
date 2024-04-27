import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { LoadingController, ModalController } from '@ionic/angular';
import { ProductCategoryDTO } from 'src/app/dtos/typesDtos';
import { DataManagementService } from 'src/app/services/data-management.service';
import { CategoryCreatorComponent } from './../category-creator/category-creator.component';

@Component({
  selector: 'app-product-creator',
  templateUrl: './product-creator.component.html',
  styleUrls: ['./product-creator.component.scss'],
})
export class ProductCreatorComponent implements OnInit {
  @Input() categories: ProductCategoryDTO[] = [];
  @Input() roomId: number = 0;
  productForm: FormGroup;

  constructor(
    private modalCtrl: ModalController,
    private dataManagement: DataManagementService,
    private loadingCtrl: LoadingController
  ) {
    this.productForm = new FormGroup({
      roomId: new FormControl(`${this.roomId}`),
      name: new FormControl('', Validators.required),
      categoryIds: new FormControl('', Validators.required),
    });
  }

  ngOnInit() {
    console.log(this.categories);
  }

  async closeModal() {
    return await this.modalCtrl.dismiss();
  }

  async submit() {
    if (this.productForm.valid) {
      const loading = await this.loadingCtrl.create({});
      loading.present();
      this.productForm.value.roomId = this.roomId;
      if (this.productForm.value.categoryIds === '') {
        this.productForm.value.categoryIds = [];
      }
      this.dataManagement
        .createProduct(this.productForm.value)
        .then(async (data) => {
          this.modalCtrl.dismiss({ newProduct: data });
          loading.dismiss();
        });
    }
  }

  async createCategory() {
    const modal = await this.modalCtrl.create({
      component: CategoryCreatorComponent,
      componentProps: {
        roomId: this.roomId,
      },
      initialBreakpoint: 0.7,
    });
    modal.onDidDismiss().then((dataReturned) => {
      if (dataReturned.data?.newCategory) {
        this.categories.push(dataReturned.data.newCategory);
        this.categories.sort((a, b) =>
          a.name.replace(/ /g, '').localeCompare(b.name.replace(/ /g, ''))
        );
      }
    });
    modal.present();
  }
}
