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

import ninja.Result;
import ninja.Results;
import ninja.Context;
import models.Game;
import com.google.inject.Singleton;
import ninja.params.PathParam;


@Singleton
public class ApplicationController {

    public Result index() {

        return Results.html();

    }
    
    public Result helloWorldJson() {
        
        SimplePojo simplePojo = new SimplePojo();
        simplePojo.content = "Hello World! Hello Json!";

        return Results.json().render(simplePojo);

    }
    
    public static class SimplePojo {

        public String content;
        
    }

    /* Blackjack controllers */


    /* Get new game */
    public Result newGame(Context context, @PathParam("start_chips") int start_chips) {
        // int start_chips = 100;
        Game game = new Game();
        game.startGame(start_chips);

        return Results.json().render(game);
    }

    /* Give the player another card if hand value is under 21 */
    public Result hit(Context context, Game current_game) {
        if (current_game.player.fetchHandValue() < 21) {
            current_game.hit(current_game.player);

            // Check if player busted 
            current_game.checkPlayerBust();
        }

        return Results.json().render(current_game);
    }


    /* Dealer turn logic */
    public Result dealerTurn(Context context, Game current_game) {
        current_game.dealerTurn();
        current_game.determineWinner();

        return Results.json().render(current_game);
    }


    /* Raise the current bet */
    public Result raiseBet(Context context, Game current_game, @PathParam("bet") int bet) {
        current_game.raiseBet(bet);

        return Results.json().render(current_game);

    }


    /* Double down */
    public Result doubleDown(Context context, Game current_game) {
        current_game.doubleDown();

        return Results.json().render(current_game);

    }


    /* Split hand */
    public Result splitHand(Context context, Game current_game) {
        current_game.split();

        return Results.json().render(current_game);

    }
}
