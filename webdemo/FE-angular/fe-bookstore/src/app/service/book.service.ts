import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {Book} from "../models/book";

@Injectable({
  providedIn: 'root'
})
export class BookService {


  bookApi = 'http://localhost:8080/api/book';

 // @ts-ignore
  private apiBaseUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {
  }

  // @ts-ignore
  getAllBook(page: number,keyName: string, keyCategory: string ): Observable<any> {
    return this.http.get<any>(this.bookApi +'/list' +'?page='+page + '&keyName=' + keyName +'&keyCategory=' + keyCategory);
    }

  deleteBook(book:Book) {
    return this.http.patch<any>(this.bookApi + '/delete/' +  book.id,book);
  }

  createBook(book:Book):Observable<any> {
  return this.http.post<any>(this.bookApi + '/create', book);
  }

  getBookById(id: number):Observable<any> {
    return this.http.get<any>(this.bookApi + '/' + id);
  }

  updateBook(id: number, book:Book): Observable<any>{
    return this.http.patch<any>(this.bookApi + '/update/' +id, book)
  }
}
