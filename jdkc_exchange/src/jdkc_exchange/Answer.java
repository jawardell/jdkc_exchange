package jdkc_exchange;

import java.time.*;
import java.util.*;

/**
 * A class which implements the concept of responding to questions.
 * The Answer class is an instance type for Post 
 * which implements the functionality of responding to 
 * a Question. Since Questions belong to Groups and 
 * Answers belong to Questions, Answers therefore must 
 * belong to one unique Group. Answers are unique based on the 
 * text attribute. Answers can become associated with 
 * Questions, that is, Questions can have one or 
 * more Answer responses. In addition, Answers 
 * contain likes which can be added by Members in 
 * the same Group as this Answer. Members can like an Answer only once
 * and Members outside of the Answer group may not like this Answer.
 * @author Kayla Rivera
 * @author KD Adkins
 * @see Question
 * @see Post
 */


public class Answer extends Post  {
	private Set<String> emails;
	
	private Question question;
	/**
	 * Creates an instance of Answer. Each instance of Answer requires and 
	 * instance of Question.
	 * @param question The instance of Question to which this Answer corresponds.
	 * @param text The text of the answer.
	 * @param date The Date the answer was posted.
	 */
	public Answer(Question question, String text, LocalDateTime date) {
		super(text, date);
		this.question = question;
		emails = new TreeSet<>();
	}

	/**
	 * Returns the question instance connected to this answer instance.
	 * @return the question instance
	 */
	public Question getQuestion() {
		return question;
	}
	
	/**
	 * Simple String that gives information about this answer instance.
	 * @return String an informative string about this
	 */
	public String toString() {
		if(question == null) {
			
			
			return String.format("%s", "\nNULL QUESTION\n");
		}
		else {
			
			return String.format("\n\nANSWER to Q:\"%s\"\nA:\"%s\"\n\n", 
					getQuestion().getText(), getText());
		}
	}
	
	/**
	 * Override the object equals method such that the 
	 * two answers are equivalent if they have the same content
	 * @return returns true if the two answers have the same text
	 */
	public boolean equals(Object o) {
		if(o instanceof Answer) {
			Answer a = (Answer)o;
			return a.getText().equalsIgnoreCase(text);
		}
		return false;
	}
	
	
	/**
	 * Adds a like to this post if the user has not already liked it.
	 * Likes are unique on email address of the member.
	 * @param email  The email of the Member who liked this Answer.
	 * @return returns true if the email address was added successfully
	 */
	protected boolean addLike(String email) {
		return emails.add(email);
		
	}
	
	/**
	 * Returns the number of likes this answer has.
	 * @return number of likes the answer has
	 */
	public int getNumLikes() {
		return emails.size();
	}
	
	/**
	 * Returns the set likers for the answer.
	 * @return set of email addresses of people who liked this
	 */
	public Set<String> getWhoLiked() {
		return emails;
	}
}