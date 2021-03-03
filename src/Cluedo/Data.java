package Cluedo;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Dictionary;
import java.util.Scanner; // Import the Scanner class to read text files
import org.json.*;

public class Data {
    String board;
    JSONObject jsonData;
    public Data(String file_name){
        this.readFile(file_name);
    }
    public void readFile(String file_name){
        if(file_name.substring(file_name.length() - 3, file_name.length()).equals("txt")){
            this.board = this.readTxt(file_name);
        }
        else if(file_name.substring(file_name.length() - 4, file_name.length()).equals("json")){
            this.readJSON(file_name);
        }
    }
    private void readJSON(String file_name){
        String data = readTxt(file_name); //assign your JSON String here
        if (data != null) {
            this.jsonData = new JSONObject(data);
        }
    }
    private String readTxt(String file_name){
        try {
            File file = new File("src/Cluedo/Data/"+file_name);
//            System.out.println(file.getAbsolutePath());
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

    public static void main(String[] args) {
        Data data = new Data("data.json");
    }

}
