/**
 * 
 * This is an integration test which assess 
 * the behavior of joining groups and its effects 
 * on the system. A log file which contains a detailed 
 * error report is generated. 
 * 
 * 
 * Feature: joining groups
 * 
 * Pass/Fail Criteria: assert statement fails 
 * 
 * written for JUnit
 * 
 */

package tests;

import java.time.*;

import org.junit.Assert;
import org.junit.Test;

import jdkc_exchange.Group;
import jdkc_exchange.Member;


public class IntJoiningGroups {


	@Test
	public void groupMembershipCollectionTest() {
		Member m = new Member("tom", "ford", "tford", "tford@hmail.com", LocalDateTime.now());
		Group g = new Group("Group 1", "this is a group", LocalDateTime.now());
		m.joinGroup(g, LocalDateTime.now());
		Assert.assertTrue(g.getMembers().contains(m));
	}
	
	@Test
	public void memberCollectionOfMemberships() {
		Member m = new Member("tom", "ford", "tford", "tford@hmail.com", LocalDateTime.now());
		Group g = new Group("Group 1", "this is a group", LocalDateTime.now());
		m.joinGroup(g, LocalDateTime.now());
		Assert.assertTrue(m.getGroups().contains(g));
	}
	
	
	@Test
	public void memberNumberofMemberships() {
		Member m = new Member("tom", "ford", "tford", "tford@hmail.com", LocalDateTime.now());
		Group g1 = new Group("Group 1", "this is a group", LocalDateTime.now());
		Group g2 = new Group("Group 2", "this is a group", LocalDateTime.now());
		m.joinGroup(g1, LocalDateTime.now());
		m.joinGroup(g2, LocalDateTime.now());
		Assert.assertTrue(m.getNumGroups() == 2);
	}
	
	@Test
	public void groupNumberofMembers() {
		Member m1 = new Member("tom", "ford", "tford", "tford@hmail.com", LocalDateTime.now());
		Member m2 = new Member("tom", "ford", "tford", "tford@hmail.com", LocalDateTime.now());
		Group g = new Group("Group 1", "this is a group", LocalDateTime.now());
		m1.joinGroup(g, LocalDateTime.now());
		m2.joinGroup(g, LocalDateTime.now());
		Assert.assertTrue(g.getNumMembers() == 2);
		
	}
	
	@Test
	public void memberHasGroup() {
		Member m = new Member("tom", "ford", "tford", "tford@hmail.com", LocalDateTime.now());
		Group g = new Group("Group 1", "this is a group", LocalDateTime.now());
		m.joinGroup(g, LocalDateTime.now());
		Assert.assertTrue(m.getGroup("Group 1").equals(g));
	}
	
	@Test
	public void groupHasMember() {
		Member m = new Member("tom", "ford", "tford", "tford@hmail.com", LocalDateTime.now());
		Group g = new Group("Group 1", "this is a group", LocalDateTime.now());
		m.joinGroup(g, LocalDateTime.now());
		Assert.assertTrue(m.getGroup("Group 1").equals(g));
	}
	
}

 
