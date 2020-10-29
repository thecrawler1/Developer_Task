package com.thecrawler;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));
        // Iterate for all file/directory paths
        for (String path: args) {
            ArrayList<String> file;
            try {
                file = Util.readHandHistories(path);
            } catch (FileNotFoundException e) {
                System.err.println("Invalid file path: " + path);
                continue;
            }
            ArrayList<ArrayList<String>> handHistories = Util.extractHands(file);
            Parser parser = new Parser();
            for (ArrayList<String> handHistory: handHistories) {
                try {
                    parser.parse(handHistory);
                    parser.print();
                } catch (Exception e) {
                    System.err.println("Error in parse hand.\n");
                }
            }
        }
    }
}
