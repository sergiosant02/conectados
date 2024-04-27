import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {
  LoadingController,
  ModalController,
  NavController,
  ToastController,
} from '@ionic/angular';
import { HttpResponse } from '../models/responses.js';
import { CategoryCreatorComponent } from './../components/category-creator/category-creator.component';
import { ProductCreatorComponent } from './../components/product-creator/product-creator.component';
import { constants } from './../constants.ts';
import { ProductCategoryDTO, ProductDTO, RoomDTO } from './../dtos/typesDtos';
import { RoomExtDTO } from './../dtos/typesExtDtos';
import { DataManagementService } from './../services/data-management.service';
import { PersistenceService } from './../services/persistence.service';

@Component({
  selector: 'app-tab1',
  templateUrl: 'tab1.page.html',
  styleUrls: ['tab1.page.scss'],
})
export class Tab1Page {
  constants = constants;

  products: ProductDTO[] = [];
  categories: ProductCategoryDTO[] = [];
  roomDTO: RoomDTO;
  categorySeleced: ProductCategoryDTO[] = [];
  productSelected: ProductDTO[] = [];
  onEdition: boolean = false;

  constructor(
    private datamangement: DataManagementService,
    private loadingCtrl: LoadingController,
    private navController: NavController,
    private router: ActivatedRoute,
    private toastCtrl: ToastController,
    private persistence: PersistenceService,
    private modalCtrl: ModalController
  ) {
    this.roomDTO = this.persistence.getValue(constants.SELECTED_ROOM);
    this.datamangement
      .getRoom(this.roomDTO.id)
      .then((res: HttpResponse<RoomExtDTO>) => {
        if (res.code !== 200) {
          this.toastCtrl
            .create({
              message: res.message,
              duration: 3000,
            })
            .then((toast) => toast.present());
        } else {
          console.dir(res);
          this.products = res.data.productDTOs;
          console.log(this.products);
          this.productSelected = this.products;
          this.categories = res.data.productCategoryDTOs;
          this.categorySeleced = this.categories;
        }
      });
  }

  changeEditionStatus() {
    this.onEdition = !this.onEdition;
  }

  deleteCategory(category: ProductCategoryDTO) {
    this.datamangement.deleteCategory(category.id);
    this.categories = this.categories.filter((c) => c.id !== category.id);
  }

  async createproduct() {
    const modal = await this.modalCtrl.create({
      component: ProductCreatorComponent,
      componentProps: {
        categories: this.categories,
        roomId: this.roomDTO.id,
      },
      initialBreakpoint: 0.9,
    });
    modal.onDidDismiss().then((dataReturned) => {
      if (dataReturned.data?.newProduct) {
        this.products.push(dataReturned.data.newProduct.data);
        this.products.sort((a, b) =>
          a.name.replace(/ /g, '').localeCompare(b.name.replace(/ /g, ''))
        );
        this.productSelected = this.products.filter((p) =>
          p.categoryIds.some((id) =>
            this.categorySeleced.map((category) => category.id).includes(id)
          )
        );
      }
    });
    modal.present();
  }

  async createCategory() {
    const modal = await this.modalCtrl.create({
      component: CategoryCreatorComponent,
      componentProps: {
        roomId: this.roomDTO.id,
      },
      initialBreakpoint: 0.7,
    });
    modal.onDidDismiss().then((dataReturned) => {
      if (dataReturned.data?.newCategory) {
        this.categories.push(dataReturned.data.newCategory);
        this.products.sort((a, b) =>
          a.name.replace(/ /g, '').localeCompare(b.name.replace(/ /g, ''))
        );
      }
    });
    modal.present();
  }

  selectCategory(category: ProductCategoryDTO) {
    this.categorySeleced.push(category);
    this.productSelected = this.products.filter((p) =>
      p.categoryIds.some((id) =>
        this.categorySeleced.map((category) => category.id).includes(id)
      )
    );
  }

  unselectCatgeroy(category: ProductCategoryDTO) {
    this.categorySeleced = this.categorySeleced.filter(
      (cat) => cat.id !== category.id
    );
    console.dir(this.productSelected);
    this.productSelected = this.products.filter((p) =>
      p.categoryIds.some((id) =>
        this.categorySeleced.map((category) => category.id).includes(id)
      )
    );
  }

  deleteProduct(id: number) {
    this.datamangement.deleteProduct(id).then(() => {
      this.products = this.products.filter((p) => p.id !== id);
    });
    this.products = this.products.filter((p) => p.id !== id);
    this.productSelected = this.productSelected.filter((p) => p.id !== id);
  }
}
