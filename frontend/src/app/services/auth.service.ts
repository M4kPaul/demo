import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AbstractControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { AppConstants } from '../common/app.constants';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  isLoggedIn = false;
  username = "";

  constructor(private http: HttpClient) {}

  login(credentials: { [key: string]: AbstractControl }): Observable<any> {
    return this.http.post(
      AppConstants.AUTH_API + 'login',
      {
        username: credentials.username,
        password: credentials.password,
      },
      httpOptions
    );
  }

  register(user: { [key: string]: AbstractControl }): Observable<any> {
    return this.http.post(
      AppConstants.AUTH_API + 'register',
      {
        username: user.username,
        password: user.password,
        matchingPassword: user.confirmPassword,
      },
      httpOptions
    );
  }
}
