# Data Plan
## Joshua Boss

This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci307/current/assign/01_data/):


### What is the answer to the two questions below for the data file yob1900.txt (pick a letter that makes it easy too answer)? 

1. For the year 1900, the top ranked name for females was "Mary" and for males was "John".
2. For the letter q, and gender female, and the year 1900, there are a total of 3 names 
that start with q and a total of 111 babies with names that start with the lettter q.

### Describe the algorithm you would use to answer each one.
In order to answer the first question, which asks for the top ranked name for each gender given
a specific year, I would implement the following algorithm. I would read the comma-delimited file of the 
SSA's file format. To do that, I would first create a new FileReader object that opens the file. This 
object which will be passed as the paramater for a new BufferedReader object which I will use to analyze 
the data from the file. In terms of the algorithm, because I know that the file follows the SSA format 
which sorts first on sex, then number of occurances, and finally alphabetically, I can simply say that the 
very first name in the list is the top ranked female name. I will store the line as a string array
split by the comma delimeter, and extract the first element for the name. Then I will utilize a while 
loop, storing each subsequent line in a temporary string array and stop the while loop once the sex 
changes from female to male. I'll check this by checking the second element in the temporary string array 
and storing it in char variable called sex. The while loop will continue until sex variable is m for male.
Then once this change happens I will simply say that the first male name is the highest rank male name. 


For the second question, which asks given a year, gender, and letter how many names start with the given 
letter and how many total babies were given those names. In order to implement this algorithm, I would
create a new FileReader object that opens the given file, and a new BufferedReader object to read the text
file. In order to implement the algorithm, I would would create two int variables, one that stores the
total number of names with the given letter, and another the total number of babies that have names 
that start with the given letter. Then, I would use a while loop to loop through every single line in the
file. In the while loop, for each line I would store the line as a string array formed from the line split
by a comma. Then I would check to see if the first letter of the name is the same as the target letter and 
the sex is the target sex. I would check these by using the charAt() method. If not, I continue. If it is the same as the target letter, I would then convert the string to an int variable by using the parseInt() method from the 
Integer class. I would update the two counting variables I created. The total number of unique names by 
simply adding one to the current total, and then update the total babies count by adding the number from 
the last column of the row. (That I just changed from a string to an int). Then by the end of the loop 
I should be able to return both of the requested counts.

### Likely you may not even need a data structure to answer the questions below, but what about some of the questions in Part 2?

The only data structure that I would use to answer the questions below would be a string array to store each line 
temporarily. To answer the questions in part two, I think I would use a Tree Map that stores years as keys. The years would be ordered (for questions that use ranges of years). The keys would map to nested arrays, or in other words, an array of String arrays where each string array is a line from the year's corresponding dataset. This way the order 
from each dataset is preserved (rank). With this data structure you should be able to answer the questions from 
part two. 

### What are some ways you could test your code to make sure it is giving the correct answer (think beyond just choosing "lucky" parameter values)?

Some ways to test my code would be to create data files of the same exact format as the SSA file format, but add
my own tests to make sure that edge cases work. I  could utilize the given dataset files form multiple different
years. What I could then do is analyze them myself to see what the correct answer is, run my code, and then compare
if the answer my code provides is the same as the one I determined by hand. As for writing my own files, I could 
include edge cases, such as lots of files with alphabetical tiebreakers and files with a very small or large number
of names. To help with the second question, I could create files for the sole purpose of tracking one of the 
letters and make it shorter so that I can easily do the math by hand. I could choose   

### What kinds of things make the second question harder to test?

The second question is harder to test with the given files because it takes a lot longer to parse through the data set
by hand and add up all of the names for a given year, gender and letter. The first question you can literally just 
look at the top of the female and male sections to solve the problem by hand. The second problem also has multiple 
things you need to track. For instance the first problem only asks you to return the name of the most popular name 
for a given year. The second problem asks you to return both the number of names and the total number of babies that 
correspond to names with a given letter. There are also changes of type needed for this problem. You have to sum up totals. There are also three inputs in the second problem while the first has two inputs, meaning that in the first problem is easier to solve because you only need to check if one condition is satisfied while the second one you check two conditions. 

### What kind of errors might you expect to encounter based on the questions or in the dataset?

I might expect to encounter errors with reading the dataset. I might also have errors with type conflicts as there 
will be a lot of changing types of data. For instance strings to integers and characters. For the first problem, I 
hopefully won't encounter too many errors, but for the second problem it's possible that I encounter errors with 
summing up the total number of names that start with a given letter or the total number of babies whos name's start 
with that letter. I might also experience some errors with counting data from the incorrect gender. These are all of 
the possibilities that I could think of. 

### How would you detect those errors and what would a reasonable "answer" be in such cases?

Going in order of the errors that I listed, to detect errors with reading the input file, I could print out lines of the data set to make sure that I am reading them properly. I can also print out the type of the lines, and see if I can store them in a String array to make sure I can then work with the data. In terms of type conflicts I could just print the type of all of the pieces of data that I am using to make sure that everything will work properly. For instance before I try to use an Integer method on an integer I should make sure that it is in fact an integer. In order to detect errors in question two, that have to do with the tracking the number of babies who's name's start with the given letter, and the number of names that start with a given letter, I could detect it by tracking what the answer should be for certain inputs by hand and printing that out next to the real value. In terms of how to actually do it by hand, it's likely that I will create my own text files because the ones given are very long and will be much too hard to be able to track them all by hand - especially if the letter we are tracking is very common. Issues with gender can be detected again by tracking what answers SHOULD be and then comparing them to what they are. I could detect if a wrong answer is occuring because of issues with gender if I switch what the gender should be and it's correct. 

### How would your algorithm and testing need to change (if at all) to handle multiple files (i.e., a range of years)?

My algorithm would need to change a bit to account for multiple files. What I would do is pass a list of the text files to my algorithm rather than just a text file. 

For the first problem, I would have to create four more variables to store the max values for males and females respectively and the corresponding names. Then I would loop through the list of files and use my old algorithm on each list, and update the max names and max value for each corresponding name if the current top name is greater than the running max. Then at the end it would return the top male and femlale names from all files instead of only one file. 

For the second problem I would also have to use a list of files and loop through that list of files. This algorithm would actually change less, because it's already summing up all of the names and babies with corresponding to names that start with the target letter. All I would change is make the variables are declared before the for loop, and have the while loop inside the for loop. Return the same values and it should work. 
