package com.revature.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.revature.beans.CurrentHands;
import com.revature.beans.GameStates;

@Repository(value="gameStatesRepository")
@Transactional
@EnableTransactionManagement
public class GameStatesRepository {
	
	@Autowired
	SessionFactory sessionFactory;
	
	public int addGameState(GameStates g) {
		Session s = sessionFactory.getCurrentSession();
		int result = (int) s.save(g);
		return result;
	}

	public GameStates getGameStateById(int id) {
		Session s = sessionFactory.getCurrentSession();
		GameStates g = (GameStates) s.get(GameStates.class, id);
		return g;
	}

	public List<GameStates> getGameStates() {
		List<GameStates> gameStates = new ArrayList<GameStates>();
		Session s = sessionFactory.getCurrentSession();
		gameStates = s.createQuery("from GameStates").list();
		return gameStates;
	}

	public void updateGameState(GameStates g) {
		Session s = sessionFactory.getCurrentSession();
		s.update(g);
	}

	public void deleteGameState(GameStates g) {
		Session s = sessionFactory.getCurrentSession();
		s.delete(g);
	}
	
	public void transferWinnings(GameStates g, CurrentHands c) {
		Session s = sessionFactory.getCurrentSession();
		c.setWinnings(c.getWinnings() + g.getPot());
		s.update(c);
	}
	
	public int getCurrentTurn(GameStates g) {
		return g.getCurrentTurn();
	}

}
