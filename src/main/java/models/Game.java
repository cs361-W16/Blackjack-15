package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {
    /* Attributes */
    public java.util.List<Card> deck = new ArrayList<>();


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

    /* Shuffle the deck */
    public void shuffle() {
        long seed = System.nanoTime();
        Collections.shuffle(deck, new Random(seed));
    }
}