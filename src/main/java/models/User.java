package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class User {
	/* Attributes */
	private java.util.List<Card> hand = new ArrayList<>();
	private int money;


	/* Methods */

	public User() {

	}


	/* Returns the last card in the hand */
	public Card peekHand() {
		return hand.get(hand.size());
	}


	/* Adds a card to the hand */
	public void pushHand(Card new_card) {
		hand.add(new_card);
	}


	/* Returns number of cards in hand */
	public int getHandSize() {
		return hand.size();
	}



}