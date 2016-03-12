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
        assertEquals(1, g.player.fetchHandSize());
    }

    @Test
    public void testHitDealer() {
        Game g = new Game();
        g.buildDeck();

        g.hit(g.dealer);
        assertEquals(1, g.dealer.fetchHandSize());
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

            Suit unshuffledSuit = unshuffledCard.fetchSuit();
            int unshuffledType = unshuffledCard.fetchType();
            Suit shuffledSuit = shuffledCard.fetchSuit();
            int shuffledType = shuffledCard.fetchType();

            if(unshuffledSuit == shuffledSuit && unshuffledType == shuffledType) {
                matches++;
            }
        }

        Card card1 = shuffled.deck.get(0);
        Card card2 = shuffled.deck.get(1);

        System.out.print(card1.fetchValue());
        System.out.print(card2.fetchValue());

        assertTrue(card1.fetchValue() != card2.fetchValue());

        // Ensure that shuffled deck is at least 85% unique
        assertTrue((matches / size) <= 0.15);
    }


    @Test
    public void testStartGame() {
        int start_chips = 100;
        Game g = new Game();
        
        g.startGame(start_chips);
        assertEquals(2, g.player.fetchHandSize());
        assertEquals(98, g.player.fetchMoney());
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
        assertEquals(22, g.fetchCurrentBet());
    }


    @Test
    public void testDealerRules() {
        int dealer_limit = 17;
        int start_chips = 100;
        Game g = new Game();
        g.startGame(start_chips);

        /* Check that the dealer only hits if below 17 */
        int dealer_hand_value = g.dealer.fetchHandValue();
        g.dealerTurn();

        // Dealer stays
        if (dealer_hand_value >= dealer_limit) {
            assertTrue(g.dealer.fetchHandValue() == dealer_hand_value);
        }
        // Dealer hits
        else {
            assertTrue(g.dealer.fetchHandValue() > dealer_hand_value);
        }
    }


    @Test
    public void testDetermineWinnerDealerBust() {
        int dealer_limit = 17;
        int start_chips = 100;
        Game g = new Game();

        // Give the user a card
        g.player.pushHand(new Card(2, Suit.spades, 2));

        // Give the dealer over 21 
        g.dealer.pushHand(new Card(10, Suit.spades, 10));
        g.dealer.pushHand(new Card(10, Suit.hearts, 10));
        g.dealer.pushHand(new Card(2, Suit.hearts, 2));

        assertEquals(1, g.determineWinner());
    }


    @Test
    public void testDetermineWinnerPlayer() {
        int dealer_limit = 17;
        int start_chips = 100;
        Game g = new Game();

        /* Set player's hand to be greater than dealer */
        g.player.pushHand(new Card(10, Suit.clubs, 10));
        g.player.pushHand(new Card(10, Suit.spades, 10));

        g.dealer.pushHand(new Card(10, Suit.hearts, 10));
        g.dealer.pushHand(new Card(7, Suit.hearts, 10));

        assertEquals(1, g.determineWinner());
    }


    @Test
    public void testDetermineWinnerPlayerMoney() {
        int dealer_limit = 17;
        int start_chips = 100;
        Game g = new Game();
        g.player.setMoney(100);
        g.raiseBet(20);

        /* Set player's hand to be greater than dealer */
        g.player.pushHand(new Card(10, Suit.clubs, 10));
        g.player.pushHand(new Card(10, Suit.spades, 10));

        g.dealer.pushHand(new Card(10, Suit.hearts, 10));
        g.dealer.pushHand(new Card(7, Suit.hearts, 7));

        /* Determine that player's money is increased by correct amount */
        g.determineWinner();
        assertEquals(120, g.player.fetchMoney());
    }


    @Test 
    public void testDetermineWinnerDealer() {
        int dealer_limit = 17;
        int start_chips = 100;
        Game g = new Game();

        /* Set player's hand to be greater than dealer */
        g.player.pushHand(new Card(2, Suit.clubs, 2));
        g.player.pushHand(new Card(3, Suit.spades, 3));

        g.dealer.pushHand(new Card(10, Suit.hearts, 10));
        g.dealer.pushHand(new Card(7, Suit.hearts, 7));

        assertEquals(0, g.determineWinner());
    }


    @Test 
    public void testDetermineWinnerTie() {
        int dealer_limit = 17;
        int start_chips = 100;
        Game g = new Game();

        /* Set player's hand to be greater than dealer */
        g.player.pushHand(new Card(10, Suit.clubs, 10));
        g.player.pushHand(new Card(10, Suit.spades, 10));

        g.dealer.pushHand(new Card(10, Suit.hearts, 10));
        g.dealer.pushHand(new Card(10, Suit.diamonds, 10));

        assertEquals(2, g.determineWinner());
    }

    @Test
    public void testSplitHand() {
        Game g = new Game();
        g.buildDeck();
        g.shuffle();

        g.player.pushHand(new Card(10, Suit.clubs, 10));
        g.player.pushHand(new Card(10, Suit.diamonds, 10));

        g.split();

        assertEquals( g.player.fetchCard(0).value, g.playerSplit.fetchCard(0).value );
    }

    @Test
    public void testDoubleDown() {
        int start_chips = 100;
        Game g = new Game();
        g.startGame(start_chips);

        g.doubleDown();
        assertEquals(4, g.fetchCurrentBet() );
    }




}