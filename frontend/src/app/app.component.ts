import { HttpClient } from '@angular/common/http';
import { AppService } from './app.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { finalize } from "rxjs/operators";;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  greeting = {'id': 'XXX', 'content': 'Hello World'};
  username = "m4k";

  constructor(
    private app: AppService,
    private http: HttpClient,
    private router: Router) {
      this.app.authenticate(undefined, undefined);
    }

  authenticated() { return this.app.authenticated; }

  logout() {
    this.http.post('logout', {}).pipe(
      finalize(() => {
        this.app.authenticated = false;
        this.router.navigateByUrl('/login');
    })).subscribe();
  }
}
