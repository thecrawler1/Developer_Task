package com.thecrawler;

public class Player {

    private final int seat;
    private final String nickname;
    private final float startingStack;
    private float newStack;
    private String streetFolded; // Street that player folded (null if not folded)
    private String cards;

    public Player(int seat, String nickname, float startingStack) {
        this.seat = seat;
        this.nickname = nickname;
        this.startingStack = startingStack;
        this.newStack = startingStack;
    }

    public int getSeat() {
        return seat;
    }

    public String getNickname() {
        return nickname;
    }

    public float getStartingStack() {
        return startingStack;
    }

    public float getNewStack() {
        return newStack;
    }

    public void setNewStack(float newStack) {
        this.newStack = newStack;
    }

    public String getStreetFolded() {
        return streetFolded;
    }

    public void setStreetFolded(String streetFolded) {
        this.streetFolded = streetFolded;
    }

    public String getCards() {
        return cards;
    }

    public void setCards(String cards) {
        this.cards = cards;
    }
}
