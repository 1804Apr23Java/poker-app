import { Component, OnInit } from '@angular/core';
import { UserStatsService } from '../../services/user-stats.service';
import { GameState } from '../../models/game-state.model';
import { Statistics } from '../../models/statistics.model';
import { UserInfo } from '../../models/user-info.model';
import { NavBarService } from '../../services/nav-bar.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})

export class UserProfileComponent implements OnInit {

  public gameState: GameState = null;
  public userInfo: Statistics;

  constructor(private userStatsService: UserStatsService, public nav: NavBarService) { }

  getUserInformation(): void {
    this.userStatsService.fetchStatsInformation()
      .subscribe(
        (userInfo: Statistics) => {
          this.userInfo = userInfo;
          console.log(this.userInfo);
        },
        error => { console.log(error); }
      );
  }

  ngOnInit() {
    this.nav.show();
    this.getUserInformation();
  }

}
