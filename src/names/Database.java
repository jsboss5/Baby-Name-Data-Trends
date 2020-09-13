package names;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

//TODO = 1 account for genders that aren't male
public class Database {

    /**
     * This class is used to read in information (either from a file path or URL) and
     * writes it to a hashmap data base that maps a year to nested list of lines from that year's file"
     */

    private String inputPath;
    private URL inputURL;
    private HashMap<Integer, List<List<String>>> dataSet;

    private final static String startOfAllURLs = "https:";
    private final static String hrefSearchTag = "href=\"yob";
    private final static String expectedFileExtension = ".txt";
    private final static String emptyString = "";
    private final static int hrefTagLength = 6;
    private final static int fileYearLength = 11;

    public Database(String fileName) {
        /**
         * Creates a new dataBase from a fileName
         */
        try {
            dataSet = this.createDataStructureFromPath(fileName);
            inputPath = fileName;
        }
        catch(IOException e){
            System.out.print("fileName incorrect");
        }
        }

    public Database(URL dataURL) throws IOException {
        /**
         * Creates a new dataBase from a URL
         * Could Fail if URL is incorrect
         * @param = dataURL - url that database is being built off of
         */
        dataSet = this.createDataStructureFromURL(dataURL);
        inputURL = dataURL;
    }

    public HashMap<Integer, List<List<String>>> getDataBase(){
       /**
       this getter method returns the dataBase instance variable (private)
        */
        return dataSet;
    }

    public String getFilePath(){
        /**
        This method returns the filePath private instance variable
         */
        return inputPath;
    }

    private HashMap<Integer, List<List<String>>> createDataStructureFromURL(URL url) throws IOException {


            // read text returned by server
            BufferedReader URLReader = createBufferedReader(url.toString());
            ArrayList<String> pathNames = new ArrayList<>();

            String currentLineInFile;
            String preFixToFile = emptyString;
            if(!url.toString().endsWith(expectedFileExtension)) {                  //if it's not a single file then loop through
                while ((currentLineInFile = URLReader.readLine()) != null) {
                    int indexOfHrefTag = currentLineInFile.indexOf(hrefSearchTag);
                    if(indexOfHrefTag != -1){
                        String individualYearFile = currentLineInFile.substring(indexOfHrefTag+hrefTagLength,indexOfHrefTag + hrefTagLength + fileYearLength );
                        pathNames.add(individualYearFile);
                        preFixToFile = addSlashToDir(url.toString());
                    }
                }
            }
            else{
                pathNames.add(url.toString());
            }
            HashMap<Integer, List<List<String>>> dataBase = addYearDataToMap(pathNames, preFixToFile);     //Tree Map That will be returned
        return dataBase;


    }

    private HashMap<Integer, List<List<String>>> createDataStructureFromPath(String path) throws IOException {
        //creates a TreeMap that maps a year to an arrayList of string lists where each String list is a line
        // in the data file.

        String[] pathNames;
        File f = new File(path);

        if (f.isDirectory()) {
            pathNames = f.list();
            path = addSlashToDir(path);
        }

        else{
            pathNames = new String[]{path}; //create list with single element
            path = emptyString;
        }

            HashMap<Integer, List<List<String>>> dataBase = addYearDataToMap(Arrays.asList(pathNames), path);     //Tree Map That will be returned

        return dataBase;
    }

    private String addSlashToDir(String path){
        if (!path.endsWith("/")) {
            path += "/";     //handles input errors where directories don't have slash at the end
        }
        return path;
    }

    private HashMap<Integer,List<List<String>>> addYearDataToMap(List<String> fileNames, String pathPreFix) throws IOException {
        HashMap<Integer, List<List<String>>> dataBase = new HashMap<>();
        for (String pathName : fileNames) {
            Integer year = yearOfFile(pathName);
            List<List<String>> lines = addLinesToList(pathPreFix + pathName);
            dataBase.put(year, lines);
        }
        return dataBase;

    }

    private List<List<String>> addLinesToList(String path) throws IOException {
        /*
        This method is used called in the create database function - it's job is to take in the path as an input
        and then loop through all of the lines in a file and return a list of lists of strings which will be mapped
        to a year in the Tree Map
         */

        String row;
        List<List<String>> lines = new ArrayList(); //add to this and then put in the map
        BufferedReader txtReader = createBufferedReader(path);
        while ((row = txtReader.readLine()) != null) {
            String[] data = row.split(",");
            List<String> line = Arrays.asList(data);
            lines.add(line);
        }

        return lines;

    }

    private BufferedReader createBufferedReader(String fileName) throws IOException {
        /*
        This method returns a bufferedReader that can be used to read in a file - it is used
        by all of the question methods - because all of them need to read a file.
         */
        if(fileName.startsWith(startOfAllURLs)){
            URL url = new URL(fileName);
            return new BufferedReader(new InputStreamReader(url.openStream()));
        }
        return new BufferedReader(new FileReader(fileName));       //creates the file reader that will be used
    }



    private Integer yearOfFile(String path) {
        return Integer.parseInt(path.split("/")[path.split("/").length - 1].substring(3, 7));
    }


}
