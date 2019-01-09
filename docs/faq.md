### Client FAQs
**Q: Why should I become a member of jdkc_exchange?**<br>
A: To view the contents of jdkc_exchange. Content is locked to non-members. 

**Q: Why should I join a group?**<br>
A: So that you can make posts. Those without membership to a group cannot post. 

**Q: Are there posts that do not belong to a group?**<br>
A: No. All posts are contained within a group. 

**Q: Can I post without membership?**<br>
A: No. Since all posts are in groups, you must have memebership to a group in order to post.

**Q: Can a post belong to more than one person?**<br>
A: No. Every post is unique, even if they say the same thing. Each post originates from only one person. 

**Q: Can a post not have an owner?**<br>
A: No. Every post was created by a group-member.

**Q: How many groups can I join?**<br>
A: As many as you want.

**Q: Can my username be my email address?**<br>
A: No, it has to be different.
 ****
 ### Developer FAQs
 **Q: Can there be Posts outside of Groups?**<br>
 No. Each instance of `Post` must be contained within an instance of `Group`. <br>
 `Member.addQuestion` or `Member.addAnswer` does not proceed if the `Group` parameter is `null`. There are not any posts floating in *limbo* (outside of a group).
 
 **Q: Does a Member have to have Membership to a Group to ask a Question there?**<br>
A: Yes. Each post is contained within a group that is only unlocked to group members.
<br>Thus, the `Member.addQuestion` and `Member.addAnswer` methods do not proceed unless membership with the `Group` parameter has been located.<br><br>
For example, let *kayaking* and *banking* be instances of `Group` and I am an instance of  `Member`.  If I only have membership with the *kayaking* group, I cannot ask a question in the *banking* group.

**Q: Can a Member have a Membership with 0 Groups and still ask a Question?**<br>
A: No. All instances of `Post` are organized into instances of `Group`. Thus, if someone is not a group-member, it is impossible for them to make a post. Recall that  posting is locked for non-group-members. There are not any posts in *limbo* (outside of a group). 

 **Q: Can a Post's Membership be different than its owner's Membership**<br>
 A: No. Each instance of `Question` or `Answer` has an instance of `Membership`. The membership  instance is the one of the person who added the Post.<br><br>
For example, let *kayaking* be an instance of `Group` and I am an instance of `Member`. I have an instance, *mem*, of `Membership` to *kayaking*. I create a new instance, *p1*,  of `Question` in the kayaking group. Then, *p1* is a derivative of my membership to *kayaking*.  Thus, *p1*'s membership is set to *mem*. This allows us to know which `Member` made what post.
 
**Q: Follow Up - Do Posts have to have Membership?**<br>
A: Yes, posts must have an owner, by definition. There cannot be nameless posting. 
If I see a question on the exchange, I can always see the `Member` who posted it. 


**Q: Can group-memberships be shared?**<br>
A: No. At runtime, when someone wants to participate in a group, they obtain an empty membership. Then, they configure the membership for the group they want by adding an instance of `Group`. So, multiple instances of `Membership` are not the same, although they might be configured for the same group.<br><br>

For example, if I am a member of the kayaking group and Kayla is also a member of the kayaking group, we both have *similar* memberships, but the memberships are not the *same*. Our distinct memberships are listed in the `Group` "roster" as well as in our `Member` "profile".  

Similarly, it should go without saying that instances of the classes may have the exact same members, but they aren't the same object. 

**Do Questions have to have Answers?**<br>
No. Group-members might have asked a question that no one has answered yet. <br>

**Do Answers have to have Questions?**<br>
Yes. Every instance of `Answer` must correspond to one instance of `Question`. It is impossible for an Answer to not have a Question. The instantiation of an `Answer` depends on the instance of a `Question`.