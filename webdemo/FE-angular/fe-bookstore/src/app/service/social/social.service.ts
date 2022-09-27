import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SocialService {

  private baseUrl = 'http://localhost:8080/api/social/';
  constructor(private http: HttpClient) { }

  loginWithGoogle(token: String): Observable<any>{
    // @ts-ignore
    return this.http.post<any>(`${this.baseUrl}google`, {token}).pipe(
      map(
        response => {
          sessionStorage.setItem('token','Bearer ' + response.token);
          return response;
        }
      )
    );
  }
}
