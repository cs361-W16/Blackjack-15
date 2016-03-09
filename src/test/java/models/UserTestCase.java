package models;

import org.junit.Test;
import static org.junit.Assert.*;


public class UserTestCase {

    @Test
    public void testAddToHand() {
        Card card = new Card(3, Suit.clubs);
        User user = new User();

        user.pushHand(card);
        assertEquals(card, user.peekHand());
    }
}