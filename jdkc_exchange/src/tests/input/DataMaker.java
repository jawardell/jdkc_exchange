/**
 * to run this program, remove the 
 * package statement, compile, and run in the terminal
 * oct 30th, 2018 writes data to file for input to persistence manager
 * uses the format listed at https://github.com/vsu-se/team2_fall18/blob/master/docs/userrules.md
 */
package tests.input;
import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
public class DataMaker {
	final static boolean NOTIFICATION = false;
	public static void main(String[] args) throws Exception {
		final int MAX_LIKES = 10;
		final int CLEAN_FACTOR = 10;
		final int NUM_LINES = 200;
		String[] names  = new String[140];
		Scanner scanner = new Scanner(new File("NAMES"));
		int i = 0;
		while(scanner.hasNext()) {
			names[i++] = scanner.nextLine();
		}
		
		String[] doms = { "@gmail.com", "@valdosta.edu", 
				"@yahoo.com", "@me.com", "@you.go", "@me.stay"};
		
		String[] emails  = new String[140];
		i = 0;
		Scanner s1 = new Scanner(new File("NAMES"));
		while(i < 140) {
			emails[i++] = s1.nextLine() + rand(0, 900) + doms[rand(0, doms.length)];
		}
		s1.close();
		String[] actions = { "G", "M", "J", "PA", "PQ" };
		
		String[] unames = new String[140];
		
		i = 0;
		while(i < 140) {
			unames[i] = names[i] + rand(0,1000);
			i++;
		}
		
		
		
		
		String[] titles = new String[140];
		String[] descs = new String[140];
		
		i = 0;
		Scanner s2 = new Scanner(new File("TITLES"));
		while(s2.hasNext()) {
			titles[i++] = s2.nextLine();
		}
		s2.close();
		
		i = 0;
		Scanner s3 = new Scanner(new File("DESCRIPTIONS"));
		while(s3.hasNext()) {
			descs[i++] = s3.nextLine();
		}
		s3.close();

		
		File file = new File("RANDS");
		
		PrintWriter pw = new PrintWriter(file);
		
		i = 0;
		System.out.print("\nwriting");
		while(i < NUM_LINES) {
			if(i % 15000 == 0) System.out.printf(".");
			String choice = actions[rand(0, actions.length)];
			LocalDateTime d = LocalDateTime.now();
			String date = d.getYear() + "-" + d.getMonthValue() + "-" + d.getDayOfMonth() + "-" + d.getHour() + "-" + d.getMinute();
			String res = "";
			switch (choice) {
				case "M" : 
				res = String.format("%s,%s,%s,%s,%s,%s\n", choice,  
						names[rand(0, titles.length)], names[rand(0, titles.length)], unames[rand(0, unames.length)], 
						unames[rand(0, unames.length)] + emails[rand(0, emails.length)], date);
				break;
				case "G" : 
				res = String.format("%s,%s,%s,%s\n", choice,  titles[rand(0, titles.length)],
						descs[rand(0, descs.length)], date);
				break;
				case "J" : 
				res = String.format("%s,%s,%s,%s\n", choice, unames[rand(0, unames.length)]+ emails[rand(0, emails.length)], 
						titles[rand(0,titles.length)], date);
				break;
				case "PQ" : 
				res = String.format("%s,%s,?%s?,%s,%s,%s\n", choice,  titles[rand(0, titles.length)],
						descs[rand(0, descs.length)], date, unames[rand(0, unames.length)]+ emails[rand(0, emails.length)], 
						titles[rand(0,titles.length)]);
				break;
				case "PA" : 
				String likeString = "";
				int numLikes = rand(0, MAX_LIKES);
				int j = 0;
				while(j < numLikes) {
					likeString += String.format(",%s", unames[rand(0, unames.length)]+ emails[rand(0, emails.length)]);
					j++;
				}
				res = String.format("%s,%s,%s,%s,%s,%s%s\n", choice, titles[rand(0,titles.length)],
						descs[rand(0, descs.length)], date, 
						unames[rand(0, unames.length)]+ emails[rand(0, emails.length)], 
						titles[rand(0,titles.length)], likeString);
				break;
			}
			if(i % CLEAN_FACTOR == 0) res = shuffle(res);
			pw.write(res);
			i++;
		}
		
		System.out.println("done\n\n");
		scanner.close();
		pw.close();
		
		
	}
	
	static String shuffle(String s) {
		char[] str = new char[s.length()];
		int i = 0;
		while(i < str.length) {
			char c = s.charAt(i);
			str[i] = (char)(c^3); 
			i++;
		}
		i = 0;
		s = "";
		while(i < str.length) {
			s += str[i++];
		}
		s+="\n";
		if(NOTIFICATION)
		return "\n\n\tscrambled line: " + s;
		return s;
	}

	static int rand(int low, int high) {
		return (int)(Math.random()*high + low);
	}
}
