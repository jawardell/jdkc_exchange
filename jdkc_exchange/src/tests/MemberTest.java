package tests;
import org.junit.*;
import java.time.LocalDateTime;


import jdkc_exchange.Member;


public final class MemberTest {
	@Test
	//@Description(value = "tests the basic functionality of the getGroups method")
	public void testGetGroups() {
		Member member = new Member(null, null, null, null, null);
		Assert.assertEquals("should return empty list", member.getGroups(0).size(), 0);
		Assert.assertEquals("should return empty list", member.getGroups(1).size(), 0);
		int s1 = LocalDateTime.now().getNano();
		int s2 = Integer.MAX_VALUE % s1;
		int rand = (int)(Math.random()*s1 + s2);
		Assert.assertEquals("should return empty list", member.getGroups(rand).size(), 0);
	}
	
	@Test
	//@Description(value = "tests the basic functionality of the getQuestions method")
	public void testGetQuestions() {
		Member member = new Member(null, null, null, null, null);
		Assert.assertEquals("should return empty list", member.getQuestions(null).size(), 0);
	}
	
	@Test
	//@Description(value = "tests the basic functionality of the getAnswers method")
	public void testGetAnswers() {
		Member member = new Member(null, null, null, null, null);
		Assert.assertEquals("should return empty list", member.getAnswers(null).size(), 0);
	}
	
	
	
	@Test
	public void handlesNullConstructorProperly() {
		//create an instance of Member
		Member mem = new Member(null, null, null, null, null);
			
		/**
		 * Why does this screen Name to commented?
		 */
		Assert.assertTrue("First Name shouldn't be initialized to null", mem.getFirstName() != null);
		Assert.assertTrue("Last Name shouldn't be initialized to null", mem.getLastName() != null);
		//Assert.assertTrue("Screen Name shouldn't be initialized to null", mem.getScreenName() != null);
		Assert.assertTrue("Email Address shouldn't be initialized to null", mem.getEmail() != null);
		Assert.assertTrue("Date should be initialized to null", mem.getDateCreated() != null);
	}

	@Test
	public void firstNameShouldBeCreatedInConstructor() {
		//String fName = null;
		Member mem = new Member("firstName", null, null, null, null);
		//assert statement
		Assert.assertEquals("First Name must the same as one used in constructor", "firstName", mem.getFirstName());
	}
	@Test
	public void lastNameShouldBeCreatedInConstructor() {
		//String lName = null;
		Member mem = new Member(null, "lName", null, null, null);

		//assert statement
		Assert.assertEquals("Last Name must the same as one used in constructor", new String("lName"), mem.getLastName());
	}
	
	@Test
	public void screenNameShouldBeCreatedInConstructor() {
		//String screen = null;
		Member mem = new Member(null, null, "sName", null, null);

		//assert statement
		Assert.assertEquals("Screen Name must the same as one used in constructor", new String("sName"), mem.getScreenName());
	}
	
	@Test
	public void emailShouldBeCreatedInConstructor() {
		//String email = null;
		Member mem = new Member(null, null, null, "email", null);

		//assert statement
		Assert.assertEquals("Last Name must the same as one used in constructor", new String("email"), mem.getEmail());
	}
	
	@Test
	public void dateShouldBeCreatedInConstructor() {
		LocalDateTime d = null;
		Member mem = new Member(null, null, null, null, d = LocalDateTime.now());

		//assert statement
		Assert.assertEquals("Last Name must the same as one used in constructor", d, mem.getDateCreated());
	}


	@Test
	public void numMemberShouldBeZero() {
		//create an instance of Membershp
		Member mem = new Member(null, null, null, null, null);
				
		//assert statement
		Assert.assertEquals("Members group must initialize to zero", 0, mem.getNumGroups());
	}

	@Test
	public void shouldReturnNullGroup() {
		Member mem = new Member(null, null, null, null, null);

		Assert.assertNull("Members group must initialize to zero", mem.getGroup(null));
		//mem.toString();
		//mem.getAnswers(null);
		Assert.assertTrue(mem.getGroup(null) == null);
	}

	@Test
	public void listOfMembersReturnEmptyList() {
		//create an instance of Member
		Member mem = new Member(null, null, null, null, null);
				
		//assert statement 
		Assert.assertEquals("The list of groups should be empty", 0, mem.getGroups().size());
	}

	

	@Test
	public void dateReturnedShouldBeNull() {
		LocalDateTime date = null;
		Member mem = new Member(null, null, null, null, null);

		//assert statement
		Assert.assertEquals("There is no group so no date to return", date, mem.getDateJoined(null));
	}

	
	
	@Test
	public void listOfQuestionsIsEmptyList () {
		//create an instance of Member
		Member mem = new Member(null, null, null, null, null);
		
		//assert statement 
		Assert.assertEquals("The list of questions should be empty", 0, mem.getQuestions(null).size());
	}

	@Test
	public void listOfAnswersIsEmptyList() {
		//create an instance of Member
		Member mem = new Member(null, null, null, null, null);
		
		//assert statement 
		Assert.assertEquals("The list of answers should be empty", 0, mem.getAnswers(null).size());
	}

	
	@Test
	public void mustFollowToStringConvention() {
		//create an instance of group
		Member mem2 = new Member("firstName", "lastName", "screenName", "email", LocalDateTime.now());
		
		String groups = new String();
		String questions = new String();
		String answers = new String();
		
		String str1 = null;
		
		String str2 =  String.format("\n\n~~~~~Member~~~~~\n" 
				+ "Name: %s, %s\n"
				+ "Email: %s\n" 
				+ "Screen Name: %s\n" 
				+ "Date Created:%s\n"
				+ "Groups:\n%s\n" 
				+ "Questions:\n%s\n" 
				+ "Answers:\n%s\n" 
				+ "~~~~~~~~~~~~~~~~\n\n",
				mem2.getLastName(), mem2.getFirstName(), mem2.getEmail(), mem2.getScreenName(), 
				mem2.getDateCreated().toString(), groups, questions, answers);
		
		//assert statements
		Assert.assertEquals("Null toString should return null", null, str1);
		Assert.assertEquals(mem2.toString(), str2);
	}

}
