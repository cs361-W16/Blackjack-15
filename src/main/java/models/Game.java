package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {
    /* Attributes */
    public java.util.List<Card> deck = new ArrayList<>();
    public java.util.List<User> players = new ArrayList<>();
    public User dealer;


    /* Default constructor deals to only one player */
    public Game() {
        dealer = new User(0);
        players.add(new User(0));
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

    /* Deal a random card to the provided user */
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
    public void startGame(int number_of_players, int start_chips) {
        // Initialize number of players (not including dealer)
        for (int i = 0; i < number_of_players; i++) {
            players.add(new User(i));
        }

        /* Give each player 2 cards to start */
        for (int i = 0; i < number_of_players; i++) {
            hit(players.get(i));
            hit(players.get(i));
        }

        // Give the dealer 2 cards to start
        hit(dealer);
        hit(dealer);
    }
}