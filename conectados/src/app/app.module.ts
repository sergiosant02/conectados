import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';
import { AuthTokenService } from './interceptors/auth-token.service';
import { DataManagementService } from './services/data-management.service';

import { IonicModule, IonicRouteStrategy } from '@ionic/angular';

import {
  HTTP_INTERCEPTORS,
  HttpClient,
  HttpClientModule,
} from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    IonicModule.forRoot(),
    AppRoutingModule,
    HttpClientModule,
  ],
  providers: [
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy },
    { provide: HTTP_INTERCEPTORS, useClass: AuthTokenService, multi: true },
    DataManagementService,
    AuthTokenService,
    HttpClient,
    HttpClientModule,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
