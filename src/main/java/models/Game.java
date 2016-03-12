package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.io.Serializable;

public class Game implements Serializable {
    /* Attributes */
    public java.util.List<Card> deck = new ArrayList<>();
    public User player;
    public User dealer;
    public User playerSplit;
    public int current_bet;
    public int round_winner = 3;
    public int bet_factor = 2;
    public boolean split_hand = false;



    /* Default constructor deals to only one player */
    public Game() {
        player = new User();
        dealer = new User();
        playerSplit = new User();
    }


    /* Build a standard deck */
    public void buildDeck() {
        /* Add 14 cards per suit */
        int j;
        for(int i = 2; i < 15; i++){
            if (i > 10) {
                j = 10;
            }
            else if (i == 14) {
                j = 11;
            }
            else {
                j = i;
            }
            deck.add(new Card(j, Suit.clubs, i));
            deck.add(new Card(j, Suit.hearts, i));
            deck.add(new Card(j, Suit.diamonds, i));
            deck.add(new Card(j, Suit.spades, i));

        }
        shuffle();
    }


    /* Deal a random card to the either the user or dealer */
    public void hit(User user) {
        // Pull from deck
        Card new_card = deck.get(deck.size() - 1);
        deck.remove(deck.size() - 1);

        // Give to user
        user.pushHand(new_card);
    }


    /* Shuffle the deck */
    public void shuffle() {
        long seed = System.nanoTime();
        Collections.shuffle(deck, new Random(seed));
    }


    /* Start a new game by dealing two cards to each player */
    public void startGame(int start_chips) {
        int starting_bet = 2;

        // Initialize deck
        buildDeck();

        /* Give player 2 cards to start */
        hit(player);
        hit(player);
        
        /* Give the dealer 2 cards to start */
        hit(dealer);
        hit(dealer);

        // Give player starting chips
        player.setMoney(start_chips);

        /* Set the starting bet */
        raiseBet(starting_bet);
    }
    

    /* Return the current bet */
    public int fetchCurrentBet() {
        return current_bet;
    }


    /* Raise the current bet by the given value. Returns 1 if user has enough money, 0 otherwise. */
    public int raiseBet(int raise) {
        if (player.subtractMoney(raise) == 1) {
            current_bet = current_bet + raise;

            return 1;
        }
        else {
            return 0;
        }
    }


    /* Handles the dealer hit/stay logic */
    public void dealerTurn() {
        int dealer_limit = 17;
        int dealer_hand_value = dealer.fetchHandValue();

        while (dealer_hand_value <= dealer_limit) {
            hit(dealer);
            dealer_hand_value = dealer.fetchHandValue();
        }
    }


    /* Determines the winner. Returns 1 if player, 0 if dealer, 2 if tie. */
    public int determineWinner() {
        int player_hand_value = player.fetchHandValue();
        int dealer_hand_value = dealer.fetchHandValue();
        int playerSplit_hand_value;
        if (split_hand) {
            playerSplit_hand_value = playerSplit.fetchHandValue();
        }
        else {
            playerSplit_hand_value = 0;
        }

        /* Player wins */
        if (dealer_hand_value > 21 || player_hand_value > dealer_hand_value || playerSplit_hand_value > dealer_hand_value) {
            round_winner = 1;
            player.addMoney(current_bet * bet_factor);
            return 1;
        }
        /* Dealer wins */
        else if (player_hand_value < dealer_hand_value && playerSplit_hand_value < dealer_hand_value) {
            round_winner = 0;
            return 0;
        }
        /* Tie */
        else {
            round_winner = 2;
            return 2;
        }
    }


    /* Splits the players hand. If both cards are equal in value, takes the second one from the player to make a new hand and then gives the old hand and new hand another card */
    public void split() {
        if ( player.fetchCard(0).type == player.fetchCard(1).type ) {
            playerSplit.pushHand( player.fetchCard(1) );
            player.removeHand(1);
            hit(player);
            hit(playerSplit);
            split_hand = true;
        }
    }


    public int doubleDown( ) {
        if (player.subtractMoney(current_bet) == 1) {
            current_bet = current_bet * 2;

            return 1;
        }
        else {
            return 0;
        }
    }

}