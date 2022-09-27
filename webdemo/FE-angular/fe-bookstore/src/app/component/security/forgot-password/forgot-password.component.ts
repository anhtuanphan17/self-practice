import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {CountdownComponent, CountdownConfig} from "ngx-countdown";
import {SecurityService} from "../../../service/security/security.service";
import {Router} from "@angular/router";
import {DataService} from "../../../service/common/data.service";

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {
  config: CountdownConfig = {
    leftTime: 60,
    formatDate: ({ date }) => `${date / 1000}`,
    demand: true
  };

  confirmCode!: string;
  errorMessage!: string;
  notFoundMessage!: string;
  spinFlag = false;
  countTimeOut: any;

  usernameForm: FormGroup = new FormGroup({
    email: new FormControl(''),
    code: new FormControl(''),
    token: new FormControl(''),
  })

  constructor(private securityService:SecurityService,
              private router: Router,
              private dataService: DataService) { }

  ngOnInit(): void {
  }

  submit(openSuccessModalBtn: HTMLButtonElement, countdown: CountdownComponent,
         closeModal: HTMLButtonElement, errorModalBtn: HTMLButtonElement, resetCodeBtn: HTMLButtonElement) {
    this.securityService.findAccountByEmail(this.usernameForm.value.email).subscribe(() => {
        openSuccessModalBtn.click();
        countdown.resume();
        this.spinFlag = false;
        this.dataService.sendData(this.usernameForm.value.email);
        this.router.navigate(['/reset-password'])
        // // tslint:disable-next-line:only-arrow-functions
        // this.countTimeOut = setTimeout(function () {
        //   closeModal.click();
        //   // tslint:disable-next-line:only-arrow-functions
        //   setTimeout(function () {
        //     errorModalBtn.click();
        //   }, 500);
        //   countdown.restart();
        //   resetCodeBtn.click();
        // }, 60000);
      },
        error => {
        this.spinFlag = false;
        this.notFoundMessage = 'Không tìm thấy tài khoản của bạn.';
      });
  }

  refreshCode(countdowm: CountdownComponent) {
    this.securityService.refreshChangePasswordCode(this.usernameForm.value.email).subscribe(res => {
      console.log('refresh success');
      countdowm.restart();
      this.confirmCode = '';
      this.notFoundMessage = '';
      clearTimeout(this.countTimeOut);
    }, error => {
      console.log('refresh false');
    });
  }

  checkCode(closeModal: HTMLButtonElement) {
    // @ts-ignore
    this.usernameForm.controls.code.setValue(this.confirmCode);
    this.securityService.checkChangePasswordCode(this.usernameForm.value).subscribe(res => {
      closeModal.click();
      this.router.navigate(['/reset-password']);
    }, error => {
      this.errorMessage = 'Mã xác nhận không hợp lệ';
    });
  }

  changeSpinFlag() {
    this.spinFlag = true;
    this.notFoundMessage = '';
  }

}
