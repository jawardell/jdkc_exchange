package tests;

import java.io.File;

import org.junit.*; 

import jdkc_exchange.PersistenceManager;
import jdkc_exchange.SiteManager;


public final class PersistenceManagerTest {

	@Test
	//@Description(value="tests the basic functionality of PersistenceManager constructor")
	public void testConstructor() {
		Assert.assertNotNull(PersistenceManager.class);
	}
	
	@Test
	//@Description(value="tests the basic functionality of save method")
	public void testSave() {
		
		SiteManager sm = new SiteManager();
		
		File file = new File("src/tests/output/pm_save_test");
		PersistenceManager.save(file, sm);
		
		Assert.assertEquals(0, file.length());
			
	}

	@Test
	//@Description(value="tests the basic functionality of read method")
	public void testRead() {
		
		
		File file = new File("src/tests/output/pm_save_test");
		SiteManager sm = PersistenceManager.read(file);
		
		Assert.assertNotNull(sm);
		
	}

}
