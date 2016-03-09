package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class User {
	/* Attributes */
	public java.util.List<Card> hand = new ArrayList<>();


	/* Methods */
	public User() {

	}


	public void addToHand(Card new_card) {
		hand.add(new_card);
	}



}