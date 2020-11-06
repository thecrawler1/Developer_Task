package com.thecrawler;

import java.util.ArrayList;
import java.util.Arrays;

public class Hand {

    private String handId;
    private String currency;
    private float smallBlindValue;
    private float bigBlindValue;
    private String dateAndTime;
    private int buttonPosition;
    private final ArrayList<Player> players;
    private float pot;
    private float rake;
    private String board;
    private String lastStreet;

    public Hand() {
        this.players = new ArrayList<>();
        this.lastStreet = "preflop";
    }

    public void print() {
        System.out.println("Hand ID: " + handId);
        System.out.println("The Blinds: " + formatMoney(currency, smallBlindValue) + "/" + formatMoney(currency, bigBlindValue));
        System.out.println("Date and Time: " + dateAndTime);
        System.out.println("Button position: " + buttonPosition);
        printNamesStacksAndSeats();
        printPlayersThatFoldedPreflop();
        printPlayerThatPlayedFlopTurnOrRiver();
        System.out.println("Total pot and rake\n - " + formatMoney(currency, pot) + ", " + formatMoney(currency, rake));
        if (board != null) {
            System.out.println("The Board\n - " + board);
        }
        printPlayersThatReachTheShowdown();
        printStackBalanceOfEachPlayer();
        System.out.println();
    }

    private void printNamesStacksAndSeats() {
        System.out.println("Players names, stacks and seats:");
        for (Player player: players) {
            System.out.println(" - " + player.getNickname() + ", " + formatMoney(currency, player.getStartingStack()) + ", " + player.getSeat());
        }
    }

    private void printPlayersThatFoldedPreflop() {
        boolean firstOccurrence = true;
        for (Player player: players) {
            String streetFolded = player.getStreetFolded();
            if (streetFolded != null && streetFolded.equals("preflop")) {
                if (firstOccurrence) {
                    System.out.println("Players that folded preflop (if any)");
                    firstOccurrence = false;
                }
                System.out.println(" - " + player.getNickname());
            }
        }
    }

    private void printPlayerThatPlayedFlopTurnOrRiver() {
        String[] streets = {"flop", "turn", "river", "show down"};
        for (int i = 0; i <= Math.min(Arrays.asList(streets).indexOf(lastStreet), 2); i++) {
            String street = streets[i];
            boolean firstOccurrence = true;
            for (Player player: players) {
                String streetFolded = player.getStreetFolded();
                if (streetFolded == null || Arrays.asList(streets).indexOf(streetFolded) >= i) {
                    if (firstOccurrence) {
                        System.out.println("Players that played the " + street + " (if any)");
                        firstOccurrence = false;
                    }
                    System.out.println(" - " + player.getNickname());
                }
            }
        }
    }

    private void printPlayersThatReachTheShowdown() {
        boolean firstOccurrence = true;
        if (lastStreet.equals("show down")) {
            for (Player player: players) {
                if (player.getStreetFolded() == null) { // Player reach the showdown when streetFolded == null
                    if (firstOccurrence) {
                        System.out.println("Players that reach the showdown and theirs hole cards");
                        firstOccurrence = false;
                    }
                    String cards = player.getCards();
                    System.out.println(" - " + player.getNickname() + ": " + (cards != null ? cards : "mucks"));
                }
            }
        }
    }

    private void printStackBalanceOfEachPlayer() {
        System.out.println("The stack balance of each player after the hand in % (2 decimal places)");
        for (Player player: players) {
            float stackDiff = (100 * (player.getNewStack() - player.getStartingStack()) / player.getStartingStack());
            System.out.print(" - " + player.getNickname() + ": " + formatMoney(currency, player.getNewStack()));
            System.out.print(stackDiff > 0 ? " (+" : " (");
            System.out.println(String.format("%.2f", stackDiff) + "%)");
        }
    }

    private String formatMoney(String currency, float value) {
        return currency.concat(String.format("%.2f", value));
    }

    public String getHandId() {
        return handId;
    }

    public void setHandId(String handId) {
        this.handId = handId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getSmallBlindValue() {
        return smallBlindValue;
    }

    public void setSmallBlindValue(float smallBlindValue) {
        this.smallBlindValue = smallBlindValue;
    }

    public float getBigBlindValue() {
        return bigBlindValue;
    }

    public void setBigBlindValue(float bigBlindValue) {
        this.bigBlindValue = bigBlindValue;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public int getButtonPosition() {
        return buttonPosition;
    }

    public void setButtonPosition(int buttonPosition) {
        this.buttonPosition = buttonPosition;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public Player getPlayerByNickname(String nickname) {
        for (Player player: players) {
            if (nickname.equals(player.getNickname())) {
                return player;
            }
        }
        return null;
    }

    public void sumStackToPlayerByNickname(float sum, String nickname) {
        Player player = getPlayerByNickname(nickname);
        player.setNewStack(player.getNewStack() + sum);
    }

    public float getPot() {
        return pot;
    }

    public void setPot(float pot) {
        this.pot = pot;
    }

    public float getRake() {
        return rake;
    }

    public void setRake(float rake) {
        this.rake = rake;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getLastStreet() {
        return lastStreet;
    }

    public void setLastStreet(String lastStreet) {
        this.lastStreet = lastStreet;
    }
}
