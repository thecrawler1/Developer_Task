package com.thecrawler;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));
        Parser parser = new Parser();
        // Iterate for all file/directory paths
        for (String path: args) {
            ArrayList<String> handHistories;
            try {
                handHistories = Util.readHandHistories(path);
            } catch (FileNotFoundException e) {
                System.err.println("Invalid file path: " + path);
                continue;
            }
            ArrayList<ArrayList<String>> hands = Util.extractHands(handHistories);
            for (ArrayList<String> hand: hands) {
                try {
                    parser.parse(hand);
                    parser.print();
                } catch (Exception e) {
                    System.err.println("Error in parse hand.\n");
                }
            }
        }
    }
}
