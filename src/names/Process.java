package names;
import com.sun.source.tree.Tree;

import javax.swing.text.Highlighter;
import java.io.*;
import java.nio.file.Files;
import java.security.KeyStore;
import java.util.*;

//TODO - Javadoc comments
public class Process {
    private static final String badNameMessage = "Name not present";
    private static final String badRangeMessage = "Range not in Dataset";
    private static final String emptyString = "";
    private String inputPath;
    private HashMap<Integer, List<List<String>>> dataSet; //I want to create a

    public Process(Database filesMap) throws IOException {
        dataSet = filesMap.getDataBase();
        inputPath = filesMap.getFilePath();
    }

    private boolean namesInSetCheck(String name, char sex) {
        /**
        This method checks if the name is in any of the files of the directory - if not it returns false
        otherwise it returns true
         */
        int rankSum = 0;     //crate the sume that will be checked later if it's 0
        for (Map.Entry<Integer, List<List<String>>> entry : dataSet.entrySet()){
            Integer year = entry.getKey();

            rankSum += (getRank(name, sex, year));
        }

        return rankSum!= 0;      //if there is at least one, then the name is there
    }

    private boolean rangeYearsInSetCheck(int min, int max) {
        /**
        Checks to see if given range is in set
         */

        return (min >= Collections.min(dataSet.keySet()) && max<= Collections.max(dataSet.keySet()) && min<=max);
    }

    private int getRank(String name, char sex, int year){
        /**
        Returns an int that represents the rank of a name/sex pair given the year
         Returns 0 if it is not in the list
         */

          List<List<String>> listOfFileLines = dataSet.get(year);
          listOfFileLines = makeSexList(listOfFileLines, sex);

          for(int dex = 0; dex<listOfFileLines.size(); dex++){
              if(listOfFileLines.get(dex).get(0).toUpperCase().equals(name.toUpperCase())) {
                  return dex + 1;
              }
          }
          return 0;
    }

    private String findMaxKey(HashMap<String,HashSet<String>> mapOfDesiredMax){
        int maxSize = 0;
        String maxKey = emptyString;
        for(String prefix: mapOfDesiredMax.keySet()){
            if(mapOfDesiredMax.get(prefix).size() > maxSize){
                maxKey = prefix;
                maxSize =  mapOfDesiredMax.get(prefix).size();
            }
        }
        return maxKey;

    }
    private List<List<String>> makeSexList(List<List<String>> yearList, char sex){
        /**
        Returns a sublist of the year's data either as a male list or female list
         */
        int femaleIndex = 0;
        while(yearList.get(femaleIndex).get(1).toUpperCase().charAt(0) == 'F'){
            femaleIndex++;
        }
        int maleIndex = femaleIndex;
        while(maleIndex < yearList.size() && yearList.get(maleIndex).get(1).toUpperCase().charAt(0) == 'M'){
            maleIndex++;
        }
        if(Character.toUpperCase(sex) == 'F') return  yearList.subList(0, femaleIndex);
        else if(Character.toUpperCase(sex) == 'M') return yearList.subList(femaleIndex, maleIndex);
        return yearList.subList(maleIndex, yearList.size());
    }


    public String[] topMaleAndFemale(){

        /**
        This method answers question 1 from (https://www2.cs.duke.edu/courses/fall20/compsci307d/assign/01_data/part1.php)
        Given a year (via the file stored in the constructor), it returns the top male and female names
        It returns the names in a string[] of the form {topFemale,topMale}
         */
        String topFemale = dataSet.get(Collections.min(dataSet.keySet())).get(0).get(0);
        String topMale = makeSexList(dataSet.get(Collections.min(dataSet.keySet())), 'M').get(0).get(0);
        return new String[]{topFemale, topMale};

    }

    public int[] numNameAndBabies(char sex, char fLetter){
        /**
        This method answers question 2 from (https://www2.cs.duke.edu/courses/fall20/compsci307d/assign/01_data/part1.php)
        Given a year, a gender, and a letter, it returns many names start with that letter and how many total babies were given those names
        Its input parameters are chars sex and fLetter which represent the target sex and desired first letter respectively
        It returns the total number of names and total number of babies in an int[] like {nameTotals, babyTotals}
         */

        int nameTotal = 0;   //total number of names for a given sex that start with a given letter
        int babyTotal = 0;  //total number of babies named with a name that starts with a given letter of a given sex

        for (List<String> line : makeSexList(dataSet.get(Collections.min(dataSet.keySet())), sex)) {
            if (line.get(0).toUpperCase().charAt(0) == Character.toUpperCase(fLetter)) {
                nameTotal++;
                babyTotal += Integer.parseInt(line.get(2));
            }
        }

        return new int[]{nameTotal, babyTotal};
    }


    public HashMap<Integer, Integer> rankingsOverTime(String name, char sex){
        /**
         * returns a hashmap that maps year to ranking of a given name
         * @param = name, sex
         */
        return rankingsOverTimeSpecificInterval(name, sex, Collections.min(dataSet.keySet()), Collections.max(dataSet.keySet()));
    }


    public String[] sameRankRecentYear(String name, char sex, int year){
        /**
        takes in a fileName, a name, a sex and a year and returns a string array such that the
        first value is a name and the second is the sex of the name/sex pair which has the same rank
        in the most recent year of the dataset as that of the given name/sex pair in given year.
         */

        if(!rangeYearsInSetCheck(year, year)){       //check to make sure requested year is valid
            return new String[2];
        }

        int rank = getRank(name, sex, year);   //get the rank of the name/sex in given year

        List<List<String>> listOfLines = dataSet.get(Collections.max(dataSet.keySet()));       //stores last years lines in lines
        listOfLines = makeSexList(listOfLines, sex);

        if(rank-1 < listOfLines.size() && rank-1>=0){
        String newName = listOfLines.get(rank-1).get(0);
        String newSex = listOfLines.get(rank-1).get(1);

        return new String[]{newName, newSex};

        }
        return new String[]{badNameMessage, emptyString};  //This is just in case the rank of a name is not most recent year

    }

    public String[] topRankedNameOfRange(int lowYear, int highYear, char sex){
        /**
        this method is given an interval of time and a sex and it returns the name that appeared at the top of the list
        most often over the course of the interval, and how many times it appeared there. Tie goes to name first in
        alphabetical order
         */

        HashMap<String,Integer> nameCount = new HashMap<>();

        if(!rangeYearsInSetCheck(lowYear, highYear)) {
            return new String[2];
        }

        for (lowYear = lowYear; lowYear <= highYear; lowYear++) {
            if(!dataSet.containsKey(lowYear)) continue;    //this accounts for gaps in the rang

            List<List<String>> lines = dataSet.get(lowYear);
            lines = makeSexList(lines, sex);


            if(nameCount.containsKey(lines.get(0).get(0))){    //if its already there increment, if not put it there
            nameCount.put(lines.get(0).get(0), nameCount.get(lines.get(0).get(0)) + 1 );
            }
            else{
                nameCount.put(lines.get(0).get(0), 1);
            }
        }

        String maxKey = Collections.max(nameCount.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey(); //got this code from stackOverFlow

        return new String[]{maxKey, nameCount.get(maxKey).toString()};

    }

    public TreeSet<String> mostPopularSexFirstLetter(int lowYear, int highYear, char sex){
        /**
        given an interval of years, the method returns an ordered treeset of a list of names that all occur
        in the time period and have the most common first name. Tie goes to alphabetical.
         @params = low and high years are the ranges, sex is the sex
         returns a treeset
         */
        TreeMap<Character, TreeSet<String>> letterToNameList = new TreeMap<>();
        TreeMap<Character, Integer> letterToCount = new TreeMap<>();

        if(!rangeYearsInSetCheck(lowYear, highYear)) {
            return new TreeSet();
        }

        for (lowYear = lowYear; lowYear <= highYear; lowYear++) {
            if(!dataSet.containsKey(lowYear)) continue;    //this accounts for gaps in the range
            List<List<String>> listOfFileLines = dataSet.get(lowYear);
            List<List<String>> genderSpecificList = makeSexList(listOfFileLines,sex);

            for(int lineOfFile = 0; lineOfFile < genderSpecificList.size(); lineOfFile++){
                Character firstLetter = listOfFileLines.get(lineOfFile).get(0).toUpperCase().charAt(0);
                //create the entries in map if they don't exist
                if(!letterToNameList.containsKey(firstLetter)){
                    letterToCount.put(firstLetter, 1);
                    TreeSet<String> add = new TreeSet<>();
                    add.add(listOfFileLines.get(lineOfFile).get(0));
                    letterToNameList.put(firstLetter, add);
                }

                else{    //update the maps
                    letterToCount.put(firstLetter, letterToCount.get(firstLetter) + 1);
                    letterToNameList.get(firstLetter).add(listOfFileLines.get(lineOfFile).get(0));
                }
            }
        }
        Character maxLetter = Collections.max(letterToCount.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey(); //got this code from stackOverFlow

        return letterToNameList.get(maxLetter);

    }


    public HashMap<Integer, Integer> rankingsOverTimeSpecificInterval(String name, char sex, int lowYear, int highYear){
        /**
        Given a name and gender, and a range, what are all the rankings of that name gender pair in the range
        Inputs are fileName, name, and sex. It returns a treemap where keys are the years and values
        are the rankings for the given name
         */

        HashMap<Integer, Integer> yearToRank = new HashMap<>();

        if(!namesInSetCheck(name, sex)) return new HashMap();
        if(!rangeYearsInSetCheck(lowYear,highYear)) return new HashMap();

        for (Map.Entry<Integer, List<List<String>>> entry : dataSet.entrySet()) {
            Integer year = entry.getKey();
            if(year>= lowYear && year<= highYear){
                Integer rank = getRank(name, sex, year);
                yearToRank.put(year, rank);
            }
        }
        return yearToRank;
    }

    public int differenceInRankGivenYears(String name, char sex, int lowYear, int highYear){
        /**
         * Returns the differnece of rank between lowYear and highYear - postiive return means
         * the name got more popular, negative means it got less popular
         * the negativeValueToAccountForNameotInSet - is to compensate for fact rank returns 0 if
         * it is not in the year - and therefore maintains the relationship that positive means rank got better
         * and negative means rank got worse
         * returns MIN_VALUE if range years not in the set, and MAX val if name isn't in set
         */
        if(!rangeYearsInSetCheck(lowYear, highYear)){       //check to make sure requested year is valid
            return Integer.MIN_VALUE;
        }
        if(!namesInSetCheck(name, sex)){
            return Integer.MAX_VALUE;
        }
        int negValToAccountForNameNotInSet = 1;
        if(getRank(name,sex,lowYear)==0 ^ getRank(name, sex, highYear)==0){
            negValToAccountForNameNotInSet *= -1;
        }
        return (getRank(name,sex,lowYear) - getRank(name, sex, highYear)) * negValToAccountForNameNotInSet;
    }

    public String nameOfGreatestRankChange(char sex, int lowYear, int highYear){
        /**
         * Tie goes to name that comes first in the first list
         * @params = sex, which is the sex, and then lower and upper bounds of the range
         * returns the name of the person who's rank changd most
         */

        if(!rangeYearsInSetCheck(lowYear,highYear)){
            return badRangeMessage;
        }
        String nameWithMaxRankDifference = emptyString;
        int maxRankDifference = 0;
        for(List<String> lineOfFile : makeSexList(dataSet.get(lowYear), sex)){
            int currentDifference = differenceInRankGivenYears(lineOfFile.get(0),sex, lowYear,highYear);
            //if(currentDifference == Integer.MIN_VALUE) return badRangeMessage;
            if(Math.abs(currentDifference) > maxRankDifference){
                maxRankDifference = Math.abs(currentDifference);
                nameWithMaxRankDifference = lineOfFile.get(0);
            }
        }
        return nameWithMaxRankDifference;
    }

    public int averageRank(String name, char sex, int lowYear, int highYear){
        /**
         * Finds average rank of name given sex and time period
         * @param = name, sex, lowYear, highYear
         *  returns int
         */
        int totalYearsOfRange = 0;
        double totalRank = 0;

        if(!rangeYearsInSetCheck(lowYear,highYear)){
            return Integer.MIN_VALUE;
        }
        if(!namesInSetCheck(name, sex)) return Integer.MAX_VALUE;

        HashMap<Integer, Integer> rankingsOverTime = rankingsOverTimeSpecificInterval(name, sex, lowYear,highYear);

        for(int currYear = lowYear; currYear<=highYear; currYear++){
            if(!rankingsOverTime.keySet().contains(currYear)){
                continue;
            }
            totalRank += rankingsOverTime.get(currYear);
            totalYearsOfRange ++;
        }

        if (totalRank!= 0){
        return (int)(Math.round(totalRank/totalYearsOfRange));
    }
        return 0;
    }

    public String highestAverageRank(char sex, int lowYear, int highYear){
        /**
         * returns the name with the highest (lowest number) average rank over time span
         * @param - sex, and interval of time
         */
        if(!rangeYearsInSetCheck(lowYear,highYear)){
            return badRangeMessage;
        }
        int highestAverageRank = Integer.MAX_VALUE;
        String highestAverageRankName = emptyString;
        for(List<String> lineOfFile : makeSexList(dataSet.get(lowYear), sex)){
            if(averageRank(lineOfFile.get(0),sex,lowYear,highYear) < highestAverageRank){
                highestAverageRank = averageRank(lineOfFile.get(0),sex,lowYear,highYear);
                highestAverageRankName = lineOfFile.get(0);
            }
        }
    return highestAverageRankName;
        }

    public int averageRankOverRecentYears(String name, char sex, int numYears){
        /**
         * returns average rank of a name over the most recent numYears years
         * @param - name, sex, numYears
         * returns int - that represents the averageRank
         */
        return averageRank(name,sex,Collections.max(dataSet.keySet())-numYears +1, Collections.max(dataSet.keySet()));
    }

    public List<String> namesAtGivenRankOverRange(char sex, int rank, int lowYear, int highYear){
        /**
         * returns all the names in that were at a given rank over the course of a time period
         * the names returned are in a list
         * @param = sex, rank, lowYear, UpperYear (bounds)
         */
        List<String> namesAtRankList = new ArrayList<>();
        if(!rangeYearsInSetCheck(lowYear,highYear))return new ArrayList<>();
        for(int curYear = lowYear; curYear<=highYear;curYear++) {
            if (!dataSet.containsKey(curYear)) continue;   //accounts for gaps
            List<List<String>> sexList = makeSexList(dataSet.get(curYear), sex);
            if (rank < 1 || rank > sexList.size()) {
                continue;
            }
            namesAtRankList.add(sexList.get(rank - 1).get(0));
        }
        return namesAtRankList;
    }
    public String[] heldRankMostOften(char sex, int rank, int lowYear, int highYear){
        /**
         * returns string array of name of person who held sepecified rank most often in time period
         * and that corresponding frequency.    returns somethign like {name, frequency}
         * @params = sex, rank, bounds for years
         */
        if(!rangeYearsInSetCheck(lowYear,highYear)) return new String[2];

        TreeMap<String, Integer> nameToFrequencyAtRank = new TreeMap<>();
        List<String> namesAtGivenRank = namesAtGivenRankOverRange(sex, rank, lowYear, highYear);

        for(int dex = 0; dex<namesAtGivenRank.size(); dex++){
            if(!nameToFrequencyAtRank.containsKey(namesAtGivenRank.get(dex))){
                nameToFrequencyAtRank.put(namesAtGivenRank.get(dex), 1);
            }
            else{
                nameToFrequencyAtRank.put(namesAtGivenRank.get(dex), nameToFrequencyAtRank.get(namesAtGivenRank.get(dex))+1);
            }
        }
        String maxName = Collections.max(nameToFrequencyAtRank.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey(); //got this code from stackOverFlow
        return new String[]{maxName, nameToFrequencyAtRank.get(maxName).toString()};
    }

    private HashSet<String> getAllNames(char sex, int lowYear, int highYear){
        HashSet<String> totalNameSet = new HashSet<>();

        for(int curYear = lowYear; curYear <= highYear; curYear ++){
            List<List<String>> sexList = makeSexList(dataSet.get(curYear),sex);
            for(List<String> lineOfFile:sexList){
                totalNameSet.add(lineOfFile.get(0).toUpperCase());
            }
        }
        return totalNameSet;
    }


    public String mostCommonPrefix(char sex, int lowYear, int highYear){
        /**
         * returns the most common prefix word among all the names in the specified range
         * @params: sex, lowYar, highYear,
         */
        if(!rangeYearsInSetCheck(lowYear,highYear)){
            return badRangeMessage;
        }
        HashMap<String, HashSet<String>> prefixToDerivedNamesList = new HashMap<>();
        for(String name: getAllNames(sex,lowYear,highYear)){
            for(String curName : getAllNames(sex,lowYear,highYear)){
                if(curName.startsWith(name) && !curName.equals(name)){
                    if(!prefixToDerivedNamesList.containsKey(name)){
                            prefixToDerivedNamesList.put(name,new HashSet<>());
                        }
                        prefixToDerivedNamesList.get(name).add(curName);
                }
            }
        }
        return findMaxKey(prefixToDerivedNamesList);
    }
}