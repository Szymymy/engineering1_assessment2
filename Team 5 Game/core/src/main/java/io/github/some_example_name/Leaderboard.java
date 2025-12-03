package io.github.some_example_name;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * Leaderboard is the class used to track the highest scores scored by players
 * across runs. Stores all scores in a .txt file using a unique 3 letter
 * nickname and the score.
 */
public class Leaderboard {
    /**
     * The array used to store the leaderboard for manipulation.
     */
    private String[] leaderboard;


    /**
     * Leaderboard constructor, intialises an empty leaderboard first and then
     * loads any data in a file.
     */
    public Leaderboard() {
        leaderboard = new String[] {"NUL0","NUL0","NUL0","NUL0","NUL0"};
        loadLeaderboard();
    }

    /**
     * loadLeaderboard() opens a text file and reads the data in it to then 
     * parse it into a String[].
     * @return returns a String[] containing data held in the data.txt file, 
     * storing the leaderboard.
     */
    public String[] loadLeaderboard() {
        try {
            File saveFile = new File("data.txt");

            if (saveFile.createNewFile()) {
                System.out.println("File created: " + saveFile.getName());
            } 
            else {
                System.out.println("File already exists.");
                Scanner reader = new Scanner(saveFile);

                int i = 0;
                while (reader.hasNextLine()) {
                    String data = reader.nextLine();
                    leaderboard[i] = data;
                    i++;
                }
                reader.close();
            }
            return leaderboard;
        }
        catch (IOException e) {
            System.out.println("An error has occured.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * updateLeaderboard takes two params to update the leaderboard with a new
     * entry. If the nickname chosen already exists it will return false,
     * otherwise true.
     * @param newScore the potential new high-score to be added to leaderboard.
     * @param newName the potential new nickname to be added to the leaderboard.
     * @return true if succesful, false if otherwise.
     */
    public boolean updateLeaderboard(int newScore, String newName) {
        HashMap<String, Integer> scoreMap = new HashMap<>();
        if (scoreMap.containsKey(newName)) {
            return false;
        }
        String[] newLeaderboard = loadLeaderboard();
        for (String scores: newLeaderboard) {    
            String score = scores.substring(3);
            scoreMap.put(scores.substring(0, 3), Integer.parseInt(score));
        }
        Entry<String, Integer> minEntry = scoreMap.entrySet().stream().min(Map.Entry.comparingByValue()).orElse(null);
        if (newScore > minEntry.getValue()) {
            scoreMap.remove(minEntry.getKey());
            scoreMap.put(newName, newScore);
        }
        int i = 0;
        while (!scoreMap.isEmpty()) {
            Entry<String, Integer> maxEntry = scoreMap.entrySet().stream().max(Map.Entry.comparingByValue()).orElse(null);
            newLeaderboard[i] = (maxEntry.getKey()+maxEntry.getValue());
            scoreMap.remove(maxEntry.getKey());
            i++;
        }
        try {
            FileWriter scoreWriter = new FileWriter("data.txt");
            for (String scores: newLeaderboard) {
                scoreWriter.write(scores+"\n");
            }
            scoreWriter.close();
        } 
        catch(IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
        leaderboard = newLeaderboard;
        return true;
    }

    /**
     * Leaderboard getter.
     * @return String[] containing leaderboard.
     */
    public String[] getLeaderboard() {
        return leaderboard;
    }

    /**
     * Leaderboard clearer.
     */
    public void clearLeaderboard() {
        int i = 0;
        while (i < leaderboard.length) {
            leaderboard[i] = "NUL0";
            i++;
        }
    }
}
