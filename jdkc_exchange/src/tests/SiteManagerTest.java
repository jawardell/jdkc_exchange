package tests;


import java.time.LocalDateTime;

import org.junit.*;  
import jdkc_exchange.SiteManager;


public final class SiteManagerTest {

	@Test
	////@Description(value="tests the basic functionality of SiteManager constructor")
	public void testConstructor() {
		
		SiteManager sm = new SiteManager();
		Assert.assertNotNull(sm);
		
	}
	
	@Test
	////@Description(value="tests the basic functionality of getMembers method")
	public void testGetMembers() {
		
		SiteManager sm = new SiteManager();
		
		Assert.assertEquals(0, sm.getMembers().size());

	}
	
	@Test
	////@Description(value="tests the basic functionality of addMember method")
	public void testAddMember() {
		
		SiteManager sm = new SiteManager();
		LocalDateTime date = LocalDateTime.now();
		sm.addMember("Christian", "Gerhardt", "clg", "clgerhardt@vsu", date);
		
		Assert.assertEquals(1, sm.getMembers().size());
		
	}

	
	@Test
	////@Description(value="tests the basic functionality of getMember method")
	public void testGetMember() {
		
		SiteManager sm = new SiteManager();
		LocalDateTime date = LocalDateTime.now();
		sm.addMember("Christian", "Gerhardt", "clg", "clgerhardt@vsu", date);
		
		Assert.assertEquals("clgerhardt@vsu", sm.getMember("clgerhardt@vsu").getEmail());
		
	}
	
	
	@Test
	//@Description(value="tests the basic functionality of OVERLOADED getMembers method")
	public void testGetMembersOverloaded() {
		
		SiteManager sm = new SiteManager();
		LocalDateTime date = LocalDateTime.now();
		sm.addMember("Christian", "Gerhardt", "clg", "clgerhardt@vsu", date);
		sm.addMember("Christian", "Gerhardt", "ger", "lieble@vsu", date);
		
		Assert.assertEquals(2,sm.getMembers("ger").size());
			
		
	}
	
	@Test
	//@Description(value="tests the basic functionality of getGroups method")
	public void testGetGroups() {
		
		SiteManager sm = new SiteManager();
		Assert.assertEquals(0,sm.getGroups().size());
		
	}
	
	@Test
	//@Description(value="tests the basic functionality of addGroup method")
	public void testAddGroup() {
		
		SiteManager sm = new SiteManager();
		LocalDateTime date = LocalDateTime.now();
		sm.addGroup("Soccer Club", "This is the soccer club", date);
		Assert.assertEquals(1,sm.getGroups().size());
			
		
	}
	
	
	@Test
	//@Description(value="tests the basic functionality of getGroup method")
	public void testGetGroup() {
		
		SiteManager sm = new SiteManager();
		LocalDateTime date = LocalDateTime.now();
		sm.addGroup("Soccer Club", "This is the soccer club", date);
		Assert.assertEquals("Soccer Club",sm.getGroup("Soccer Club").getTitle());
		
	}
	

	
	@Test
	//@Description(value="tests the basic functionality of getGroups OVERLOADED method")
	public void testGetGroupsOverloaded() {
		
		SiteManager sm = new SiteManager();
		LocalDateTime date = LocalDateTime.now();
		sm.addGroup("Soccer Club", "This is the soccer club", date);
		Assert.assertEquals(1,sm.getGroups("lub").size());
		
	}
	
	@Test
	//@Description(value="tests the basic functionality of getPopularGroups method")
	public void testGetPopularGroups() {
		
		SiteManager sm = new SiteManager();
		LocalDateTime date = LocalDateTime.now();
		sm.addGroup("Soccer Club", "This is the soccer club", date);
		Assert.assertEquals(1,sm.getPopularGroups(2).size());
		
		
	}
	
	@Test
	//@Description(value="tests the basic functionality of getActiveGroups method")
	public void testGetActiveGroups() {
		
		SiteManager sm = new SiteManager();
		LocalDateTime date = LocalDateTime.now();
		sm.addGroup("Soccer Club", "This is the soccer club", date);
		Assert.assertEquals(1,sm.getActiveGroups(15).size());
		
		
	}
	
	@Test
	//@Description(value="tests the basic functionality of getActiveMembers method")
	public void testGetActiveMembers() {
		
		SiteManager sm = new SiteManager();
		LocalDateTime date = LocalDateTime.now();
		sm.addGroup("Soccer Club", "This is the soccer club", date);
		Assert.assertEquals(0,sm.getActiveMembers(3).size());
		
	}
	
	
}
