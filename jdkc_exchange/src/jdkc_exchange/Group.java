package jdkc_exchange;

import java.time.*;
import java.util.*;


/**
 * A class which implements the concept of Group.
 * A Group object contains memberships, members, questions, 
 * and answers. A Group is identified by its title, which 
 * is unique among Groups. A Group is also identified by 
 * its description which does not have to be unique. The Group 
 * object acts as a container for related Questions and 
 * the Answers that members add to them.
 * @author KD Adkins
 * @see Membership
 */
public class Group{
	private LocalDateTime dateCreated;
	private String title;
	private String description;
	private Map<Member, Membership> memberships;
	private Map<String, Member> members;
	private Map<String, Question> questions;
	private Map<Answer, Question> answers;

	/**
	 *                 MAP INFORMATION:
	 * memberships -- key: member     value: membership
	 * members     -- key: email      value: member
	 * questions   -- key: title      value: question
	 * answers     -- key: answer     value: question   	
	*/
	
	
	
	/**
	 * Constructor that instantiates a Group object.
	 * @param title String
	 * @param description String
	 * @param dateCreated LocalDateTime
	 */
	public Group(String title, String description, LocalDateTime dateCreated) {
		if (title == null) {
			this.title = "";
		} else {
			this.title = title;
		}
		if (description == null) {
			this.description = "";
		} else {
			this.description = description;
		}
		if (dateCreated == null) {
			this.dateCreated = LocalDateTime.MAX;
		} else {
			this.dateCreated = dateCreated;
		}
		
		memberships = new HashMap<>();
		members = new TreeMap<>();//sorts on name first
		questions = new HashMap<>();
		answers = new HashMap<>();
	}

	/**
	 * returns date Group was created
	 * @return date Date
	 */
	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	/**
	 * returns title of the group
	 * @return title of the group
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * returns description of the group
	 * @return returns description of type String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * returns the number of members in the group
	 * @return number of members type int
	 */
	public int getNumMembers() {
		return memberships.size();
	}

	/**
	 * returns a member of the group. Searched by email
	 * @param email String
	 * @return member of the group. null if acn't be found
	 */
	public Member getMember(String email) {
		if(email != null) {
			return members.get(email);
		}
		return null;
	}
	
	
	/**
	 * Custom comparator class
	 * This helps sort the Lists once converted from Maps
	 * @param email String
	 * @return member of the group. null if acn't be found
	 */
	private class SortNames implements Comparator<Member>{

	    public int compare(Member a, Member b) 
	    { 
	    	if(a.getLastName().compareToIgnoreCase(b.getLastName()) == 0) {
	    		return a.getFirstName().compareToIgnoreCase(b.getFirstName());
	    	}
	    	else {
	    		return a.getLastName().compareToIgnoreCase(b.getLastName()); 
	    	}
	    } 
	}
	
	/**
	 * returns a list of members sorted by last name, then first name
	 * @return list of Member objects
	 */
	public List<Member> getMembers() {
		
		List<Member> mems = new ArrayList<>(members.values());
		if(mems.size() == 0) {
			return new ArrayList<Member>(members.values());
		}
		Collections.sort(mems, new SortNames());
		Member memName = mems.get(0);//Return just the member so you can grab first or last name
		for(int i = 1; i < mems.size(); i++) {
			if(memName.getLastName().equalsIgnoreCase(mems.get(i).getLastName())) {
				//if the last names are the same then order based on first name
				if(memName.getFirstName().compareToIgnoreCase(mems.get(i).getLastName()) > 0) {	//str1 > str2
					Member temp = memName;
					memName = mems.get(i);//this is the smaller string 
					mems.set(i-1, temp);
					mems.set(i, memName);
					
				}
			}
		}
		return new ArrayList<Member>(members.values());
	}
	

	
	/**
	 * Helper method for getting a Question based on the title. 
	 * Matching IS case sensitive. 
	 * Matching DOES NOT care about trailing space
	 * Matching DOES care about space between words
	 * Returns Null if the Question is not in this group.
	 * Matching must be exact other than trailing space.
	 * Ex.) about boats  DOESNT MATCH   about Boats
	 * Ex.) about boats  DOESNT MATCH   about   boats
	 * Ex.) about boats  DOESNT MATCH   about b0ats
	 * @param title     the title for the Question that you are looking for 
	 * @return the Question object whos title matches what you are looking for
	 */
	protected Question getQuestion(String title) {
		title = title.trim();
		
		return questions.get(title);
		
		
	}
	
	
	
	/**
	 * Returns sorted list of questions.
	 * @return list of questions sorted on date created
	 */
	public List<Question> getQuestions() {
		ArrayList<Question> list = new ArrayList<>(questions.values());
		Collections.sort(list, new CompareOnDate());
		return list;
	}
	
	/**
	 * Returns n most recent questions sorted by date
	 * @param n   the number of questions being requested 
	 * @return list of questions sorted on order asked
	 */
	public List<Question> getQuestions(int n){
		ArrayList<Question> quest = new ArrayList<>(getQuestions());
		ArrayList<Question> questToReturn = new ArrayList<>();
		int i = 0;
		while(i != n && n > 0) {
			if(i == quest.size()) {
				Collections.sort(questToReturn, new CompareOnDate());
				Collections.reverse(questToReturn);
				return questToReturn;
			}
			questToReturn.add(quest.get(i));
			i++;
		}
		Collections.sort(questToReturn, new CompareOnDate());
		Collections.reverse(questToReturn);
		return questToReturn;
	}
	
	/**
	 * Returns n most recent answers sorted on order they were provided
	 * @param n   the number of answers being requested 
	 * @return list of answers sorted on date provided
	 */
	public List<Answer> getAnswers(int n){
		ArrayList<Answer> ans = new ArrayList<>(getAnswers());
		ArrayList<Answer> ansToReturn = new ArrayList<>();
		int i = 0;
		while(i != n && n > 0) {
			if(i == ans.size()) {
				Collections.sort(ansToReturn, new CompareOnDate());
				Collections.reverse(ansToReturn);
				return ansToReturn;
			}
			ansToReturn.add(ans.get(i));
			i++;
		}
		Collections.sort(ansToReturn, new CompareOnDate());
		Collections.reverse(ansToReturn);
		
		return ansToReturn;
	}

	/**
	 * Returns sorted list of answers.
	 * @return list of answers sorted on date created
	 */
	public List<Answer> getAnswers() {
		ArrayList<Answer> list = new ArrayList<>(answers.keySet());
		Collections.sort(list, new CompareOnDate());
		return list;
	}
	
	
	
	private class CompareOnDate implements Comparator<Post> {
		/**
		 * Compares posts on date created.
		 * @param o1 post object
		 * @param o2 post object
		 * @return returns 1 if o1's date is earlier than o2's date.
		 */
		public int compare(Post o1, Post o2) {
			return o1.getDate().compareTo(o2.getDate());
		}
	}
	
	
	/**
	 * Adds answers to Group's collection of Answers.
	 * @param ans The Answer instance to be added to 
	 * this Group's collection of Answers. 
	 * @param quest The Question instance to be added 
	 * to this Group's collection of Questions.
	 */
	protected void addAnswer(Answer ans, Question quest) {
		if(ans != null && quest != null) {
			answers.put(ans, quest);
		}
	}
	
	/**
	 * Adds questions to Group's collection of Questions.
	 * @param quest The Question instance that will be added to the 
	 * Group collection of Questions.
	 */
	protected void addQuestion(Question quest) {
		if(quest != null) {
			questions.put(quest.getTitle(), quest);
		}
	}
	
	/**
	 * User enters in top n members that they want to see
	 * Based on number of questions and answers posted
	 * @param n Integer
	 * @return list of members in order based on activity. null if can't be found
	 */
	
	public List<Member> getActiveMembers(int n){
		HashMap<Member,Integer> activeMems = new HashMap<>();
		HashMap<Member, Integer> sortedActiveMems = new LinkedHashMap<>();
		List<Member> memsToReturn = new ArrayList<>();
		
			/**
			 * This block of code builds the
			 * member's activity and puts it
			 * in a map
			 * */
			for(Map.Entry<String, Member> entry : members.entrySet()) {
				Member m = entry.getValue();
				int activity = 0;
				for(int i = 0; i < m.getQuestions(m.getGroup(getTitle())).size(); i++) {
					if(m == m.getQuestions(m.getGroup(getTitle())).get(i).getAuthor()) {
						activity++;
					}
				}
				for(int i = 0; i < m.getAnswers(m.getGroup(getTitle())).size(); i++) {
					if(m == m.getAnswers(m.getGroup(getTitle())).get(i).getAuthor()) {
						activity++;
					}
				}
				activeMems.put(m, activity);//need this to match the member to corresponding activity count
			}

		
		/**
		 * This grabs the 
		 * hashmap, sorts it,
		 * then inserts it 
 		 * into the linkedhashmap
 		 * in reverse order
		 **/
		activeMems.entrySet().stream()
        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
        .forEach(x -> sortedActiveMems.put(x.getKey(), x.getValue()));
		
		Member prevMem = new Member(null, null, null, null, null);
		for(Map.Entry<Member, Integer> entry : sortedActiveMems.entrySet()) {
			if(n <= 0) {
				return memsToReturn;//For test purposes
			}
			
			if(memsToReturn.size() >= n) {
				if(entry.getValue() == sortedActiveMems.get(prevMem)){
					/**
					 * If the next member has the same value 
					 * as the current then add that member also
					 **/
					memsToReturn.add(entry.getKey());
				}
				else {
					return memsToReturn;
				}
			}
			if(!memsToReturn.contains(entry.getKey())) { memsToReturn.add(entry.getKey()); }
			prevMem = entry.getKey();
		}
		
		
		return memsToReturn;
	}
	

	

	/**
	 * returns knowledgeable information regarding the group
	 * @return information of type String
	 */
	public String toString() {
		return String.format("\n\nGroup Name: %s\nCreated On: %s\n"
				+ "Description: %s\nNumber of Members: %s\n\n", title, dateCreated, description, getNumMembers());
	}

	
	/**
	 * Protected helper method which adds a Membership
	 * object to this Group's list of Memberships.
	 * @param m 
	 * 		the membership object that you want to add 
	 * 		to the list of memberships
	 */
	protected void addMembership(Membership m) {
		if(m != null) {
			memberships.put(m.getMember(), m);
			members.put(m.getMember().getEmail(), m.getMember());
		}
	}   
	
	/**
	 * Protected helper method that returns the Member's 
	 * Membership object associated with this group.
	 * @param m
	 * 		the Member who's membership you are 
	 * 		trying to retrieve.
	 * @return the member's membership to this group
	 */
	protected Membership getMembership(Member m) {
		if(m != null)
			return memberships.get(m);
		return null;
	}
	
	/**
	 * Overridden equals method which bases equality on String title
	 * @param o    object being compared to this 
	 * @return false if the two objects do not have the same title
	 */
	@Override
	public boolean equals(Object o) {
		if(o instanceof Group) {
			Group g = (Group)o;
			return title.equals(g.getTitle());
		}
		return false;
	}
	
}
