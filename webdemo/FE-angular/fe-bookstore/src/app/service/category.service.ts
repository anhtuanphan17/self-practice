import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  bookApi = 'http://localhost:8080/api/book';

  // @ts-ignore
  private apiBaseUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {
  }

  // @ts-ignore
  getAllCategory(): Observable<any> {
    return this.http.get<any>(this.bookApi + '/category');

  }
}
