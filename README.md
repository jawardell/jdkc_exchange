<p align="center">
<a href="https://mypages.valdosta.edu/jawardell/javadoc/index.html"><img src="https://gist.githubusercontent.com/jawardell/b4f19ba96149af2324ad4516b0ddd6af/raw/39c8ef474c1e4eeb90c4b2c9a34078055aeac9d6/logo.png"/></a>
</p>

# jdkc_exchange API
jdkc_exchange is an API for managing networks of object oriented Q&A communities. This is a brief overview of how it can be used. <br>

## Usage
You'll need to have the Java Runtime Environment installed and configured. If you plan to contribute to the API, you will also need to install the Java Development Kit. 

To use this API, download the jar file `jdkc_exchange.jar` and add it to your project's build path.


## Documentation

View the documentation for this project by clicking [here](http://mypages.valdosta.edu/jawardell/javadoc/index.html).

## Authors 
@kdtrey @clgerhardt @kayrivera @jawardell

## Class Methods
Methods have public package access and all attributes are encapsulated privately within their class.

#### Member
method        |  description
------------- | -------------
Member        | instantiates the class
joinGroup     | joins member to a group
getNumGroups  | returns the number of groups the member has
getGroup      | returns the group corresponding to the group ID
getGroups     | returns a list of groups the member has
addQuestion   | adds a question to the group
getDateJoined | returns the date the member joined this group
addAnswer     | adds a member's answer to the group
getQuestions  | returns a list of Questions the user has 
getAnswers    | returns a list of Answers the user has 
toString      | returns a string representation of the member 
getQuestions [overloaded]| Returns the n most recent questions asked by this member in this group sorted on the order they were asked
getAnswers [overloaded]| Returns the n most recent answers asked by this member in this group sorted on the order they were provided
getGroups [overloaded]| Returns a list of the n Groups that the member is most active in, sorted on title





#### Group
method        |  description
------------- | -------------
Group         | Constructor to instantiate Group object
getDateCreated| returns the date the group was created
getTitle      | returns title of the group
getDescription| returns descriptive info regarding group
getNumMembers | returns number of member in the group
getMember     | returns a member. Searches using email
getMembers    | returns a list of members in the group
getQuestions  | returns a list of questions sorted by date created
getAnswers    | returns a list of answers sorted by date created
getActiveMembers | Returns a list of the n most active Members of this group, sorted by acivity
getQuestions [overloaded] | Returns the n most recent questions asked in this group sorted on the order they were asked
getAnswers [overloaded] |  Returns the n most recent answers in this group sorted on the order they were provided





#### Post
method        |  description
------------- | -------------
Post          | Constructor to instantiate Post object
getText       | returns the text of the post
getDate       | returns the date the post was created
setText       | sets the text of the post
getAuthor     | returns the member/author the post
getGroup      | returns to which group the post was posted under


#### Question
method        |  description
------------- | -------------
Question      | Question constructor which takes in three params.
getTitle      | Gets the title of the question instance.
setTitle      | Set the title of the question post.
addAnswer     | Add an answer to this question instance.
getAnswer     | Returns an list of answers from the question instance.
toString      | Return a string with information about the question instance. 
	 


#### Answer
method        |  description
------------- | -------------
Answer        | Constructor which takes in three params.
getQuestion   | Returns the question instance connected to this answer instance.
toString      | Simple String that gives information about this answer instance.


#### PersistenceManager
method        |  description
------------- | -------------
PersistenceManager        | Constructor that takes in no parameters
read   | Static method to read the system from file and return a SiteManager object
save      | Static method to save the entire system


#### SiteManager
method        |  description
------------- | -------------
SiteManager        | Constructor with no responsibilities
addMember   | Adds a new Member provided they don’t already exist returning whether successful or not
getMember      | Returns the Member corresponding to this emailAddress if they exist
getMembers| Returns a list of all Members, sorted by last name, then first name.
getMembers [overloaded]| Returns a list of all Members, sorted by last name, then first name
addGroup | Adds a new Group provided it doesn’t already exist returning whether successful or not
getGroup | Returns the Group corresponding to this title if it exists.
getGroups | Returns a list of all Groups, sorted by title.
getGroups [overloaded]| Returns a list of all Groups, sorted by title.
getPopularGroups | Returns a list of the n Groups that have the most members, sorted descending
getActiveGroups | Returns a list of the n Groups that have the most questions and answers combined, sorted descending
getActiveMembers | Returns a list of the n Members that have the most questions and answers across all the groups they are a member of

