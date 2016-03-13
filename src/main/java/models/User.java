package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.io.Serializable;

public abstract class User {
	/* Attributes */
	public java.util.List<Card> hand = new ArrayList<>();
	public int money;


	/* Methods */
	public abstract Card fetchCard(int i);
	public abstract Card peekHand();
	public abstract void pushHand(Card new_card);
	public abstract void removeHand(int i);
	public abstract int fetchHandSize();
	public abstract int fetchHandValue();
	public abstract void setMoney(int new_money);
	public abstract void addMoney(int addition);
	public abstract int subtractMoney(int subtraction);
	public abstract int fetchMoney();
}