import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";

@Injectable()
export class AppService {

    authenticated = true;

    constructor(private http: HttpClient) {}

    authenticate(credentials: { username: string; password: string; } | undefined, callback: (() => any) | undefined) {

        const headers = new HttpHeaders(credentials ? {
            authorization : 'Basic ' + btoa(credentials.username + ':' + credentials.password)
        } : {});

        this.http.get('http://localhost:9000/user', {headers: headers}).subscribe(response => {
            if ((response as any).name) {
                this.authenticated = true;
            } else {
                this.authenticated = false;
            }
            return callback && callback();
        })
    }
}