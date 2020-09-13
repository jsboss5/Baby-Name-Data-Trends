Baby Name Data Trends
====
This project uses data about [baby names from the US Social Security Administration](https://www.ssa.gov/oact/babynames/limits.html) to answer specific questions. 

### Timeline

Start Date:  Sunday August 23rd, 2020

Finish Date: Tuesday September 8th, 2020

Hours Spent: 40+

### Resources Used
StackOverflow

### Running the Program

Main class: 
Input class is the main class - change the inputs in the main method of the input class
to specify paramaters to answer questions. 

Data files needed: 

* You need a path or a valid URL to get the program to work. The input class figures
out whether a path is a filepath or a url so all you have to do is choose which
to pass to the methods that run the program found at the bottom of the main method. 

* NOTE - I have two testing classes, one that uses the URL to complete dataSet (URLs_Process_Test.java), and another that 
       has most of my tests on a few differnet custom file sets (Files_Process_test.java) 


### Notes/Assumptions
1. Only single files/urls to files will be used for questions 1 and 2. Files or directoreis can be used for the rest.   

2. All file names are of the format "yob(digit1)(digit2)(digit3)(digit4).txt"

3. Ties for names are always sorted in alphabetical order. 

4. If a gender is not M or F, it is considered other, and for all gender specific methods will be compared against all people whose genders do 
 not fall under male or female

5. All URLs must start with "http"


6. INTEGER.MIN_VALUE is returned for differenceRangeGivenYears method if the range is not in the set. Integer.MAX_VALUE()
 is returned if the name is not in the set.
 
 7. checking for errors
 - I converted everything to Uppercase to account for case differences
 - I checked in every method to make sure the given years and names are in the dataset

### Impressions
This was a fun, yet challanging project for me. When I looked at tasks indivdually they didn't seem hard...
but making everything work together, keeping clean code, and accomplishing so many tasks in a short amount of time
was  difficult. This project was a great practice using good design principles, keeping clean code, and utilizing effective J-Unit testing. 
