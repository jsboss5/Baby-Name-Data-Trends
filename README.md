data
====

This project uses data about [baby names from the US Social Security Administration](https://www.ssa.gov/oact/babynames/limits.html) to answer specific questions. 


Name: Joshua Boss

### Timeline

Start Date:  Sunday August 23rd

Finish Date: Tuesday September 8th

Hours Spent: 40

### Resources Used
StackOverflow
Lectures

### Running the Program

Main class: 
Input class is the main class - change the inputs in the main method of the input class
to specify paramaters to answer questions. 

Data files needed: 

* You need a path or a valid URL to get the program to work. The input class figures
out whether a path is a filepath or a url so all you have to do is choose which
to pass to the methods that run the program found at the bottom of the main method. 

* NOTE - when running on the complete dataset, it works as expected, but runtime is very slow.

* NOTE - I have two testing classes, one that uses the URL to complete dataSet (URLs_Process_Test.java), and another that 
       has most of my tests on a few differnet custom file sets (Files_Process_test.java)
Key/Mouse inputs: N/A

Cheat keys: N/A

Known Bugs: 


Extra credit:
I did number 9 (the optional problem)

### Notes/Assumptions
1. Only single files/urls to files will be used for questions 1 and 2. Files or directoreis can be used for the rest.   

2. All file names are of the format "yob(digit1)(digit2)(digit3)(digit4).txt"

3. For question 3 on part 2, if there is a tie, the top name is then sorted by alphabetical order. 

4. If a gender is not M or F, it is considered other and for all gender specific methods will be compared against all people whose genders do 
 not fall under male or female

5. All URLs must start with "http"

6. if a name is not in a single year file of a set but is in other names in the set, the rank returned from getRank of that 
    name for that specific year will be 0. 

8. INTEGER.MIN_VALUE is returned for differenceRangeGivenYears method if the range is not in the set. Integer.MAX_VALUE()
 is returned if the name is not in the set.
 
 9. chekcing for errors
 - cases i converted everything to upper
 - i checked everymethod that has years and names with method that checks if they are valid in set**

### Impressions
This was really hard I'm not going to lie. When I looked at tasks indivdually they didn't seem hard...
but making eerything work together, keeping clean code, and accomplishing so many tasks in a short amount of time
is new for me and was very difficult. I look forward to getting better as the class moves along. I want to make sure I really think
hard about how I plan to implement something in the future instead of going with my first idea. 
