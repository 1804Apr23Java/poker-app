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

import com.revature.beans.GameStates;
import com.revature.beans.Stats;
import com.revature.beans.Users;

@Repository(value = "usersRepository")
@Transactional
@EnableTransactionManagement
public class UsersRepository {

	@Autowired
	SessionFactory sessionFactory;

	// Adds an empty game state
	// Default game state for a new user
	GameStates g = new GameStates(-1, "", 0, 0, 0, 0, "");

	public int addUser(Users u) {
		Session s = sessionFactory.getCurrentSession();

		u.setGameStates(g);

		int result = (int) s.save(u);
		System.out.println(result);
		return result;
	}

	public Users getUserById(int id) {
		Session s = sessionFactory.getCurrentSession();
		Users u = (Users) s.get(Users.class, id);
		return u;
	}

	public List<Users> getAllUsers() {
		List<Users> users = new ArrayList<Users>();
		Session s = sessionFactory.getCurrentSession();
		users = s.createQuery("from Users").list();
		return users;
	}
	
	public Users getUserByUsername(String username) {
		Session s = sessionFactory.getCurrentSession();		
		Users user = (Users) s.createCriteria(Users.class).add(Restrictions.eq("username", username)).uniqueResult();	
		return user;
	}

	public void updateUser(Users u) {
		Session s = sessionFactory.getCurrentSession();
		s.update(u);
	}

	public void deleteUser(Users u) {
		Session s = sessionFactory.getCurrentSession();
		s.delete(u);
	}

	public ArrayList<Users> getUsersWithGameId(int id) {
		List<Users> users = this.getAllUsers();
		ArrayList<Users> usersInGame = new ArrayList<>();
		for (Users u : users) {
			if (u.getGameStates().getGame_Id() == id) {
				usersInGame.add(u);
			}
		}
		return usersInGame;
	}

}
