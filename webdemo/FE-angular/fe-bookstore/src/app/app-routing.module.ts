import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {BookListComponent} from "./component/book/book-list/book-list.component";
import {LoginComponent} from "./component/security/login/login.component";
import {HomepageComponent} from "./component/common/homepage/homepage.component";
import {BookCreateComponent} from "./component/book/book-create/book-create.component";
import {BookEditComponent} from "./component/book/book-edit/book-edit.component";
import {ForgotPasswordComponent} from "./component/security/forgot-password/forgot-password.component";
import {ResetPasswordComponent} from "./component/security/reset-password/reset-password.component";


const routes: Routes = [
  {path: '', component: HomepageComponent},
  {path: 'login', component: LoginComponent},
  {path: 'forgot-password', component: ForgotPasswordComponent},
  {path: 'reset-password', component: ResetPasswordComponent},
  {path: 'list',component: BookListComponent},
  {path: 'book/create',component: BookCreateComponent},
  {path: 'book/edit/:id',component: BookEditComponent}
];

@NgModule({

  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
