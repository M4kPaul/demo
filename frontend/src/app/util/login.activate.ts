import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from "@angular/router";
import { Observable } from "rxjs";
import { TokenStorageService } from "../services/token-storage.service";

@Injectable()
export class LoginActivate implements CanActivate {
  constructor(private tokenStorageService: TokenStorageService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean>|Promise<boolean>|boolean {
    if (!this.tokenStorageService.getToken()) {
      this.router.navigate(['login']);
    }
    return true;
  }
}