import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from "../../../service/security/token-storage.service";
import {ShareService} from "../../../service/security/share.service";
import {Router} from "@angular/router";
import {SocialAuthService} from "@abacritt/angularx-social-login";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})

export class HeaderComponent implements OnInit {

  username: string | undefined;
  imageLink: string | undefined;
  // @ts-ignore
  idPatient: number;
  currentUser: string | undefined;
  // @ts-ignore
  role: string;
  isLoggedIn = false;

  constructor(private tokenStorageService: TokenStorageService,
              private shareService: ShareService,
              private router: Router,
              private socialAuthService: SocialAuthService,
  ) {
    this.shareService.getClickEvent().subscribe(() => {
      this.loadHeader();
    });
  }

  ngOnInit(): void {
    // this.loadHeader();
  }

  loadHeader(): void {
    if (this.tokenStorageService.getToken()) {
      this.currentUser = this.tokenStorageService.getUser().username;
      this.role = this.tokenStorageService.getUser().roles[0];
      this.username = this.tokenStorageService.getUser().username;
      this.imageLink = this.tokenStorageService.getUser().imageLink;
    }
    this.isLoggedIn = this.username != null;
  }

  logOut() {
    this.tokenStorageService.signOut();
    this.socialAuthService.signOut();
    this.isLoggedIn = !this.isLoggedIn;
    this.router.navigate(['/login']);
  }

}
