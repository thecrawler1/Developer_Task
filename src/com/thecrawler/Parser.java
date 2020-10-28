package com.thecrawler;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private final Pattern RGX_HAND_INFO = Pattern.compile("PokerStars Hand #([0-9]*): {1,2}Hold'em No Limit \\(([$€£])([0-9.]*)/[$€£]([0-9.]*) .*\\) - (.*)");
    private final Pattern RGX_BTN_POSITION = Pattern.compile("Table '.*' [2-9]-max Seat #([1-9]) is the button");
    private final Pattern RGX_PLAYER_INFO = Pattern.compile("Seat ([1-9]): (.*) \\([$€£]([0-9.]*) in chips\\)");
    private final Pattern RGX_BLIND_POST = Pattern.compile("(.*): posts (?:small|big) blind [$€£]([0-9.]*)");
    private final Pattern RGX_STREET_INDICATOR = Pattern.compile("\\*\\*\\* (FLOP|TURN|RIVER|SHOW DOWN) \\*\\*\\*");
    private final Pattern RGX_PLAYER_FOLDED = Pattern.compile("(.*): folds");
    private final Pattern RGX_PLAYER_ACTION = Pattern.compile("(.*): (calls|bets|raises) [$€£]([0-9.]*)(?: to [$€£]|)([0-9.]*|)");
    private final Pattern RGX_POT_AND_RAKE = Pattern.compile("Total pot [$€£]([0-9.]*) \\| Rake [$€£]([0-9.]*)");
    private final Pattern RGX_BOARD = Pattern.compile("Board \\[(.*)]");
    private final Pattern RGX_PLAYER_CARDS = Pattern.compile("(.*): shows \\[(.*)]");
    private final Pattern RGX_UNCALLED_BET = Pattern.compile("Uncalled bet \\([$€£]([0-9.]*)\\) returned to (.*)");
    private final Pattern RGX_PLAYER_COLLECTED = Pattern.compile("(.*) collected [$€£]([0-9.]*) from pot");

    private Hand hand;

    public void parse(ArrayList<String> lines) {
        hand = new Hand();

        for (String line: lines) {
            readHandInfo(line);
            readButtonPosition(line);
            readPlayerInfo(line);
            readBlindPost(line);
            readStreetIndicator(line);
            readPlayerFolded(line);
            readPlayerAction(line);
            readPotAndRake(line);
            readBoard(line);
            readPlayerCards(line);
            readUncalledBet(line);
            readPlayerCollected(line);
        }
    }

    public void print() {
        hand.print();
    }

    private void readHandInfo(String line) {
        Matcher matcher = RGX_HAND_INFO.matcher(line);
        if (matcher.find()) {
            String handId = matcher.group(1);
            String currency = matcher.group(2);
            float smallBlindValue = Float.parseFloat(matcher.group(3));
            float bigBlindValue = Float.parseFloat(matcher.group(4));
            String dateAndTime = matcher.group(5);
            hand.setHandId(handId);
            hand.setCurrency(currency);
            hand.setSmallBlindValue(smallBlindValue);
            hand.setBigBlindValue(bigBlindValue);
            hand.setDateAndTime(dateAndTime);
        }
    }

    private void readButtonPosition(String line) {
        Matcher matcher = RGX_BTN_POSITION.matcher(line);
        if (matcher.find()) {
            int buttonPosition = Integer.parseInt(matcher.group(1));
            hand.setButtonPosition(buttonPosition);
        }
    }

    private void readPlayerInfo(String line) {
        Matcher matcher = RGX_PLAYER_INFO.matcher(line);
        if (matcher.find()) {
            int seat = Integer.parseInt(matcher.group(1));
            String nickname = matcher.group(2);
            float stack = Float.parseFloat(matcher.group(3));
            Player player = new Player(seat, nickname, stack);
            hand.addPlayer(player);
        }
    }

    private void readBlindPost(String line) {
        Matcher matcher = RGX_BLIND_POST.matcher(line);
        if (matcher.find()) {
            String nickname = matcher.group(1);
            float value = Float.parseFloat(matcher.group(2));
            Player player = hand.getPlayerByNickname(nickname);
            player.setNewStack(player.getNewStack() - value);
        }
    }

    private void readStreetIndicator(String line) {
        Matcher matcher = RGX_STREET_INDICATOR.matcher(line);
        if (matcher.find()) {
            hand.setLastStreet(matcher.group(1).toLowerCase());
        }
    }

    private void readPlayerFolded(String line) {
        Matcher matcher = RGX_PLAYER_FOLDED.matcher(line);
        if (matcher.find()) {
            String nickname = matcher.group(1);
            Player player = hand.getPlayerByNickname(nickname);
            player.setStreetFolded(hand.getLastStreet());
        }
    }

    private void readPlayerAction(String line) {
        Matcher matcher = RGX_PLAYER_ACTION.matcher(line);
        if (matcher.find()) {
            String nickname = matcher.group(1);
            String action = matcher.group(2);
            float value = Float.parseFloat(matcher.group(action.equals("raises") ? 4 : 3));
            Player player = hand.getPlayerByNickname(nickname);
            player.setNewStack(player.getNewStack() - value);
        }
    }

    private void readPotAndRake(String line) {
        Matcher matcher = RGX_POT_AND_RAKE.matcher(line);
        if (matcher.find()) {
            float pot = Float.parseFloat(matcher.group(1));
            float rake = Float.parseFloat((matcher.group(2)));
            hand.setPot(pot);
            hand.setRake(rake);
        }
    }

    private void readBoard(String line) {
        Matcher matcher = RGX_BOARD.matcher(line);
        if (matcher.find()) {
            String board = matcher.group(1);
            hand.setBoard(board);
        }
    }

    private void readPlayerCards(String line) {
        Matcher matcher = RGX_PLAYER_CARDS.matcher(line);
        if (matcher.find()) {
            String nickname = matcher.group(1);
            String cards = matcher.group(2);
            Player player = hand.getPlayerByNickname(nickname);
            player.setCards(cards);
        }
    }

    private void readUncalledBet(String line) {
        Matcher matcher = RGX_UNCALLED_BET.matcher(line);
        if (matcher.find()) {
            float value = Float.parseFloat(matcher.group(1));
            String nickname = matcher.group(2);
            Player player = hand.getPlayerByNickname(nickname);
            player.setNewStack(player.getNewStack() + value);
        }
    }

    private void readPlayerCollected(String line) {
        Matcher matcher = RGX_PLAYER_COLLECTED.matcher(line);
        if (matcher.find()) {
            String nickname = matcher.group(1);
            float value = Float.parseFloat(matcher.group(2));
            Player player = hand.getPlayerByNickname(nickname);
            player.setNewStack(player.getNewStack() + value);
        }
    }
}
