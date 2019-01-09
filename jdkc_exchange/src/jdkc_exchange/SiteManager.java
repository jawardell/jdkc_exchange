package jdkc_exchange;

import java.time.LocalDateTime;
import java.util.*;

/**
 * A class which implements an aggregate of classes. 
 * The SiteManager class gathers together and organizes 
 * instances of the system and provides functionality for 
 * using and accessing instances in the aggregate. 
 * @author Christian Gerhardt
 * @see PersistenceManager
 */
public class SiteManager{
	private Map<String, Member> members;
	private Map<String, Group> groups;

	
	public SiteManager(){
		members =  new HashMap<>();
		groups = new HashMap<>();
	}
	
	/**
	 * Adds a member to the members HashMap
	 * @param firstName   the first name of Member
	 * @param lastName   the last name of Member
	 * @param screenName  the screen name of Member
	 * @param emailAddress the email of Member
	 * @param dateCreated date member was created
	 * @return true or false if the member was added.
	 */
	public boolean addMember(String firstName, String lastName, String screenName, String emailAddress, LocalDateTime dateCreated) {
		
		if(screenName == null || screenName == "" || emailAddress == null) {
			return false;
		}
		else if(!members.containsKey(emailAddress)) {
			Member m = new Member(firstName, lastName, screenName, emailAddress, dateCreated);
			members.put(emailAddress, m);
			return true;
		}
		else {
			return false;	
		}
		
		
	}
	/**
	 * Gets the email of the Member if they exist on the site.
	 * @param emailAddress email address of the member
	 * @return the email of the member
	 */
	public Member getMember(String emailAddress) {
        return members.get(emailAddress);
	}
	
	/**
	 * Gets the list of members on the site
	 * @return a list of members
	 */
	public List<Member> getMembers() {
		List<Member> members_list = new ArrayList<Member>(members.values());
		Collections.sort(members_list, new CompareOnMemberName());
		return members_list;
	}
	
	/**
	 * Gets a list of members which have correlation to the text given
	 * @param text a text given by user
	 * @return a list of members
	 */
	public List<Member> getMembers(String text) {
		List<Member> members_list = new ArrayList<Member>(members.values());
		List<Member> text_found_member_list = new ArrayList<Member>();
		text = text.toLowerCase();
		for(Member m:members_list) {
			if((m.getFirstName().toLowerCase()).contains(text)) {
				text_found_member_list.add(m);
			}
			else if((m.getLastName().toLowerCase()).contains(text)) {
				text_found_member_list.add(m);
			}
			else if((m.getScreenName().toLowerCase()).contains(text)) {
				text_found_member_list.add(m);
			}
			else if((m.getEmail().toLowerCase()).contains(text)) {
				text_found_member_list.add(m);
			}
		}
		
		Collections.sort(text_found_member_list, new CompareOnMemberName());
		return text_found_member_list;
	}
	
	private class CompareOnMemberName implements Comparator<Member> {
		/**
		 * Compares member on last name on other members if last names each then first name.
		 * @param o1 member object
		 * @param o2 member object
		 * @return returns 
		 */
		public int compare(Member o1, Member o2) {
			if((o1.getLastName()).compareTo(o2.getLastName()) == 0){
				return (o1.getFirstName()).compareTo(o2.getFirstName());
			}
			else {
				return (o1.getLastName()).compareTo(o2.getLastName());
			}
		}
	}
	
	/**
	 * Adds a group the site provided they do not exist already
	 * @param title title of the group
	 * @param description description of the group
	 * @param dateCreated date the group was created
	 * @return true or false if the group was added
	 */
	public boolean addGroup(String title, String description, LocalDateTime dateCreated) {
		if(title == null) {
			return false;
		}
		if(!groups.containsKey(title)) {
			Group g = new Group(title, description, dateCreated);
			groups.put(title, g);
			return true;
		}
		else {
			return false;
	
		}
	}
	
	/**
	 * Gets a group based on title provided they exist on the site
	 * @param title title of the group
	 * @return the group object
	 */
	public Group getGroup(String title) {
		
			return groups.get(title);

	}
	
	/**
	 * Gets all groups on this site
	 * @return a list of groups
	 */
	public List<Group> getGroups(){
		
		List<Group> groups_list = new ArrayList<Group>(groups.values());
		
		Collections.sort(groups_list, new CompareOnTitle());
		
		return groups_list;
	}
	
	/**
	 * Gets a list of groups which have correlation to the text given
	 * @param text text given by user
	 * @return a list of groups
	 */
	public List<Group> getGroups(String text){
		List<Group> groups_list = new ArrayList<Group>(groups.values());
		List<Group> text_found_group_list = new ArrayList<Group>();
		
		text = text.toLowerCase();
		for(Group g: groups_list) {
			if((g.getTitle().toLowerCase()).contains(text)) {
				text_found_group_list.add(g);
			}
			else if((g.getDescription().toLowerCase()).contains(text)){
				text_found_group_list.add(g);
			}
		}
		
		Collections.sort(text_found_group_list, new CompareOnTitle());

		return text_found_group_list;
	}
	
	private class CompareOnTitle implements Comparator<Group> {
		/**
		 * Compares group on title of group.
		 * @param o1 group object
		 * @param o2 group object
		 * @return returns 
		 */
		public int compare(Group o1, Group o2) {
			return (o1.getTitle()).compareTo(o2.getTitle());
		}
	}
	
	/**
	 * Gets a list of the most popular n groups based on number of members
	 * @param n number of groups request by user
	 * @return list of groups
	 */
	public List<Group> getPopularGroups(int n){
		List<Group> groups_list = new ArrayList<Group>(groups.values());
		
		Collections.sort(groups_list, new CompareOnNumOfMembers());
		
		
		return groups_list;
	}
	
	private class CompareOnNumOfMembers implements Comparator<Group> {
		/**
		 * Compares group on number of members in group.
		 * @param o1 group object
		 * @param o2 group object
		 * @return returns 
		 */
		public int compare(Group o1, Group o2) {
			return (o1.getMembers().size()) - (o2.getMembers().size());
		}
	}
	
	/**
	 * Gets a list of the most popular n groups based on number posts
	 * @param n number of groups request by user
	 * @return a list of groups
	 */
	public List<Group> getActiveGroups(int n) {
		List<Group> groups_list = new ArrayList<Group>(groups.values());
		
		Collections.sort(groups_list, new CompareOnNumOfPost());
		
		return groups_list;	
	}
	
	private class CompareOnNumOfPost implements Comparator<Group> {
		/**
		 * Compares group on number of posts in group.
		 * @param o1 group object
		 * @param o2 group object
		 * @return returns 
		 */
		public int compare(Group o1, Group o2) {
			return (o1.getAnswers().size() + o1.getQuestions().size()) - (o2.getAnswers().size() + o2.getQuestions().size());
		}
	}
	
	/**
	 * Gets a list of the most active members in the site (based on number of total posts)
	 * @param n number of members request by users
	 * @return a list of members
	 */
	public List<Member> getActiveMembers(int n){
		//Get group & list objects and put them in there own respective list
		List<Group> groups_list = new ArrayList<Group>(groups.values());
		List<Member> members_list = new ArrayList<Member>(members.values());
		//Sort the members_list so that when we add them to the HashMap, we are storing them in sorted order.
		Collections.sort(members_list, new CompareOnMemberName());
		//Create a HashMap to link Member objects to their totals (sum of total questions and answers).
		Map<Member, Integer> members_hp_with_totals = new HashMap<Member, Integer>();
		for(Member m: members_list) {
			int total = 0;
			for(Group g: groups_list) {
				Membership mm = m.getMembership(g);
//				if(mm != null) I did this to avoid NullPointerException but another error arises at Line 279
					total += mm.getAnswers().size() + mm.getQuestions().size();
			}
			members_hp_with_totals.put(m, total);
			
		}
		//Make a new list of of just the totals, and sort them in descending order.
		List<Integer> totals_list = new ArrayList<Integer>(members_hp_with_totals.values());
		Collections.sort(totals_list, Collections.reverseOrder());
		
		//Create a new list to hold members
		List<Member> n_members_list = new ArrayList<Member>();
		if(members_list.size() < n || members_list.size() > n) {
			//The outer loops for how many entries are in members not n.
			for(int i = 0; i < members_list.size();i++) {
				//loops through the sorted HashMap (Members are sorted)
				if(members_hp_with_totals.entrySet() == null) {
					System.out.println("yep");
				}
				for (Map.Entry<Member, Integer> entry : members_hp_with_totals.entrySet()) {
					//if the totals_list at i equals HashMap value
					//Then we'll add it to the members_list and remove it from the HashMap
					if(totals_list.get(i) == entry.getValue()) {
						n_members_list.add(entry.getKey());
						members_hp_with_totals.remove(entry.getKey());
					}
				}
			}
			
			
		}
		else {
			//The outer loops for the value you of n.
			for(int i = 0; i < n;i++) {
				//loops through the sorted HashMap (Members are sorted)
				for (Map.Entry<Member, Integer> entry : members_hp_with_totals.entrySet()) {
					//if the totals_list at i equals HashMap value
					//Then we'll add it to the members_list and remove it from the HashMap
					if(totals_list.get(i) == entry.getValue()) {
						n_members_list.add(entry.getKey());
						members_hp_with_totals.remove(entry.getKey());
					}
				}
			}
		}
		return n_members_list;
	}

}