package com.thecrawler;

import java.util.ArrayList;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));

        String path = args[0];
        ArrayList<String> file = Util.readHandHistoryFile(path);
        ArrayList<ArrayList<String>> handHistories = Util.extractHands(file);
        Parser parser = new Parser();

        for (ArrayList<String> handHistory: handHistories) {
            parser.parse(handHistory);
            parser.print();
        }
    }
}
