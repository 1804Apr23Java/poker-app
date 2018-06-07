//package com.revature.test;
//
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
//import com.revature.beans.CurrentHands;
//import com.revature.service.CurrentHandsService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:beans.xml"})
//@Sql("classpath:populatedb.sql")
//public class CurrentHandsTest {
//	
//	@Autowired
//	private CurrentHandsService chs;
//	
//	@Test
//	public void testGetCurrentHandById() {
//		CurrentHands c = chs.getCurrentHandById(1);
//		assertNotNull(c);
//	}
//	
//	@Test
//	public void testAddCurrentHand() {
//		CurrentHands c = new CurrentHands();
//	}
//	
//	@Test
//	public void testGetCurrentHandByUsername() {
//		CurrentHands c = chs.getCurrentHandByUsername("km2018");
//		assertNotNull(c);
//	}
//	
//	@Test
//	public void testGetCurrentHands() {
//		List<CurrentHands> cl = chs.getCurrentHands();
//		assertNotNull(cl);
//	}
//}
