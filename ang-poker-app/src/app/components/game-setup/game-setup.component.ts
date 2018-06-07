import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';

// models
import { UserInfo } from '../../models/user-info.model';
import { GameState } from '../../models/game-state.model';

// services
import { NavBarService } from '../../services/nav-bar.service';
import { UserProfileInfoService } from '../../services/user-profile-info.service';

// pipe
import { FilterUsernamePipe } from '../../pipes/filter-username.pipe';

@Component({
  selector: 'app-game-setup',
  templateUrl: './game-setup.component.html',
  styleUrls: ['./game-setup.component.css']
})
export class GameSetupComponent implements OnInit {
  public users: UserInfo;
  public inviteFriendsForm: FormGroup;

  public friendsToInvite: string[] = [];

  constructor(private userInfoService: UserProfileInfoService, private formBulder: FormBuilder, public nav: NavBarService) { }

  getUserInformation(): void {
  this.userInfoService.fetchUserInformation()
    .subscribe(
      (userInfo: UserInfo) => {
        this.users = userInfo;
      },
      error => { console.log(`Error: ${error}`); }
    );
}

  ngOnInit() {
    this.nav.show();
    this.getUserInformation();
    this.inviteFriendsForm = this.formBulder.group({
      // friendsToInvite: [null, Validators.required],
      timeLimit: [null, Validators.required]
    });
  }

  public userAction() {

    const url = 'url';
    const xhr = new XMLHttpRequest();

    xhr.open('POST', url, true);
    xhr.onreadystatechange = function() {
      if (this.readyState === 4 && this.status === 200) {

      }
    };
  }

  public addFriendsToArr(user: UserInfo) {

    if (this.friendsToInvite.includes(user.username)) {
      const index: number = this.friendsToInvite.indexOf(user.username);
      this.friendsToInvite.splice(index, 1);
      return;
    }

    if (this.friendsToInvite.length < 6) {
      this.friendsToInvite.push(user.username);
    }
  }
}
