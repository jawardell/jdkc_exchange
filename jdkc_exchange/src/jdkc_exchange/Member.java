package jdkc_exchange;

import java.time.LocalDateTime;
import java.util.*;


/**
 * A class which implements the concept of a Member individual.
 * The Member class allows the user to identify as an individual 
 * in the system. Members are unique on email and screen name. 
 * Members also contain additional information such as their first 
 * and last names, the date which they were created, and their membership 
 * list. The Member alone cannot participate in the system without 
 * memberships. It is not until a Member joins a Group that they can 
 * make posts. 
 * @author Joanne Wardell
 * @see Membership
 */

public class Member {
	private LocalDateTime dateCreated;
	private String firstName;
	private String lastName;
	private String screenName;
	private String emailAddress;
	private Map<String, Membership> memberships;

	/**
	 * Creates an instance of Member.
	 * @param firstName    the first name of the member
	 * @param lastName     the last name of the member
	 * @param screenName   the member's user name; must be different than the email address
	 * @param emailAddress the member's email address; can't be the same as the screen name
	 * @param dateCreated  the date/time the user creates an account
	 */
	public Member(String firstName, String lastName, String screenName, String emailAddress,LocalDateTime dateCreated) {
		this.firstName = firstName == null ? "" : firstName;
		this.lastName = lastName == null? "" : lastName;
		this.emailAddress = emailAddress == null ? "" : emailAddress;
		this.screenName = screenName == null ? "" : screenName;
		this.dateCreated = dateCreated == null ? LocalDateTime.now() : dateCreated;
		memberships = new HashMap<>();
	}

	/**
	 * Joins the member to the group by creating a new membership 
	 * Uses the date parameter as the date/time when the group was joined.
	 * 
	 * @param group the group that the member is joining
	 * @param date  the date/time at which the member joined the group
	 */
	public void joinGroup(Group group, LocalDateTime date) {
		if (group != null && date != null) {
			Membership membership = new Membership(group, this, date);
			group.addMembership(membership);
			memberships.put(group.getTitle(), membership);
		}
	}

	/**
	 * Returns the number of groups that the member belongs to.
	 * 
	 * @return the number of groups the Member's memberships have
	 */
	public int getNumGroups() {
		return memberships.size();
	}

	/**
	 * Returns the group corresponding the to group ID
	 * 
	 * @param groupId the ID associated with a specific instance of Group
	 * @return the group with the matching groupID
	 */
	public Group getGroup(String groupId) {
		if((groupId == null) || (memberships == null)) {
			return null;
		}
		if(memberships.get(groupId) == null) {
			return null;
		}
		return memberships.get(groupId).getGroup();
	}

	/**
	 * Iterates over the list of memberships and collects the groups, populating an
	 * ArrayList of type Group which is returned at the end.
	 * 
	 * @return an ArrayList of groups the Member's memberships have
	 */
	public List<Group> getGroups() {
		List<Group> list = new ArrayList<>();
		for(Membership m : memberships.values())
			list.add(m.getGroup());
		list.sort(new CompareOnTitle());
		return list;
	}

	/**
	 * Adds a question to a group and updates the memberships. 
	 * Searches for the group using the title attribute.
	 * 
	 * @param group    the group that the question will be added to
	 * @param question the question that is added to the group
	 * @param time     the time that the question was asked
	 */
	public void addQuestion(Group group, Question question, LocalDateTime time) {
		boolean areNull = (group == null) || (question == null) || (time == null);
		if (memberships.containsKey(group.getTitle()) && !areNull) {
			Membership m  = memberships.get(group.getTitle());
			question.setMembership(m);//take ownership of question
			m.addQuestion(question);//take ownership of question
			group.addQuestion(question);//put question in group
		}
	}

	/**
	 * Returns the date/time this Member joined the group, not the time that the
	 * group was created. Uses the title attribute to locate the group.
	 * 
	 * @param group the group you are trying to get the date record from
	 * @return the date/time the group was joined
	 */
	public LocalDateTime getDateJoined(Group group) {
		if(group != null && memberships.containsKey(group.getTitle())) {
			return memberships.get(group.getTitle()).getDateJoined();
		}
		return null;
	}

	/**
	 * Adds this Member's answer to a question belonging to a group. 
	 * Uses the title attribute to locate the group.
	 * 
	 * @param group    the group to which the question and answer belong
	 * @param question the question which the answer completes
	 * @param answer   the answer to the group's question
	 * @param time     the time at which the answer was provided for the group's question
	 */
	public void addAnswer(Group group, Question question, Answer answer, LocalDateTime time) {
		boolean areNull = (group == null) || (question == null) || (answer == null) || (time == null);
		boolean hasMembership = memberships.containsKey(group.getTitle());
		
		if(!areNull && hasMembership) {
			Membership m = memberships.get(group.getTitle());
			
			boolean questionInGroup = m.getGroup().getQuestions().contains(question);
			
			if(questionInGroup) {
				answer.setMembership(m);//take ownership of answer
				m.addAnswer(answer, question);//take ownership of answer
				question.addAnswer(answer);//append answer to question
				group.addAnswer(answer, question);
			}
		}
	}

	/**
	 * Returns the list of questions from the specified group using the title attribute
	 * to locate the group.
	 * 
	 * @param group the instance of Group from which you want the questions
	 * @return an ArrayList containing the group's questions
	 */
	public List<Question> getQuestions(Group group) {
		if(group != null && memberships.containsKey(group.getTitle())) {
			List<Question> list = new ArrayList<>(group.getQuestions());
			list.sort(new CompareOnDate());
			return list;
		}
		

		return new ArrayList<>();
	}

	/**
	 * Returns the list of answers from the specified group using the title attribute
	 * to locate the group.
	 * 
	 * @param group the instance of Group from which you want the answers
	 * @return an ArrayList containing the group's questions
	 */
	public List<Answer> getAnswers(Group group) {	
		if(group != null && memberships.containsKey(group.getTitle())) {
			List<Answer> list = new ArrayList<>(group.getAnswers());
			list.sort(new CompareOnDate());
			return list;
		}
		return new ArrayList<>();
	}

	/**
	 * Returns a formatted string about this member.
	 * 
	 * @return formatted string about this
	 */
	public String toString() {
		String groups = new String();
		String questions = new String();
		String answers = new String();

		for (Membership membership : memberships.values()) {
			groups += membership.toString();
			for (Question question : membership.getQuestions().values()) 
					questions += question.toString();
				
			for (Answer answer : membership.getAnswers().keySet()) 
					answers += answer.toString();
				
		}

		return String.format("\n\n~~~~~Member~~~~~\n" 
				+ "Name: %s, %s\n"
				+ "Email: %s\n" 
				+ "Screen Name: %s\n" 
				+ "Date Created:%s\n"
				+ "Groups:\n%s\n" 
				+ "Questions:\n%s\n" 
				+ "Answers:\n%s\n" 
				+ "~~~~~~~~~~~~~~~~\n\n",
				lastName, firstName, emailAddress, screenName, 
				dateCreated.toString(), groups, questions, answers);
	}

	/**
	 * Returns the emailAddress instance variable.
	 * 
	 * @return emailAddress instance variable
	 */
	public String getEmail() {
		return emailAddress;
	}

	/**
	 * Returns this member's time stamp
	 * 
	 * @return the date the member was created
	 */
	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	/**
	 * Returns this member's first name
	 * 
	 * @return firstName instance variable
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns this member's last name.
	 * 
	 * @return lastName instance variable
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns this member's screen name.
	 * 
	 * @return this member's screen name
	 */
	public String getScreenName() {
		return screenName;
	}
	
	/**
	 * Returns the membership associated with the group parameter.
	 * @param g   The Group instance whose Membership is being requested.
	 * @return membership object to the requested group
	 */
	protected Membership getMembership(Group g) {
		if (this.getGroups().contains(g) && g != null) {
			for(Membership m : memberships.values()) {
				if(m.getGroup().equals(g)) {
					return m;
				}
			}
		}
		return null;
	}
	
	
	
	/**
	 * Protected helper method for getting this Member's activity level of a group.
	 * @param g group which you want the activity level of
	 * @return activity level which is the sum of the number of question
	 * and the number of answers this user has in the group
	 */
	protected int getActivityLevel(Group g) {
		if(g != null) 
		return getGroup(g.getTitle()).getAnswers().size() 
			+ getGroup(g.getTitle()).getQuestions().size();
		return -1;
	}
	
	
	
	
	/**
	 * Returns a list of the n Groups that the member is most active in, 
	 * sorted on title. Activity measured by the total posts made by the Member.
	 * @param n   the number of groups you are requesting
	 * @return    a list of the Member's top n most active groups
	 */
	public List<Group> getGroups(int n) {
		/**
		 * make an array list with all of the groups in it
		 * sort the list on activity level 
		 * iterate through the list 	
		 * check to see what n we are on. 
		 * if we are at n, stop do not continue
		 * otherwise, add the element at i to the res AL
		 * only increase count if the tie is broken 
		 * 
		 * stop doing this when count is equal to n
		 */
		
		if(n <= 0)
			return new ArrayList<>();
		
		List<Group> groups = this.getGroups();
		if(groups.size() <= n)
			return groups;
		
		List<Group> res = new ArrayList<>();
		Collections.sort(groups, new CompareOnActivityLevel());
		int count, curr, prev;
		curr = prev = count = -1;
		count++;
		for(Group g : groups) {
			curr = getActivityLevel(g);

			boolean tieBroken = (curr != prev) && (prev != -1);
			
			if(tieBroken) 
				count++;
			
			if(count == n)
				break;
			
			res.add(g);
			
			prev = curr;
		}
		Collections.sort(res, new CompareOnTitle());
		return res;
	}
	
	
	
	/**
	 * Comparator for sorting on activity group level
	 */
	private class CompareOnActivityLevel implements Comparator<Group> {

		@Override
		public int compare(Group g1, Group g2) {
			return getActivityLevel(g2) - getActivityLevel(g1);	
		}
	}
	
	
	
	
	/**
	 * Returns the n most recent Questions asked by this Member in this Group sorted 
	 * on the order they were asked, most recent first.
	 * @param group     the group from which you want the n most recently asked questions
	 * @param n         the number of questions that you are requesting
	 * @return a list of the top n most recently asked Questions asked by this Member
	 */
	public List<Question> getQuestions(Group group, int n) {
		/**
		 * Get the list of questions in group that this member has posted. 
		 * Sort it by date using the comparator. 
		 * If the list contains n or less item, just return the list. 
		 * Otherwise: iterate backwards thru the sorted list.
		 * Remove the oldest questions until the list is of size n.
		 * Return the list.
		 */
		
		List<Question> questions = this.getQuestions(group);
		questions.sort(new CompareOnDate());

		
		if(questions.size() <= n) {
			return questions;
		}
		
		for(int i = questions.size() - 1; i >= 0; i--) {
			if(questions.size() == n) {
				break;
			}
			questions.remove(i);
		}
		
		return questions;
	}
	
	
	/**
	 * Returns the n most recent answer asked by this member in this 
	 * group sorted on the order they were provided, most recent first.
	 * @param group     the group from which you want the n most recently posted answers
	 * @param n         the number of answers that you are requesting
	 * @return  a list of the top n most recently posted Answers asked by this Member
	 */
	public List<Answer> getAnswers(Group group, int n) {
		/**
		 * Get the list of answers in group that this member has posted. 
		 * Sort it by date using the comparator. 
		 * If the list contains n or less item, just return the list. 
		 * Otherwise: iterate backwards thru the sorted list.
		 * Remove the oldest answers until the list is of size n.
		 * Return the list.
		 */
		
		List<Answer> answers = this.getAnswers(group);
		answers.sort(new CompareOnDate());

		
		if(answers.size() <= n) {
			return answers;
		}
		
		for(int i = answers.size() - 1; i >= 0; i--) {
			if(answers.size() == n) {
				break;
			}
			answers.remove(i);
		}
		
		return answers;	
	}
	
	
	/**
	 * A comparator for sorting Groups on title.
	 * @author joannewardell
	 */
	private class CompareOnTitle implements Comparator<Group> {
		/**
		 * Compares two Group Objects using the title attribute.
		 * @param o1     one of the Group Objects being compared
		 * @param o2     one of the Group Objects being compared
		 * @return Returns an integer value determining if the two objects 
		 * are considered equal. Returns -1 if o1's title occurs after o2's title 
		 * in the English alphabet, 0 if they are the same, and 1 if 
		 * o1's title occurs after o2's title in the English alphabet
		 */
		@Override
		public int compare(Group o1, Group o2) {
			if((o1 instanceof Group) && (o2 instanceof Group)) {
				Group g1 = (Group)o1;
				Group g2 = (Group)o2;
				return g1.getTitle().compareTo(g2.getTitle());
			}
			return 0;
		}
	}
	
	
	/**
	 * A comparator for sorting Posts on date posted.
	 * @author joannewardell
	 *
	 */
	private class CompareOnDate implements Comparator<Post> {
		/**
		 * Compares two Post Objects using the dateCreated attribute.
		 * @param o1    one of the Post Objects being compared
		 * @param o2    one of the Post Objects being compared
		 * @return returns -1 if the question o1 has an earlier 
		 * time stamp than the o2 post, 0 if they have the same 
		 * time stamp, and 1 if o1 has a later time stamp than o2.
		 */
		@Override
		public int compare(Post o1, Post o2) {
			if((o1 instanceof Post) && (o2 instanceof Post)) {
				Post p1 = (Post)o1;
				Post p2 = (Post)o2;
				return p1.getDate().compareTo(p2.getDate());
			}
			
			return 0;
		}
	}
	
	
	/**
	 * Allows this member to add a like to the answer a
	 * @param a     the answer this member wants to like
	 * @return   Returns true if a like was added to the Answer.
	 */
	public boolean like(Answer a) {
		if(getMembership(a.getGroup()) == null) {
			return false;
		}
		else {
			//use membership helper method
			return getMembership(a.getGroup()).like(a);
		}
	}
	
	
	/**
	 * Overridden equals method which bases equality on String title
	 * @param o    object being compared to this 
	 * @return false if the two objects do not have the same title
	 */
	@Override
	public boolean equals(Object o) {
		if(o instanceof Member) {
			Member m = (Member)o;
			return emailAddress.equals(m.getEmail());
		}
		return false;
	}
	
}