import { NgModule, Pipe } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
// components

import { RegisterComponent } from '../components/register/register.component';
import { LoginComponent } from '../components/login/login.component';
import { NavbarComponent } from '../components/navbar/navbar.component';
import { HomepageComponent } from '../components/homepage/homepage.component';
import { UserProfileComponent } from '../components/user-profile/user-profile.component';
import { EditProfileComponent } from '../components/edit-profile/edit-profile.component';
import { EditPasswordComponent } from '../components/edit-password/edit-password.component';
import { GameSetupComponent } from '../components/game-setup/game-setup.component';
import { GameboardComponent } from '../components/gameboard/gameboard.component';

const appRoutes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'home', component: HomepageComponent },
  { path: 'profile', component: UserProfileComponent },
  { path: 'edit-email', component: EditProfileComponent },
  { path: 'edit-password', component: EditPasswordComponent },
  { path: 'create-game', component: GameSetupComponent },
  { path: 'gameboard', component: GameboardComponent }

];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(appRoutes, {useHash: false})
  ],
  exports: [ RouterModule ],
  declarations: []
})
export class AppRoutingModule { }
