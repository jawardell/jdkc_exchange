package tests;
import java.time.*;
import org.junit.*;

import jdkc_exchange.Question;

public final class QuestionTest {
	@Test
	public void questionConstructorTest() {
		LocalDateTime date = null;
		Question question = new Question("title", "text", date = LocalDateTime.now());
		Assert.assertEquals("This is what the initial value of title should be: ", "title", question.getTitle());
		Assert.assertEquals("This is what the initial value of text should be: ", "text", question.getText());
		Assert.assertEquals("This is what the initial value of date should be: ", date, question.getDate());
	}
	@Test
	public void questionGetTitleTest() {
		Question question = new Question("title", null, null);
		Assert.assertEquals("This is what the value of title should be: ", "title", question.getTitle());
	}
	@Test
	public void questionSetTitleTest() {
		Question question = new Question("title", null, null);
		question.setTitle("new title");
		Assert.assertEquals("This is what the value of title should be after set: ", "new title", question.getTitle());
	}
	@Test
	public void questionAddAnswerTest() {
		Question question = new Question(null, null, null);
		question.addAnswer(null);
		Assert.assertEquals("This is what the length of the array list of answers should be after adding (null) answer: ", 0, question.getAnswers().size());		
	}
	@Test
	public void questionGetAnswersTest() {
		Question question = new Question(null, null, null);
		Assert.assertEquals("This is what the length of the array list of answers should be: ", 0, question.getAnswers().size());		
	}
	@Test
	public void questionToStringTest() {
		Question question = new Question(null, null, null);
		Assert.assertEquals(35, question.toString().length());		
	}
	
}
