/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;


import org.junit.Test;

import ninja.NinjaDocTester;
import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import ninja.Results;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import models.*;
import com.google.gson.Gson;

public class ApiControllerDocTesterTest extends NinjaDocTester {
    
    String URL_INDEX = "/";
    String URL_HELLO_WORLD_JSON = "/hello_world.json";
    String URL_BLACKJACK = "/blackjack";
    
    @Test
    public void testGetIndex() {
    
        Response response = makeRequest(
                Request.GET().url(
                        testServerUrl().path(URL_INDEX)));

        assertThat(response.payload, containsString("ng-app=\"Blackjack\""));


    }
    
    @Test
    public void testGetHelloWorldJson() {
    
        Response response = makeRequest(
                Request.GET().url(
                        testServerUrl().path(URL_HELLO_WORLD_JSON)));

        ApplicationController.SimplePojo simplePojo 
                = response.payloadJsonAs(ApplicationController.SimplePojo.class);
        
        assertThat(simplePojo.content, CoreMatchers.equalTo("Hello World! Hello Json!"));

    
    }


    /* Aces Up controller tests */


    @Test
    public void testNewGame() {
        Response response = makeRequest(
            Request
                .POST().url(testServerUrl().path("/new_game/" + Integer.toString(100))));

        // Parse JSON to Java object
        Game request_game = response.payloadJsonAs(Game.class);
        assertEquals(48, request_game.deck.size());
    }


    @Test
    public void testPlayerHitValid() {
        Game game = new Game();
        game.startGame(100);

        Response response = makeRequest(
            Request
                .POST().url(testServerUrl().path("/hit"))
                .contentTypeApplicationJson()
                .payload(game));

        // Parse JSON to Java object
        Game request_game = response.payloadJsonAs(Game.class);
        assertEquals(3, request_game.player.fetchHandSize());
    }


    @Test
    public void testPlayerHitInvalid() {
        Game game = new Game();
        game.startGame(100);

        // Give the user cards worth over 21
        game.player.pushHand(new Card(10, Suit.hearts, 10));
        game.player.pushHand(new Card(10, Suit.spades, 10));

        Response response = makeRequest(
            Request
                .POST().url(testServerUrl().path("/hit"))
                .contentTypeApplicationJson()
                .payload(game));

        // Parse JSON to Java object
        Game request_game = response.payloadJsonAs(Game.class);
        assertEquals(4, request_game.player.fetchHandSize());
    }


    @Test
    public void testDealerTurn() {
        Game game = new Game();
        game.startGame(100);

        int dealer_hand_value = game.dealer.fetchHandValue();

        Response response = makeRequest(
            Request
                .POST().url(testServerUrl().path("/dealer_turn"))
                .contentTypeApplicationJson()
                .payload(game));

        // Parse JSON to Java object
        Game request_game = response.payloadJsonAs(Game.class);
        
        // Check that dealer hit or stayed
        if (dealer_hand_value >= 17) {
            assertEquals(2, request_game.dealer.fetchHandSize());
        }
        else {
            assertThat(request_game.dealer.fetchHandSize(), not(2));
        }

    }


    @Test
    public void testDetermineWinner() {
        Game game = new Game();
        game.startGame(100);

        Response response = makeRequest(
            Request
                .POST().url(testServerUrl().path("/dealer_turn"))
                .contentTypeApplicationJson()
                .payload(game));

        // Parse JSON to Java object
        Game request_game = response.payloadJsonAs(Game.class);
      
        assertThat(request_game.round_winner, not(3));

    }


    @Test
    public void testRaiseBet() {
        Game game = new Game();
        game.startGame(100);

        Response response = makeRequest(
            Request
                .POST().url(testServerUrl().path("/raise/20"))
                .contentTypeApplicationJson()
                .payload(game));

        // Parse JSON to Java object
        Game request_game = response.payloadJsonAs(Game.class);
        int current_bet = request_game.fetchCurrentBet();
      
        assertEquals(current_bet, 22);
    }


    @Test 
    public void testDoubleDown() {
        Game game = new Game();
        game.startGame(100);

        Response response = makeRequest(
            Request
                .POST().url(testServerUrl().path("/double_down"))
                .contentTypeApplicationJson()
                .payload(game));

        Game request_game = response.payloadJsonAs(Game.class);

        assertEquals(4, request_game.fetchCurrentBet());
    }


    @Test 
    public void testSplitHand() {
        Game game = new Game();
        game.startGame(100);

        // Add matching value cards to the hand
        game.player.hand.add(0, new Card(1, Suit.spades, 1));
        game.player.hand.add(1, new Card(1, Suit.hearts, 1));

        Response response = makeRequest(
            Request
                .POST().url(testServerUrl().path("/split"))
                .contentTypeApplicationJson()
                .payload(game));

        Game request_game = response.payloadJsonAs(Game.class);

        assertEquals(true, request_game.split_hand);
    }


    

}
