package com.thecrawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static ArrayList<String> readHandHistories(String path) throws FileNotFoundException {
        ArrayList<String> lines = new ArrayList<>();
        File fileOrDirectory = new File(path);
        File[] files;
        if (fileOrDirectory.isDirectory()) {
            // If is a directory, add all files
            files = fileOrDirectory.listFiles();
        } else {
            // If is a file, add this file
            files = new File[]{fileOrDirectory};
        }
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNextLine()) {
                        lines.add(scanner.nextLine());
                    }
                }
            }
        }
        return lines;
    }

    public static ArrayList<ArrayList<String>> extractHands(ArrayList<String> lines) {
        Pattern firstLineHandHistory = Pattern.compile("PokerStars Hand #([0-9]*): {1,2}Hold'em No Limit \\(([$€£])([0-9.]*)/[$€£]([0-9.]*) .*\\) - (.*)");
        ArrayList<ArrayList<String>> hands = new ArrayList<>();
        ArrayList<String> aux = new ArrayList<>();
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
        return hands;
    }
}
