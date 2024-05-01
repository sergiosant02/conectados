import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  LoadingController,
  ModalController,
  NavController,
  ToastController,
} from '@ionic/angular';
import { CreateTicketComponent } from '../components/create-ticket/create-ticket.component';
import { constants } from '../constants.ts';
import { ShoppingListDataExtDTO } from '../dtos/typesExtDtos';
import { ProductDTO, RoomDTO } from './../dtos/typesDtos';
import { DataManagementService } from './../services/data-management.service';
import { PersistenceService } from './../services/persistence.service';

@Component({
  selector: 'app-tab3',
  templateUrl: 'tab3.page.html',
  styleUrls: ['tab3.page.scss'],
})
export class Tab3Page implements OnInit {
  constants = constants;
  roomDTO: RoomDTO;
  onEditionMode: boolean = false;
  shoppingLists: ShoppingListDataExtDTO[] = [];
  products: ProductDTO[] = [];

  constructor(
    private datamangement: DataManagementService,
    private loadingCtrl: LoadingController,
    private persistenceService: PersistenceService,
    private navController: NavController,
    private modalCtrl: ModalController,
    private toastCtrl: ToastController,
    private router: Router
  ) {
    this.roomDTO = this.persistenceService.getValue(constants.SELECTED_ROOM);
    this.datamangement.getShoppingListOfRoom(this.roomDTO.id).then((res) => {
      this.shoppingLists = res.data.sort((a, b) =>
        a.name!.replace(/ /g, '').localeCompare(b.name!.replace(/ /g, ''))
      );
    });
  }
  ngOnInit(): void {
    this.getData();
    this.datamangement.getShoppingListOfRoom(this.roomDTO.id).then((res) => {
      this.shoppingLists = res.data.sort((a, b) =>
        a.name!.replace(/ /g, '').localeCompare(b.name!.replace(/ /g, ''))
      );
    });
  }

  ionViewWillEnter() {
    this.getData();
    this.datamangement.getShoppingListOfRoom(this.roomDTO.id).then((res) => {
      this.shoppingLists = res.data.sort((a, b) =>
        a.name!.replace(/ /g, '').localeCompare(b.name!.replace(/ /g, ''))
      );
    });
  }

  public changeEditionMode() {
    this.onEditionMode = !this.onEditionMode;
  }

  private async getData(): Promise<void> {
    this.datamangement.getRoom(this.roomDTO.id).then(async (res) => {
      if (res.code >= 400) {
        const toast = await this.toastCtrl.create({
          duration: 2000,
          position: 'bottom',
        });
        toast.present();
      } else {
        this.products = res.data.productDTOs;
      }
    });
  }

  public async createTicket() {
    this.products = (
      await this.datamangement.getRoom(this.roomDTO.id)
    ).data.productDTOs;
    const modal = await this.modalCtrl.create({
      component: CreateTicketComponent,
      componentProps: {
        roomId: this.roomDTO.id,
      },
      initialBreakpoint: 0.4,
    });
    modal.onDidDismiss().then((dataReturned) => {
      if (dataReturned.data?.newTicket) {
        this.shoppingLists.push(dataReturned.data.newTicket);
      }
    });
    modal.present();
  }

  public deleteTicket(event: Event, index: number) {
    event.stopPropagation();
    this.datamangement.deleteShoppingList({
      id: this.shoppingLists[index].id,
      roomId: this.shoppingLists[index].roomId,
      name: '',
      completed: false,
      itemIds: [],
    });
    this.shoppingLists = this.shoppingLists.filter((value, i) => i != index);
  }
}
