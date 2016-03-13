package models;

import org.junit.Test;
import static org.junit.Assert.*;


public class DealerTestCase {

    /* Dealer tests */
    @Test
    public void testPushHand() {
        Card card = new Card(3, Suit.clubs, 3);
        Dealer user = new Dealer();

        user.pushHand(card);
        assertEquals(card, user.peekHand());
        assertEquals(1, user.fetchHandSize());
    }


    @Test
    public void testAddMoney() {
        Dealer user = new Dealer();

        user.setMoney(0);
        user.addMoney(20);

        assertEquals(20, user.fetchMoney());
    }


    @Test
    public void testSubtractInvalid() {
        Dealer user = new Dealer();

        user.setMoney(20);
        assertEquals(0, user.subtractMoney(30));
    }


    @Test
    public void testSubtractValid() {
        Dealer user = new Dealer();

        user.setMoney(20);
        assertEquals(1, user.subtractMoney(10));
    }


    @Test
    public void testFetchHandValue() {
        Dealer user = new Dealer();
        user.pushHand(new Card(3, Suit.spades, 3));
        user.pushHand(new Card(4, Suit.hearts, 4));

        assertEquals(7, user.fetchHandValue());
    }

}