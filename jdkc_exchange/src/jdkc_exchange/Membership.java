package jdkc_exchange;
import java.time.*;
import java.util.*;

/**
 * 
 * A class for authenticating Members of Groups. 
 * The Membership instance connects the Member to 
 * the Group and is heavily used to check that a Member 
 * is indeed in the Group. The Membership instance is unique 
 * per Member-Group connection, that is, one Member cannot 
 * have multiple memberships to the same Group. 
 * @author Joanne Wardell
 *
 */


public class Membership {
	private Group group;
	private LocalDateTime dateJoined; 
	private int points;

	private Member member;
	private Map<String, Question> questions;
	private Map<Answer, Question> answers;
	
	
	/**
	 * MAP INFORMATION: 
	 * 
	 * use answer as the key and question as the value
	 * we can have different keys lead to the same values
	 * 
	 */
	
	
	
	/**
	* Creates an instance of Membership. 
	* Warning: the constructor fails if any of arguments are null.
	* @param group       the group to which the membership belongs
	* @param member      the member to which the membership belongs
	* @param dateJoined  the time stamp of the membership
	**/
	protected Membership(Group group, Member member, LocalDateTime dateJoined) {
		points = 0;
		boolean areNull = (member == null) || (group == null) || (dateJoined == null);
		if(!areNull) {
			questions = new HashMap<>();
			answers = new HashMap<>();
			this.dateJoined = dateJoined;
			this.group = group;
			this.member = member;
		} else {
			return;
		}	
	}
	
	/**
	 * Adds a question to the list of questions.
	 * 
	 * @param question the question that you want to 
	 * add to the list of questions.
	 */
	protected void addQuestion(Question question) {
		if(question != null) {
			if(question.getTitle() != null) {
				questions.put(question.getTitle(), question);
			}
		}
	}
	
	
	/**
	 * Adds and answer to the list of answers.
	 * @param answer The Answer instance to be 
	 * added to this Membership's collection of Answers.
	 * @param question The Question instance to be added 
	 * to this Membership's collection of Questions.
	 * 
	 */
	protected void addAnswer(Answer answer, Question question) {
		if(answer != null && question != null) {
			answers.put(answer, question);
		}

	}
	

	/**
	* Returns the group instance variable.
	* @return the group instance variable
	**/
	protected Group getGroup() {
		return group;
	}
	
	
	/**
	* Sets the points instance variable.
	* @param points	the value you want the current points to be
	**/
	protected void setPoints(int points) {
		this.points = points;
	}

	/**
	 * Returns the points instance variable.
	* @return the points instance variable
	**/
	protected int getPoints() {
		return points;
	}


	/**
	 * Returns the member instance variable.
	* @return the member instance variable
	**/
	protected Member getMember() {
		return member;
	}

	/**
	* @return the date and time of when the last group was joined
	**/
	protected LocalDateTime getDateJoined() {
		return dateJoined;
	}
	

	/**
	 * Returns the list of questions this membership has.
	 * @return list of questions this has
	 */
	protected Map<String, Question> getQuestions() {
		return questions;
	}
	
	/**
	 * Returns the list of answers this membership has.
	 * @return list of answers this has
	 */
	protected Map<Answer, Question> getAnswers() {
		return answers;
	}
	
	
	/**
	 * Returns a formatted string about this membership.
	 * @return formatted string about this
	 **/
	public String toString() {
		boolean areNull = (dateJoined == null) || (group == null) || (member == null);
		if(areNull) {
			return "\nNULL MEMBERSHIP\n";
		}
		
		return String.format("\n%s's MEMBERSHIP TO %s\n"
				+ "\nPoints: %d\n"
				+ "\nDate Joined: %s\n", 
				member.getScreenName(), 
				member.getGroup(group.getTitle()).getTitle(), 
				points, dateJoined.toString());
	}
	
	
	
	/**
	 * Helper method for allowing member to like answers
	 * @param a     the answer this member wants to like
	 * @return returns true if a like was successfully added 
	 * to the Answer with this Membership.
	 */
	protected boolean like(Answer a) {
		Group g = a.getMembership().getGroup();
		if(this.getGroup().getTitle() == g.getTitle()) {//equality?
			a.addLike(this.getMember().getEmail());
		}
		return false;
		
	}
}
