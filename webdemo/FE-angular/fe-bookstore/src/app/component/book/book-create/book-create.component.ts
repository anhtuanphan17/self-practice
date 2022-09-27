import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Category} from "../../../models/category";
import {BookService} from "../../../service/book.service";
import {CategoryService} from "../../../service/category.service";
import {Router} from "@angular/router";
import {finalize} from "rxjs";
import {AngularFireStorage} from "@angular/fire/compat/storage";

@Component({
  selector: 'app-book-create',
  templateUrl: './book-create.component.html',
  styleUrls: ['./book-create.component.css']
})
export class BookCreateComponent implements OnInit {

  imgVip = 'https://icon-library.com/images/photograph-icon/photograph-icon-17.jpg';
  bookForm: FormGroup;
  selectedImage: any = null;
  flagCheckImage!: boolean ;
  alertImage = '';
  flag = false;
  bookName!: string;
  errorProductName!: string;
  categoryList: Category[] = [];
  // tslint:disable-next-line:variable-name
  product_price = '';
  // price: any;
  constructor(private bookService: BookService,
              private categoryService: CategoryService,
              private router: Router,
              // private alertService: AlertService,
              @Inject(AngularFireStorage) private storage: AngularFireStorage) {
    this.bookForm = new FormGroup({
      id: new FormControl(''),
      name: new FormControl('', [Validators.required, Validators.maxLength(255)]),
      price: new FormControl(''),
      image: new FormControl(''),
      description: new FormControl('', [Validators.required]),
      categoryDto: new FormControl('', Validators.compose([Validators.required])),
    });
  }

  comparefn(t1: Category, t2: Category): boolean {
    return t1 && t2 ? t1.id === t2.id : t1 === t2;
  }
  ngOnInit(): void {
    // this.productForm.valueChanges.subscribe(form => {
    //   if (form.price) {
    //     this.productForm.patchValue({
    //       price: this.currencyPipe.transform(form.price.replace(/\D/g, '').replace(/^0+/, ''), 'USD', 'symbol', '1.0-0')
    //     }, {emitEvent: false});
    //   }
    // });

    console.log(this.validateImange(this.imgVip));
    this.bookForm.controls['categoryDto'].setValue('');
    this.categoryService.getAllCategory().subscribe(data => {
      this.categoryList = data;
    });
  }

  onImageChangeFromFile($event: any) {
    if ($event.target.files && $event.target.files[0]) {
      const file = $event.target.files[0];
      console.log(file);
      console.log(file.type);
      if (file.type == 'image/jpeg') {
        console.log('correct');

      // } else {
      //   this.bookForm.reset();
      //   this.bookForm.controls.imageInput.setValidators([Validators.required]);
      //   this.bookForm.get('imageInput').updateValueAndValidity();
      }
    }
  }

  validateImange(e :any): boolean {
    return e == 'https://t4.ftcdn.net/jpg/04/70/29/97/360_F_470299797_UD0eoVMMSUbHCcNJCdv2t8B2g1GVqYgs.jpg';
  }

  /*
      Created by TuanPA
      Date: 9:08 3/6/2022
  */

  showPreview(event: any) {
    this.selectedImage = event.target.files[0];
    const ext = this.selectedImage.name.substring(this.selectedImage.name.length - 3);
    console.log(ext);
    if (this.selectedImage.name != 'jpg') {
    }
    if (this.selectedImage) {
      this.alertImage = '';
    }
    const reader = new FileReader();
    reader.readAsDataURL(this.selectedImage);
    reader.onload = e => {
      this.imgVip = reader.result as string;
    };
  }


  CommaFormatted(event:any) {
    // skip for arrow keys
    if (event.which >= 37 && event.which <= 40) {
      return;
    }
    // format number
    if (this.product_price) {
      this.product_price = this.product_price.replace(/\D/g, '')
        .replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }
  }

  numberCheck(args:any) {
    if (args.key === 'e' || args.key === '+' || args.key === '-') {
      return false;
    } else {
      return true;
    }
  }

  /*
      Created by TuanPA
      Date: 9:08 3/6/2022
  */
  save(errorModalBtn: HTMLButtonElement, successButton: HTMLButtonElement) {
    let arr = this.product_price.split(',');
    let money = '';
    for (let a of arr) {
      money += a;
    }
    money = money.trim();
    this.bookForm.controls['price'].setValue(money);
    // if (this.validateImange(this.imgVip)){
    //   this.flagCheckImage = true;
    //   this.productForm.controls.image.setErrors({existed: 'Empty! Please input!'});
    // }
    if (this.bookForm.invalid) {
      if (!this.selectedImage) {
        this.alertImage = 'Vui lòng nhập ảnh';
      }
      if (this.bookForm.controls['name'].value == '') {
        this.bookForm.controls['name'].setErrors({empty: 'Empty! Please input!'});
      }
      if (this.bookForm.controls['price'].value == '') {
        this.bookForm.controls['price'].setErrors({empty: 'Empty! Please input!'});
      }
      // if (this.productForm.controls.image.value == null) {
      //   this.productForm.controls.image.setErrors({empty: 'Empty! Please input!'});
      // }
// @ts-ignore: Object is possibly 'null'.
      if (this.bookForm.controls['categoryDto'].value == '') {
        this.bookForm.controls['categoryDto'].setErrors({empty: 'Empty! Please input!'});
      }
    } else {
      this.alertImage = '';
      const nameImg = '/PD-' + this.selectedImage.name + '.jpg';
      const fileRef = this.storage.ref(nameImg);
      this.storage.upload(nameImg, this.selectedImage).snapshotChanges().pipe(
        finalize(() => {
          fileRef.getDownloadURL().subscribe((url) => {
            this.bookForm.patchValue({image: url});
            this.flagCheckImage = false;
            console.log(this.bookForm.value);
            this.bookService.createBook(this.bookForm.value).subscribe(() => {
                this.bookForm.reset();
                successButton.click();
                console.log('success');
              }, error => {
                console.log(this.bookForm.value);
              console.log(error);
                // tslint:disable-next-line:no-conditional-assignment
                // if (this.errorProductName = error.error.errorMap.name) {
                  console.log(this.errorProductName);
                  errorModalBtn.click();
                // }
              }
            );
          });
        })
      ).subscribe();
    }
  }


  /*
    Created by TuanPA
    Date: 9:08 3/6/2022
*/
  get name() {
    return this.bookForm.get('name');
  }
  get price() {
    return this.bookForm.get('price');
  }
  get image() {
    return this.bookForm.get('image');
  }
  get description() {
    return this.bookForm.get('description');
  }
  get categoryDto() {
    return this.bookForm.get('categoryDto');
  }

  validateCategory(target: any) {
    if (this.bookForm.controls['categoryDto'].value != '') {
      this.bookForm.controls['categoryDto'].setErrors({empty: null});
      this.bookForm.controls['categoryDto'].updateValueAndValidity();
    } else {
      this.bookForm.controls['categoryDto'].setErrors({empty: 'Empty! Please input!'});
    }
  }

  checkValidatePrice(input: any) {
    if (input.target.value != '') {
      this.bookForm.controls['price'].setErrors({empty: null});
      this.bookForm.controls['categoryDto'].updateValueAndValidity();
    } else {
      this.bookForm.controls['price'].setErrors({empty: 'Empty! Please input!'});
    }
  }

}
