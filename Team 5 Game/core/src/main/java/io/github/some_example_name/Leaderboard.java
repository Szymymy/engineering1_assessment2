package io.github.some_example_name;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Leaderboard {
    private int[] leaderBoard = new int[]{0,0,0,0,0};

    public int[] loadLeaderboard() {
        try {
            File saveFile = new File("save.txt");

            if (saveFile.createNewFile()) {
                System.out.println("File created: " + saveFile.getName());
            } 
            else {
                System.out.println("File already exists.");
                Scanner reader = new Scanner(saveFile);

                int i = 0;
                while (reader.hasNextLine()) {
                    String data = reader.nextLine();
                    try {    
                        leaderBoard[i] = Integer.parseInt(data);
                    }
                    catch (NumberFormatException e) {
                        leaderBoard[i] = 0;
                    }
                    i++;
                }
                reader.close();
            }
            return leaderBoard;
        }

        catch (IOException e) {
            System.out.println("An error has occured.");
            e.printStackTrace();
        }
        return null;
    }

    public void updateLeaderboard(int score) {
        int[] newLeaderboard = loadLeaderboard();
        if (score > newLeaderboard[newLeaderboard.length-1]) {
            newLeaderboard[newLeaderboard.length-1] = score;
        }
        Arrays.sort(newLeaderboard);

        try {
            FileWriter writer = new FileWriter("save.txt");
            for(int i: newLeaderboard){
                writer.write(i+"\n");
            }
        }
        catch (IOException e) {
            System.out.println("An error has occured.");
            e.printStackTrace();
        }


    }
}
