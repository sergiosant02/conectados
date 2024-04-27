import { Component, Input } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { LoadingController, ModalController } from '@ionic/angular';
import { DataManagementService } from 'src/app/services/data-management.service';

@Component({
  selector: 'app-create-ticket',
  templateUrl: './create-ticket.component.html',
  styleUrls: ['./create-ticket.component.scss'],
})
export class CreateTicketComponent {
  @Input() roomId: number = 0;
  ticketForm: FormGroup;

  constructor(
    private modalCtrl: ModalController,
    private dataManagement: DataManagementService,
    private loadingCtrl: LoadingController
  ) {
    this.ticketForm = new FormGroup({
      roomId: new FormControl(this.roomId),
      name: new FormControl('', Validators.required),
    });
  }

  async closeModal() {
    return await this.modalCtrl.dismiss();
  }

  async submit() {
    if (this.ticketForm.valid) {
      const loading = await this.loadingCtrl.create({});
      loading.present();
      this.ticketForm.value.roomId = this.roomId;
      console.log(this.roomId);
      console.log(this.ticketForm.value.roomId);
      this.dataManagement
        .createShoppingList(this.ticketForm.value)
        .then(async (data) => {
          loading.dismiss();
          await this.modalCtrl.dismiss({ newTicket: data.data });
        });
    }
  }
}
