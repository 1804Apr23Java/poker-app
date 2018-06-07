import { Injectable } from '@angular/core';
import { UserDecision } from '../models/user-decision.model';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import {Http, RequestOptions, Request, Headers } from '@angular/http';
import { HttpModule } from '@angular/http';

import { map, tap, catchError } from 'rxjs/operators';

import { Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserActionService {

  private headers = new Headers({'content-Type': 'application/json'});
  private httpOptions = new RequestOptions(
    { headers: this.headers, withCredentials: true });

  private httpOptions2 = {
  headers: new HttpHeaders({
    'Access-Control-Allow-Origin': '*',
    'Authorization': 'authkey',
    'userid': '1'
  }),
  withCredentials: true
};

  private url = 'https://pokerapp.cfapps.io/currentHands/action/';

  constructor(private httpClient: HttpClient) { }

  // public postUserDecision(decision: UserDecision): Observable<UserDecision> {
  //
  //   return this.http.post<UserDecision>(this.url + "/" + decision.decision, decision, this.httpOptions);
  //   .pipe(
  //     catchError(this.handleError('addHero'))
  //   );
  // }

  public postUserDecision(decision: UserDecision): Observable<UserDecision> {
    return this.httpClient.post<UserDecision>(this.url + decision.decision, decision, this.httpOptions2);
  }

  // private handleError(error: HttpErrorResponse) {
  //   if (error.error instanceof ErrorEvent) {
  //     // A client-side or network error occurred. Handle it accordingly.
  //     console.error('An error occurred:', error.error.message);
  //   } else {
  //     // The backend returned an unsuccessful response code.
  //     // The response body may contain clues as to what went wrong,
  //     console.error(
  //       `Backend returned code ${error.status}, ` +
  //       `body was: ${error.error}`);
  //   }
  //   // return an observable with a user-facing error message
  //   return throwError(
  //     'Something bad happened; please try again later.');
  // }
}
