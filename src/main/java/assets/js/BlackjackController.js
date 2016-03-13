angular.module('Blackjack').controller('BlackjackController', function($scope, $http, $interval) {
    // Debugging
    window.$scope = $scope;

    $scope.invalidMove = false;
    $scope.userMessage = '';
    $scope.gameState = {};

    /* --- On page load --- */

    // Get initial game state
    $http.post('/new_game/100').then(function(result) {
        $scope.gameState = result.data;
    });

    /* --- Actions --- */

    $scope.hit = function() {
        $http.post('/hit', $scope.gameState).then(function(result) {
            $scope.gameState = result.data;

            // Check if player lost from bust
            if ($scope.gameState.round_winner == 0) {
                // Tell the player they lost
                alertUser("You lost - because you bust!");
            }
        });
    };

   $scope.hitSplit = function() {
        $http.post('/hit_split', $scope.gameState).then(function(result) {
            $scope.gameState = result.data;

            // Check if player lost from bust
            if ($scope.gameState.round_winner == 0) {
                // Tell the player they lost
                alertUser("You lost - because you bust!");
            }
        });
    };

    $scope.dealerTurn = function() {
        $http.post('/dealer_turn', $scope.gameState).then(function(result) {
            $scope.gameState = result.data;

            // Check if the player lost
            if ($scope.gameState.round_winner == 0) {
                alertUser("You lost - dealer has better hand!");
            } else if ($scope.gameState.round_winner == 1) {
                alertUser("You won!");
            } else if ($scope.gameState.round_winner == 2) {
                alertUser("You tied with the dealer! Not too late to back out!");
            }
        });
    };

    $scope.doubleDown = function() {
        $http.post('/double_down', $scope.gameState).then(function(result) {
            $scope.gameState = result.data;
        });
    };

    $scope.split = function() {
        $http.post('/split', $scope.gameState).then(function(result) {
            $scope.gameState = result.data;
        });
    };

    /* for "New Game" button */
    $scope.newGame = function() {
        $http.post('/new_game/100').then(function(result) {
            $scope.gameState = result.data;
            clearMessage();
        });
    };

    $scope.newRound = function(){
        $http.post('new_game/' + $scope.gameState.player.money).then(function(result) {
            $scope.gameState = result.data;
        });
        clearMessage();
    };

    $scope.raise = function() {
        $http.post('raise/1', $scope.gameState).then(function(result) {
            $scope.gameState = result.data;
        });
    }

    /* --- Helper functions --- */
    function alertUser(str){
        $scope.userMessage = str;
    }

    function clearMessage(){
        $scope.userMessage = '';
    }
});
