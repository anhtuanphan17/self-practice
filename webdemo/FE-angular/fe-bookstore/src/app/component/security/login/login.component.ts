import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {SecurityService} from "../../../service/security/security.service";
import {TokenStorageService} from "../../../service/security/token-storage.service";
import {ShareService} from "../../../service/security/share.service";
import {GoogleLoginProvider, SocialAuthService, SocialUser} from "@abacritt/angularx-social-login";
import {SocialService} from "../../../service/social/social.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.maxLength(50)]),
    password: new FormControl('', [Validators.required, Validators.maxLength(100)]),
    remember_me: new FormControl()
  });

  roles: string[] = [];
  errorMessage = '';
  username: string | undefined;
  showPassword: any;
  socialUser!: SocialUser;
  isLoggedin?: boolean;


  constructor(private securityService: SecurityService,
              private tokenStorageService: TokenStorageService,
              private shareService: ShareService,
              private router: Router,
              private socialAuthService: SocialAuthService,
              private socialService: SocialService) {

  }

  ngOnInit(): void {
    this.socialAuthService.authState.subscribe((user) => {
      this.socialUser = user;
      this.isLoggedin = user != null;
      console.log(this.socialUser);
      this.socialService.loginWithGoogle(user.idToken).subscribe(
        res => {
          console.log(res);
          this.tokenStorageService.saveTokenSession(res.token);
          this.tokenStorageService.saveUserSession(res);
          this.tokenStorageService.setRememberFlag();
          this.securityService.isLoggedIn = true;
          this.username = this.tokenStorageService.getUser().username;
          this.roles = this.tokenStorageService.getUser().roles;
          this.shareService.sendClickEvent();
          this.router.navigate(['/list']);

        });
    })
  }

  toggleShowPassword() {
    this.showPassword = !this.showPassword;
  }

  login(errorModalBtn: HTMLButtonElement, closeErrorModal: HTMLButtonElement) {
    this.securityService.login(this.loginForm.value).subscribe(data => {
      if (this.loginForm.value.remember_me) {
        this.tokenStorageService.saveTokenLocal(data.token);
        this.tokenStorageService.saveUserLocal(data);
        this.tokenStorageService.setRememberFlag();
      } else {
        this.tokenStorageService.saveTokenSession(data.token);
        this.tokenStorageService.saveUserSession(data);
      }
      this.securityService.isLoggedIn = true;
      this.username = this.tokenStorageService.getUser().username;
      this.roles = this.tokenStorageService.getUser().roles;
      this.loginForm.reset();
      this.shareService.sendClickEvent();
      this.router.navigate(['/list']);
    }, error => {
      if (error.status === 403) {
        this.errorMessage = 'Sai tên đăng nhập hoặc mật khẩu.';
        // if (this.password?.valueChanges) {
        //   this.errorMessage = '';
        // }
        this.securityService.isLoggedIn = false;
      } else if (error.status === 0) {
        errorModalBtn.click();
        // tslint:disable-next-line:only-arrow-functions
        setTimeout(function () {
          closeErrorModal.click();
        }, 3000);
        this.securityService.isLoggedIn = false;
      }
    });
  }

  get usernameForm() {
    return this.loginForm.get('username');
  }

  get password() {
    return this.loginForm.get('password');
  }

  loginWithGoogle() {
    console.log('haha');
    this.socialAuthService.signIn(GoogleLoginProvider.PROVIDER_ID);
  }

  logOut(): void {
    this.socialAuthService.signOut();
  }
}
