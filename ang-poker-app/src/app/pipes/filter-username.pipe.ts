import { Pipe, PipeTransform } from '@angular/core';
import { UserInfo } from '../models/user-info.model';
@Pipe({
  name: 'filterUsername'
})
export class FilterUsernamePipe implements PipeTransform {

  constructor() {}

  transform(items: UserInfo[], query: string): any {
    if (!items) { return []; }
    if (!query) { return items; }

    query = query.toLowerCase();
    return items.filter(arrValue => {
      return arrValue.username.toLowerCase().includes(query);
    });
  }
}
