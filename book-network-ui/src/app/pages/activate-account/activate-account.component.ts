import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/services';
import {skipUntil} from 'rxjs';

@Component({
  selector: 'app-activate-account',
  templateUrl: './activate-account.component.html',
  styleUrl: './activate-account.component.scss'
})
export class ActivateAccountComponent {

  message: string ='';
  isOkay: boolean = true;

  submitted: boolean = false;


  constructor(
    private router: Router,
    private authService: AuthenticationService
    
  ) {}

  onCodeCompleted(token: string) {
    this.confirmAccount(token);

  }

  redirectToLogin(){
    this.router.navigate(['activate-account'])
  }

  private confirmAccount(token: string) {
    this.authService.confirm( {
      token
    }).subscribe({
      next:() => {
        this.message = 'Your account has been successuflly activated.\nNow you can process to login' ;
        this.submitted= true;
        this.isOkay=true;
      },
      error: () => {
        this.message ='Token has been expired or invalid'
        this.submitted=true
        this.isOkay= false
      }
    });
  }

}
