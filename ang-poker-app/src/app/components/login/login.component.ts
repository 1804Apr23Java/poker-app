import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';

// services
import { NavBarService } from '../../services/nav-bar.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  message: String = 'Logout Successful';
  constructor(private formBulder: FormBuilder, public nav: NavBarService) { }

  ngOnInit() {
    this.nav.hide();
    this.loginForm = this.formBulder.group({
      username: [null, Validators.required],
      password: [null, Validators.required]
    });
  }
}
