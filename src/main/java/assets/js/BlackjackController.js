angular.module('Blackjack').controller('BlackjackController', function($scope, $http, $interval) {
    // Debugging
    window.$scope = $scope;

    $scope.invalidMove = false;
    $scope.userMessage = '';
    $scope.gameState = {};
    $scope.showLastCard = false;

    $scope.playerTotal = 0;
    $scope.playerSplitTotal = 0;

    /* --- On page load --- */

    // Get initial game state
    $http.post('/new_game/100').then(function(result) {
        $scope.gameState = result.data;
        $scope.playerTotal = calculateTotalValue($scope.gameState.player.hand);

        $scope.$watch('gameState.player.hand', function(newHand, oldHand){
            if(handsDiffer(newHand, oldHand)){
                $scope.playerTotal = calculateTotalValue(newHand);
            }
        });
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

    $scope.dealerTurn = function() {
        $http.post('/dealer_turn', $scope.gameState).then(function(result) {
            $scope.gameState = result.data;
            $scope.showLastCard = true;

            // Check if the player lost
            if ($scope.gameState.round_winner == 0) {
                alertUser("You lost - dealer has better hand!");
            } else if ($scope.gameState.round_winner == 1) {
                alertUser("You won!");
            } else if ($scope.gameState.round_winner == 2) {
                alertUser("You tied with the dealer! Not too late to back out!");
            }

            if($scope.gameState.player.money == 0){
                $scope.playerLost = true;
                $scope.$apply();
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
            $scope.playerSplitTotal = calculateTotalValue($scope.gameState.playerSplit.hand);

            $scope.$watch('gameState.playerSplit.hand', function(newHand, oldHand){
                if(handsDiffer(newHand, oldHand)){
                    $scope.playerSplitTotal = calculateTotalValue(newHand);
                }
            });
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

    /* for "New Game" button */
    $scope.newGame = function() {
        $http.post('/new_game/100').then(function(result) {
            $scope.gameState = result.data;
            clearMessage();
            $scope.playerLost = false;
            $scope.$apply();
            if ($scope.showLastCard == true) $scope.showLastCard = false;
        });
    };

    $scope.newRound = function(){
        $http.post('new_game/' + $scope.gameState.player.money).then(function(result) {
            $scope.gameState = result.data;
        });
        clearMessage();
        if ($scope.showLastCard == true) $scope.showLastCard = false;
    };

    $scope.raise = function() {
        $http.post('raise/1', $scope.gameState).then(function(result) {
            $scope.gameState = result.data;
        });
    };

    /* --- Helper functions --- */

    function alertUser(str){
        $scope.userMessage = str;
    }

    function clearMessage(){
        $scope.userMessage = '';
    }

    function calculateTotalValue(hand){
        var sum = 0;
        for(var i = 0; i < hand.length; i++){
            sum += hand[i].value;
        }

        return sum;
    }

    function handsDiffer(newHand, oldHand){
        if(newHand.length != oldHand.length){
            return true;
        }else{
            for(var i = 0; i < newHand.length; i++){
                if(newHand[i].suit != oldHand[i].suit && newHand[i].type != oldHand[i].type){
                    return true;
                }
            }
        }

        return false;
    }
});
