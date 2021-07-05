import { Injectable } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";

@Injectable()
export class MySnackBar {
    constructor(private _snackBar: MatSnackBar) { }

    openSnackBar(message: string, color: string) {
        this._snackBar.open(message, 'X', {
          horizontalPosition: 'right',
          verticalPosition: 'bottom',
          duration: 5000,
          panelClass: [`${color}-snackbar`]
        });
      }
}