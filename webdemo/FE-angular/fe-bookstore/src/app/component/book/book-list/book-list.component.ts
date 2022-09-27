import {Component, OnInit} from '@angular/core';
import {Book} from "../../../models/book";
import {BookService} from "../../../service/book.service";
import {Router} from "@angular/router";
import {FormControl, FormGroup} from "@angular/forms";
import {TokenStorageService} from "../../../service/security/token-storage.service";
import {HttpResponse} from "@angular/common/http";

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})

export class BookListComponent implements OnInit {
  bookList: Book[] = [];
  message = false;
  public activeProjectIndex: number | undefined;
  flag = false;
  idClick: number | undefined = 0;
  page = 0;
  keyName = '';
  keyCategory = '';
  first = false;
  last = false;
  totalPage = 0;
  searchForm: FormGroup;
  notChoose = false;


  constructor(private bookService: BookService,
              private tokenStorageService: TokenStorageService,
              private router: Router) {
    this.searchForm = new FormGroup({
      typeSearch: new FormControl(''),
      inputSearch: new FormControl(''),
    });
  }


  ngOnInit(): void {
    this.bookService.getAllBook(this.page, this.keyName, this.keyCategory).subscribe(
      (data: any) => {
        this.bookList = data.content;
        this.first = data.first;
        this.last = (data.pageable.offset + data.pageable.pageSize) >= data.totalElements;
        this.page = data.number;
        this.totalPage = data.totalPages;
      }, err => {
        console.log(err);
      });
    this.message = false;
  }


  public activeProject(index: number, book: Book): void {
    if (this.activeProjectIndex != index) {
      this.flag = true;
    } else {
      this.flag = !this.flag;
    }
    this.activeProjectIndex = index;
    if (this.flag == true) {
      this.idClick = book.id;
    } else {
      this.idClick = 0;
    }
  }


  next() {
    if (this.page < this.totalPage - 1) {
      this.bookService.getAllBook(this.page + 1, this.keyName, this.keyCategory).subscribe(
        (data: any) => {
          console.log(data.first);
          this.bookList = data.content;
          this.page = data.number;
          this.first = data.first;
          this.last = (data.pageable.offset + data.pageable.pageSize) >= data.totalElements;
          this.totalPage = data.totalPages;
        }, err => {
          console.log(err);
          this.message = true;
        }
      );
    }
  }


  previous() {
    if (this.page > 0) {
      this.bookService.getAllBook(this.page - 1, this.keyName, this.keyCategory).subscribe(
        (data: any) => {
          this.bookList = data.content;
          this.page = data.number;
          this.first = data.first;
          this.last = (data.pageable.offset + data.pageable.pageSize) >= data.totalElements;
          this.totalPage = data.totalPages;
        }, err => {
          console.log(err);
          this.message = true;
        }
      );
    }
  }

  goFirstPage() {
    this.bookService.getAllBook(0, this.keyName, this.keyCategory).subscribe(
      (data: any) => {
        this.bookList = data.content;
        this.page = data.number;
        this.first = data.first;
        this.last = (data.pageable.offset + data.pageable.pageSize) >= data.totalElements;
        this.totalPage = data.totalPages;
      }, err => {
        console.log(err);
        this.message = true;
      }
    );
  }

  goLastPage() {
    if (this.page < this.totalPage - 1) {
      this.bookService.getAllBook(this.totalPage - 1, this.keyName, this.keyCategory).subscribe(
        (data: any) => {
          this.bookList = data.content;
          this.page = data.number;
          this.first = data.first;
          this.last = (data.pageable.offset + data.pageable.pageSize) >= data.totalElements;
          this.totalPage = data.totalPages;
        }, err => {
          console.log(err);
          this.message = true;
        }
      );
    }
  }


  search() {
    // @ts-ignore
    const type = this.searchForm.get('typeSearch').value;

    // @ts-ignore
    const input = this.searchForm.get('inputSearch').value;

    if (type === "") {
      this.notChoose = true;
    }
    if (type !== "") {
      this.notChoose = false;
    }

    if (type === 'name') {
      this.bookService.getAllBook(this.page, this.keyName = input.trim(), '').subscribe(
        data => {
          this.message = false;
          this.bookList = data.content;
          this.first = data.first;
          this.last = (data.pageable.offset + data.pageable.pageSize) >= data.totalElements;
          this.totalPage = data.totalPages;
        }, err => {
          console.log(err);
          this.message = true;
        })
    }

    if (type === 'category'){
      console.log(1);
      this.bookService.getAllBook(this.page, '', this.keyCategory = input.trim()).subscribe(
        data => {
          this.message = false;
          this.bookList = data.content;
          this.first = data.first;
          this.last = (data.pageable.offset + data.pageable.pageSize) >= data.totalElements;
          this.totalPage = data.totalPages;
        }, err => {
          console.log(err);
          this.message = true;
        })
    }
  }

  clearAll() {
    // @ts-ignore
    this.searchForm.get('inputSearch').setValue('');
    this.keyName = '';
    this.ngOnInit();
  }

  deleteBook(book: Book, successButton :HTMLButtonElement) {
    this.bookService.deleteBook(book).subscribe(
      () =>{
        successButton.click();
        this.ngOnInit();
      }, (err: HttpResponse<any>) =>{
        console.log(err);
      }
    )
  }
}
