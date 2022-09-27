import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { BookListComponent } from './component/book/book-list/book-list.component';
import { HeaderComponent } from './component/common/header/header.component';
import { FooterComponent } from './component/common/footer/footer.component';
import {RouterOutlet} from "@angular/router";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from "./app-routing.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { LoginComponent } from './component/security/login/login.component';
import {TokenInterceptorService} from "./service/security/token-interceptor.service";
import {
  FacebookLoginProvider,
  GoogleLoginProvider,
  SocialAuthServiceConfig,
  SocialLoginModule
} from "@abacritt/angularx-social-login";
import { BookCreateComponent } from './component/book/book-create/book-create.component';
import { BookEditComponent } from './component/book/book-edit/book-edit.component';
import { HomepageComponent } from './component/common/homepage/homepage.component';
import {CKEditorModule} from "@ckeditor/ckeditor5-angular";
import {environment} from "../environments/environment";
import {AngularFireModule} from "@angular/fire/compat";
import { ForgotPasswordComponent } from './component/security/forgot-password/forgot-password.component';
import {CountdownModule} from "ngx-countdown";
import { ResetPasswordComponent } from './component/security/reset-password/reset-password.component';


@NgModule({
  declarations: [
    AppComponent,
    BookListComponent,
    HeaderComponent,
    FooterComponent,
    LoginComponent,
    BookCreateComponent,
    BookEditComponent,
    HomepageComponent,
    ForgotPasswordComponent,
    ResetPasswordComponent
  ],
  imports: [
    BrowserModule,
    RouterOutlet,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    SocialLoginModule,
    FormsModule,
    AngularFireModule.initializeApp(environment.firebaseConfig),
    CKEditorModule,
    CountdownModule
  ],
  providers: [
    {
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptorService,
    multi: true
  },
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider(
              '798259810440-870tb342l6442geci9gtm7iildpl4677.apps.googleusercontent.com'
            )
          },
          // {
          //   id: FacebookLoginProvider.PROVIDER_ID,
          //   provider: new FacebookLoginProvider('1640008832855470')
          // }
        ]
      } as SocialAuthServiceConfig,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
