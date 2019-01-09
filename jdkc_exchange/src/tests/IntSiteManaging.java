package tests;

import java.time.LocalDateTime;  
import java.util.*;

import org.junit.*;
import jdkc_exchange.*;


/**
 * This is an integration test which assesses the s2 feature of 
 * "SiteManaging" AKA, creating aggregates of classes/units.
 * The SiteManager should successfully create aggregates with 
 * the expected associations and correct multiplicities.
 * The Site Manager should correctly implement filters against 
 * duplicates and other logical/runtime hazards. 
 * The Site Manager should correctly sort its collections.
 * Finally, the methods should work as expected and the units 
 * should work together as expected.
 * @version s2.0
 */
public class IntSiteManaging {
	@Test
	//@Description("tests the behavior of adding members")
	public void testAddingMembers() {
		/**
		 * Does the member get correctly added? 
		 * Does the SM prevent duplicates? 
		 * Does the SM prevent nulls? 
		 * What else can we check here?
		 */
		
		SiteManager sm = new SiteManager();
		
		sm.addMember("Joanne", "Wardell", "jawardell", "jawardell@vsu", LocalDateTime.now());
		sm.addMember("Joanne", "Wardell", "jawardell", "jawardell@vsu", LocalDateTime.now());
		
		//did not add the duplicate member
		Assert.assertEquals(1, sm.getMembers().size());
		
		sm.addMember(null, null, null, null, null);
		
		//did not add the null member
		Assert.assertEquals(1, sm.getMembers().size());
		
		//the member is there
		Member m = new Member("Joanne", "Wardell", "jawardell", "jawardell@vsu", LocalDateTime.now());
		Assert.assertEquals(m.getEmail(), sm.getMembers().get(0).getEmail());
		
	}
	
	@Test
	//@Description("tests the behavior of getting a member")
	public void testGettingAMember() {
		/**
		 * Can we retrieve the correct Member? 
		 * What happens with an empty string as an argument?
		 * What happens with a null argument?
		 * Am I allowed to retrieve a someone with a
		 * 		 user name that doesn't exist?
		 * What else can we check here?
		 */
		
		
		SiteManager sm = new SiteManager();
		
		LocalDateTime d = LocalDateTime.now();
		sm.addMember("Joanne", "Wardell", "jawardell", "jawardell@vsu", d);
		
		Assert.assertEquals("Joanne", sm.getMember("jawardell@vsu").getFirstName());
		Assert.assertEquals("Wardell", sm.getMember("jawardell@vsu").getLastName());
		Assert.assertEquals("jawardell", sm.getMember("jawardell@vsu").getScreenName());
		Assert.assertEquals("jawardell@vsu", sm.getMember("jawardell@vsu").getEmail());
		Assert.assertEquals(d, sm.getMember("jawardell@vsu").getDateCreated());
		
		Assert.assertEquals(null, sm.getMember(null));
		
		
		Assert.assertEquals(null, sm.getMember("dummy"));





	}
	
	
	@Test
	//@Description("tests the behavior of getting all members")
	public void testGettingMembers() {
		/**
		 * Are we retrieving all of the Members? 
		 * Are any members missing? 
		 * Are there any duplicates in the list of Members?
		 * Do we need to check for nulls here?
		 * What else can we check here?
		 */
		
		SiteManager sm = new SiteManager();
		sm.addMember("Joanne", "Wardell", "jawardell", "jawardell@vsu", LocalDateTime.now());
		sm.addMember("Christian", "Gerhardt", "clgerhardt", "clgerhardt@vsu", LocalDateTime.now());
		sm.addMember("Kayla", "Rivera", "kjrivera", "kjrivera@vsu", LocalDateTime.now());
		sm.addMember("KD", "Adkins", "dsadkins", "dsadkins@vsu", LocalDateTime.now());
		
		Assert.assertTrue(sm.getMembers().contains(sm.getMember("jawardell@vsu")));
		Assert.assertTrue(sm.getMembers().contains(sm.getMember("clgerhardt@vsu")));
		Assert.assertTrue(sm.getMembers().contains(sm.getMember("kjrivera@vsu")));
		Assert.assertTrue(sm.getMembers().contains(sm.getMember("dsadkins@vsu")));
		
		Assert.assertTrue(sm.getMembers().size() == 4);
		
		
	}
	
	@Test
	//@Description("tests the behavior of getting a Group")
	public void testGetGroup() {
		/**
		 * Is the correct group object retrieved? 
		 * How can I test to ensure that the same 
		 * 		group object is returned?
		 * What if a null group is requested? 
		 * What if an empty group is requested?
		 * What if a duplicate group is requested?
		 * What else can I check here?
		 */
		
		
		SiteManager sm = new SiteManager();
		
		LocalDateTime d = LocalDateTime.now();
		sm.addGroup("cooking", "a group about cooking", d);
		
		Assert.assertEquals("cooking", sm.getGroup("cooking").getTitle());
		Assert.assertEquals("a group about cooking", sm.getGroup("cooking").getDescription());
		Assert.assertEquals(d, sm.getGroup("cooking").getDateCreated());
		
		Assert.assertEquals(null, sm.getMember(null));
		
		
		Assert.assertEquals(null, sm.getMember("dummy"));
		
	}
	
	
	@Test
	//@Description("tests the behavior of adding a Group")
	public void testAddGroup() {
		/**
		 * If I add a group, how can I verify that it's there?
		 * What happens if I try to add a null group? 
		 * Does the SM allow duplicate groups?
		 * If I add a duplicate group, how can I verify that 
		 *      there is not a duplicate?
		 * What else can I test here?
		 */
		
		
		SiteManager sm = new SiteManager();
		sm.addGroup("cooking", "a group about cooking", LocalDateTime.now());
		sm.addGroup("kayaking", "a group about kayaking", LocalDateTime.now());
		sm.addGroup("reading", "a group about reading", LocalDateTime.now());		
		sm.addGroup("sailing", "a group about sailing", LocalDateTime.now());
		
		Assert.assertTrue(sm.getGroups().contains(sm.getGroup("cooking")));
		Assert.assertTrue(sm.getGroups().contains(sm.getGroup("kayaking")));
		Assert.assertTrue(sm.getGroups().contains(sm.getGroup("reading")));
		Assert.assertTrue(sm.getGroups().contains(sm.getGroup("sailing")));
		
		Assert.assertTrue(sm.getGroups().size() == 4);
		
		
		sm.addGroup(null, null, null);

		//did not add null group
		Assert.assertTrue(sm.getGroups().size() == 4);

		sm.addGroup("sailing", "a group about sailing", LocalDateTime.now());

		//did not add duplicate group
		Assert.assertTrue(sm.getGroups().size() == 4);

	}
	
	
	
	@Test
	//@Description("tests the behavior of getting all Groups")
	public void testGetGroups() {
		/**
		 * Is every single group in the list?
		 * Is a group missing that should be there?
		 * Are duplicates contained in the group? 
		 * Will multiple null groups be in the list?
		 * Will multiple empty groups be in the list?
		 * Will I ever get a null list?
		 * Will I ever get an empty list?
		 * What else can I check here?
		 */
		
		
		
		SiteManager sm = new SiteManager();
		sm.addGroup("cooking", "a group about cooking", LocalDateTime.now());
		sm.addGroup("kayaking", "a group about kayaking", LocalDateTime.now());
		sm.addGroup("reading", "a group about reading", LocalDateTime.now());		
		sm.addGroup("sailing", "a group about sailing", LocalDateTime.now());
		
		Assert.assertTrue(sm.getGroups().contains(sm.getGroup("cooking")));
		Assert.assertTrue(sm.getGroups().contains(sm.getGroup("kayaking")));
		Assert.assertTrue(sm.getGroups().contains(sm.getGroup("reading")));
		Assert.assertTrue(sm.getGroups().contains(sm.getGroup("sailing")));
		
		Assert.assertTrue(sm.getGroups().size() == 4);
		
		
		sm.addGroup(null, null, null);

		//did not add null group
		Assert.assertTrue(sm.getGroups().size() == 4);

		sm.addGroup("sailing", "a group about sailing", LocalDateTime.now());

		//did not add duplicate group
		Assert.assertTrue(sm.getGroups().size() == 4);
		
	}
	
	@Test
	//@Description("tests the behavior of getting all matching Groups")
	public void testGetMatchingGroups() {
		/**
		 * What if I use a null argument? 
		 * What if I use an empty string? 
		 * What if I use different casing?
		 * What if I use escape characters?
		 * Do all of the items contain my search query-string?
		 * Do any items not contain my search query-string?
		 * Do any items contain my search string that are irrelevant?
		 * What else can i check here?
		 */
		
		SiteManager sm = new SiteManager();
		sm.addGroup("cooking", "a group about cooking", LocalDateTime.now());
		sm.addGroup("kayaking", "a group about kayaking", LocalDateTime.now());
		sm.addGroup("reading", "a group about reading", LocalDateTime.now());		
		sm.addGroup("sailing", "a group about sailing", LocalDateTime.now());
		
		//null request returns null object
		Assert.assertEquals(null, sm.getGroup(null));
		
		//empty string returns null object
		Assert.assertEquals(null, sm.getGroup(""));
		
		//different casing   [not sure what is the correct implementation here, 
		//does casing matter in theory]
		
		
		
		//could so some stuff with regex here.. not sure yet how/what

		
		//esacpe chars.. [shouldn't make a difference]
		Assert.assertEquals(null, sm.getGroup("\b\n\t\r\f\"\\"));
		
		
		//does the list contain all the groups that i wanted
		List<Group> groups = sm.getGroups("ing");
		Assert.assertEquals(4, groups.size());
		Assert.assertTrue(sm.getGroups().contains(sm.getGroup("cooking")));
		Assert.assertTrue(sm.getGroups().contains(sm.getGroup("kayaking")));
		Assert.assertTrue(sm.getGroups().contains(sm.getGroup("reading")));
		Assert.assertTrue(sm.getGroups().contains(sm.getGroup("sailing")));
		

		//does the list contain a false match
		List<Group> groups1 = sm.getGroups("a");
		Assert.assertEquals(4, groups1.size());
		Assert.assertTrue(sm.getGroups().contains(sm.getGroup("cooking")));
		Assert.assertTrue(sm.getGroups().contains(sm.getGroup("kayaking")));
		Assert.assertTrue(sm.getGroups().contains(sm.getGroup("reading")));
		Assert.assertTrue(sm.getGroups().contains(sm.getGroup("sailing")));
		

		
		
	}
	
	@Test
	//@Description("tests that member association exists and is correctly implemented")
	public void SiteManagerHasMembers() {
		/**
		 * Does the SM have a collection of Members?
		 * Can the SM have multiple Members in the collection?
		 * Is the collection mutable?
		 * Are the multiplicities correct?
		 * 		eg. if i add 5 members manually, can i verify that 5
		 * 		are in the SM collection?
		 * Is the navigability correct?
		 * 		eg. SM has a collection of many members is a 1-way navigability
		 * 
		 * What else can I test regarding the member association?
		 */
		
		SiteManager sm = new SiteManager();
		
		List<Member> mems = new ArrayList<>();
		
		mems.add(new Member("Joanne", "Wardell", "jawardell", "jawardell@vsu", LocalDateTime.now()));
		mems.add(new Member("Kayla", "Rivera", "kjrivera", "kjrivera@vsu", LocalDateTime.now()));
		mems.add(new Member("KD", "Adkins", "dsadkins", "dsadkins@vsu", LocalDateTime.now()));
		mems.add(new Member("Christian", "Gerhardt", "clgerhardt", "clgerhart@vsu", LocalDateTime.now()));

		boolean memMissing = false;
		
		sm.addMember("Joanne", "Wardell", "jawardell", "jawardell@vsu", LocalDateTime.now());
		sm.addMember("Kayla", "Rivera", "kjrivera", "kjrivera@vsu", LocalDateTime.now());
		sm.addMember("KD", "Adkins", "dsadkins", "dsadkins@vsu", LocalDateTime.now());
		sm.addMember("Christian", "Gerhardt", "clgerhardt", "clgerhart@vsu", LocalDateTime.now());
		
		for(int i = 0; i < mems.size(); i++) {
			if(!mems.contains(sm.getMembers().get(i))) {
				memMissing = true;
				
			}
			
		}
		
		System.out.println("mems size" + mems.size());
		
		//the collection is correctly implemented
		Assert.assertFalse(memMissing);
		
	}
	
	@Test
	//@Description("tests that group association exists and is correctly implemented")
	public void SiteManagerHasGroups() {
		/**
		 * Does the SM have a collection of Group?
		 * Can the SM have multiple Groups in the collection?
		 * Is the collection mutable?
		 * Are the multiplicities correct?
		 * 		eg. if i add 5 members each with 4 groups manually,
		 *      can I verify that there are 20 total groups in the collection?
		 * Is the navigability correct?
		 * 		eg. SM has a collection of many groups is a 1-way navigability
		 * 
		 * What else can I test regarding the group association?
		 */
		
		
		
		SiteManager sm = new SiteManager();
		sm.addGroup("reading", "a group about reading", LocalDateTime.now());
		sm.addGroup("banking", "a group about banking", LocalDateTime.now());
		sm.addGroup("cooking", "a group about cooking", LocalDateTime.now());
		sm.addGroup("driving", "a group about driving", LocalDateTime.now());
		
		
		List<Group> groups = new ArrayList<>();
		
		groups.add(new Group("cooking", "a group about cooking", LocalDateTime.now()));
		groups.add(new Group("driving", "a group about driving", LocalDateTime.now()));
		groups.add(new Group("reading", "a group about reading", LocalDateTime.now()));
		groups.add(new Group("banking", "a group about banking", LocalDateTime.now()));

		boolean groupMissing = false;
		
		for(int i = 0; i < groups.size(); i++) {
			if(!groups.contains(sm.getGroups().get(i))) {
				groupMissing = true;
				
			}
			Group g = sm.getGroup(groups.get(i).getTitle());
			
			Assert.assertTrue(g.getTitle() == groups.get(i).getTitle());
			
		}
		
		//the collection is correctly implemented
		Assert.assertFalse(groupMissing);
	}
}
