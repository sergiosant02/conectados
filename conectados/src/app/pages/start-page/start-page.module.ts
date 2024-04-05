import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { StartPagePageRoutingModule } from './start-page-routing.module';

import { StartPagePage } from './start-page.page';

@NgModule({
  imports: [
    IonicModule.forRoot(),
    CommonModule,
    FormsModule,
    IonicModule,
    StartPagePageRoutingModule,
  ],
  declarations: [StartPagePage],
})
export class StartPagePageModule {}
