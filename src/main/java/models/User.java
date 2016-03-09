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
		return hand.get(hand.size() - 1);
	}


	/* Adds a card to the hand */
	public void pushHand(Card new_card) {
		hand.add(new_card);
	}


	/* Returns number of cards in hand */
	public int getHandSize() {
		return hand.size();
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
	public int getMoney() {
		return money;
	}

}