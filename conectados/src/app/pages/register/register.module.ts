import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { RegisterPageRoutingModule } from './register-routing.module';

import { HttpClientModule } from '@angular/common/http';
import { SharedModule } from '../../components/shared/shared.module';
import { RegisterPage } from './register.page';

@NgModule({
  imports: [
    IonicModule.forRoot(),
    CommonModule,
    FormsModule,
    IonicModule,
    RegisterPageRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    HttpClientModule,
  ],
  declarations: [RegisterPage],
})
export class RegisterPageModule {}
