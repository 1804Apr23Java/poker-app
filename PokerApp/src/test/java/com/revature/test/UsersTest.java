//package com.revature.test;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.revature.beans.GameStates;
//import com.revature.beans.Users;
//import com.revature.repository.UsersRepository;
//import com.revature.service.UsersService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:beans.xml"})
//@Sql("classpath:populatedb.sql")
//public class UsersTest {
//	
//	private UsersRepository ur;
//	
//	@Autowired
//	private UsersService us;
//	
//	@Test
//	public void testInsertUsers() {
//		GameStates g = new GameStates("", 0, 0, 0, 0, "");
//		Users u = new Users(g, "jm@gmail.com", "password", "JJ", "Mubang", "jm2018", false);
//		us.addUser(u);
//		assertNotNull(us.getUserByUsername("jm2018"));
//	}
//	
//	@Test
//	public void testGetUsersById() {
//		Users u = us.getUserById(1);
//		assertEquals(1, u.getuserId());
//	}
//	
//	@Test
//	public void testGetUsers() {
//		List<Users> ul = us.getAllUsers();
//		assertNotNull(ul);
//	}
//	
//	@Test
//	public void testGetUsersByUsername() {
//		Users u = us.getUserByUsername("km2018");
//		assertEquals("Kevin", u.getFirstName());
//	}
//	
//	@Test
//	public void testGetUsersWithGameId() {
//		List<Users> ul = us.getUsersWithGameId(1);
//		assertNotNull(ul);
//	}
//}