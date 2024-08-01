package org.example;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Card> hand;
    private int balance;

    public Player(int initialBalance) {
        hand = new ArrayList<>();
        balance = initialBalance;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public int getHandValue() {
        int total = 0;
        int numAces = 0;

        for (Card card : hand) {
            total += card.getValue();
            if (card.getRank().equals("A")) {
                numAces++;
            }
        }

        // Adjust for Aces if total > 21
        while (total > 21 && numAces > 0) {
            total -= 10;
            numAces--;
        }

        return total;
    }

    public void clearHand() {
        hand.clear();
    }

    public int getBalance() {
        return balance;
    }

    public void adjustBalance(int amount) {
        balance += amount;
    }

    public List<Card> getHand() {
        return hand;
    }
}
