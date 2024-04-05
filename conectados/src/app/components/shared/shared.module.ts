import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import { RoomCreatorComponent } from '../room-creator/room-creator.component';

@NgModule({
  declarations: [ErrorMessageComponent, RoomCreatorComponent],
  imports: [
    CommonModule,
    IonicModule.forRoot(),
    FormsModule,
    ReactiveFormsModule,
  ],
  exports: [ErrorMessageComponent, RoomCreatorComponent],
})
export class SharedModule {}
