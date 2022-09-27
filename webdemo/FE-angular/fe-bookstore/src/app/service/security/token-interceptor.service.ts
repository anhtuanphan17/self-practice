import { Injectable } from '@angular/core';
import {TokenStorageService} from "./token-storage.service";
import {HttpInterceptor} from "@angular/common/http";

@Injectable()
export class TokenInterceptorService implements HttpInterceptor{
  constructor(private tokenStorageService: TokenStorageService) {
  }
  token: any;
  intercept(req :any, next: any) {
    let authReq = req;
    if (this.tokenStorageService.getUser() != null) {
      this.token = this.tokenStorageService.getUser().token;
    } else {
      this.token = '';
    }
    if (this.token != null) {
      authReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${this.token}`,
          'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,PATCH,OPTIONS'
        },
      });
    }
    return next.handle(authReq);
  }
}
