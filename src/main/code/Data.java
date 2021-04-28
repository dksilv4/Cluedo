package code;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Dictionary;
import java.util.Scanner; // Import the Scanner class to read text files
import org.json.*;

/**
 * Class to read and write data from files for the game
 */
public class Data {
    private String board;
    private JSONObject jsonData;
    private String projectPath = "src/main/code/Data/";

    /**
     * Constructs Data by reading the input file
     *
     * @param file_name a file name string
     */
    public Data(String file_name) {
        this.readFile(file_name);
    }

    /**
     * @return the text that represents the board
     */
    public String getBoard() {
        return this.board;
    }


    /**
     * @return data from json file
     */
    public JSONObject getJsonData() {
        return this.jsonData;
    }

    /**
     * A method that depending on the filename it is either reads txt or json file.
     *
     * @param file_name a file name string
     */
    private void readFile(String file_name) {
        if (file_name.substring(file_name.length() - 3, file_name.length()).equals("txt")) {
            this.board = this.readTxt(file_name);
        } else if (file_name.substring(file_name.length() - 4, file_name.length()).equals("json")) {
            this.readJSON(file_name);
        }
    }

    /**
     * Reads json file
     *
     * @param file_name a JSON file name
     */
    private void readJSON(String file_name) {
        String data = readTxt(file_name); //assign your JSON String here
        if (data != null) {
            this.jsonData = new JSONObject(data);
        }
    }

    /**
     * Reads a text file and returns a string representing the board data
     *
     * @param file_name a txt file
     * @return a string or null
     */
    private String readTxt(String file_name) {
        try {
            File file = new File(this.projectPath + file_name);
            Scanner myReader = new Scanner(file);
            StringBuilder data = new StringBuilder();
            while (myReader.hasNextLine()) {
                data.append(myReader.nextLine());
                data.append("\n");
            }
            myReader.close();
            return data.toString();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }
    }

}
