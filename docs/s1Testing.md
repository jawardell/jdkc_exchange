# Integration Test Plan <br><br>

## Features to Test
#### joining groups
- lists are populated properly
	* `Group` collection of memberships
	* `Member` collection of memberships
- multiplicities calculated correctly
	* `Member`: number of memberships
	* `Group`: number of memberships
- associations are valid
	* `Membership`'s instance of `Group`
	* `Membership`'s instance of `Member`

#### making posts
- lists are populated properly
	* `Group`'s collection of questions
	* `Group`'s collection of answers

- associations are valid
	* `Post`'s instance of `Membership`
	* `Question`'s instance of `Answer`
	* `Answer`'s collection of questions
	* question or answer added to the correct `Group` collection
	* question or answer added to the correct `Member` collection


## Features NOT to Test
#### date validity
- valid date ranges
- chronology of events
- dates matching up
- sorting by date

#### data types
- precision
- efficient arithmetic

#### formatting
- toString formatting
- date/time formatting

#### time and space efficiency
- data structures
- nested loops
- condtional branching
- algorithmic efficiency

