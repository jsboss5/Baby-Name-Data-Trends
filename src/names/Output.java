package names;

import java.util.*;

public class Output {
    /**
     * Ran out of time to comment all - basically they just print each question from each section of the project
     * Not too difficult
     */

    private static final String rangeTag = "range";
    private static final String nameTag = "name";
    private static final String rangeNotInDataSetTag = "Range not in Dataset";
    private void notInSetPrint(String type){
        System.out.println("it doesn't look the " + type + " is in the dataSet\n");
    }


    public void TopMaleAndFemalePrint(String[] result){
        /**
        Prints results for top Male and female for given year
         */
        System.out.println("Top Ranked Female: " + result[0]);  //print top female
        System.out.println("Top Ranked Male: " + result[1]+ " \n") ;  //print top male
    }

    public void numNameAndBabiesPrint(int[] result, char sex, char targetLetter){
        /**
        Prints results for total number of names and babies with given first letter
         */
        System.out.println("Total number of " + sex + " names that start with the letter " + targetLetter +
                ": " + result[0]);
        System.out.println("Total number of " + sex + " babies that have names that start with the letter "
                + targetLetter + ": " + result[1] + "\n");
    }

    public void rankingsOverTimePrint(HashMap<Integer, Integer> results, String name, char sex){


        if(!results.equals(new HashMap())) {
            System.out.println("The rank over time for the name " + name + " of sex " + sex +
                    " is given by the following map: ");
            System.out.println(results + "\n");
        }
        else{
            notInSetPrint(nameTag);
        }
    }

    public void sameRankRecentYearPrint(String[] results, String name, char sex, int year){
        System.out.println("Given the name, gender and year, " + name + ", "
                + sex + ", " + year + " respectively, in most recent year, name/sex combo that " +
                "had the same rank was..."
        );
        System.out.println(results[0]+ " " +
                results[1] + "\n"
        );
    }
    public void topRankedNameOfRange(String[] results, int lowYear, int highYear, char sex){
        if(!Arrays.equals(results, new String[2])){
        System.out.println("The " + sex + " name that occured most popular most often over the range " + lowYear + " - " + highYear +
                " was " + results[0] + " appearing atop the list a total " +
                "of " + results[1] + " times. \n"
        );}
        else{
            notInSetPrint(rangeTag);
        }
    }
    public void mostPopularSexFirstLetterPrint(TreeSet<String> results, int lowYear, int highYear, char sex){

        if (!results.equals(new TreeSet<>())){
            System.out.println("The most popular letter over the interval of " + lowYear + " - " +
                    highYear + " was the letter " + results.first().charAt(0) + " and the list " +
                    "of names in the period with that first letter is: " + results + "\n");
        }
        else{
        notInSetPrint(rangeTag);
    }
    }

    public void differenceInRankGivenYears(int differenceInRank, int lowYear, int highYear, String name, char sex){
        if(differenceInRank == Integer.MIN_VALUE){
            notInSetPrint(rangeTag);
        }
        else if(differenceInRank == Integer.MAX_VALUE){
            notInSetPrint(nameTag);
        }
        else if(differenceInRank<0){
         System.out.println("The rank decreased " + Integer.toString(Math.abs(differenceInRank)) + " ranks from " + lowYear + " to " +
                 highYear + " for the " + sex + " name " + name + "\n");
        }
        else if(differenceInRank > 0){
            System.out.println("The rank increased " + Integer.toString(Math.abs(differenceInRank)) + " ranks from " + lowYear + " to " +
                    highYear + " for the " + sex + " name " + name + "\n");
        }
        else{
            System.out.println("The rank didn't change from " + lowYear + " to " + highYear + " for the " + sex + " name " + name + "\n");
        }
        }
    public void nameOfGreatestRankChangePrint(String topName){
        if(topName.equals(rangeNotInDataSetTag)){
            notInSetPrint(rangeTag);
        }
        else{
        System.out.println("the name with the greatest rank change was "  + topName + "\n");
    }
    }
    public void averageRankPrint(int average, String name, char sex){
        if(average == Integer.MIN_VALUE){
            notInSetPrint(rangeTag);
        }
        else if(average == Integer.MAX_VALUE){
            notInSetPrint(nameTag);
        }
        else{
        System.out.println("the average rank for " + sex + " " + name + " was " + average + ".\n");
    }
    }
    public void highestAverageRankPrint(String bestName,  char sex){
        if(bestName.equals(rangeNotInDataSetTag)){
            notInSetPrint(rangeTag);
        }
        else{
        System.out.println("the name with the highest average rank for " + sex + "'s was " + bestName + ".\n");
    }
    }

    public void namesAtGivenRankOverRange(List<String> namesAtRank, char sex, int rank){
        if(namesAtRank.equals(new ArrayList<>())){
            notInSetPrint(rangeTag);
        }
        else{
        System.out.print("All the " + sex +"  names occurring in the given range at rank " + rank + " " +
                "can be found here: " + namesAtRank + ".\n\n");
    }
    }

    public void heldRankMostOftenPrint(String[] personAndFreq, char sex, int rank){
        if(Arrays.asList(personAndFreq).equals(new ArrayList<>())){
            notInSetPrint(rangeTag);
        }
        System.out.println("The person who held rank " + rank + " longest was " + personAndFreq[0] + " having that rank" +
                "a total of " + personAndFreq[1] + " times!\n" );
    }
    public void mostCommonPrefix(String prefix){
        System.out.println("the most common prefix was " + prefix);
    }
}
