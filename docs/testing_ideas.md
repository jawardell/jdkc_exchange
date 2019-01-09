# Testing Ideas:

* Test all contructors and methods with null

****

* Test all constructors and methods with all data types (?)

****
* Members with 0 Memberships cannot Post.<br>
We need to prevent this from happening:<br>

```java
Member member = new Member("", "", "", "", null);
member.addQuestion(null, new Question("", "", null), null);
```
****
* Every Post must have a membership.<br>
We need to prevent this from happening:<br>

```java
Post post = new Question("", "", null);
// post.setMembership(null)
```

****
* Every Post must be in a group.<br>
We need to handle this `null` argument:<br>

```java
Post post = new Question("", "", null);
```
****
* Every Answer must have a Question. <br>
This shouldn't happen: <br>

```java
Post answer_to_q1 = new Answer("", "", null);
Post q1 = new Question("", "", null);
```
****
