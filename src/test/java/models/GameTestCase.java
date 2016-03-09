package models;

import org.junit.Test;
import static org.junit.Assert.*;


public class GameTestCase {

    @Test
    public void testCreation() {
        Game g = new Game();
        assertNotNull(g);
    }

    @Test
    public void testBuildDeck() {
        Game g = new Game();
        g.buildDeck();

        assertEquals(52, g.deck.size());
    }

    @Test
    public void testHitPlayer() {
        Game g = new Game();
        g.buildDeck();

        g.hit(g.player);
        assertEquals(1, g.player.getHandSize());
    }

    @Test
    public void testHitDealer() {
        Game g = new Game();
        g.buildDeck();

        g.hit(g.dealer);
        assertEquals(1, g.dealer.getHandSize());
    }


    @Test
    public void testShuffle() {
        Game unshuffled = new Game();
        unshuffled.buildDeck();

        Game shuffled = new Game();
        shuffled.buildDeck();
        shuffled.shuffle();

        // Compare each card in both decks
        int matches = 0;
        int size = unshuffled.deck.size();
        for(int i = 0; i < size; i++){
            Card unshuffledCard = unshuffled.deck.get(i);
            Card shuffledCard = shuffled.deck.get(i);

            Suit unshuffledSuit = unshuffledCard.getSuit();
            int unshuffledVal = unshuffledCard.getValue();
            Suit shuffledSuit = shuffledCard.getSuit();
            int shuffledVal = shuffledCard.getValue();

            if(unshuffledSuit == shuffledSuit && unshuffledVal == shuffledVal) {
                matches++;
            }
        }

        // Ensure that shuffled deck is at least 85% unique
        assertTrue((matches / size) <= 0.15);
    }


    @Test
    public void testStartGame() {
        int start_chips = 100;
        Game g = new Game();
        
        g.startGame(start_chips);
        assertEquals(2, g.player.getHandSize());
        assertEquals(98, g.player.getMoney());
    }


    @Test
    public void testRaiseBetInvalid() {
        int start_chips = 10;
        Game g = new Game();
        g.startGame(start_chips);

        assertEquals(0, g.raiseBet(20));
    }


    @Test
    public void testRaiseBetValid() {
        int start_chips = 100;
        Game g = new Game();
        g.startGame(start_chips);

        g.raiseBet(20);
        assertEquals(22, g.getCurrentBet());
    }


    @Test
    public void testDealerRules() {
        int dealer_limit = 17;
        int start_chips = 100;
        Game g = new Game();
        g.startGame(start_chips);

        /* Check that the dealer only hits if below 17 */
        int dealer_hand_size = g.dealer.getHandSize();
        g.dealerTurn();

        // Dealer holds
        if (dealer_hand_size >= dealer_limit) {
            assertTrue(g.dealer.getHandSize() == dealer_hand_size);
        }
        // Dealer hits
        else {
            assertTrue(g.dealer.getHandSize() > dealer_hand_size);
        }
    }


    @Test
    public void testDetermineWinnerPlayer() {
        int dealer_limit = 17;
        int start_chips = 100;
        Game g = new Game();
        g.startGame(start_chips);

        /* Set player's hand to be greater than dealer */
        g.player.pushHand(new Card(10, Suit.clubs));
        g.player.pushHand(new Card(10, Suit.spades));

        g.dealer.pushHand(new Card(10, Suit.hearts));
        g.dealer.pushHand(new Card(7, Suit.hearts));

        assertTrue(1, g.determineWinner());
    }

    

}