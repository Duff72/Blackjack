package org.example;

import java.util.Scanner;

public class GameRunner {
    public static void main(String[] args) {
        // Play some music
        String filepath = "CasinoJazz.wav";
        PlayMusic music = new PlayMusic();
        music.playMusic(filepath);

        Scanner sc = new Scanner(System.in);
        final int MIN_BET = 5; // Minimum increment for the bet

        // Initialize the player's balance at zero
        int playerBalance = 0;

        // Initial player balance setup
        System.out.println("Welcome to Blackjack!");



        while (true) {
            // Initialize the game
            Deck deck = new Deck();
            deck.shuffle();

            Player player = new Player(playerBalance); // Player's starting balance
            Player dealer = new Player(0); // Dealer doesn’t have a balance

            // Set the bet amount
            int betAmount = 0;
            boolean validBet = false;
            while (!validBet) {
                System.out.println("Your current balance: $" + player.getBalance());
                System.out.println("Enter your bet amount (must be in increments of $" + MIN_BET + "):");
                if (sc.hasNextInt()) {
                    betAmount = sc.nextInt();
                    sc.nextLine(); // Consume newline

                    if (betAmount >= MIN_BET && betAmount % MIN_BET == 0) {
                        validBet = true;
                        player.adjustBalance(-betAmount); // Deduct bet amount from player's balance
                    } else {
                        System.out.println("Invalid bet amount. Please enter a valid amount.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.nextLine(); // Consume invalid input
                }
            }

            // Deal initial hands
            player.addCard(deck.deal());
            dealer.addCard(deck.deal());
            player.addCard(deck.deal());
            dealer.addCard(deck.deal());

            // Display player's hand and dealer's face-up card
            System.out.println("Your hand: " + player.getHand());
            System.out.println("Your hand value: " + player.getHandValue());

            // Show dealer's face-up card
            Card dealerFaceUpCard = dealer.getHand().get(0);
            System.out.println("Dealer's face-up card: " + dealerFaceUpCard);

            // Player's turn
            while (player.getHandValue() < 21) {
                System.out.println("Do you want to (H)it or (S)tand?");
                String choice = sc.nextLine();

                if (choice.equalsIgnoreCase("H")) {
                    player.addCard(deck.deal());
                    System.out.println("You drew: " + player.getHand().get(player.getHand().size() - 1));
                    System.out.println("Your hand value: " + player.getHandValue());

                    if (player.getHandValue() > 21) {
                        System.out.println("Busted! Your hand value is over 21.");
                        break;
                    }
                } else if (choice.equalsIgnoreCase("S")) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter 'H' to hit or 'S' to stand.");
                }
            }

            // Dealer's turn if the player didn't bust
            if (player.getHandValue() <= 21) {
                // Reveal the dealer's second card
                System.out.println("Dealer's full hand: " + dealer.getHand());
                System.out.println("Dealer's hand value: " + dealer.getHandValue());

                while (dealer.getHandValue() < 17) {
                    dealer.addCard(deck.deal());
                    System.out.println("Dealer drew: " + dealer.getHand().get(dealer.getHand().size() - 1));
                    System.out.println("Dealer's hand value: " + dealer.getHandValue());
                }

                // Determine the winner
                int playerValue = player.getHandValue();
                int dealerValue = dealer.getHandValue();

                if (playerValue > 21) {
                    System.out.println("Dealer wins!");
                } else if (dealerValue > 21 || playerValue > dealerValue) {
                    System.out.println("You win!");
                    player.adjustBalance(betAmount * 2); // Player wins and gets double the bet amount
                } else if (playerValue < dealerValue) {
                    System.out.println("Dealer wins!");
                } else {
                    System.out.println("It's a tie!");
                    player.adjustBalance(betAmount); // Return the bet amount in case of a tie
                }
            }

            // Update the player's balance
            playerBalance = player.getBalance();
            System.out.println("Your new balance: $" + playerBalance);

            // Continue playing even if the balance is zero or negative
            System.out.println("Continuing the game with a balance of $" + playerBalance);

        }}}