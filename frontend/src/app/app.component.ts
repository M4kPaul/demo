import { ThisReceiver } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { TokenStorageService } from './services/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  private roles: string[] = [];
  isLoggedIn = false;
  username: string = '';

  constructor(
    private tokenStorageService: TokenStorageService,
    public dialog: MatDialog) {}

  ngOnInit(): void {
    if (localStorage.getItem('agree') !== 'true') {
      const dialogRef = this.dialog.open(InfoDialog, { disableClose: true });
      
      dialogRef.afterClosed().subscribe((result: any) => {
        if (result != true) {
          window.location.replace('https://github.com/m4kpaul/demo');
        } else {
          localStorage.setItem('agree', 'true');
        }
      });
    }

    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;
      this.username = user.username;
    }
  }

  logout() {
    this.tokenStorageService.logout();
    window.location.reload();
  }
}

@Component({
  selector: 'info-dialog',
  templateUrl: 'common/info.dialog.html',
})
export class InfoDialog {}