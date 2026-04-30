package acscapstone.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataHelper {

    private BufferedReader reader;
    public float[][] convertedData; // An array of unknown lengths of 3 length arrays. X,Y,Z
    public HashMap<int[], String> data = new HashMap<>(); // This will be the data...
    // Basically, I am going to check if the data exists, since there are some data holes
    // If it does... GREAT. If it doesn't, use a default which is defined in another class
    public int greatestDistance = 0;

    public DataHelper() {}

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

            // Call it to update the listbox
            limitsUI.updateReadableLinesLabel(usableLines);

            // This string specifically
            //System.out.println(l);
            String line = lines.get(l);

            // Get the values from the list
            String[] splitLine = line.split("\\(");
            //System.out.println(Arrays.toString(splitLine));
            for (int i = 0; i < splitLine.length; i++) {
                if (splitLine[i].contains(")")) {
                    try {
                        splitLine[i] = splitLine[i].replace(")", "");
                        String[] tempLine = splitLine[i].split(",");
                        //System.out.println(Arrays.toString(tempLine));
                        int[] temp = {Integer.parseInt(tempLine[0]), Integer.parseInt(tempLine[1])};
                        if (Integer.parseInt(tempLine[2]) > greatestDistance) {
                            greatestDistance = Integer.parseInt(tempLine[2]);
                        }
                        data.put(temp, tempLine[2]); // Put distance into the positon of x, y. Distance is a string
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
