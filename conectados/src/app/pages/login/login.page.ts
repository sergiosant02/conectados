import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import {
  LoadingController,
  NavController,
  ToastController,
} from '@ionic/angular';
import { constants } from '../../constants.ts';
import { DataManagementService } from '../../services/data-management.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {
  constants = constants;
  loginForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private dataManagement: DataManagementService,
    private toastController: ToastController,
    private navCtr: NavController,
    private loadingCtrl: LoadingController
  ) {
    this.loginForm = this.formBuilder.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(6),
      ]),
    });
  }

  ngOnInit() {
    console.log('login');
  }

  async logginSubmit() {
    const loading = await this.loadingCtrl.create({});
    const toast = await this.toastController.create({
      duration: 2000,
      position: 'bottom',
      color: 'dark',
      buttons: [
        {
          text: 'Cerrar',
          role: 'cancel',
        },
      ],
    });
    loading.present();
    this.dataManagement
      .login(this.loginForm.value)
      .then(async (_) => {
        toast.message = `Bienvenido de nuevo`;
        await toast.present();
        this.navCtr.navigateRoot('/');
        loading.dismiss();
      })
      .catch(async (err) => {
        toast.message = `Error de credenciales, vuelva a intentarlo.`;
        await toast.present();
        loading.dismiss();
      });
  }
}
