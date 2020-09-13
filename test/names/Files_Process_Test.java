package names;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.rmi.UnexpectedException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class Files_Process_Test {
    private Process testP1;    //used for t
    private Process testP2;
    private Process testP3;
    private Database dataBaseP1;
    private Database dataBaseP2;
    private Database  dataBaseP3;
    private Input testInput;
    private static final String rangeNotThereString = "Range not in Dataset";
    private static final String nameNotThereString = "Name not present";

    @BeforeEach
    void setup() throws IOException {
        String fileNameP1 = "data/custom_tests/yob3000.txt";
        String fileNameP2 = "data/custom_tests/custom2010s/";
        String fileNameP3 = "data/custom_tests/customOptional";

        Input testInput = new Input();

        dataBaseP1 = new Database(fileNameP1);
        dataBaseP2 = new Database(fileNameP2);
        dataBaseP3 = new Database(fileNameP3);
        testP1 = new Process(dataBaseP1);      //for part one questions require a single file
        testP2 = new Process(dataBaseP2);      //for part two questions - can be a directory or single file
        testP3 = new Process(dataBaseP3);
    }

    //Tests for FileInputs///==============================


    //Q1 P1 - Tests question one from part one of project - Tests topMaleAndFemale method
    // Top male name and top female name from given year
    @Test
    void topMaleAndFemale3000() throws IOException {
        assertTrue(testP1.topMaleAndFemale()[0].equals("Emily") && testP1.topMaleAndFemale()[1].equals("Jacob"));
    }
    @Test
    void topMaleAndFemale1970() throws IOException {
        String fileName1970 = "data/ssa_complete/yob1970.txt";
        Database dataBase1970 = new Database(fileName1970);
        Process t1970 = new Process(dataBase1970);
        assertTrue(t1970.topMaleAndFemale()[0].equals("Jennifer") && t1970.topMaleAndFemale()[1].equals("Michael"));
    }

    @Test
    //tests to make sure incorrect name isn't counted as correct
    void topMaleAndFemale_True_Positive() throws IOException {
        assertTrue(!testP1.topMaleAndFemale()[0].equals("Olivia") && !testP1.topMaleAndFemale()[1].equals("Noah"));
    }

    //==================================================================//

    //Q2 P1 - Tests question 2 from part one of project - finds the
    @Test
    void numNameAndBabiesWithGivenLetter_FEM_A() throws IOException {
        char sex = 'F';
        char fLetter = 'A';
        assertTrue(testP1.numNameAndBabies(sex, fLetter)[0] == 3 && testP1.numNameAndBabies(sex, fLetter)[1] == 16);
    }
    @Test
    void numNameAndBabiesWithGivenLetter_MAL_M() throws IOException {
        char sex = 'M';
        char fLetter = 'M';
        assertTrue(testP1.numNameAndBabies(sex, fLetter)[0] == 2 && testP1.numNameAndBabies(sex, fLetter)[1] == 17);
    }
    @Test
    //tests that name not there returns 0 for both counts
    void numNameAndBabiesWithGivenLetter_True_Positive() throws IOException {
        char sex = 'M';
        char fLetter = 'B';
        assertTrue(testP1.numNameAndBabies(sex, fLetter)[0] == 0 && testP1.numNameAndBabies(sex, fLetter)[1] == 0);
    }
    //==================================================================//

    //Q1 P2 - Tests question 1 from part 2 of project - tests the rank over time method given sex and name -
    @Test
    void rankingsOverTime_Emily_F() throws IOException {
        String name = "Emily";
        char sex = 'F';
        assertTrue(testP2.rankingsOverTime(name, sex).get(2010) == 6 && testP2.rankingsOverTime(name, sex).get(2011) == 4
                    && testP2.rankingsOverTime(name,sex).get(2012) == 2 && testP2.rankingsOverTime(name,sex).get(2013) == 2
                    && testP2.rankingsOverTime(name,sex).get(2014) == 2);
    }

    @Test
    void rankingsOverTime_Jacob_M() throws IOException {
        String name = "Jacob";
        char sex = 'M';
        assertTrue(testP2.rankingsOverTime(name, sex).get(2010) == 1 && testP2.rankingsOverTime(name, sex).get(2011) == 4
                && testP2.rankingsOverTime(name,sex).get(2012) == 9 && testP2.rankingsOverTime(name,sex).get(2013) == 12
                && testP2.rankingsOverTime(name,sex).get(2014) == 7 );
    }
    @Test
   // tests name that isn't in any of the files in set
    void rankingsOverTime_True_Negative() throws IOException {
        String name = "asdfasdf";
        char sex = 'M';
        assertTrue(testP2.rankingsOverTime(name, sex).equals(new HashMap<>()));
    }
    @Test
        // tests name that isn't in any of the files in set
    void rankingsOverTime_Some_missing() throws IOException {
        String name = "Scott";
        char sex = 'M';
        assertTrue(testP2.rankingsOverTime(name, sex).get(2014) == 3);
    }



    //==================================================================//

    //Q2 P2 - Tests question 2 from part 2 of project - given year, name, sex finds name,sex of same rank in
    //most recent year
    @Test
    void sameRankRecentYear_Emily_F() throws IOException {
        String name = "Alexandra";
        char sex = 'F';
        int year = 2012;
        assertTrue(Arrays.equals(testP2.sameRankRecentYear(name,sex,year), new String[]{"Dawn", "F"}));
    }

    @Test
    void sameRankRecentYear_Matthew_M() throws IOException {
        String name = "Jacob";
        char sex = 'M';
        int year = 2010;
        assertTrue(Arrays.equals(testP2.sameRankRecentYear(name,sex,year), new String[]{"James", "M"}));
    }
    //tests name that is not present
    @Test
    void sameRankRecentYear_True_Negative() throws IOException {
        String name = "MMMMMMMM";
        char sex = 'M';
        int year = 2012;
        assertTrue(Arrays.equals(testP2.sameRankRecentYear(name,sex,year), new String[]{nameNotThereString, ""}));
    }

    //tests rank that is in the requested year but not most recent year for Male
    @Test
    void sameRankRecentYear_No_Same_Rank_Male() throws IOException {
        String name = "Jacob";
        char sex = 'M';
        int year = 2013;
        assertTrue(Arrays.equals(testP2.sameRankRecentYear(name,sex,year), new String[]{nameNotThereString, ""}));
    }
    //tests rank that is in the requested year but not most recent year Female
    @Test
    void sameRankRecentYear_No_Same_Rank_Fem() throws IOException {
        String name = "Karen";
        char sex = 'F';
        int year = 2013;
        assertTrue(Arrays.equals(testP2.sameRankRecentYear(name,sex,year), new String[]{nameNotThereString, ""}));
    }
    // ======================================================================//

    //Q3 P2 - Finds name that  was most popular how often it was most popular given range of years and sex -
    @Test
    void topRankedNameGivenRange_Male_04_08() throws IOException {
        int lowYear = 2011;
        int highYear = 2014;
        char sex = 'M';
        assertTrue(Arrays.equals(testP2.topRankedNameOfRange(lowYear, highYear,sex), new String[]{"James", "3"}));
    }
    @Test
    void topRankedNameGivenRange_Fem_01_09() throws IOException {
        int lowYear = 2010;
        int highYear = 2013;
        char sex = 'F';
        assertTrue(Arrays.equals(testP2.topRankedNameOfRange(lowYear, highYear,sex), new String[]{"Emma", "3"}));
    }
    @Test
    void topRankedNameGivenRange_bad_Range() throws IOException {
        int lowYear = 2000;
        int highYear = 2010;
        char sex = 'F';
        assertTrue(Arrays.equals(testP2.topRankedNameOfRange(lowYear, highYear,sex), new String[]{null, null}));
    }

//======================================================================
//Q4 P4 - Finds which first letter was most popular among girl names - returns list of those names
@Test
void mostPopularSexFirstLetter_2010_2014() throws IOException {
    int lowYear = 2010;
    int highYear = 2011;
    char sex = 'F';

    TreeSet<String> correct = new TreeSet<>();
    String[] correctArray = new String[]{ "Abigail", "Addison", "Alexandra", "Alexis", "Ava"};
    correct.addAll(Arrays.asList(correctArray));

    assertTrue((testP2.mostPopularSexFirstLetter(lowYear, highYear, sex).equals(correct)));
}
    @Test
    void mostPopularSexFirstLetter_2014() throws IOException {
        int lowYear = 2014;
        int highYear = 2014;
        char sex = 'F';
        TreeSet<String> correct = new TreeSet<>();
        String[] correctArray = new String[]{ "Elizabeth", "Emily", "Emma"};
        correct.addAll(Arrays.asList(correctArray));

        assertTrue((testP2.mostPopularSexFirstLetter(lowYear, highYear, sex).equals(correct)));
    }
    @Test
    void mostPopularSexFirstLetter_Bad_Range() throws IOException {
        int lowYear = 2014;
        int highYear = 2012;
        char sex = 'F';
        assertTrue((testP2.mostPopularSexFirstLetter(lowYear, highYear, sex).equals(new TreeSet<>())));
    }

    //=================================================================//

    //Q1 Complete
    @Test
    void  rankingsOverTimeSpecInt_2010_2012_GOODVAL() throws IOException {
        int lowYear = 2010;
        int highYear = 2012;
        String name = "Olivia";
        char sex = 'F';

        assertTrue((testP2.rankingsOverTimeSpecificInterval(name, sex, lowYear, highYear).get(2010).equals(3)) &&
                testP2.rankingsOverTimeSpecificInterval(name, sex, lowYear, highYear).get(2011).equals(3) &&
                testP2.rankingsOverTimeSpecificInterval(name, sex, lowYear, highYear).get(2012).equals(4));
    }

    @Test
    void  rankingsOverTimeSpecInt_2010_2013_MissingVAL() throws IOException {
        int lowYear = 2010;
        int highYear = 2013;
        String name = "OOOOOO";
        char sex = 'M';

        assertTrue((testP2.rankingsOverTimeSpecificInterval(name, sex, lowYear, highYear).equals(new HashMap<>())));
    }
    @Test
    void  rankingsOverTimeSpecInt_2010_2012_partialMissingVal() throws IOException {
        int lowYear = 2012;
        int highYear = 2014;
        String name = "Michael";
        char sex = 'M';

        assertTrue((testP2.rankingsOverTimeSpecificInterval(name, sex, lowYear, highYear).get(2012).equals(6) &&
                testP2.rankingsOverTimeSpecificInterval(name, sex, lowYear, highYear).get(2013).equals(0)) &&
                testP2.rankingsOverTimeSpecificInterval(name, sex, lowYear, highYear).get(2014).equals(0));
    }
//// ===============================================================///

    //Q2 Complete
    @Test
    void  differenceInRankGivenYears_2012_2014_custom() throws IOException {
        int lowYear = 2012;
        int highYear = 2014;
        String name = "Jacob";
        char sex = 'M';

        assertTrue(testP2.differenceInRankGivenYears(name,sex,lowYear,highYear)==2);
    }
    @Test
    void  differenceInRankGivenYears_2010_2013_custom_missing_one_Year() throws IOException {
        int lowYear = 2010;
        int highYear = 2013;
        String name = "Ava";
        char sex = 'F';

        assertTrue(testP2.differenceInRankGivenYears(name,sex,lowYear,highYear)==-5);
    }
    @Test
    void  differenceInRankGivenYears_2010_2013_custom_WrongYears() throws IOException {
        int lowYear = 2010;
        int highYear = 2015;
        String name = "Josh";
        char sex = 'M';

        assertTrue(testP2.differenceInRankGivenYears(name,sex,lowYear,highYear)== Integer.MIN_VALUE);
    }
    @Test
    void  differenceInRankGivenYears_2010_2013_custom_WrongName() throws IOException {
        int lowYear = 2010;
        int highYear = 2013;
        String name = "PPPPPP";
        char sex = 'M';

        assertTrue(testP2.differenceInRankGivenYears(name,sex,lowYear,highYear)== Integer.MAX_VALUE);
    }

    //Q3 Complete
    @Test
    void  nameOfGreatestRankChange_F_2010_2014() throws IOException {
        int lowYear = 2010;
        int highYear = 2014;
        char sex = 'F';

        assertEquals(testP2.nameOfGreatestRankChange(sex,lowYear,highYear),("Abigail"));
    }
    @Test
    void  nameofGreatestRankChange_M_2010_2011() throws IOException {
        int lowYear = 2010;
        int highYear = 2011;
        char sex = 'M';

        assertEquals(testP2.nameOfGreatestRankChange(sex,lowYear,highYear),("Jacob"));
    }
    @Test
    void  namofGreatestRankChange_wrong_Years() throws IOException {
        int lowYear = 2010;
        int highYear = 2017;
        char sex = 'M';

        assertTrue(testP2.nameOfGreatestRankChange(sex,lowYear,highYear).equals("Range not in Dataset"));
    }

    // ========================================================//
    //Q4P3
    @Test
    void  averageRank_Custom_2010_2011_F() throws IOException {
        int lowYear = 2010;
        int highYear = 2011;
        char sex = 'F';
        String name = "Emily";

        assertTrue(testP2.averageRank(name,sex,lowYear,highYear)==5);
    }
    @Test
    void  averageRank_Custom_2012_2014_M() throws IOException {
        int lowYear = 2012;
        int highYear = 2014;
        char sex = 'M';
        String name = "James";

        assertTrue(testP2.averageRank(name,sex,lowYear,highYear)==3);
    }
    @Test
    void  averageRank_Custom_1999_2002_bad_Name() throws IOException {
        int lowYear = 2010;
        int highYear = 2013;
        char sex = 'M';
        String name = "MMMMMMMM";

        assertTrue(testP2.averageRank(name,sex,lowYear,highYear)==0);
    }

//=========================================================================//
    //Q5 Complete
    @Test
    void  highestAverageRank_Custom_2010_2012_F() throws IOException {
        int lowYear = 2010;
        int highYear = 2012;
        char sex = 'F';

        assertTrue(testP2.highestAverageRank(sex,lowYear,highYear).equals("Emma"));
    }
    @Test
    void  highestAverageRank_Custom_2011_2012_M() throws IOException {
        int lowYear = 2011;
        int highYear = 2012;
        char sex = 'M';

        assertTrue(testP2.highestAverageRank(sex,lowYear,highYear).equals("James"));
    }
    @Test
    void highestAverageRank_Custom_2000_2005_bad_years() throws IOException{
        int lowYear = 1700;
        int highYear = 2000;
        char sex = 'M';
        assertEquals(testP2.highestAverageRank(sex,lowYear,highYear),(rangeNotThereString));
    }
    //================================================================//
    //Q6 complete
    @Test
    void averageRankOverRecentYears() throws IOException{
        int numYears = 2;
        String name = "James";
        char sex = 'M';
        assertEquals(testP2.averageRankOverRecentYears(name,sex,numYears),5);
    }
    @Test
    void averageRankOverRecentYears_bad_name() throws IOException{
        int numYears = 3;
        String name = "JJJJJJj";
        char sex = 'F';
        assertEquals(testP2.averageRankOverRecentYears(name,sex,numYears), 0);
    }
    @Test
    void averageRankOverRecentYears_bad_years() throws IOException{
        int numYears = 400;
        String name = "John";
        char sex = 'M';
        assertEquals(testP2.averageRankOverRecentYears(name,sex,numYears),Integer.MIN_VALUE);
    }
//=============================================
    //Q7 Complete
    @Test
    void namesAtGivenRankOverRange_Custom_2012_2014() throws IOException{
        int rank = 3;
        char sex = 'M';
        int lowYear = 2012;
        int highYear = 2014;
        String[] correctList = new String[]{"Alexander", "Eric", "Scott"};
        assertTrue(testP2.namesAtGivenRankOverRange(sex, rank, lowYear, highYear).equals(Arrays.asList(correctList)));
    }
    @Test
    void namesAtGivenRankOverRange_Custom_2010_2011_bad_Rank() throws IOException{
        int rank = 12;
        char sex = 'F';
        int lowYear = 2010;
        int highYear = 2011;
        String[] correctList = new String[]{"Abigail"};
        assertTrue(testP2.namesAtGivenRankOverRange(sex, rank, lowYear, highYear).equals(Arrays.asList(correctList)));
    }

    @Test
    void namesAtGivenRankOverRange_Custom_bad_years() throws IOException{
        int rank = 2;
        char sex = 'F';
        int lowYear = 2004;
        int highYear = 2011;
        assertEquals(testP2.namesAtGivenRankOverRange(sex, rank, lowYear, highYear),(new ArrayList<>()));
    }

//=========================================================//
    //Q8 Complete
    @Test
    void heldRankMostOften_Custom_2012_2014_M_3() throws IOException{
    int rank = 3;
    char sex = 'M';
    int lowYear = 2012;
    int highYear = 2014;
    assertEquals(testP2.heldRankMostOften(sex, rank, lowYear, highYear)[0],("Alexander"));
    assertEquals(Integer.parseInt(testP2.heldRankMostOften(sex, rank, lowYear, highYear)[1]),1);
}
    @Test
    void heldRankMostOften_Custom_2010_2012_M_3() throws IOException{
        int rank = 5;
        char sex = 'F';
        int lowYear = 2010;
        int highYear = 2012;
        assertEquals(testP2.heldRankMostOften(sex, rank, lowYear, highYear)[0],("Madison"));
        assertEquals(Integer.parseInt(testP2.heldRankMostOften(sex, rank, lowYear, highYear)[1]),2);
    }

    @Test
    void heldRankMostOften_Custom_Bad_Years() throws IOException{
        int rank = 5;
        char sex = 'F';
        int lowYear = 2014;
        int highYear = 2012;
        assertTrue(Arrays.equals((testP2.heldRankMostOften(sex, rank, lowYear, highYear)),(new String[2])));
    }
    //==================================================
    // optional question 9
    @Test
    void mostCommonPrefix_customOpt_M() throws IOException{
        char sex = 'M';
        int lowYear = 2000;
        int highYear = 2001;
        assertEquals(testP3.mostCommonPrefix(sex, lowYear,highYear),"JOSH");
    }
    @Test
    void mostCommonPrefix_customOpt_F() throws IOException{
        char sex = 'F';
        int lowYear = 2000;
        int highYear = 2001;
        assertEquals(testP3.mostCommonPrefix(sex, lowYear,highYear),"ELIZ");
    }
    @Test
    void mostCommonPrefix_customOpt_Bad_Range() throws IOException{
        char sex = 'F';
        int lowYear = 1999;
        int highYear = 2004;
        assertEquals(testP3.mostCommonPrefix(sex, lowYear,highYear),rangeNotThereString);
    }
    //=========================
}