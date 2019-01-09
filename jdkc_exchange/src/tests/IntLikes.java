/**
 * This is an integration test which assesses the feature of 
 * adding likes to posts. We will make sure that the multiplicities are 
 * correct. We will make sure that the multiplicities are correctly 
 * calculated. We will also make sure that the associations are correctly 
 * implemented. In addition, we makes sure that only qualified users create
 * add likes to posts. Users must be in the same group of the post that 
 * they are trying to like. Users must not already have an existing like.
 */


package tests;

import org.junit.*;
import java.time.LocalDateTime;

//import jdk.jfr.Description;

import jdkc_exchange.*;

public class IntLikes {
	
	//@Description("tests the functionality of adding the first like")
	@Test
	public void testLike() {
		Member m1 = new Member("Joanne", "Wardell", "jawardell", 
				"jawardell@vsu", LocalDateTime.now());
		Group g = new Group("kayaking", "a group about kayaking", LocalDateTime.now());
		m1.joinGroup(g, LocalDateTime.now());
		Question q = new Question("the time!","what time is it?", LocalDateTime.now());
		m1.addQuestion(g, q, LocalDateTime.now());
		
		
		Member m2 = new Member("Kayla", "Rivera", "kjrivera", 
				"kjrivera@vsu", LocalDateTime.now());
		Answer a = new Answer(q, "the time is NOW", LocalDateTime.now());
		m2.joinGroup(g, LocalDateTime.now());
		m2.addAnswer(g, q, a, LocalDateTime.now());
		
		m2.like(a);
		
		Assert.assertTrue(a.getNumLikes() == 1);
		
		m1.like(a);
		
		Assert.assertTrue(a.getNumLikes() == 2);

		
	}
	
	
	
	
	//@Description("tests the handling of muliple like attempts")
	@Test
	public void testMultiLike() {
		Member m1 = new Member("Joanne", "Wardell", "jawardell", 
				"jawardell@vsu", LocalDateTime.now());
		Group g = new Group("kayaking", "a group about kayaking", LocalDateTime.now());
		m1.joinGroup(g, LocalDateTime.now());
		Question q = new Question("the time!","what time is it?", LocalDateTime.now());
		m1.addQuestion(g, q, LocalDateTime.now());
		
		
		Member m2 = new Member("Kayla", "Rivera", "kjrivera", 
				"kjrivera@vsu", LocalDateTime.now());
		Answer a = new Answer(q, "the time is NOW", LocalDateTime.now());
		m2.joinGroup(g, LocalDateTime.now());
		m2.addAnswer(g, q, a, LocalDateTime.now());
		
		m1.like(a);
		
		Assert.assertTrue(a.getNumLikes() == 1);
		
		m1.like(a);
		
		Assert.assertTrue(a.getNumLikes() == 1);
		
	}
	
	//@Description("tests the handling of unauthorized like attempts")
	@Test
	public void testUnauthLike() {
		Member m1 = new Member("Joanne", "Wardell", "jawardell", 
				"jawardell@vsu", LocalDateTime.now());
		Group g = new Group("kayaking", "a group about kayaking", LocalDateTime.now());
		m1.joinGroup(g, LocalDateTime.now());
		Question q = new Question("the time!","what time is it?", LocalDateTime.now());
		m1.addQuestion(g, q, LocalDateTime.now());
		
		
		Member m2 = new Member("Kayla", "Rivera", "kjrivera", 
				"kjrivera@vsu", LocalDateTime.now());
		Answer a = new Answer(q, "the time is NOW", LocalDateTime.now());
		m2.joinGroup(g, LocalDateTime.now());
		m2.addAnswer(g, q, a, LocalDateTime.now());
		
		
		Member m3 = new Member("KD", "Adkins", "dsadkins", 
				"dsadkins@vsu", LocalDateTime.now());
		
		m3.like(a);
		
		Assert.assertTrue(a.getNumLikes() == 0);
		
		m1.like(a);
		
		Assert.assertTrue(a.getNumLikes() == 1);
		
		m3.like(a);

		Assert.assertTrue(a.getNumLikes() == 1);
		
		m2.like(a);

		Assert.assertEquals(2,a.getNumLikes());
		
		
		m3.like(a);

		
		Assert.assertTrue(a.getNumLikes() == 2);

		
	}
	
	
	/**
	 * These tests will reside inside of the jdkc_exchange package
	 * because they will test the private api.
	
	//@Description("tests that the associations are implemented correctly for likes")
	@Test
	public void answersHaveLikes() {
		
	}
	
	//@Description("tests that the multiplicities for likes are correct")
	@Test
	public void answersNumLikes() {
		
	}
	
	 */
}
