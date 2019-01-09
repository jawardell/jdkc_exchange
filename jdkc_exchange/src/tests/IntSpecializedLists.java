package tests;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import jdkc_exchange.*;

/**
 * This is an integration test which assesses the s2 feature of 
 * "SpecializedLists" AKA, retrieving lists of various sizes based on
 * popularity, activity, and date. The SiteManager list methods
 * are not tested here because they are tested in the Integration 
 * test for SiteManaging.
 * Popularity is a function of the number of members.
 * Activity is a function of the number of posts.
 * Date is determined by the time stamp argument provided.
 * The group and member classes should return proper lists for 
 * activity and date requests only.
 * All lists should be sorted properly according to the specifications. 
 * All lists should return the correct elements according to the specs. 
 * All lists should be the correct size according the specifications.
 * @version s2.0
 * @see IntSiteManaging
 */


public final class IntSpecializedLists {
	@Test
	//@Description("tests the beahavior of getting all Groups from a Member")
	public   void testGetMemberGroups() {
		Member m1 = new Member("Jeffrey", "Kapone", "Al Kapone", "kaponeDaMan@gmail.com", LocalDateTime.now());
		Group g1 = new Group("Awesome Group", "most awesome group ever", LocalDateTime.now());
		m1.joinGroup(g1, LocalDateTime.now());
		
		List<Group> groups = m1.getGroups();
		Assert.assertTrue(groups.contains(g1));
		Assert.assertTrue(groups.size() == 1);
		/**
		 * Is the list sorted properly? 
		 * Does the list contain all of the items that i was expecting? 
		 * Are there any items missing? 
		 * Does the list contain duplicates? 
		 * Is the list sorted in ascending or descending order? 
		 * Does the list contain the correct amount of items? 
		 * What else can I test here?
		 */
		
	}
	
	@Test
	//@Description("tests the beahavior of getting top n active Groups from a Member")
	public   void testGetMemberActiveGroups() {
		
		/**
		 * What about ties?
		 * What if member is in less than n groups?
		 * What if member is in more than n groups?
		 * What about negatives?
		 * Is the list sorted properly? 
		 * Does the list contain all of the items that i was expecting? 
		 * Are there any items missing? 
		 * Does the list contain duplicates? 
		 * Is the list sorted in ascending or descending order? 
		 * Does the list contain the correct amount of items? 
		 * What else can I test here?
		 */
		
		Member m1 = new Member("joanne", "wardell", "jawardell", "j@vsu", LocalDateTime.now());
		Group g1 = new Group("kayaking", "a group for kayaking", LocalDateTime.now());
		Group g2 = new Group("soccer", "a group for soccer", LocalDateTime.now());
		Group g3 = new Group("books", "a group for books", LocalDateTime.now());
		m1.joinGroup(g1, LocalDateTime.now());
		
		m1.joinGroup(g2, LocalDateTime.now());
		m1.joinGroup(g3, LocalDateTime.now());
		m1.addQuestion(g1, new Question("about paddles", "what is a good paddle for me?", LocalDateTime.now()), LocalDateTime.now());
		m1.addQuestion(g1, new Question("type of kayak ", "is there a kind of kayak that's good?", LocalDateTime.now()), LocalDateTime.now());
		m1.addQuestion(g1, new Question("life jackets ", "should i wear a life jacket?", LocalDateTime.now()), LocalDateTime.now());

		m1.addQuestion(g2, new Question("about kicking", "what is a corner kick?", LocalDateTime.now()), LocalDateTime.now());
		m1.addQuestion(g2, new Question("rules inquiry", "what are the 17 rules of soccer?", LocalDateTime.now()), LocalDateTime.now());
		
		m1.addQuestion(g3, new Question("favorite author", "why is john steinbeck your favorite?", LocalDateTime.now()), LocalDateTime.now());

		Assert.assertEquals("list should contain 0 items", 0, m1.getGroups(0).size());
		Assert.assertEquals("list should contain 1 item", 1, m1.getGroups(1).size());
		Assert.assertEquals("list should contain 2 items", 2, m1.getGroups(2).size());
		Assert.assertEquals("list should contain 3 items", 3, m1.getGroups(3).size());
		
		
		m1.addQuestion(g3, new Question("GEB mu puzzle", "can you turn MI to MU?", LocalDateTime.now()), LocalDateTime.now());
		Assert.assertEquals("list should contain 0 items", 0, m1.getGroups(0).size());
		Assert.assertEquals("list should contain 1 item", 1, m1.getGroups(1).size());
		Assert.assertEquals("list should contain 3 items", 3, m1.getGroups(2).size());
		Assert.assertEquals("list should contain 3 items", 3, m1.getGroups(3).size());
		
		m1.addQuestion(g2, new Question("about offside rule", "is this player in offside position?", LocalDateTime.now()), LocalDateTime.now());
		Assert.assertEquals("list should contain 2 items", 2, m1.getGroups(1).size());
		Assert.assertEquals("list should contain 3 items", 3, m1.getGroups(4).size());

		//member in seven groups, with 3 most active groups, 0 0 1 1 2 2 2
		Question q1 = new Question("which airline", "what is your fav domestic airline?", LocalDateTime.now());
		Member m2 = new Member("Kayla", "Rivera", "krivera1", "kjrivera@yahoo.com", LocalDateTime.now());
		Group g4 = new Group("Mathematics", "a group for math q&a", LocalDateTime.now());
		Group g5 = new Group("Music", "a group for music q&a", LocalDateTime.now());
		Group g6 = new Group("Night Life", "q&a's about night venues", LocalDateTime.now());
		Group g7 = new Group("Travel", "Questions/Answers about traveling", LocalDateTime.now());
		Group g8 = new Group("Fashion", "a group for fashion q&a's", LocalDateTime.now());
		Group g9 = new Group("Coffee", "a group for coffee q&a's", LocalDateTime.now());
		Group g10 = new Group("Cars", "a group for car q&a's", LocalDateTime.now());
		
		m2.joinGroup(g4, LocalDateTime.now());
		m2.joinGroup(g5, LocalDateTime.now());
		m2.joinGroup(g6, LocalDateTime.now());
		m2.joinGroup(g7, LocalDateTime.now());
		m2.joinGroup(g8, LocalDateTime.now());
		m2.joinGroup(g9, LocalDateTime.now());
		m2.joinGroup(g10, LocalDateTime.now());

		m2.addQuestion(g6, new Question("good place for drinks?", "where's a good place for drinks", LocalDateTime.now()), LocalDateTime.now());
		m2.addQuestion(g6, new Question("about Luckies", "does Luckies have a lounge?", LocalDateTime.now()), LocalDateTime.now());
		m2.addQuestion(g7, q1, LocalDateTime.now());
		m2.addAnswer(g7, q1, new Answer(q1, "Delta because they have nicer planes.", LocalDateTime.now()), LocalDateTime.now());
		m2.addQuestion(g8, new Question("best shoes", "what are the best kind of shoes?", LocalDateTime.now()), LocalDateTime.now());
		m2.addQuestion(g8, new Question("how to match with blue", "do these pants match?", LocalDateTime.now()), LocalDateTime.now());
		m2.addQuestion(g10, new Question("Tesla Model S", "can you post a pic of your model s?", LocalDateTime.now()), LocalDateTime.now());
		m2.addQuestion(g9, new Question("Latte", "can someone explain what a latte is?", LocalDateTime.now()), LocalDateTime.now());
		Assert.assertEquals("list should contain 0 items", 0, m2.getGroups(0).size());
		Assert.assertEquals("list should contain 3 items", 3, m2.getGroups(1).size());
		Assert.assertEquals("list should contain 5 items", 5, m2.getGroups(2).size());
		Assert.assertEquals("list should contain 7 items", 7, m2.getGroups(3).size());
		Assert.assertEquals("list should contain 7 items", 7, m2.getGroups(4).size());
		Assert.assertEquals("list should contain 7 items", 7, m2.getGroups(5).size());
		Assert.assertEquals("list should contain 7 items", 7, m2.getGroups(1000).size());
		Assert.assertEquals("list should contain 7 items", 7, m2.getGroups(Integer.MAX_VALUE).size());
		Assert.assertEquals("list should contain 0 items", 0, m2.getGroups(Integer.MIN_VALUE).size());






		



		
	}
	
	@Test
	//@Description("tests the beahavior of getting top n recent Questions from a Member's group")
	public   void testGetMemberRecentQuestions() {
		Member m1 = new Member("KD", "Adkins", "kdadkins", "ks@vsu", LocalDateTime.now());
		Group g1 = new Group("soccer", "a group for soccer", LocalDateTime.now());
		
		m1.joinGroup(g1, LocalDateTime.now());

		
		Question q1 = new Question("about cleats", "what are some good cleats?", LocalDateTime.now());
		Question q2 = new Question("ball size", "what size ball should I buy", LocalDateTime.now());
		Question q3 = new Question("Subs", "how many substitutions are allowed?", LocalDateTime.now());
		m1.addQuestion(g1, q1, LocalDateTime.now());
		m1.addQuestion(g1, q2, LocalDateTime.now());
		m1.addQuestion(g1, q3, LocalDateTime.now());

		List<Question> mQs = m1.getQuestions(g1);

		
		//Is Sorted
		Assert.assertTrue((mQs.get(0).getDate().isBefore(mQs.get(1).getDate())) || (mQs.get(0).getDate().equals(mQs.get(1).getDate())));
		Assert.assertTrue((mQs.get(0).getDate().isBefore(mQs.get(2).getDate())) || (mQs.get(0).getDate().equals(mQs.get(2).getDate())));
		Assert.assertTrue((mQs.get(1).getDate().isBefore(mQs.get(2).getDate())) || (mQs.get(1).getDate().equals(mQs.get(2).getDate())));
		
		Assert.assertEquals(3, mQs.size());
		Assert.assertEquals(3, m1.getQuestions(g1, 5).size());
		Assert.assertEquals(2, m1.getQuestions(g1, 2).size());
		
		Assert.assertEquals("Should return an empty list", new ArrayList<>(), m1.getQuestions(g1, -3));
		
		Assert.assertTrue(m1.getQuestions(g1, 3).contains(q1));
		Assert.assertTrue(m1.getQuestions(g1, 3).contains(q2));
		Assert.assertTrue(m1.getQuestions(g1, 3).contains(q3));
		

		/**
		 * What if the member is not in the group?
		 * What if the group has less than n questions?
		 * What if the group has more than n questions?
		 * What if the group has multiple questions asked at once?
		 * What about negatives?
		 * Is the list sorted properly? 
		 * Does the list contain all of the items that i was expecting? 
		 * Are there any items missing? 
]		 * Is the list sorted in ascending or descending order? 
		 * Does the list contain the correct amount of items? 
		 * What else can I test here?
		 */
	}
	
	
	@Test
	//@Description("tests the beahavior of getting top n recent Answers from a Member's group")
	public   void testGetMemberRecentAnswers() {
		
		Member m1 = new Member("KD", "Adkins", "kdadkins", "ks@vsu", LocalDateTime.now());
		Group g1 = new Group("soccer", "a group for soccer", LocalDateTime.now());
		
		m1.joinGroup(g1, LocalDateTime.now());

		
		Question q1 = new Question("about cleats", "what are some good cleats?", LocalDateTime.now());
		Question q2 = new Question("ball size", "what size ball should I buy", LocalDateTime.now());
		Question q3 = new Question("Subs", "how many substitutions are allowed?", LocalDateTime.now());
		m1.addQuestion(g1, q1, LocalDateTime.now());
		m1.addQuestion(g1, q2, LocalDateTime.now());
		m1.addQuestion(g1, q3, LocalDateTime.now());
		
		Answer a1 = new Answer(q1, "answer questions 1", LocalDateTime.now());
		m1.addAnswer(g1, q1, a1, LocalDateTime.now());
		Answer a2 = new Answer(q1, "answer questions 2", LocalDateTime.now());
		m1.addAnswer(g1, q2, a2, LocalDateTime.now());
		Answer a3 = new Answer(q1, "answer questions 3", LocalDateTime.now());
		m1.addAnswer(g1, q3, a3, LocalDateTime.now());

		List<Answer> mAs = m1.getAnswers(g1);

		//Is Sorted
		Assert.assertTrue((mAs.get(0).getDate().isBefore(mAs.get(1).getDate())) || (mAs.get(0).getDate().equals(mAs.get(1).getDate())));
		Assert.assertTrue((mAs.get(0).getDate().isBefore(mAs.get(2).getDate())) || (mAs.get(0).getDate().equals(mAs.get(2).getDate())));
		Assert.assertTrue((mAs.get(1).getDate().isBefore(mAs.get(2).getDate())) || (mAs.get(1).getDate().equals(mAs.get(2).getDate())));
		
		Assert.assertEquals(3, mAs.size());
		Assert.assertEquals(3, m1.getAnswers(g1,5).size());
		Assert.assertEquals(3, m1.getAnswers(g1,5).size());
		
		Assert.assertEquals("Should return empty list", 0, m1.getGroups().get(0).getAnswers(-3).size());
		
		Assert.assertTrue(m1.getAnswers(g1,5).contains(a1));
		Assert.assertTrue(m1.getAnswers(g1,5).contains(a2));
		Assert.assertTrue(m1.getAnswers(g1,5).contains(a3));
		/**
		 * What if the member is not in the group?
		 * What if the group has less than n answers?
		 * What if the group has more than n answers?
		 * What if the group has multiple answers provided at once?
		 * What about negatives?
		 * Is the list sorted properly? 
		 * Does the list contain all of the items that i was expecting? 
		 * Are there any items missing? 
		 * Does the list contain duplicates? 
		 * Is the list sorted in ascending or descending order? 
		 * Does the list contain the correct amount of items? 
		 * What else can I test here?
		 */
	}
	
	@Test
	//@Description("tests the beahavior of getting top n active Members from a Group")
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
		m4.addAnswer(g1, q5, a4, LocalDateTime.now());
		
		
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
		
		/**
		 * What about ties?
		 * What if member is active in less than n groups?
		 * What if member is active in more than n groups?
		 * What about negatives?
		 * Is the list sorted properly? 
		 * Does the list contain all of the items that i was expecting? 
		 * Are there any items missing? 
		 * Is the list sorted in ascending or descending order? 
		 * Does the list contain the correct amount of items? 
		 * What else can I test here?
		 */
	}
	
	
	@Test
	//@Description("tests the beahavior of getting top n recent Answers from a group")
	public   void testGetGroupRecentAnswers() {
		Member m1 = new Member("KD", "Adkins", "kdadkins", "ks@vsu", LocalDateTime.now());
		Group g1 = new Group("soccer", "a group for soccer", LocalDateTime.now());
		
		m1.joinGroup(g1, LocalDateTime.now());

		
		Question q1 = new Question("about cleats", "what are some good cleats?", LocalDateTime.now());
		Question q2 = new Question("ball size", "what size ball should I buy", LocalDateTime.now());
		Question q3 = new Question("Subs", "how many substitutions are allowed?", LocalDateTime.now());
		m1.addQuestion(g1, q1, LocalDateTime.now());
		m1.addQuestion(g1, q2, LocalDateTime.now());
		m1.addQuestion(g1, q3, LocalDateTime.now());
		
		Answer a1 = new Answer(q1, "answer questions 1", LocalDateTime.now());
		m1.addAnswer(g1, q1, a1, LocalDateTime.now());
		Answer a2 = new Answer(q1, "answer questions 2", LocalDateTime.now());
		m1.addAnswer(g1, q2, a2, LocalDateTime.now());
		Answer a3 = new Answer(q1, "answer questions 3", LocalDateTime.now());
		m1.addAnswer(g1, q3, a3, LocalDateTime.now());

		List<Answer> grAs = g1.getAnswers();

		
		//Is Sorted
		Assert.assertTrue((grAs.get(0).getDate().isBefore(grAs.get(1).getDate())) || (grAs.get(0).getDate().equals(grAs.get(1).getDate())));
		Assert.assertTrue((grAs.get(0).getDate().isBefore(grAs.get(2).getDate())) || (grAs.get(0).getDate().equals(grAs.get(2).getDate())));
		Assert.assertTrue((grAs.get(1).getDate().isBefore(grAs.get(2).getDate())) || (grAs.get(1).getDate().equals(grAs.get(2).getDate())));
		
		Assert.assertEquals(3, grAs.size());
		Assert.assertEquals(3, g1.getAnswers(5).size());
		Assert.assertEquals(0, g1.getAnswers(-3).size());
		
		
		Assert.assertTrue(g1.getAnswers(3).contains(a1));
		Assert.assertTrue(g1.getAnswers(3).contains(a2));
		Assert.assertTrue(g1.getAnswers(3).contains(a3));
		/**
		 * What if the group has less than n answers?
		 * What if the group has more than n answers?
		 * What if the group has multiple answers provided at once?
		 * What about negatives?
		 * Is the list sorted properly? 
		 * Does the list contain all of the items that i was expecting? 
		 * Are there any items missing? 
		 * Does the list contain duplicates? 
		 * Is the list sorted in ascending or descending order? 
		 * Does the list contain the correct amount of items? 
		 * What else can I test here?
		 */
	}
	
	
	@Test
	//@Description("tests the beahavior of getting top n recent Questions from a Group")
	public   void testGetGroupRecentQuestions() {
		Member m1 = new Member("KD", "Adkins", "kdadkins", "ks@vsu", LocalDateTime.now());
		Group g1 = new Group("soccer", "a group for soccer", LocalDateTime.now());
		
		m1.joinGroup(g1, LocalDateTime.now());

		
		Question q1 = new Question("about cleats", "what are some good cleats?", LocalDateTime.now());
		Question q2 = new Question("ball size", "what size ball should I buy", LocalDateTime.now());
		Question q3 = new Question("Subs", "how many substitutions are allowed?", LocalDateTime.now());
		m1.addQuestion(g1, q1, LocalDateTime.now());
		m1.addQuestion(g1, q2, LocalDateTime.now());
		m1.addQuestion(g1, q3, LocalDateTime.now());

		List<Question> grQs = g1.getQuestions();

		
		//Is Sorted
		Assert.assertTrue((grQs.get(0).getDate().isBefore(grQs.get(1).getDate())) || (grQs.get(0).getDate().equals(grQs.get(1).getDate())));
		Assert.assertTrue((grQs.get(0).getDate().isBefore(grQs.get(2).getDate()))  || (grQs.get(0).getDate().equals(grQs.get(2).getDate())));
		Assert.assertTrue((grQs.get(1).getDate().isBefore(grQs.get(2).getDate()))  || (grQs.get(1).getDate().equals(grQs.get(2).getDate())));
		
		Assert.assertEquals(3, grQs.size());
		Assert.assertEquals(3, m1.getGroups().get(0).getQuestions(5).size());
		Assert.assertEquals(new ArrayList<Question>(), m1.getGroups().get(0).getQuestions(-3));
		
		
		Assert.assertTrue(grQs.contains(q1));
		Assert.assertTrue(grQs.contains(q2));
		Assert.assertTrue(grQs.contains(q3));
		/**
		 * What if the group has less than n questions?
		 * What if the group has more than n questions?
		 * What if the group has multiple questions asked at once?
		 * What about negatives?
		 * Is the list sorted properly? 
		 * Does the list contain all of the items that i was expecting? 
		 * Are there any items missing? 
		 * Does the list contain duplicates? 
		 * Is the list sorted in ascending or descending order? 
		 * Does the list contain the correct amount of items? 
		 * What else can I test here?
		 */
	}
	
}
