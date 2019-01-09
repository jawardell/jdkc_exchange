package jdkc_exchange;

import java.util.*;
import java.time.*;

/**
 * A class for implementing the concept of a Question. 
 * The Question is an instance type for Post which allows 
 * Group-Members to add inquiries to Groups. Questions contain 
 * multiple Answers and are unique based on the title attribute.
 * @author Kayla Rivera
 * @author Christian Gerhardt
 * @see Answer
 * @see Post
 */

public class Question extends Post {
	private String title;
	private Map<String, Answer> answers;
	
	/**
	 * Question constructor which takes in three params
	 * @param title the unique title of the question
	 * @param text	text that goes with the post
	 * @param date	date when the post is made
	 */
	public Question(String title, String text, LocalDateTime date) {
		super(text, date);
		if(title == null) {
			this.title = "";
		}else {
			this.title = title;
		}
		answers = new HashMap<>();

	}
	/**
	 * Gets the title of the question instance 
	 * @return title of the question instance 
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Set the title of the question post
	 * @param title The title to which 
	 * this Questions instance variable 
	 * should be set to. 
	 */
	public void setTitle(String title) {
		if(title != null) {
			this.title = title;
		}
	}
	/**
	 * Add an answer to this question instance
	 * @param answer The Answer instance 
	 * to be appended to this Question instance. 
	 */
	public void addAnswer(Answer answer) {
		if(answer != null) {
			answers.put(answer.getText(), answer);
		}
	}
	/**
	 * returns an list of answers from the question instance
	 * @return a list of answers from the question instance
	 */
	public List<Answer> getAnswers() {
		return new ArrayList<>(answers.values());
	}
	/**
	 * @return a string with information about the question instance 
	 */
	public String toString() {
		return String.format("\n\nQUESTION: \"%s\" "
				+ "\nProvided on: %s\n\n", text, date);
	}
	
	/**
	 * Override the object equals method such that the 
	 * two questions are equivalent if they have the same title
	 * @return returns true if the two questions have the same title
	 * AND have the same content
	 */
	public boolean equals(Object o) {
		if(o instanceof Question) {
			Question q = (Question)o;
			return q.getTitle().equalsIgnoreCase(title) && q.getText().equalsIgnoreCase(text);
		}
		return false;
	}


}