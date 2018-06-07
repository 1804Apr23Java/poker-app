import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CurrentHand } from '../models/current-hand.model';

@Injectable({
  providedIn: 'root'
})
export class CurrentHandService {

  constructor(private httpClient: HttpClient) { }

  public fetchHandInformation(): Observable<CurrentHand> {
    return this.httpClient.get<CurrentHand>('http://pokerapp.cfapps.io/currentHands/all');
  }
}
