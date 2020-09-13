package names;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Input {
    private String IOexceptionString = "The file given is not found!\n";

    private Database chooseBetweenFileAndUrl(String fileOrURL) throws IOException {
        Database dataBase2;
        if(fileOrURL.startsWith("http")){
            URL url = new URL(fileOrURL);
            dataBase2 = new Database(url);
        }
        else{
            dataBase2 = new Database(fileOrURL);
        }
        return dataBase2;
    }

    private void runOnlySingleYear(String fileNameP1, char targetLetter, char sex) throws IOException {
       try{
        Output results = new Output();
        Database database1 = chooseBetweenFileAndUrl(fileNameP1);
        Process part1 = new Process(database1);

        //Q1
        System.out.println("Q1 Test");
        results.TopMaleAndFemalePrint(part1.topMaleAndFemale());
        //Q2
        System.out.println("Q2 Test");
        results.numNameAndBabiesPrint(part1.numNameAndBabies(sex, targetLetter), sex, targetLetter);
    }
       catch(IOException e){
            System.out.println(IOexceptionString);
       }
    }


    private void runMultipleYearsOrSingleYear(String fileNameP2, String name, char sex, int year, int lowYear, int highYear, int numYears, int rank) throws IOException {
       try {
           Output results = new Output();

           Database dataBase2 = chooseBetweenFileAndUrl(fileNameP2);

           Process part2 = new Process(dataBase2);


           //Q3
           System.out.println("Q1 Basic");
           results.rankingsOverTimePrint(part2.rankingsOverTime(name, sex), name, sex);
           //Q4

           System.out.println("Q2 Basic");
           results.sameRankRecentYearPrint(part2.sameRankRecentYear(name, sex, year), name, sex, year);

           //Q5
           System.out.println("Q3 Basic");
           results.topRankedNameOfRange(part2.topRankedNameOfRange(lowYear, highYear, sex), lowYear, highYear, sex);

           // Q6
           System.out.println("Q4 Basic");
           results.mostPopularSexFirstLetterPrint(part2.mostPopularSexFirstLetter(lowYear, highYear, sex), lowYear, highYear, sex);

           //Q1
           System.out.println("Q1 Complete");
           results.rankingsOverTimePrint(part2.rankingsOverTimeSpecificInterval(name, sex, lowYear, highYear), name, sex);


           //Q2 Complete
           System.out.println("Q2 Complete");
           results.differenceInRankGivenYears(part2.differenceInRankGivenYears(name, sex, lowYear, highYear), lowYear, highYear, name, sex);

           //Q3
           System.out.println("Q3 Complete");
           results.nameOfGreatestRankChangePrint(part2.nameOfGreatestRankChange(sex, lowYear, highYear));

           //Q4
           System.out.println("Q4 Complete");
           results.averageRankPrint(part2.averageRank(name, sex, lowYear, highYear), name, sex);

           //Q5
           System.out.println("Q5 Complete");
           results.highestAverageRankPrint(part2.highestAverageRank(sex, lowYear, highYear), sex);

           //Q6
           System.out.println("Q6 Complete");
           results.averageRankPrint(part2.averageRankOverRecentYears(name, sex, numYears), name, sex);

           //Q7

           System.out.println("Q7 Complete");
           results.namesAtGivenRankOverRange(part2.namesAtGivenRankOverRange(sex, rank, lowYear, highYear), sex, rank);

           //Q8
           System.out.println("Q8 Complete");
           results.heldRankMostOftenPrint(part2.heldRankMostOften(sex, rank, lowYear, highYear), sex, rank);

           //Q9
           System.out.println("Bonus Question 9 - Complete");
           results.mostCommonPrefix(part2.mostCommonPrefix(sex, lowYear, highYear));
       }
       catch(IOException e){
           System.out.println(IOexceptionString);
       }
    }



    public static void main(String[] args) throws IOException {
    /**
       *** IMPORTANT ****
       To run the program, choose whichever inputs you want below, and change the fileName if
       you wish as well. I have not yet set up getting files from internet.

     NOTE - running on complete data set takes a long time to finish running
    */

   //==============================================================//
        //inputs needed for part 1
        String fileNameP1 = "data/custom_tests/yob3000.txt";    //must be single file
        char targetLetter = 'A';   //choose the letter (A-Z)
        char sexP1 = 'F';            //choose the sex (F,M)


        //TODO - Switch fileNameP2 to see it on whole dataset
        //inputs needed for part 2 (next four questions)
        //String fileNameP2 = "data/ssa_complete/";
        String fileNameP2 = "data/custom_tests/custom2010s/";   //can be directory or file
        String urlNameP2 = "https://www2.cs.duke.edu/courses/fall20/compsci307d/assign/01_data/data/ssa_2000s/yob2000.txt";

        String name = "jAmEs";
        int year = 2011;
        int lowYear = 2012;
        int highYear = 2014;
        char sexP2 = 'M';
        int numYears = 2;
        int rank = 5;

//==============================================================//
        //Todo - NOTE -  change fileNameP2 in last line of class to urlNameP2 if you wish to see it read from online
        //Runs output class methods   -
        Input inputObject = new Input();
        inputObject.runOnlySingleYear(fileNameP1, targetLetter, sexP1);
        inputObject.runMultipleYearsOrSingleYear(fileNameP2, name, sexP2, year,lowYear, highYear, numYears, rank);
    }


}
