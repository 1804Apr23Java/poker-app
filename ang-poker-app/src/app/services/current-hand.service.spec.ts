import { TestBed, inject } from '@angular/core/testing';

import { CurrentHandService } from './current-hand.service';

describe('CurrentHandService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CurrentHandService]
    });
  });

  it('should be created', inject([CurrentHandService], (service: CurrentHandService) => {
    expect(service).toBeTruthy();
  }));
});
