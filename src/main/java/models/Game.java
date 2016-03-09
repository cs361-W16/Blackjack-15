package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {
    /* Attributes */
    public java.util.List<Card> deck = new ArrayList<>();
    public User player = new User();
    public User dealer = new User();
    private int current_bet;


    /* Default constructor deals to only one player */
    public Game() {

    }


    /* Build a standard deck */
    public void buildDeck() {
        /* Add 14 cards per suit */
        for(int i = 2; i < 15; i++){
            deck.add(new Card(i, Suit.clubs));
            deck.add(new Card(i, Suit.hearts));
            deck.add(new Card(i, Suit.diamonds));
            deck.add(new Card(i, Suit.spades));
        }
    }


    /* Deal a random card to the either the user or dealer */
    public void hit(User user) {
        // Pull from deck
        Card new_card = deck.get(deck.size() - 1);
        deck.remove(deck.size() - 1);

        // Give to user
        user.pushHand(new_card);
    }


    /* Shuffle the deck */
    public void shuffle() {
        long seed = System.nanoTime();
        Collections.shuffle(deck, new Random(seed));
    }


    /* Start a new game by dealing two cards to each player */
    public void startGame(int start_chips) {
        int starting_bet = 2;

        // Initialize deck
        buildDeck();

        /* Give player 2 cards to start */
        hit(player);
        hit(player);
        
        /* Give the dealer 2 cards to start */
        hit(dealer);
        hit(dealer);

        // Give player starting chips
        player.setMoney(start_chips);

        /* Set the starting bet */
        player.subtractMoney(2);
        current_bet = 2;
    }
    

    /* Return the current bet */
    public int getCurrentBet() {
        return current_bet;
    }


    /* Raise the current bet by the given value. Returns 1 if user has enough money, 0 otherwise. */
    public int raiseBet(int raise) {
        if (player.subtractMoney(raise) == 1) {
            current_bet = current_bet + raise;

            return 1;
        }
        else {
            return 0;
        }
    }


    /* Handles the dealer hit/hold logic */
    public void dealerTurn() {
        int dealer_limit = 17;
        int dealer_hand_value = dealer.getHandValue();

        while (dealer_hand_value <= dealer_limit) {
            hit(dealer);
            dealer_hand_value = dealer.getHandValue();

            // Debugging
            System.out.print("Dealer hand: " + dealer_hand_value);
        }
    }


    /* Determines the winner. Returns 1 if player, 0 if dealer, 2 if tie. */
    public int determineWinner() {
        int player_hand_value = player.getHandValue();
        int dealer_hand_value = dealer.getHandValue();

        if (player_hand_value > dealer_hand_value) {
            return 1;
        }
        else if (player_hand_value < dealer_hand_value) {
            return 0;
        }
        else {
            return 2;
        }
    }
}