import { Component, Input, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { constants } from 'src/app/constants.ts';
import { ProductDTO, RoomDTO } from 'src/app/dtos/typesDtos';
import { ShoppingListDataExtDTO } from 'src/app/dtos/typesExtDtos';
import { DataManagementService } from 'src/app/services/data-management.service';
import { PersistenceService } from 'src/app/services/persistence.service';
import { AddShoppingItemComponent } from '../add-shopping-item/add-shopping-item.component';

@Component({
  selector: 'app-items-table',
  templateUrl: './items-table.component.html',
  styleUrls: ['./items-table.component.scss'],
})
export class ItemsTableComponent implements OnInit {
  @Input() products: ProductDTO[] = [];
  @Input() onEditionMode: boolean = false;
  @Input({ required: true }) shoppingList: ShoppingListDataExtDTO | undefined;
  room: RoomDTO;
  constants = constants;

  totalCost: number = 0.0;

  constructor(
    private modalCtrl: ModalController,
    private datamanagement: DataManagementService,
    private persistenceService: PersistenceService
  ) {
    this.room = this.persistenceService.getValue(constants.SELECTED_ROOM);
  }
  ngOnInit(): void {
    this.shoppingList?.items.sort((a, b) =>
      a.name!.replace(/ /g, '').localeCompare(b.name!.replace(/ /g, ''))
    );
    this.shoppingList?.items.forEach((a) => {
      this.totalCost += a.value * a.quantity;
    });
  }

  public async addArticle() {
    this.products = (
      await this.datamanagement.getRoom(this.room.id)
    ).data.productDTOs;
    const modal = await this.modalCtrl.create({
      component: AddShoppingItemComponent,
      componentProps: {
        shoppingListId: this.shoppingList?.id,
        products: this.products,
      },
      initialBreakpoint: 0.8,
    });
    modal.onDidDismiss().then((dataReturned) => {
      if (
        dataReturned.data.newArticle &&
        dataReturned.data.newArticle != null
      ) {
        this.shoppingList?.items.push(dataReturned.data.newArticle);
        this.totalCost +=
          dataReturned.data.newArticle.quantity *
          dataReturned.data.newArticle.value;
        this.shoppingList?.items.sort((a, b) =>
          a.name!.replace(/ /g, '').localeCompare(b.name!.replace(/ /g, ''))
        );
      }
    });
    modal.present();
  }

  public deleteArticle(index: number) {
    if (this.shoppingList?.items[index].id) {
      this.totalCost -=
        this.shoppingList?.items[index].quantity *
        this.shoppingList?.items[index].value;
      this.datamanagement.deleteShoppingItem(this.shoppingList?.items[index]);
      this.shoppingList.items = this.shoppingList.items.filter(
        (item) => item.id != this.shoppingList?.items[index].id
      );
    }
  }
}
