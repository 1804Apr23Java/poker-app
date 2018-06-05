package com.revature.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.CurrentHands;
import com.revature.beans.Stats;
import com.revature.repository.StatsRepository;

@Service(value="statsService")
public class StatsService {

	@Autowired
	StatsRepository sr;
	
	public void addStat(Stats st) {
		sr.addStat(st);
	}
	
	public Stats getStatById(int id) {
		Stats st = sr.getStatById(id);
		return st;
	}
	
	public List<Stats> getStats() {
		List<Stats> stats = sr.getStats();
		return stats;
	}
	
	public void updateStat(Stats st) {
		sr.updateStat(st);
	}
	
	public void deleteStat(Stats st) {
		sr.deleteStat(st);
	}
}