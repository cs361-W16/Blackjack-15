package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {
    /* Attributes */
    public java.util.List<Card> deck = new ArrayList<>();
    public User player = new User();
    public User dealer = new User();
    public int current_bet;


    /* Default constructor deals to only one player */
    public Game() {

    }


    /* Build a standard deck */
    public void buildDeck() {
        /* Add 14 cards per suit */
        for(int i = 2; i < 15; i++){
            deck.add(new Card(i,Suit.clubs));
            deck.add(new Card(i,Suit.hearts));
            deck.add(new Card(i,Suit.diamonds));
            deck.add(new Card(i,Suit.spades));
        }
    }


    /* Deal a random card to the either the user or dealer */
    public void hit(User user) {
        // Pull from deck
        Card new_card = deck.get(deck.size() - 1);
        deck.remove(deck.size() - 1);

        // Give to user
        user.addToHand(new_card);
    }


    /* Shuffle the deck */
    public void shuffle() {
        long seed = System.nanoTime();
        Collections.shuffle(deck, new Random(seed));
    }


    /* Start a new game by dealing two cards to each player */
    public void startGame(int start_chips) {
        // Initialize deck
        buildDeck();

        /* Give player 2 cards to start */
        hit(player);
        hit(player);
        
        /* Give the dealer 2 cards to start */
        hit(dealer);
        hit(dealer);
    }
}