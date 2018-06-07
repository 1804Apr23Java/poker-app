//package com.revature.test;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.revature.beans.GameStates;
//import com.revature.repository.GameStatesRepository;
//import com.revature.service.CurrentHandsService;
//import com.revature.service.GameStatesService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:beans.xml"})
//@Sql("classpath:populatedb.sql")
//public class GameStatesTest {
//	
//	private GameStatesRepository gr;
//	
//	@Autowired
//	private GameStatesService gss;
//	
//	@Test
//	public void testInsertGameStates() {
//		GameStates g = new GameStates("", 1, 100, 0, 800, "");
//		gss.addGameState(g);
//	}
//
//	@Test
//	public void testFindGameStatesById() {
//		GameStates g = gr.getGameStateById(1);
//		assertEquals(1, g.getGame_Id());
//	}
//
//	@Test
//	public void testgetGameStates() {
//		List<GameStates> g = gss.getGameStates();
//		assertNotNull(g);
//	}
//	
//	@Test
//	public void testGetUserById() {
//		
//	}
//}
