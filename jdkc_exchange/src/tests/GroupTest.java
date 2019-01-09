//written for JUnit
package tests;

//import jdk.jfr.Description;
import org.junit.*;
import java.time.*;
import java.util.*;

//import com.sun.org.glassfish.gmbal.Description;



import jdkc_exchange.*;


public final class GroupTest {	
	@Test
	//@Description("tests the basic fucntionality of the getActiveMembers method")
	public void testGetActiveMembers() {
		Member m1 = new Member("joanne", "wardell", "jawardell", "j@vsu", LocalDateTime.now());
		Member m2 = new Member("KD", "Adkins", "kdadkins", "ks@vsu", LocalDateTime.now());
		Member m3 = new Member("Kayla", "Rivera", "kjrivera", "kj@vsu", LocalDateTime.now());
		Member m4 = new Member("Bhristian", "Gerhart", "bhgerhart", "bh@vsu", LocalDateTime.now());
		
		Group g1 = new Group("Smarties", "a group for intelligent coders", LocalDateTime.now());
		
		m1.joinGroup(g1, LocalDateTime.now());
		m2.joinGroup(g1, LocalDateTime.now());
		m3.joinGroup(g1, LocalDateTime.now());
		m4.joinGroup(g1, LocalDateTime.now());
		
		Question q1 = new Question("sqrt", "what is the square root of blah blah?", LocalDateTime.now());
		Question q2 = new Question("area", "whats the area of a rhombus?", LocalDateTime.now());
		Question q3 = new Question("matter", "what are the ingredients to dark matter?", LocalDateTime.now());
		Question q4 = new Question("nobel", "what are the requirements to receive a Nobel Peace Prize?", LocalDateTime.now());
		Question q5 = new Question("Amazon", "How do I apply for Amazon?", LocalDateTime.now());
		Question q7 = new Question("feet", "how many feet are in 70 yards?", LocalDateTime.now());
		Question q8 = new Question("time", "who wants to test my time machine?", LocalDateTime.now());
		Question q9 = new Question("ICPC", "When is ICPC?", LocalDateTime.now());

		m1.addQuestion(g1, q1, LocalDateTime.now());
		m1.addQuestion(g1, q2, LocalDateTime.now());
		m1.addQuestion(g1, q3, LocalDateTime.now());

		m2.addQuestion(g1, q4, LocalDateTime.now());
		m2.addQuestion(g1, q5, LocalDateTime.now());
		
		m3.addQuestion(g1, q7, LocalDateTime.now());
		m3.addQuestion(g1, q8, LocalDateTime.now());
		
		m4.addQuestion(g1, q9, LocalDateTime.now());

		Answer a1 = new Answer(q1, "answer questions 1", LocalDateTime.now());
		m1.addAnswer(g1, q1, a1, LocalDateTime.now());
		
		Answer a2 = new Answer(q3, "answer questions 2", LocalDateTime.now());
		m2.addAnswer(g1, q3, a2, LocalDateTime.now());
		
		Answer a3 = new Answer(q5, "answer questions 3", LocalDateTime.now());
		m3.addAnswer(g1, q5, a3, LocalDateTime.now());
		
		Answer a4 = new Answer(q5, "answer questions 4", LocalDateTime.now());
		m4.addAnswer(g1, q2, a4, LocalDateTime.now());
		
		
		Assert.assertEquals("list should contain 0 items", 0, g1.getActiveMembers(0).size());
		Assert.assertEquals("list should contain 0 items", 0, g1.getActiveMembers(-4).size());
		
		Assert.assertEquals("list should contain 4 items", 4, g1.getActiveMembers(4).size());
		Assert.assertEquals("list should contain 4 items", 4, g1.getActiveMembers(10).size());
 		Assert.assertEquals("list should contain 3 items", 3, g1.getActiveMembers(2).size());//Solves Tie
		
		Assert.assertTrue(g1.getActiveMembers(4).contains(m1));
		Assert.assertTrue(g1.getActiveMembers(4).contains(m2));
		Assert.assertTrue(g1.getActiveMembers(4).contains(m3));
		Assert.assertTrue(g1.getActiveMembers(4).contains(m4));
		
		Assert.assertTrue(g1.getActiveMembers(2).contains(m1));
		Assert.assertTrue(g1.getActiveMembers(2).contains(m2));
		Assert.assertTrue(g1.getActiveMembers(2).contains(m3));
		
		Assert.assertTrue("M1 is highest member",g1.getActiveMembers(4).get(0) == m1);
		Assert.assertTrue("Tie. Either m2 or m3",g1.getActiveMembers(4).get(1) == m2 ||g1.getActiveMembers(2).get(1) == m3);
		Assert.assertTrue("Tie. Either m2 or m3",g1.getActiveMembers(4).get(2) == m3 || g1.getActiveMembers(2).get(2) == m2);
		Assert.assertTrue("Lowest Activity",g1.getActiveMembers(4).get(3) == m4);		
	}
	
	@Test
	//@Description("tests the basic fucntionality of the OVERLOADED getQuestions method")
	public void testGetQuestionsOverloaded() {
		Group g1  = new Group(null, null, null);
		Group g2  = new Group("title1", "description1",LocalDateTime.now());
		Group g3  = new Group("title2", "description2",LocalDateTime.MAX);
		Group g4  = new Group("title2", "description2",LocalDateTime.MIN);
		Assert.assertEquals("should return an empty list", g1.getQuestions(0),  new ArrayList<>());
		Assert.assertEquals("should return an empty list", g1.getQuestions(Integer.MIN_VALUE),  new ArrayList<>());
		Assert.assertEquals("should return an empty list", g1.getQuestions(1),  new ArrayList<>());
		Assert.assertEquals("should return an empty list", g1.getQuestions(rand()),  new ArrayList<>());
		Assert.assertEquals("should return an empty list", g2.getQuestions(0),  new ArrayList<>());
		Assert.assertEquals("should return an empty list", g2.getQuestions(Integer.MIN_VALUE),  new ArrayList<>());
		Assert.assertEquals("should return an empty list", g2.getQuestions(1),  new ArrayList<>());
		Assert.assertEquals("should return an empty list", g2.getQuestions(rand()),  new ArrayList<>());
		Assert.assertEquals("should return an empty list", g3.getQuestions(0),  new ArrayList<>());
		Assert.assertEquals("should return an empty list", g3.getQuestions(Integer.MIN_VALUE),  new ArrayList<>());
		Assert.assertEquals("should return an empty list", g3.getQuestions(1),  new ArrayList<>());
		Assert.assertEquals("should return an empty list", g3.getQuestions(rand()),  new ArrayList<>());
		Assert.assertEquals("should return an empty list", g4.getQuestions(0),  new ArrayList<>());
		Assert.assertEquals("should return an empty list", g4.getQuestions(Integer.MIN_VALUE),  new ArrayList<>());
		Assert.assertEquals("should return an empty list", g4.getQuestions(1),  new ArrayList<>());
		Assert.assertEquals("should return an empty list", g4.getQuestions(rand()),  new ArrayList<>());
		
	}
	
	@Test
	//@Description("tests the basic fucntionality of the OVERLOADED getAnswers method")
	public void testGetAnswersOverloaded() {
		Group g1  = new Group(null, null, null);
		Group g2  = new Group("title1", "description1",LocalDateTime.now());
		Group g3  = new Group("title2", "description2",LocalDateTime.MAX);
		Group g4  = new Group("title2", "description2",LocalDateTime.MIN);
		Assert.assertEquals("should return an empty list", new ArrayList<>(), g1.getAnswers(0));
		Assert.assertEquals("should return an empty list", new ArrayList<>(), g1.getAnswers(Integer.MIN_VALUE));
		Assert.assertEquals("should return an empty list", new ArrayList<>(), g1.getAnswers(1));
		Assert.assertEquals("should return an empty list", new ArrayList<>(), g1.getAnswers(rand()));
		Assert.assertEquals("should return an empty list", new ArrayList<>(), g2.getAnswers(0));
		Assert.assertEquals("should return an empty list", new ArrayList<>(), g2.getAnswers(Integer.MIN_VALUE));
		Assert.assertEquals("should return an empty list", new ArrayList<>(), g2.getAnswers(1));
		Assert.assertEquals("should return an empty list", new ArrayList<>(), g2.getAnswers(rand()));
		Assert.assertEquals("should return an empty list", new ArrayList<>(), g3.getAnswers(0));
		Assert.assertEquals("should return an empty list", new ArrayList<>(), g3.getAnswers(Integer.MIN_VALUE));
		Assert.assertEquals("should return an empty list", new ArrayList<>(), g3.getAnswers(1));
		Assert.assertEquals("should return an empty list", new ArrayList<>(), g3.getAnswers(rand()));
		Assert.assertEquals("should return an empty list", new ArrayList<>(), g4.getAnswers(0));
		Assert.assertEquals("should return an empty list", new ArrayList<>(), g4.getAnswers(Integer.MIN_VALUE));
		Assert.assertEquals("should return an empty list", new ArrayList<>(), g4.getAnswers(1));
		Assert.assertEquals("should return an empty list", new ArrayList<>(), g4.getAnswers(rand()));
		
	}
	
	
	
	private LocalDateTime date;
	private LocalDateTime d1;

	@Test
	public void handlesNullConstructorProperly() {
		//create an instance of Group
		Group group  = new Group(null, null, null);
		
		/**
		 * Constructors shouldn't allow things to be initialized to null. 
		 * This causes null pointer exceptions to occur.
		 */
		Assert.assertTrue("Title shouldn't be initialized to null", group.getTitle() != null);
		Assert.assertTrue("Description shouldn't be initialized to null", group.getDescription() != null);
		Assert.assertTrue("Date should be initialized to null", group.getDateCreated() != null);
	}
	
	@Test
	public void dateCreatedMustBeValueUsedInConstructor() {
		//create an instance of Group
		LocalDateTime d1 = null;
		Group group = new Group(null, null, d1 = LocalDateTime.now());
		
		//assert statement
		Assert.assertEquals("Date Created must the same as one used in constructor", d1, group.getDateCreated());
	}
	
	@Test
	public void titleMustBeValueUsedInConstructor() {
		//create an instance of Group
		Group group = new Group("title", null, null);
		
		//assert statement
		Assert.assertEquals("Title must be the same as one used in contructor", "title", group.getTitle());
	}
	
	@Test
	public void descriptionMustBeValueUsedInConstructor () {
		//create an instance of Group
		Group group = new Group(null, "d1", null);
		
		//assert statement
		Assert.assertEquals("Description must be the same as one used in constructor", "d1", group.getDescription());
	}
	
	@Test
	public void numberOfMembersShouldBeZero () {
		//create an instance of Group
		Group group = new Group(null, null, null);
		
		//assert statement
		Assert.assertEquals("Group members must initialize to zero", 0, group.getNumMembers());
	}
	
	@Test
	public void theMemberReturnedMustBeNullInitially() {
		//create an instance of Group
		Group group = new Group(null, null, null);
		
		//assert statement
		Assert.assertNull("Since there are no Members, the member requested must be null", group.getMember(null));
	}
	
	@Test
	public void listOfMembersInitializesToEmptyList () {
		//create an instance of Group
		Group group = new Group(null, null, null);
		
		//assert statement 
		Assert.assertEquals("The list of questions should be empty", 0, group.getMembers().size());
	}
	
	@Test
	public void listOfQuestionsInitializesToEmptyList () {
		//create an instance of Group
		Group group = new Group(null, null, null);
		setDate(null);
		Group g1 = new Group("title", "description", setDate(LocalDateTime.now()));
		
		
		//assert statement 
		Assert.assertEquals("The list of questions should be empty", 0, group.getQuestions().size());
		Assert.assertEquals("The list of questions should be empty", 0, g1.getQuestions().size());
	}
	
	@Test
	public void listOfAnswersInitializesToEmptyList() {
		//create an instance of Group
		Group group = new Group(null, null, null);
		
		//assert statement 
		Assert.assertEquals("The list of answers should be empty", 0, group.getAnswers().size());
	}
	
	@Test
	public void toStringMustFollowToStringConvention() {
		setD1(null);
		Group group2 = new Group("title", "description", setD1(LocalDateTime.now()));
		
		System.out.println(group2.toString().length());
		
		int proposedLength = 106;
		
		//assert statements
		//Assert.assertEquals("Null arguments must result in the null toString", "", group1.toString());
		
		Assert.assertTrue("To string should match expected length", proposedLength == group2.toString().length());
		//Assert.assertEquals("To string should match expected content", proposal, group2.toString());
		
	}

	LocalDateTime getDate() {
		return date;
	}

	LocalDateTime setDate(LocalDateTime date) {
		this.date = date;
		return date;
	}
	
	private int rand() {
		return (int)(Math.random()*100 + 1);
	}

	public LocalDateTime getD1() {
		return d1;
	}

	public LocalDateTime setD1(LocalDateTime d1) {
		this.d1 = d1;
		return d1;
	}
}