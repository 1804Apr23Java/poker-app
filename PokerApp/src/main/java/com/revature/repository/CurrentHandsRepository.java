package com.revature.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.revature.beans.CurrentHands;
import com.revature.beans.Users;

@Repository(value="currentHandsRepository")
@Transactional
@EnableTransactionManagement
public class CurrentHandsRepository {
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	UsersRepository ur;
	
	public int addCurrentHand(CurrentHands h, Users u) {
		Session s = sessionFactory.getCurrentSession();
		h.setUser(u);
		int result = (int) s.save(h);
		return result;
	}

	public CurrentHands getCurrentHandById(int id) {
		Session s = sessionFactory.getCurrentSession();
		Users user = ur.getUserById(id);
		CurrentHands currentHand = (CurrentHands) s.createCriteria(CurrentHands.class).add(Restrictions.eq("user", user)).uniqueResult();	
		return currentHand;
	}
	
	public CurrentHands getCurrentHandByUsername(String username) {
		Session s = sessionFactory.getCurrentSession();	
		Users user = ur.getUserByUsername(username);
		CurrentHands currentHand = (CurrentHands) s.createCriteria(CurrentHands.class).add(Restrictions.eq("user", user)).uniqueResult();	
		return currentHand;
	}

	public List<CurrentHands> getCurrentHands() {
		List<CurrentHands> currentHands = new ArrayList<CurrentHands>();
		Session s = sessionFactory.getCurrentSession();
		currentHands = s.createQuery("from CurrentHands").list();
		return currentHands;
	}

	public void updateCurrentHand(CurrentHands h) {
		Session s = sessionFactory.getCurrentSession();
		s.update(h);
	}

	public void deleteCurrentHand(CurrentHands h) {
		Session s = sessionFactory.getCurrentSession();
		s.delete(h);
	}

}
