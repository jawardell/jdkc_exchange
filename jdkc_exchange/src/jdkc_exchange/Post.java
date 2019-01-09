package jdkc_exchange;

import java.time.LocalDateTime;

/**
 * A class for implementing 
 * the functionality of participating in a Group.
 * The Post object allows Group-Members to participate 
 * in the Groups that they belong to and to interact with 
 * other Group-Members. Posts contain text attributes, time 
 * stamps, and memberships. The membership attribute allows
 * the Group-Member who created the post to take ownership 
 * of it. The uniqueness of a Post depends on its instance type.
 * 
 * @author Christian Gerhardt
 * @see Question
 * @see Answer
 */

public abstract class Post {
	
	protected String text;
	protected LocalDateTime date;
	private Membership membership;
	/**
	 * Creates an instance of Post
	 * @param text the text for the post
	 * @param date sets the date for the post
	 */
	public Post(String text, LocalDateTime date){
		if(text == null) {
			this.text = "";
		}
		else {
			this.text = text;
		}
		date = date != null ? this.date = date : LocalDateTime.now();
	}
	/**
	 * returns the text from the instance
	 * @return the text from the instance
	 */
	public String getText(){
		return text;
	}
	/**
	 * returns the date from the instance
	 * @return the date from the instance
	 */
	public LocalDateTime getDate() {
		return date;
	}
	/**
	 * Sets the text variable for this instance.
	 * @param text The text to which you would like to 
	 * set the text instance variable to.
	 **/
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * returns the member of the post
	 * @return the member of the post
	 */
	public Member getAuthor() {
		if(membership != null) {
			return membership.getMember();
		}
		return null;
	}
	/**
	 * returns the group of the post
	 * @return the group of the post
	 */
	public Group getGroup() {
		if(membership != null) {
			return membership.getGroup();
		}
		return null;
	}
	/**
	 * sets the membership of the post instance
	 * @param membership takes in an instance of Membership
	 */
	protected void setMembership(Membership membership) {
		this.membership = membership;
	}
	/**
	 * gets the memberships of the instance
	 * @return the memberships of the instance
	 */
	protected Membership getMembership() {
		if(membership != null) {
			return this.membership;
		}
		return null;
	}
	
	
	@Override
	/**
	 * Override the Object equals method
	 * @return returns true if the posts are equivalent
	 */ 
	public abstract boolean equals(Object o);
	
	
}