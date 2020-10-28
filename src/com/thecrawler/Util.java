package com.thecrawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static ArrayList<String> readHandHistoryFile(String path) {
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            ArrayList<String> lines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            return lines;
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<ArrayList<String>> extractHands(ArrayList<String> lines) {
        Pattern firstLineHandHistory = Pattern.compile("PokerStars Hand #([0-9]*):  Hold\\'em No Limit \\(([$€£])([0-9\\.]*)\\/[$€£]([0-9\\.]*) .*\\) - (.*)");
        ArrayList<ArrayList<String>> hands = new ArrayList<>();
        ArrayList<String> aux = new ArrayList<>();
        try {
            for (String line: lines) {
                Matcher matcher = firstLineHandHistory.matcher(line);
                boolean isFirstLine = matcher.find();
                if (isFirstLine) {
                    if (aux.size() > 0) {
                        hands.add(new ArrayList<>(aux));
                        aux.clear();
                    }
                    aux = new ArrayList<>();
                }
                if (line.length() > 0) {
                    aux.add(line);
                }
            }
            if (aux.size() > 0) {
                hands.add(new ArrayList<>(aux));
            }
        } catch (Exception e) {
            System.out.println("Error in extract hands");
            e.printStackTrace();
        }
        return hands;
    }
}
