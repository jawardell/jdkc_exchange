package jdkc_exchange;

import java.io.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;


/**
 * A class for persisting SiteManager data.
 * The Persistence Manager class persists the 
 * data associated with a Site Manager object. 
 * It has two methods, read and save, which 
 * are the key players in persisting data. 
 * The save method accepts a Site Manager object 
 * and creates a save file. The read method 
 * accepts a save file and instantiates a Site 
 * Manager object from the file. The file formatting which is 
 * used by this class is located at 
 * https://github.com/vsu-se/team2_fall18/blob/master/docs/userrules.md
 * @author Kayla Rivera
 * @author Christian Gerhardt
 * @see SiteManager
 */

public class PersistenceManager {
	/**
	 * Suppress the default constructor 
	 * to prevent instantiablility
	 */
	private PersistenceManager() {}
	
	/**
	 * reads the file that was saved
	 * @param file The save file used to create the Site Manager object.
	 * @return SiteManager object that holds members,groups and posts
	 */
	public static SiteManager read(File file){

			
		//create file for each object to extract from
		File f1 = new File("Member.txt"); 
		File f2 = new File("Group.txt");
		File f3 = new File("Join.txt");
		File f4 = new File("PQ.tx");
		File f5 = new File("PA.txt");

				
		//site manager object
		SiteManager sm = new SiteManager();
				
		try {
			//scanner for system file
			Scanner scan = new Scanner(file);
			
					
			//print writer for mem,group, post file
			PrintWriter p1 = new PrintWriter(f1);
			PrintWriter p2 = new PrintWriter(f2);
			PrintWriter p3 = new PrintWriter(f3);
			PrintWriter p4 = new PrintWriter(f4);
			PrintWriter p5 = new PrintWriter(f5);
					
			//loop through system file and write/extract to each individual file
			while(scan.hasNext()) {
				String in = scan.nextLine();
				if(in.isEmpty()) {
					continue;
				}
				if('M' == in.charAt(0)) {
					p1.println(in);
					continue;
				}
				if('G' == in.charAt(0)) {
					p2.println(in);
					continue;
				}
				if('J' == in.charAt(0)) {
					p3.println(in);
					continue;
				}
				if(('P' == in.charAt(0)) && ('Q' == in.charAt(1))) {
					p4.println(in);
					continue;
				}
				if(('P' == in.charAt(0)) && ('A' == in.charAt(1))) {
					p5.println(in);
					continue;
				}
			}
					
			scan.close();
			p1.close(); 
 			p2 .close();
 			p3.close();
 			p4 .close();
 			p5.close();
			
			//scanner for members, post and group file
			Scanner s1 = new Scanner(f1);
			Scanner s2 = new Scanner(f2);
			Scanner s3 = new Scanner(f3);
			Scanner s4 = new Scanner(f4);
			Scanner s5 = new Scanner(f5);
					
			//loop through Member and add members to site manager
			while(s1.hasNext()) {
				LocalDateTime timeStamp;
				String[] in = s1.nextLine().split(",");
				if(in.length == 6) {
					String[] date = in[5].split("-");
					if(date.length == 5) {
						Month month = Month.of(Integer.parseInt(date[1].trim()));
						timeStamp = LocalDateTime.of(Integer.parseInt(date[0].trim()), month, 
						        					 Integer.parseInt(date[2].trim()), 
						        					 Integer.parseInt(date[3].trim()), 
						        					 Integer.parseInt(date[4].trim()));
					}else{
						timeStamp = LocalDateTime.now();
					}
					sm.addMember(in[1], in[2], in[3], in[4], timeStamp);
				}
			}
					
			//loop through Group and add groups to site manager
			while(s2.hasNext()) {
				LocalDateTime timeStamp;
				String[] in = s2.nextLine().split(",");
				if(in.length == 4) {
					String[] date = in[3].split("-");
					if(date.length == 5) {
						Month month = Month.of(Integer.parseInt(date[1].trim()));
						timeStamp = LocalDateTime.of(Integer.parseInt(date[0].trim()), month, 
						        					 Integer.parseInt(date[2].trim()), 
						        					 Integer.parseInt(date[3].trim()), 
						        					 Integer.parseInt(date[4].trim()));
					}else{
						timeStamp = LocalDateTime.now();
					}
					sm.addGroup(in[1], in[2], timeStamp); 
				}
			}
					
			/*loop through Join. checks if a member has joined a group
			  and objects are added to site manager through members*/	  
			while(s3.hasNext()) {
				String[] in = s3.nextLine().split(",");
				LocalDateTime timeStamp;
				if(in.length == 4) {
					String[] date = in[3].split("-");
					if(date.length == 5) {
						Month month = Month.of(Integer.parseInt(date[1].trim()));
						timeStamp = LocalDateTime.of(Integer.parseInt(date[0].trim()), month, 
						        					 Integer.parseInt(date[2].trim()), 
						        					 Integer.parseInt(date[3].trim()), 
						        					 Integer.parseInt(date[4].trim()));
					} else{
						timeStamp = LocalDateTime.now();
					}
					
					if(sm.getMember(in[1]) == null) {
						continue;
					}

					sm.getMember(in[1]).joinGroup(sm.getGroup(in[2]), timeStamp);
				}
			}
				   
			//loop through PQ and add objects to site manager
			while(s4.hasNext()) {
				String[] in = s4.nextLine().split(",");
					   
				LocalDateTime timeStamp;
				if(in.length == 6) {
					String[] date = in[3].split("-");
					if(date.length == 5) {
						Month month = Month.of(Integer.parseInt(date[1].trim()));
						timeStamp = LocalDateTime.of(Integer.parseInt(date[0].trim()), month,
						        					 Integer.parseInt(date[2].trim()), 
						        					 Integer.parseInt(date[3].trim()), 
						        					 Integer.parseInt(date[4].trim()));
					}else{
						timeStamp = LocalDateTime.now();
					}
						    
					Question q = new Question(in[1],in[2], timeStamp);
					Member member; 
					member = sm.getMember(in[4]);
					if(member == null) {
						continue;
					}
					if(member.getGroup(in[5]) != null) {
						Group g = sm.getGroup(in[5]);
						member.addQuestion(g, q, q.getDate());
					}
				}
			}
			
			//loop through PA and add objects to site manager
			while(s5.hasNext()) {
				String[] in = s5.nextLine().split(",");
					   
				LocalDateTime timeStamp;
				if(in.length >= 5) {
					String[] date = in[3].split("-");
					if(date.length == 5) {
						Month month = Month.of(Integer.parseInt(date[1].trim()));
						timeStamp = LocalDateTime.of(Integer.parseInt(date[0].trim()), month,
						        					 Integer.parseInt(date[2].trim()), 
						        					 Integer.parseInt(date[3].trim()), 
						        					 Integer.parseInt(date[4].trim()));
					} else{
						timeStamp = LocalDateTime.now();
					}
					boolean areNull = (sm.getGroup(in[5]) == null) || (sm.getMember(in[4]) == null);
					if(areNull) {
						continue;
					}
					Question q = sm.getGroup(in[5]).getQuestion(in[1]);
				    Answer a = new Answer(q, in[2], timeStamp);
					Member member = sm.getMember(in[4]);
					if(member.getGroup(in[5]) != null) {
						
						member.addAnswer(sm.getGroup(in[5]), q, a, a.getDate());
						
					}
					if(in.length > 5) {
						for(int i = 6; i < in.length; i++) {
							
							areNull = (sm.getMember(in[i]) == null) || (sm.getGroup(in[5]) == null);
							if(areNull) {
								continue;
							}
							
							Member member1 = sm.getMember(in[i]);
							
							if(member1.getMembership(sm.getGroup(in[5])) != null) {	
								member1.like(a);
							}
							
						}
					}
					

				}
			}
			
 			s1.close();
 			s2.close();
 			s3.close();
 			s4.close();
 			s5.close();
 			f1.delete(); 
 			f2.delete();
 			f3.delete();
 			f4.delete();
 			f5.delete();
			
					
		}catch(IOException e){
			System.out.println(e);
		}
				
		//return site manager
		return sm;
	}
	
	/**
	 * Saves information from a SiteManager object to a save file.
	 * @param file  The file to which the SiteManager information is written.
	 * @param sm    The SiteManager object to be saved.
	 * @return true if saved successfully
	 */
	public static boolean save(File file, SiteManager sm) {
		
		//arrayList to get members and groups
		List <Member> members = new ArrayList<>(sm.getMembers()); 
	    List <Group> groups = new ArrayList<>(sm.getGroups());
	    
		try{
			//print writer takes in the file
			PrintWriter writer = new PrintWriter(file);
			
			//loop members and print
			for(Member m: members) {
				writer.print('M' + "," +  m.getFirstName() + "," + m.getLastName() + "," + 
						m.getScreenName() + "," + m.getEmail() + "," + m.getDateCreated());
				writer.println();
			}
			
			//loop groups and print
			for(Group g: groups) {
				writer.print('G' + "," + g.getTitle() + "," + g.getDescription() + "," + 
							g.getDateCreated());
				writer.println();
			}
		    
			//joins, members and the groups they've joined
			for(Member j: members) {
				for(Group i: groups){ 
					if(j.getGroup(i.getTitle()) != null){
						writer.print("J" + "," + j.getEmail() + "," +
									i.getTitle() + "," + j.getDateCreated());
						writer.println();
					}
				}
			}
			
			//Post's questions
			for(Group g: groups){
				List <Question> question = new ArrayList<>(g.getQuestions());
				for(Question q: question) {
						writer.print("PQ" + "," + q.getTitle() + "," + 
									q.getText() + "," + q.getDate() + "," + 
									((q.getMembership()).getMember()).getEmail()+ "," + 
									g.getTitle());
						writer.println();
				}
			}
		
			//Post's answers
			for(Group g: groups){
				List <Answer> answer = new ArrayList<>(g.getAnswers());
				for(Answer a: answer) {
					writer.print("PA" + "," + a.getQuestion().getTitle() + ","  +
							 a.getText()  + "," + a.getDate() + "," + ((a.getMembership()).getMember()).getEmail()  
								+ "," + g.getTitle());
					if(a.getNumLikes() != 0) {
						Set<String> email = a.getWhoLiked();
						int i = 0;
						for(String s: email) {
							if(i == 0) {
								writer.print("," + s);
								i++;
							}
							else {
								writer.print( "," + s);
							}
						}
					}
					writer.println();
				}		
			}
			writer.close();
			
		}catch(Exception e) {
			return false;
		}
		return true;	
	}
}