package tests;

import org.junit.*;  

import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
//import com.sun.org.glassfish.gmbal.Description;
// import java.util.ArrayList;
// import java.util.List;

// import com.sun.org.glassfish.gmbal.Description;

import jdkc_exchange.*;

/**
 * This is an integration test which assesses the s2 feature of "Persisting"
 * AKA, saving the state of a SiteManager aggregate for possible later use. The
 * Persistence Manager should write data to a text file that could be used to
 * restore the system at a later time. The PM should write to a txt file which
 * doesn't already exist. It should make a new text file. It should write to the
 * file in a consistent protocol that can be used to regenerate a system from
 * scratch. It shouldn't cause any input/output runtime errors. Finally, the PM
 * methods should be static meaning that the PM doesn't require an instance to
 * be used.
 * 
 * @version s2.0
 */
public final class IntPersisting {
	@Test
	//@Description("tests the behavior of saving")
	
	
	
	public void testSaving() throws Exception {
		/**
		 * Save system correctly all memberships should be there all groups should be
		 * there all posts should be there make system on the text file and observe the
		 * text file
		 */

		
		SiteManager sm = new SiteManager();

		for (int i = 0; i < 10; i++) {
			sm.addGroup(randomString(), randomString(), LocalDateTime.now());
		}

		for (int i = 0; i < 10; i++) {
			sm.addMember(randomString(), randomString(), randomString(), randomString(), LocalDateTime.now());
			for (Group g : sm.getGroups()) {
				for (Member m : sm.getMembers()) {
					m.joinGroup(g, LocalDateTime.now());
				}
			}
		}

		for (Member m : sm.getMembers()) {
			for (Group g : sm.getGroups()) {
				m.addQuestion(g, new Question(randomString(), randomString(), LocalDateTime.now()),
						LocalDateTime.now());
			}
		}

		for (Member m : sm.getMembers()) {
			for (Group g : sm.getGroups()) {
				for (Question q : m.getQuestions(g)) {
					m.addAnswer(g, q, new Answer(q, randomString(), LocalDateTime.now()), LocalDateTime.now());
				}
			}
		}
		
		for(Group g : sm.getGroups()) {
			for(Answer a : g.getAnswers()) {
				g.getMembers().get(rand(0, g.getNumMembers())).like(a);
			}
		}
		
		//getLogs(new File("src/tests/log/like_test1"), sm, "l");
		
		Assert.assertTrue(PersistenceManager.save(new File("src/tests/output/save_file_1"), sm));
		
		
		SiteManager sFromPM = PersistenceManager.read(new File("src/tests/output/save_file_1"));

		boolean groupsMissing = false;
		// are all the groups there?
		for (Group g : sm.getGroups()) {
			if (!sFromPM.getGroups().contains(g))
				groupsMissing = true;
		}

		boolean membersMissing = false;
		// are all the members there?
		for (Member m : sm.getMembers()) {
			if (!sFromPM.getMembers().contains(m))
				membersMissing = true;
		}

		boolean postsMissing = false;
		// are all the members there?
		for (Post p : getPosts(sm)) {
			if (!getPosts(sFromPM).contains(p))
				postsMissing = true;
		}
		
		boolean likesMissing = false;
		int expectedLikes = 0;
		for(Group g : sm.getGroups()) {
			for(Answer a : g.getAnswers()) {
				expectedLikes += a.getNumLikes();
			}
		}
		
		int actualLikes = 0;
		for(Group g : sFromPM.getGroups()) {
			for(Answer a : g.getAnswers()) {
				actualLikes += a.getNumLikes();
			}
		}
		
		
		likesMissing = expectedLikes == actualLikes;
		

		// assert that nothing is missing
		Assert.assertTrue(!(groupsMissing && membersMissing && postsMissing && likesMissing));
	}
	
	
	

	@Test
	//@Description("tests the behavior of reading")
	public void testReading1() throws Exception {
		/**
		 * 
		 * Can I read a file from the user and create a SM object? Is every thing that I
		 * need in the SM? every group every member every membership every question
		 * every answer Are the memberships correct? Are any objects null? Can it handle
		 * improper input/messy data? Are all associations correct? Integrations among
		 * objects in the SM? are things instantiated correctly and passed into the SM
		 * correctly?
		 * 
		 * 
		 * What else can I test here?
		 * 
		 */

		// is the length the same
		// input is perfect file
		// can it read a perfect file?



		File file = new File("src/tests/input/input_read_1");

		SiteManager sm1 = PersistenceManager.read(file);

		int joins = 0;
		
		for(Member m : sm1.getMembers()) {
			joins += m.getGroups().size();
		}
		
		List<Group> groups1 = sm1.getGroups();
		
		List<Member> members1 = sm1.getMembers();
		
		List<Answer> answers1 = getAnswers(sm1);
		
		List<Question> questions1 = getQuestions(sm1);
		
		
		
		int expectedLength = groups1.size() + members1.size() + answers1.size() + questions1.size() + joins;

		// if the file is perfect, it should have the same number of lines
		// as the sm has objects

		Assert.assertEquals(expectedLength, length(file));

		// if the file has one bad line, the file should have
		// one more line than the sm has objects

		file = new File("src/tests/input/input_read_2");
		SiteManager sm2 = PersistenceManager.read(file);

		joins = 0;
		
		for(Member m : sm2.getMembers()) {
			joins += m.getGroups().size();
		}
		
		List<Group> groups2 = sm2.getGroups();
		
		List<Member> members2 = sm2.getMembers();
		
		List<Answer> answers2 = getAnswers(sm2);
		
		List<Question> questions2 = getQuestions(sm2);
		
		expectedLength = groups2.size() + members2.size() + answers2.size() + questions2.size() + joins;

		Assert.assertEquals(expectedLength, length(file) - 1);

		// a different kind of bad line

		file = new File("src/tests/input/input_read_3");
		SiteManager sm3 = PersistenceManager.read(file);
		
		
		joins = 0;
		
		for(Member m : sm3.getMembers()) {
			joins += m.getGroups().size();
		}
		
		List<Group> groups3 = sm3.getGroups();
		
		List<Member> members3 = sm3.getMembers();
		
		List<Answer> answers3 = getAnswers(sm3);
		
		List<Question> questions3 = getQuestions(sm3);
		
		expectedLength = groups3.size() + members3.size() + answers3.size() + questions3.size() + joins;


		Assert.assertEquals(expectedLength, length(file) - 1);

		// a different kind of bad line

		file = new File("src/tests/input/input_read_4");
		SiteManager sm4 = PersistenceManager.read(file);

		joins = 0;
		
		for(Member m : sm4.getMembers()) {
			joins += m.getGroups().size();
		}
		
		List<Group> groups4 = sm4.getGroups();
		
		List<Member> members4 = sm4.getMembers();
		
		List<Answer> answers4 = getAnswers(sm4);
		
		List<Question> questions4 = getQuestions(sm4);
		
		expectedLength = groups4.size() + members4.size() + answers4.size() + questions4.size() + joins;

		Assert.assertEquals(expectedLength, length(file) - 7);

		//is the save file correct?

		SiteManager sm = new SiteManager();

		for (int i = 0; i < 10; i++) {
			sm.addGroup(randomString(), randomString(), LocalDateTime.now());
		}

		for (int i = 0; i < 10; i++) {
			sm.addMember(randomString(), randomString(), randomString(), randomString(), LocalDateTime.now());
			for (Group g : sm.getGroups()) {
				for (Member m : sm.getMembers()) {
					m.joinGroup(g, LocalDateTime.now());
				}
			}
		}

		for (Member m : sm.getMembers()) {
			for (Group g : sm.getGroups()) {
				m.addQuestion(g, new Question(randomString(), randomString(), LocalDateTime.now()),
						LocalDateTime.now());
			}
		}

		for (Member m : sm.getMembers()) {
			for (Group g : sm.getGroups()) {
				for (Question q : m.getQuestions(g)) {
					m.addAnswer(g, q, new Answer(q, randomString(), LocalDateTime.now()), LocalDateTime.now());
				}
			}
		}
		
		
		
		joins = 0;
		
		for(Member m : sm.getMembers()) {
			joins += m.getGroups().size();
		}
		
		List<Group> groups5 = sm.getGroups();
		
		List<Member> members5 = sm.getMembers();
		
		List<Answer> answers5 = getAnswers(sm);
		
		List<Question> questions5 = getQuestions(sm);
		
		expectedLength = groups5.size() + members5.size() + answers5.size() + questions5.size() + joins;
		
		
		
		Assert.assertEquals(expectedLength, length(new File("src/tests/output/save_file_6")));

		PersistenceManager.save(new File("src/tests/output/save_file_6"), sm);
		
		SiteManager sm5 = PersistenceManager.read(new File("src/tests/output/save_file_6"));
		
		PersistenceManager.save(new File("src/tests/output/save_file_7"), sm5);
		
		Assert.assertEquals(expectedLength, length(new File("src/tests/output/save_file_7")));

	}
	
	@Test
	public void testReading2() throws Exception {
		/**
		 * Can it read from a long and messy data file?
		 */
		
		File file = new File("src/tests/input/RANDS");

		SiteManager sm = PersistenceManager.read(file);
		
		Assert.assertTrue(PersistenceManager.save(new File("src/tests/output/save_file_2"), sm));
		
		sm = PersistenceManager.read(new File("src/tests/output/save_file_2"));
		
		Assert.assertTrue(PersistenceManager.save(new File("src/tests/output/save_file_3"), sm));
	}
	

	static String stringRep(File file) throws Exception {
		String res = "";
		Scanner s = new Scanner(file);
		int count = 0;
		while (s.hasNext()) {
			res += s.nextLine();
			count++;
		}
		System.out.println("count " + count);
		s.close();
		return res;
	}

	static void getLogs(File file, SiteManager sm, String s) throws Exception {
		FileOutputStream out = new FileOutputStream(file);
		switch (s) {
		case "m":
			for (Member m : sm.getMembers())
				out.write(m.toString().getBytes());
			break;
		case "g":
			for (Group g : sm.getGroups())
				out.write(g.toString().getBytes());
			break;
		case "q":
			for (Group g : sm.getGroups())
				for (Question q : g.getQuestions())
					out.write(q.toString().getBytes());
			break;
		case "a":
			for (Group g : sm.getGroups())
				for (Answer a : g.getAnswers())
					out.write(a.toString().getBytes());
			break;
		case "l":
			for (Group g : sm.getGroups())
				for (Answer a : g.getAnswers())
					for(String e : a.getWhoLiked())
						out.write(String.format("\n%s liked \"%s\"\n", e, a.getText()).getBytes());
			break;
		}
		out.close();

	}

	static int length(File file) throws Exception {
		Scanner scanner = new Scanner(file);
		int count = 0;
		while (scanner.hasNext()) {
			count++;
			scanner.nextLine();
		}
		scanner.close();
		return count;

	}

	static int getNumPosts(SiteManager sm) {
		int count = 0;
		for (Group g : sm.getGroups())
				count += g.getQuestions().size();

		for (Group g : sm.getGroups())
				count+= g.getAnswers().size();

		return count;
	}

	static List<Post> getPosts(SiteManager sm) {
		List<Post> posts = new ArrayList<>();
		for (Group g : sm.getGroups()) {
			for (Question q : g.getQuestions()) {
				posts.add(q);
			}
		}
		for (Group g : sm.getGroups()) {
			for (Answer a : g.getAnswers()) {
				posts.add(a);
			}
		}
		return posts;
	}

	static String randomString() {
		List<String> list = listOfNums(14234, 234322, 10);
		return list.get(rand(0, list.size()));
	}

	static List<String> listOfNums(int low, int high, int len) {
		Map<Integer, String> map = new HashMap<>();
		for (int i = 0; i < len; i++) {
			Integer rand = (int) (Math.random() * high + low);
			map.put(rand, rand.toString());
		}
		return new ArrayList<>(map.values());
		
	}

	static int rand(int low, int high) {
		return (int) (Math.random() * high + low);
	}
	
	
	static List<Question> getQuestions(SiteManager sm) {
		List<Question> list = new ArrayList<>();
		for(Group g : sm.getGroups()) {
			for(Question q : g.getQuestions()) {
				list.add(q);
			}			
		}
		return list;
	}
	
	static List<Answer> getAnswers(SiteManager sm) {
		List<Answer> list = new ArrayList<>();
		for(Group g : sm.getGroups()) {
			for(Answer a : g.getAnswers()) {
				list.add(a);
			}			
		}
		return list;
	}
	
	static String getParentDir() {
		if(System.getProperty("os.name").toLowerCase().contains("windows")) {
			return "jdkc_exchange\\";
		}
		return "jdkc_exhange/";
	}
}
