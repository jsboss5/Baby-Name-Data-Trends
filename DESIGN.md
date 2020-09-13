# Design File
### Joshua Boss

### People
The only person involved in the design and implementation of this project was me, Joshua Boss. I thought through the 
design that made the most sense, made every single design decision, and programmed it all from scratch. I also was respsonsible for 
making the important decisions of figuring out how to allocate my time when the deadline was approaching and I couldn't get
all the features I wanted in at the same time.

### Design Goals
In general, the goal of this project was to be able to take a set of data about baby names in the form of text files, and 
figure out a way to organize, and implement a program that can use that information to answer a number of interesting 
questions that parents naming their baby might want to know. In terms of specific design goals, I set up the design of my project
with the input, process, and output concept in mind, and created my class structure accordingly. This way, new features
would be easily addable. For instance, I designed my program with the specific intent of being able to add types of questions
that the user might want to answer. For instance, if the next programmer wanted to offer a new question that can be answered,
they would write the algorithm that answers the question in the processing phase, edit the input class to ask for the 
necessary parameters, call the correct algorithm, and then call the output method (which they would be able to write) that simply
prints out an appropriate message. I also created a specific class designed for reading in files and storing in a database,
which is separate from the algorithms (process) class, in order to be able to add in different data sources without changing 
the effect on the database. For instance, I added code that reads in files from both a directory path and a URL. But if 
the programmer wanted to add say from a zip file or some other source, they could change that one class without affecting the 
rest of the code and still have  a working program . 

 
### High Level Design 
As above mentioned, I first wanted to clearly separate the  input, processing, and output stages of the data, as per 
suggested by the design suggestions. I clearly separated input and output into two different classes. The input class receives 
all the requested parameters by the user for the possible questions they can ask. For instance if they want to see the most 
popular male names this is where they specify it. The input class then calls methods from the process stage and calls
methods from the output class to print the results. So the input class really drives the program. The processing phase
of the program I split into two classes. The first class, Database is responsible for reading in the file 
and then storing it into a database that is used by the other classes. The second class, Process.java uses a database object created in 
the database class to answer the questions. The input class then calls methods from the output class which is responsible 
for printing everything out to the console. 

### Assumptions / Decisions
There were a number of different assumptions I made that simplified my projects design that are relevant when it comes
to adding new features. The first assumption I made was about the file names. I assumed that all of them would be of the
form "yobXXXX.txt" where XXXX is the correct year for the file. I utilized substring methods and extracted the values 
of those exact years in order to determine the year of a file as it was the easiest way I could.  Another decision I
made was to read in an entire directory of files and store it in a large database, that way I could create one "Process"
object that could call all the question methods at once. This made it easy for me to add questions but also decreased
the efficiency of my program. One last decision I made was to split up the database and process classes in order to be able to 
input new data sources without affecting how my question methods worked, and vise versa. Keeping these separate was important
for being able to affectively add new features. 

### How to add new Features

There are a couple of types of new features that I could imagine one would want to add to my program. I will go through what those 
are and how to implement them in detail. The first is to add a type data source from which the file can be read. The first two 
types that I have implemented are from a file path and from a URL. If one wanted to add it from a ZIP file, which I was not 
able to complete by the deadline, they would edit only the Database class. They would add another method entitled 
readDataFromZip, which opens the zip file and accesses the data. Then they would call the exisitng methods in place, 
(addLinesToList) to be able to write those files to the treeMap Database. Another new feature that could be added, which I didn't 
get to, is question ten from the complete version of the program, which asked for a names meaning to be returned to with 
the answer of any question. The way this feature could be implemented is by adding a method to the Process class, which 
every single method would call before returning, to check the definition of the name that is about to be returned. In this method
I would probably add some sort of file rading code, or utilize a method from the Database class that already does that. 
I would probably go through each question method though first and call this definition method on either the name that is going to be returned, or the name 
of the paramter given if no name will be returned. Then I would add it to the data structure being returned in each method.  

