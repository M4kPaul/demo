import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MustMatch } from '../../util/validate-password';
import { MySnackBar } from 'src/app/util/mysnackbar';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup = new FormGroup({});
  submitted = false;
  hide1 = true;
  hide2 = true;
  isSuccessful = false;
  isRegisterFailed = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private tokenStorage: TokenStorageService,
    private router: Router,
    private mySnackBar: MySnackBar) {}

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.router.navigate(['tasks']);
    }
    this.registerForm = this.fb.group(
      {
        username: ['', [Validators.required, Validators.maxLength(64)]],
        password: ['', [Validators.required, Validators.minLength(6)]],
        confirmPassword: ['', Validators.required],
      },
      {
        validator: MustMatch('password', 'confirmPassword'),
      }
    );
  }

  get f() {
    return this.registerForm.controls;
  }

  onSubmit() {
    this.submitted = true;

    if (this.registerForm.invalid) {
      return;
    }
    
    this.authService.register(this.registerForm.value).subscribe(
      (data) => {
        this.isSuccessful = true;
        this.isRegisterFailed = false;
        this.mySnackBar.openSnackBar("Successfully registered!", "green");
        this.router.navigate(['login']);
      },
      (err) => {
        console.log(err);
        this.errorMessage = err.error.message;
        this.isRegisterFailed = true;
        this.mySnackBar.openSnackBar(this.errorMessage, "red");
      }
    );
  }
}
