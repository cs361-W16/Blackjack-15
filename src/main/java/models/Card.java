package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class Card implements Serializable {
    public final int value;
    public final Suit suit;
    public final int type;

    @JsonCreator
    public Card(@JsonProperty("value") int value, @JsonProperty("suit") Suit suit, @JsonProperty("type") int type) {
        this.value = value;
        this.suit = suit;
        this.type=type;

    }


    public Suit fetchSuit() {
        return suit;
    }


    public int fetchValue() {
        return value;
    }


    public String toString() {
        return this.value + this.suit.toString();
    }
}
