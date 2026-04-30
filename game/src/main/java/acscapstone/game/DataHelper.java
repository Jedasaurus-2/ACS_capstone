package acscapstone.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataHelper {

    private BufferedReader reader;
    public float[][] convertedData; // An array of unknown lengths of 3 length arrays. X,Y,Z
    public HashMap<int[], String> data = new HashMap<>(); // This will be the data...
    // Basically, I am going to check if the data exists, since there are some data holes
    // If it does... GREAT. If it doesn't, use a default which is defined in another class
    public int greatestDistance = 0;

    public DataHelper() {}

    // Read the units file and make data
    public void generateData() {
        data.clear();
        try {
            reader = new BufferedReader(new FileReader("units.txt"));
            List<String> lines = reader.readAllLines();
            String line = lines.getFirst();
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

    // Read a specific line of data
    public void generateData(int l, LimitsUI limitsUI) {
        greatestDistance = 0;
        data.clear();
        try {
            reader = new BufferedReader(new FileReader("units.txt"));
            List<String> lines = reader.readAllLines();
            // Get the list of readable lines (length based currently)
            ArrayList<Integer> usableLines = new ArrayList<>();
            for (int x = 0; x < lines.size(); x++) {
                if (lines.get(x).length() > 800) {
                    usableLines.add(x);
                }
            }
            // Call it
            limitsUI.updateReadableLinesLabel(usableLines);

            // This string specifically
            String line = lines.get(l);

            // Get the values from the list
            String[] splitLine = line.split("\\(");
            for (int i = 0; i < splitLine.length; i++) {
                if (splitLine[i].contains(")")) {
                    splitLine[i] = splitLine[i].replace(")", "");
                    String[] tempLine = splitLine[i].split(",");
                    int[] temp = {Integer.parseInt(tempLine[0]), Integer.parseInt(tempLine[1])};
                    if (Integer.parseInt(tempLine[2]) > greatestDistance) {
                        greatestDistance = Integer.parseInt(tempLine[2]);
                    }
                    data.put(temp, tempLine[2]); // Put distance into the positon of x, y. Distance is a string
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
