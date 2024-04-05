import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import {
  LoadingController,
  NavController,
  ToastController,
} from '@ionic/angular';
import { constants } from './../../constants.ts';
import { DataManagementService } from './../../services/data-management.service';
import { UtilsProviderService } from './../../services/utils-provider.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
})
export class RegisterPage implements OnInit {
  registerForm: FormGroup;
  constants = constants;

  constructor(
    private formBuilder: FormBuilder,
    private dataManagement: DataManagementService,
    private toastController: ToastController,
    private navCtr: NavController,
    private loadingCtrl: LoadingController,
    private utilProvider: UtilsProviderService
  ) {
    this.registerForm = new FormGroup(
      {
        name: new FormControl('', [Validators.required]),
        lastName: new FormControl('', [Validators.required]),
        email: new FormControl('', [Validators.required, Validators.email]),
        password: new FormControl('', [
          Validators.required,
          Validators.minLength(6),
        ]),
        confirmPassword: new FormControl('', [Validators.required]),
        gender: new FormControl('', [Validators.required]),
      },
      {
        validators: this.confirmPasswordValidator,
      }
    );
  }

  ngOnInit() {
    console.log('register');
  }

  async registerSubmit() {
    const loading = await this.loadingCtrl.create({});
    const toast = await this.toastController.create({
      duration: 2000, // Duración del toast en milisegundos
      position: 'bottom', // Posición del toast (top, middle, bottom)
      color: 'dark', // Color del toast
      buttons: [
        {
          text: 'Cerrar',
          role: 'cancel',
        },
      ],
    });
    loading.present();
    this.dataManagement
      .register(this.registerForm.value)
      .then(async (_) => {
        toast.message = `Bienvenido a conectados`;
        await toast.present();
        this.navCtr.navigateRoot('/');
        loading.dismiss();
      })
      .catch(async (err) => {
        let serverErr = err.error;
        let message = '';

        for (let key in serverErr) {
          message += (serverErr[key] as string[]).join(', ');
        }
        toast.message = message;
        await toast.present();
        loading.dismiss();
      });
  }

  confirmPasswordValidator: ValidatorFn = (
    control: AbstractControl
  ): ValidationErrors | null => {
    return control.value.password === control.value.confirmPassword
      ? null
      : { mismatch: true };
  };
}
