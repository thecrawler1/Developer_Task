package com.thecrawler;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));
        // Iterate for all file/directory paths
        for (String path: args) {
            ArrayList<String> handHistories;
            try {
                handHistories = Util.readHandHistories(path);
            } catch (FileNotFoundException e) {
                System.err.println("Invalid file/directory path: " + path);
                continue;
            }
            ArrayList<ArrayList<String>> hands = Util.extractHands(handHistories);
            for (ArrayList<String> handHistory: hands) {
                try {
                    Hand hand = Parser.parse(handHistory);
                    hand.print();
                } catch (Exception e) {
                    System.err.println("Error in parse hand.\n");
                }
            }
        }
    }
}
