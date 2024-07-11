import { Component } from '@angular/core';
import { Route, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  LoginObject: Login;

  constructor(private router: Router){

    this.LoginObject= new Login();


  }

  onLogin() : void{
    if (this.LoginObject.username === 'admin'&& this.LoginObject.password === 'admin'){

      alert("Login Successful");
      this.router.navigateByUrl('/dashboard');

    }else{
      alert("Invalid username or password")
    }
  }

}

export class Login {

username: string;
password: string;

constructor(){

  this.username= "";
  this.password= "";

}

}