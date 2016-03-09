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
    public void testHit() {
        Game g = new Game();
        g.buildDeck();

        g.hit(g.players.get(0));
        assertEquals(1, g.players.get(0).hand.size());

    }

    @Test
    public void testNewRound() {
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

            if(unshuffledSuit == shuffledSuit && unshuffledVal == shuffledVal){
                matches++;
            }
        }

        // Ensure that shuffled deck is at least 85% unique
        assertTrue((matches / size) <= 0.15);
    }

    @Test
    public void testStartGame() {
        int number_of_players = 1;
        int start_chips = 100;
        Game g = new Game();
        g.

        g.startGame(number_of_players, start_chips);
        assertEquals(1, g.players.size());
        // assertEquals(2, g.players[0].hand.size());

    }

    

}