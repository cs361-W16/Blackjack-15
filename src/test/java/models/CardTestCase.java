package models;

import org.junit.Test;

import static org.junit.Assert.*;


public class CardTestCase {
    @Test
    public void testFetchSuit() {
        Card c = new Card(5,Suit.clubs);
        assertEquals(Suit.clubs,c.fetchSuit());
    }

    @Test
    public void testToString() {
        Card c = new Card(5, Suit.clubs);
        assertEquals("5clubs", c.toString());
    }

    @Test
    public void testFetchValue() {
        Card c = new Card(5, Suit.clubs);
        assertEquals(5, c.fetchValue());
    }
}
