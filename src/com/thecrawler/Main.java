package com.thecrawler;

import java.util.ArrayList;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));

        ArrayList<String> file = Util.readHandHistoryFile("C:\\Users\\gabri\\hands.txt");
        ArrayList<ArrayList<String>> handHistories = Util.extractHands(file);
        Parser parser = new Parser();

        for (ArrayList<String> handHistory: handHistories) {
            parser.parse(handHistory);
            parser.print();
        }
    }
}
