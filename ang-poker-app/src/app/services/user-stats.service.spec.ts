import { TestBed, inject } from '@angular/core/testing';

import { UserStatsService } from './user-stats.service';

describe('UserStatsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [UserStatsService]
    });
  });

  it('should be created', inject([UserStatsService], (service: UserStatsService) => {
    expect(service).toBeTruthy();
  }));
});
