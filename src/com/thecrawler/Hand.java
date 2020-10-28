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
        System.out.println("The Blinds: " + currency + String.format("%.2f", smallBlindValue) + "/" + currency + String.format("%.2f", bigBlindValue));
        System.out.println("Date and Time: " + dateAndTime);
        System.out.println("Button position: " + buttonPosition);
        System.out.println("Players names, stacks and seats:");
        for (Player player: players) {
            System.out.println(" - " + player.getNickname() + ", " + currency + String.format("%.2f", player.getStartingStack()) + ", " + player.getSeat());
        }
        // Players that folded preflop (if any)
        boolean aux = true;
        for (Player player: players) {
            String streetFolded = player.getStreetFolded();
            if (streetFolded != null && streetFolded.equals("preflop")) {
                if (aux) {
                    System.out.println("Players that folded preflop (if any)");
                    aux = false;
                }
                System.out.println(" - " + player.getNickname());
            }
        }
        // Player that played the flop, turn and river (if any)
        String[] streets = {"flop", "turn", "river", "show down"};
        for (int i = 0; i <= Math.min(Arrays.asList(streets).indexOf(lastStreet), 2); i++) {
            String street = streets[i];
            aux = true;
            for (Player player: players) {
                String streetFolded = player.getStreetFolded();
                if (streetFolded == null || Arrays.asList(streets).indexOf(streetFolded) >= i) {
                    if (aux) {
                        System.out.println("Players that played the " + street + " (if any)");
                        aux = false;
                    }
                    System.out.println(" - " + player.getNickname());
                }
            }
        }
        System.out.println("Total pot and rake\n - " + currency + String.format("%.2f", pot) + ", " + currency + String.format("%.2f", rake));
        if (board != null) {
            System.out.println("The Board\n - " + board);
        }
        aux = true;
        // Players that reach the showdown and theirs hole cards
        if (lastStreet.equals("show down")) {
            for (Player player: players) {
                if (player.getStreetFolded() == null) {
                    if (aux) {
                        System.out.println("Players that reach the showdown and theirs hole cards");
                        aux = false;
                    }
                    String cards = player.getCards();
                    System.out.println(" - " + player.getNickname() + ": " + (cards != null ? cards : "mucks"));
                }
            }
        }
        System.out.println("The stack balance of each player after the hand in % (2 decimal places)");
        for (Player player: players) {
            float stackDiff = (100 * (player.getNewStack() - player.getStartingStack()) / player.getStartingStack());
            System.out.println(" - " + player.getNickname() + ": " + currency + String.format("%.2f", player.getNewStack()) + (stackDiff > 0 ? " (+" : " (") + String.format("%.2f", stackDiff) + "%)");
        }
        System.out.println();
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
