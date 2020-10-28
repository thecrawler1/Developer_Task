package com.thecrawler;

public class Player {
    private int seat;
    private String nickname;
    private final float startingStack;
    private float newStack;
    private String streetFolded; // Street that player folded (null if not folded)
    private String cards;

    public Player(int seat, String nickname, float startingStack) {
        this.seat = seat;
        this.nickname = nickname;
        this.startingStack = startingStack;
        this.newStack = startingStack;
        this.streetFolded = null;
        this.cards = null;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
