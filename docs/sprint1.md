<!-- $theme: default -->

Sprint 1 Demo
=
Joanne, KD, Christian, Kayla

----

System Requirements and Functions
=
1. Creating Members and Groups
2. Members Joining Groups
3. Creating Posts

Testing
=
1. Unit Tests
2. Integration Tests
----

## Create Members
* As a **Member** I want to participate in organizing and contributing to Q&A communities.

* Create an instance of `Member`
	* **Requirements:**
		1. **first/last name**
		3. **email address**
		4. **user name** 
			* unique from email address 
		6. **time stamp**
			 

----

## Create Groups
* **Groups** organize posts and allow members to interact with them.

* Create an instance of `Group`
	* **Requirements:**
		1. title
		2. description
		3. time stamp 

----

## Join Members to Groups
* Members acquire a membership to a group.

1. Create an instance of `Membership` 
	* **Dependencies:**
		1. instance of `Member`
		2. instance of `Group`


----
## Join Members to Groups Cont.
2. Use the `Member.joinGroup` method
	* **Dependencies:**
		1. instance of `Member`, `Group`, and `Membership`
		2. member has valid group-membership
	* **Requirements:**
		1. group
		2. time stamp

----
## Create Posts
* Members create posts in their groups.

* Create an instance of `Answer` or `Question`.

	* **Dependencies:**
		1. instances of `Group`, `Member`, and `Membership`
		4. member has valid group-membership
	* **Requirements:**
		1. content
		2. timestamp
----
## Create Questions
* Members create posts in the form of a question.

* Create an instance of `Question`.
	* **Dependencies:**
		1. instances of `Group`, `Member`, and `Membership`
		2. member has valid group-membership
	* **Requirements:**
		1. title
        2. content
		3. timestamp
----
## Create Answers
* Members create posts in the form of an answer.

* Create an instance of `Answer`.
	* **Dependencies:**
		1. instances of `Group`, `Member`, `Membership`, and `Question`
		2. member has valid group-membership
		3. question has valid group-membership
	* **Requirements:**
		1. content
		2. timestamp
----
## Unit Tests
* Unit Tests assed the functionality of individual class behaviors.
----
## Integration Tests
* Integration Tests assed the functionality of integrated class behaviors.
