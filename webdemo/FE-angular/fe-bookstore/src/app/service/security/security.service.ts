import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  // @ts-ignore
  isLoggedIn: boolean;
  securityApi = 'http://localhost:8080/api/public';

  constructor(private http: HttpClient) {
  }

  login(obj: any): Observable<any> {
    return this.http.post('http://localhost:8080/api/public/login', obj);
  }

  findAccountByEmail(email: String): Observable<any> {
    return this.http.post<any>(this.securityApi + '/find-by-email', email);
  }

  checkChangePasswordCode(checkVerificationCodeRequest: any): Observable<any> {
    return this.http.post<any>(this.securityApi + '/check-verificationCode', checkVerificationCodeRequest);
  }

  resetPassword(resetPasswordRequest: any):Observable<any> {
  return this.http.patch<any>(this.securityApi + '/reset-password',resetPasswordRequest);
  }

  refreshChangePasswordCode(email: String):Observable<any> {
    return this.http.patch<any>(this.securityApi + '/refresh-code',email);
  }
}
