package com.revature.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.revature.beans.CurrentHands;
import com.revature.beans.Stats;
import com.revature.beans.Users;

@Repository(value="statsRepository")
@Transactional
@EnableTransactionManagement
public class StatsRepository {
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	UsersRepository ur;
	
	public int addStat(Stats st) {
		Session s = sessionFactory.getCurrentSession();
		int result = (int) s.save(st);
		return result;
	}

	public Stats getStatByUserId(int id) {
		Session s = sessionFactory.getCurrentSession();
		
		Users user = ur.getUserById(id);
		Stats stat = (Stats) s.createCriteria(Stats.class).add(Restrictions.eq("user", user)).uniqueResult();	 
	
		return stat;
	}
	
	public Stats getStatsByUsername(String username) {
		Session s = sessionFactory.getCurrentSession();	
		Users user = ur.getUserByUsername(username);
		Stats stat = (Stats) s.createCriteria(Stats.class).add(Restrictions.eq("user", user)).uniqueResult();	
		return stat;
	}

	public List<Stats> getStats() {
		List<Stats> stats = new ArrayList<Stats>();
		Session s = sessionFactory.getCurrentSession();
		stats = s.createQuery("from Stats").list();
		return stats;
	}

	public void updateStat(Stats st) {
		Session s = sessionFactory.getCurrentSession();
		s.update(st);
	}

	public void deleteStat(Stats st) {
		Session s = sessionFactory.getCurrentSession();
		s.delete(st);
	}

}
