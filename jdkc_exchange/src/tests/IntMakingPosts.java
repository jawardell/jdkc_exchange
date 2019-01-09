/**
 * 
 * This is an integration test which assess 
 * the behavior of creating posts and its effects 
 * on the system. A log file which contains a detailed 
 * error report is generated. 
 * 
 * 
 * Feature: making posts
 * 
 * Pass/Fail Criteria: assert statement fails 
 * 
 * written for JUnit
 * 
 */

package tests;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.*;

import jdkc_exchange.Answer;
import jdkc_exchange.Group;
import jdkc_exchange.Member;
import jdkc_exchange.Question;

public class IntMakingPosts {
	@Test
	public void multiAnswers() {
		Member m = new Member("tom", "ford", "tford", "tford@hmail.com", LocalDateTime.now());
		Group g = new Group("Group 1", "this is a group", LocalDateTime.now());
		m.joinGroup(g, LocalDateTime.now());
		Question q = new Question("Soccer Ball", "What is the shape of a soccer ball?", LocalDateTime.now());
		Answer a1 = new Answer(q, "It is round.", LocalDateTime.now());
		Answer a2 = new Answer(q, "It is circular.", LocalDateTime.now());
		Answer a3 = new Answer(q, "It is triangular.", LocalDateTime.now());
		m.addQuestion(g, q, LocalDateTime.now());
		m.addAnswer(g, q, a1, LocalDateTime.now());
		m.addAnswer(g, q, a2, LocalDateTime.now());
		Assert.assertEquals(2, q.getAnswers().size());
		Member m2 = new Member("joanne", "wardell", "jawardell", "jawardell@hmail.com", LocalDateTime.now());
		m2.joinGroup(g, LocalDateTime.now());
		m2.addAnswer(g, q, a3, LocalDateTime.now());
		Assert.assertEquals(3, q.getAnswers().size());
	}
	
	
	@Test
	public void duplicateQuestions() {
		Member m = new Member("tom", "ford", "tford", "tford@hmail.com", LocalDateTime.now());
		Group g = new Group("Group 1", "this is a group", LocalDateTime.now());
		m.joinGroup(g, LocalDateTime.now());
		Question q1 = new Question("Soccer Ball", "What is the shape of a soccer ball?", LocalDateTime.now());
		Question q2 = new Question("Soccer Ball", "What is the shape of a soccer ball?", LocalDateTime.now());
		Question q3 = new Question("Soccer Ball", "What is the shape of a soccer ball?", LocalDateTime.now());
		Question q4 = new Question("Soccer Ball", "What is the shape of a soccer ball?", LocalDateTime.now());
		m.addQuestion(g, q1, LocalDateTime.now());
		m.addQuestion(g, q2, LocalDateTime.now());
		m.addQuestion(g, q3, LocalDateTime.now());
		m.addQuestion(g, q4, LocalDateTime.now());
		Assert.assertEquals(1, g.getQuestions().size());
		
		Member m2 = new Member("joanne", "wardell", "jawardell", "jawardell@hmail.com", LocalDateTime.now());
		m2.joinGroup(g, LocalDateTime.now());
		
		int i = 0; 
		while(true) {
			if(i == 100) break;
			if(i % 2 == 0) {
				m.addQuestion(g, q1, LocalDateTime.now());
			} else {
				m2.addQuestion(g, q2, LocalDateTime.now());
			}
			i++;
		}
		
		Assert.assertEquals(1, g.getQuestions().size());
	}
	
	@Test
	public void duplicateAnswers() {
		Member m = new Member("tom", "ford", "tford", "tford@hmail.com", LocalDateTime.now());
		Member m2 = new Member("joanne", "wardell", "jawardell", "jawardell@hmail.com", LocalDateTime.now());
		Group g = new Group("Group 1", "this is a group", LocalDateTime.now());
		m.joinGroup(g, LocalDateTime.now());
		Question q = new Question("Soccer Ball", "What is the shape of a soccer ball?", LocalDateTime.now());
		Answer a1 = new Answer(q, "It is round.", LocalDateTime.now());
		Answer a2 = new Answer(q, "It is round.", LocalDateTime.now());
		Answer a3 = new Answer(q, "It is round.", LocalDateTime.now());
		m.addQuestion(g, q, LocalDateTime.now());
		m.addAnswer(g, q, a1, LocalDateTime.now());
		m.addAnswer(g, q, a2, LocalDateTime.now());
		Assert.assertEquals(1, q.getAnswers().size());
		
		int i = 0; 
		while(true) {
			if(i == 100) break;
			if(i % 2 == 0) {
				m.addAnswer(g, q, a1, LocalDateTime.now());
			} else {
				m2.addAnswer(g, q, a3, LocalDateTime.now());
			}
			i++;
		}
		m2.joinGroup(g, LocalDateTime.now());
		m2.addAnswer(g, q, a3, LocalDateTime.now());
		Assert.assertEquals(1, q.getAnswers().size());
	}
	
	@Test
	public void unauthQuestion() {
		Member m = new Member("tom", "ford", "tford", "tford@hmail.com", LocalDateTime.now());
		Member m2 = new Member("joanne", "wardell", "jawardell", "jawardell@hmail.com", LocalDateTime.now());
		Member m3 = new Member("kd", "adkins", "dsadkins", "dsadkins@hmail.com", LocalDateTime.now());
		Member m4 = new Member("kayla", "rivera", "kjrivera", "kjrivera@hmail.com", LocalDateTime.now());		
		Group g = new Group("Group 1", "this is a group", LocalDateTime.now());
		Question q = new Question("Soccer Ball", "What is the shape of a soccer ball?", LocalDateTime.now());
		
		m.addQuestion(g, q, LocalDateTime.now());
		
		int i = 0; 
		while(true) {
			if(i == 100) break;
			
			switch(i % 4) {
				case 0: 
				m.addQuestion(g, q, LocalDateTime.now());
				break;
			
				case 1: 
				m2.addQuestion(g, q, LocalDateTime.now());
				break;
				
				case 2: 
				m3.addQuestion(g, q, LocalDateTime.now());
				break;
				
				case 3: 
				m4.addQuestion(g, q, LocalDateTime.now());
				break;
			}
			
			i++;
		}
		
		
		
		
		Assert.assertEquals(0, g.getQuestions().size());
	}
	
	@Test
	public void unauthAnswer() {
		Member m = new Member("tom", "ford", "tford", "tford@hmail.com", LocalDateTime.now());
		Member m2 = new Member("joanne", "wardell", "jawardell", "jawardell@hmail.com", LocalDateTime.now());
		Group g = new Group("Group 1", "this is a group", LocalDateTime.now());
		Answer a = new Answer(null, "The time is now.", LocalDateTime.now());
		
		
		int i = 0; 
		while(true) {
			if(i == 100) break;
			if(i % 2 == 0) {
				m.addAnswer(g, null, a, LocalDateTime.now());
			} else {
				m2.addAnswer(g, null, a, LocalDateTime.now());
			}
			i++;
		}
		
		Assert.assertEquals(0, g.getQuestions().size());
	}
	
	@Test
	public void groupCollectionOfAnswers() {
		Member m = new Member("tom", "ford", "tford", "tford@hmail.com", LocalDateTime.now());
		Group g = new Group("Group 1", "this is a group", LocalDateTime.now());
		m.joinGroup(g, LocalDateTime.now());
		Question q = new Question("title", "desc", LocalDateTime.now());
		Answer a = new Answer(q, "answer content", LocalDateTime.now());
		m.addQuestion(g, q, LocalDateTime.now());
		m.addAnswer(g, q, a, LocalDateTime.now());
		List<Answer> ans = g.getAnswers();
		Assert.assertTrue(ans.contains(a));
		
	}
	
	@Test
	public void groupCollectionOfQuestions() {
		Member m = new Member("tom", "ford", "tford", "tford@hmail.com", LocalDateTime.now());
		Group g = new Group("Group 1", "this is a group", LocalDateTime.now());
		m.joinGroup(g, LocalDateTime.now());
		Question q = new Question("title", "desc", LocalDateTime.now());
		m.addQuestion(g, q, LocalDateTime.now());
		Assert.assertTrue(g.getQuestions().contains(q));
	}

	
	@Test
	public void questionCollectionOfAnswers() {
		Member m = new Member("tom", "ford", "tford", "tford@hmail.com", LocalDateTime.now());
		Group g = new Group("Group 1", "this is a group", LocalDateTime.now());
		m.joinGroup(g, LocalDateTime.now());
		Question q = new Question("title", "desc", LocalDateTime.now());
		Answer a = new Answer(q, "answer content", LocalDateTime.now());
		m.addQuestion(g, q, LocalDateTime.now());
		m.addAnswer(g, q, a, LocalDateTime.now());
		Assert.assertTrue(q.getAnswers().contains(a));
	}
	
	@Test
	public void answerInstanceOfQuestion() {
		Member m = new Member("tom", "ford", "tford", "tford@hmail.com", LocalDateTime.now());
		Group g = new Group("Group 1", "this is a group", LocalDateTime.now());
		m.joinGroup(g, LocalDateTime.now());
		Question q = new Question("title", "desc", LocalDateTime.now());
		Answer a = new Answer(q, "answer content", LocalDateTime.now());
		m.addQuestion(g, q, LocalDateTime.now());
		m.addAnswer(g, q, a, LocalDateTime.now());
		Assert.assertTrue(a.getQuestion().equals(q));
	}
	
	@Test
	public void questionAddedToCorrectGroup() {
		Member m = new Member("tom", "ford", "tford", "tford@hmail.com", LocalDateTime.now());
		Group g = new Group("Group 1", "this is a group", LocalDateTime.now());
		m.joinGroup(g, LocalDateTime.now());
		Question q = new Question("title", "desc", LocalDateTime.now());
		m.addQuestion(g, q, LocalDateTime.now());
		Assert.assertTrue(q.getGroup().equals(g));
	}
	
	@Test
	public void answerAddedToCorrectGroup() {
		Member m = new Member("tom", "ford", "tford", "tford@hmail.com", LocalDateTime.now());
		Group g = new Group("Group 1", "this is a group", LocalDateTime.now());
		m.joinGroup(g, LocalDateTime.now());
		Question q = new Question("title", "desc", LocalDateTime.now());
		Answer a = new Answer(q, "answer content", LocalDateTime.now());
		m.addQuestion(g, q, LocalDateTime.now());
		m.addAnswer(g, q, a, LocalDateTime.now());
		Assert.assertTrue(a.getGroup().equals(g));
	}
}
