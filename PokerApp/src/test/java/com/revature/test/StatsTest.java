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
//import com.revature.beans.Stats;
//import com.revature.beans.Users;
//import com.revature.repository.StatsRepository;
//import com.revature.service.StatsService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:beans.xml"})
//@Sql("classpath:populatedb.sql")
//public class StatsTest {
//	
//	@Autowired
//	private StatsService ss;
//
//	@Test
//	public void testAddStats() {
//		Users u = new Users();
//		Stats s = new Stats(u, 100, 5, 0);
//		ss.addStat(s);
//	}
//	
//	@Test
//	public void testGetStatsById() {
//		Stats s = ss.getStatByUserId(1);
//		assertNotNull(s);
//	}
//	
//	@Test
//	public void testGetStatsByUsername() {
//		Stats s = ss.getStatsByUsername("km2018");
//		assertNotNull(s);
//	}
//	
//	@Test
//	public void testGetAllStats() {
//		List<Stats> sl = ss.getStats();
//		assertNotNull(sl);
//	}
//}
