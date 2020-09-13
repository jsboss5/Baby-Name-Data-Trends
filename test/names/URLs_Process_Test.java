package names;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class URLs_Process_Test {
    private Process testProcess;    //used for t
    private Database dataBase;

    @BeforeEach
    void setup() throws IOException {
        String URLName = "https://www2.cs.duke.edu/courses/fall20/compsci307d/assign/01_data/data/ssa_complete/";

        dataBase = new Database(new URL(URLName));
        testProcess = new Process(dataBase);      //for part one questions require a single file
    }

    //this test makes sure the URL method works and is the exact same as the file database
    @Test
    void checkURLandFileHaveSameDic() throws IOException {
        Database dbFromFile = new Database("data/ssa_complete/");
        assertTrue(dataBase.getDataBase().equals(dbFromFile.getDataBase()));  //this test takes a long time
    }

    //================================================================//
    //Q2 Basic
    @Test
    void URL_sameRankRecentYear_Emily_F() throws IOException {
        String name = "Sophia";
        char sex = 'F';
        int year = 2013;
        assertTrue(Arrays.equals(testProcess.sameRankRecentYear(name,sex,year), new String[]{"Emma", "F"}));
    }
    //Q2 Complete
    @Test
    void  URL_differenceInRankGivenYears_2000_2014() throws IOException {
        int lowYear = 2000;
        int highYear = 2014;
        String name = "Jacob";
        char sex = 'M';

        assertEquals(testProcess.differenceInRankGivenYears(name,sex,lowYear,highYear),-3);
    }

    //Q4P3
    @Test
    void  URL_averageRank_2010_2011_F() throws IOException {
        int lowYear = 1910;
        int highYear = 1911;
        char sex = 'F';
        String name = "Gladys";

        assertEquals(testProcess.averageRank(name,sex,lowYear,highYear),17);
    }


}