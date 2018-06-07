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

import com.revature.beans.Admins;

@Repository(value="adminsRepository")
@Transactional
@EnableTransactionManagement
public class AdminsRepository {

	@Autowired
	SessionFactory sessionFactory;
	
	public int addAdmin(Admins a) {
		Session s = sessionFactory.getCurrentSession();
		int result = (int) s.save(a);
		return result;
	}

	public Admins getAdminById(int id) {
		Session s = sessionFactory.getCurrentSession();
		Admins a = (Admins) s.get(Admins.class, id);
		return a;
	}

	public List<Admins> getAdmins() {
		List<Admins> admins = new ArrayList<Admins>();
		Session s = sessionFactory.getCurrentSession();
		admins = s.createQuery("from Admins").list();
		return admins;
	}

	public void updateAdmin(Admins a) {
		Session s = sessionFactory.getCurrentSession();
		s.update(a);
	}

	public void deleteAdmin(Admins a) {
		Session s = sessionFactory.getCurrentSession();
		s.delete(a);
	}

}
