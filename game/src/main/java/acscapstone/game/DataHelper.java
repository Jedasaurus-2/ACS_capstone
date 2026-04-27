package acscapstone.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class DataHelper {

    private BufferedReader reader;
    public float[][] convertedData; // An array of unknown lengths of 3 length arrays. X,Y,Z
    public HashMap<int[], String> data = new HashMap<>(); // This will be the data...
    // Basically, I am going to check if the data exists, since there are some data holes
    // If it does... GREAT. If it doesn't, use a default which is defined in another class

    public DataHelper() {}

    // Read the units file and make data
    public void generateData() {
        try {
            reader = new BufferedReader(new FileReader("units.txt"));
            String line = reader.readLine();
            try {
                line = line.substring(line.indexOf("0("));
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
            String[] splitLine = line.split("\\(");
            for (int i = 0; i < splitLine.length; i++) {
                if (splitLine[i].contains(")")) {
                    splitLine[i] = splitLine[i].replace(")", "");
                    String[] tempLine = splitLine[i].split(",");
                    int[] temp = {Integer.parseInt(tempLine[0]), Integer.parseInt(tempLine[1])};
                    data.put(temp, tempLine[2]); // Put distance into the positon of x, y
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // Read a specific line
    public void generateData(int l) {
        try {
            reader = new BufferedReader(new FileReader("units.txt"));
            String line = "";
            for (int i = 0; i < l; i++) {
                line = reader.readLine();
           }
            try {
                line = line.substring(line.indexOf("0("));
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
            String[] splitLine = line.split("\\(");
            for (int i = 0; i < splitLine.length; i++) {
                if (splitLine[i].contains(")")) {
                    splitLine[i] = splitLine[i].replace(")", "");
                    String[] tempLine = splitLine[i].split(",");
                    int[] temp = {Integer.parseInt(tempLine[0]), Integer.parseInt(tempLine[1])};
                    data.put(temp, tempLine[2]); // Put distance into the positon of x, y
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
