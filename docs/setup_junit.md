# How to Use JUnit

Using JUnit is not hard. Just follow these steps. <br><br>
1. [Download JUnit](#download-the-jar)<br>
2. [Put JUnit in Eclipse](#put-the-jar-in-eclipse)<br>
3. [Import JUnit](#import-junit)<br>
4. [Use JUnit](#assert-statements)

## Download The Jar
This is the JUnit jar: `junit-4.10.jar`<br><br>
Download:<br>
<a href="http://www.java2s.com/Code/Jar/j/Downloadjunit410jar.htm">
<img src="https://gist.githubusercontent.com/jawardell/58b723cdc0451ac4e9eb2ba577643a8a/raw/8def7408b06cdd6b8b8758f943eb50dbd38bd1c2/junit_link.png">
</a> <br><br>
Unzip the `junit-4.10.jar.zip` to get the **jar**.<br><br>
Put this Jar somwhere useful and high in the System Directory.<br>
### Mac
I made a folder in `/Library ` called `Eclipse_Jars`.<br>

Terminal Commands:

```
cd /Library
sudo mkdir Eclipse_Jars
sudo mv ~/Downloads/ /Library/Eclipse_Jars
cd
```

### Windows

A good choice is to put the jar in the folder where you have Eclipse installed. <br>
Just drag and drop the jar there.<br>
```
C:\\Users\YOURWINDOWSUSERNAME\Eclipse
```

****
## Put the Jar In Eclipse

In Eclipse, right click on the `src` folder.<br>
<img src="https://gist.githubusercontent.com/jawardell/58b723cdc0451ac4e9eb2ba577643a8a/raw/9913b2fa47bbe9af1bad1ca28ec05c0b632dae68/pic1.png"><br><br>

Click  ***Build Path > Configure Build Path...*** <br>

<img src="https://gist.githubusercontent.com/jawardell/58b723cdc0451ac4e9eb2ba577643a8a/raw/9913b2fa47bbe9af1bad1ca28ec05c0b632dae68/pic2.png"><br><br><br>
Select the ***Libraries*** tab.<br>

<img src="https://gist.githubusercontent.com/jawardell/58b723cdc0451ac4e9eb2ba577643a8a/raw/9913b2fa47bbe9af1bad1ca28ec05c0b632dae68/pic3.png"><br><br><br>

Click ***Module Path***, then click ***Add External JARs...*** <br>
<img src="https://gist.githubusercontent.com/jawardell/58b723cdc0451ac4e9eb2ba577643a8a/raw/9913b2fa47bbe9af1bad1ca28ec05c0b632dae68/pic4.png"><br><br><br>

Select the `junit-4.10.jar` file that you downloaded and saved earlier. Click ***open***.<br>
<img src="https://gist.githubusercontent.com/jawardell/58b723cdc0451ac4e9eb2ba577643a8a/raw/9913b2fa47bbe9af1bad1ca28ec05c0b632dae68/pic5.png"><br><br><br>
 
Click ***Apply and Close***.<br>
<img src="https://gist.githubusercontent.com/jawardell/58b723cdc0451ac4e9eb2ba577643a8a/raw/9913b2fa47bbe9af1bad1ca28ec05c0b632dae68/pic6.png"><br><br><br>
You'll see the jar in the project explorer.<br>
<img src="https://gist.githubusercontent.com/jawardell/58b723cdc0451ac4e9eb2ba577643a8a/raw/9913b2fa47bbe9af1bad1ca28ec05c0b632dae68/pic7.png"><br>
****
## Import JUnit
Include this import at the top of your Java source code for unit tests:<br>

```java
import org.junit.*;
```
****
## Assert Statements

Unit Testing in Java uses the `assert` keyword. Asserting is kinda' like declaring that a certain boolean statement is true. If the statement is false, an 'exception' is thrown. Runtime is interrupted.<br><br>
For example, I can set up an assertion statement like this: 

```java
public class Main {
	public static void main(String[] args) {
		assert add(2, 3) == 5 : "2 + 3 must equal 5";
	}
	
	public static int add(int x, int y) {
		return x + y;
	}
}
```

If `add(2, 3) == 5` resolves to `false`, then a `java.lang.AssertionError` is thrown and execution stops.<br><br>

In JUnit, we use the `Assert` class to make assertions for us. We use assertions to ***assert*** what the return value of a method is for the classes we are testing. <br><br>

Ex.) Testing the `division` behavior of the `Divide.java` class using `junit.Assert`:

```java
import org.junit.*;
public class DivideTester() {
	@Test
	public void divisionByOneIsAnIdentity() {
		Assert.assertEquals("Anything divided by 1 is itself.", 5, divide(5,1));
	}
}
```

<br><br>
The format for `junit.Assert.assertEquals` is 

```java
junit.Assert.assertEquals(String message, double expected, double actual)
```
`message` : a message that goes with the Exception thrown when the assert fails.<br>
`expected` : what you are asserting that the value ***is*** (needs to be)<br>
`actual` : what the method actually returns


<br><br>
### Run the Test
Make sure all of your test-methods have the `@Test` annotation before them. They won't compile if you forget to do this.

In Eclipse, right click on the code editor. Click ***Run As > JUnit Test***.<br><br>
<img src="https://gist.githubusercontent.com/jawardell/58b723cdc0451ac4e9eb2ba577643a8a/raw/0fa005cf25703757726b9b9b493ed863e367cf07/run_as_jut.PNG"><br><br>

The test results are displayed along the side.<br><br>
<img src="https://gist.githubusercontent.com/jawardell/58b723cdc0451ac4e9eb2ba577643a8a/raw/0fa005cf25703757726b9b9b493ed863e367cf07/fails.PNG"><br><br>

This icon means the test failed: <img src="https://gist.githubusercontent.com/jawardell/58b723cdc0451ac4e9eb2ba577643a8a/raw/2362ecf1a849e8cdb99899dca6e2f66597be1964/fail.png"><br><br>
This icon means the test passed: <img src="https://gist.githubusercontent.com/jawardell/58b723cdc0451ac4e9eb2ba577643a8a/raw/2362ecf1a849e8cdb99899dca6e2f66597be1964/pass.png"><br><br>
