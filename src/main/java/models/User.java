package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.io.Serializable;

public class User implements Serializable {
	/* Attributes */
	private java.util.List<Card> hand = new ArrayList<>();
	private int money;


	/* Methods */

	public User() {

	}


	/* Returns the last card in the hand */
	public Card peekHand() {
		return hand.get(hand.size() - 1);
	}


	/* Adds a card to the hand */
	public void pushHand(Card new_card) {
		hand.add(new_card);
	}


	/* Returns number of cards in hand */
	public int fetchHandSize() {
		return hand.size();
	}


	/* Returns the total value of all cards in hand */
	public int fetchHandValue() {
		int hand_value = 0;

		// Iterate through all the cards and add to sum */
		for (int i = 0; i < hand.size(); i++) {
			hand_value = hand_value + hand.get(i).fetchValue();
		}
		return hand_value;
	}


	/* Sets the user's money to given parameter value */
	public void setMoney(int new_money) {
		money = new_money;
	}


	/* Adds given value to money */
	public void addMoney(int addition) {
		money = money + addition;
	}


	/* Subtracts given value from money. Returns 1 if user has enough to subtract, 0 otherwise. */
	public int subtractMoney(int subtraction) {
		if (money >= subtraction) {
			money = money - subtraction;
			return 1;
		}
		else {
			return 0;
		}
	}


	/* Returns user's money */
	public int fetchMoney() {
		return money;
	}

}