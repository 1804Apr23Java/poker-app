import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserInfo } from '../models/user-info.model';
import { GameState } from '../models/game-state.model';



@Injectable({
  providedIn: 'root'
})

export class UserProfileInfoService {

  constructor(private httpClient: HttpClient) { }

  public fetchUserInformation(): Observable<UserInfo> {
    return this.httpClient.get<UserInfo>('http://pokerapp.cfapps.io/users/all');
  }
}
