package tests;

import java.time.*; 
import org.junit.*;
import jdkc_exchange.Answer;
//import jdk.jfr.Description;

public final class AnswerTest {
	@Test
	public void testLike() {
		Answer ans  = new Answer(null,null, null);
		Assert.assertTrue(ans.getNumLikes() == 0);
		
		Answer a1 = new Answer(null, "", null);
		Assert.assertTrue(a1.getNumLikes() == 0);

		Answer a2 = new Answer(null, " ", null);
		Assert.assertTrue(a2.getNumLikes() == 0);
		
		Answer a3 = new Answer(null, "falsdfjasd", null);
		Assert.assertTrue(a3.getNumLikes() == 0);
	}
	
	@Test
	public void handlesNullConstructorProperly() {
		//create an instance of Answer
		Answer ans  = new Answer(null, null, null);
			
		/**
		 * Constructors shouldn't allow things to be initialized to null. 
		 * This causes null pointer exceptions to occur.
		 */
		Assert.assertTrue("Text shouldn't be initialized to null", ans.getText() != null);
		Assert.assertTrue("Date should be initialized to null", ans.getDate() == null);
	}
	
	@Test
	public void dateCreatedMustBeValueUsedInConstructor() {
		//create an instance of Answer
		LocalDateTime d1 = null;
		Answer ans = new Answer(null,null, null);
		
		//assert statement
		Assert.assertEquals("Date Created must the same as one used in constructor", d1, ans.getDate());
	}
	
	@Test
	public void textCreatedMustBeValueUsedInConstructor() {
		//create an instance of Group
		Answer ans = new Answer(null,null, null);
		
		//assert statement
		Assert.assertEquals("Date Created must the same as one used in constructor", "",ans.getText());
	}
	
	@Test
	public void mustFollowToStringConvention() {
		Answer ans  = new Answer(null,null, null);
		System.out.println(ans.toString());
		//assert statements
		Assert.assertEquals("toString must follow proper convention",ans.toString(), ans.toString());
	}
	
	
}