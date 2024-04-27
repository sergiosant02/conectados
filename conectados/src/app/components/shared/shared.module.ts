import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { AddShoppingItemComponent } from '../add-shopping-item/add-shopping-item.component';
import { CategoryCreatorComponent } from '../category-creator/category-creator.component';
import { CreateTicketComponent } from '../create-ticket/create-ticket.component';
import { ErrorMessageComponent } from '../error-message/error-message.component';
import { ItemsTableComponent } from '../items-table/items-table.component';
import { NoteCreatorComponent } from '../note-creator/note-creator.component';
import { ProductCreatorComponent } from '../product-creator/product-creator.component';
import { RoomCreatorComponent } from '../room-creator/room-creator.component';
import { SearchRoomComponent } from '../search-room/search-room.component';

@NgModule({
  declarations: [
    ErrorMessageComponent,
    RoomCreatorComponent,
    ProductCreatorComponent,
    CategoryCreatorComponent,
    NoteCreatorComponent,
    ItemsTableComponent,
    CreateTicketComponent,
    SearchRoomComponent,
    AddShoppingItemComponent,
  ],
  imports: [
    CommonModule,
    IonicModule.forRoot(),
    FormsModule,
    ReactiveFormsModule,
  ],
  exports: [
    ErrorMessageComponent,
    RoomCreatorComponent,
    ProductCreatorComponent,
    CategoryCreatorComponent,
    NoteCreatorComponent,
    ItemsTableComponent,
    CreateTicketComponent,
    SearchRoomComponent,
    AddShoppingItemComponent,
  ],
})
export class SharedModule {}
