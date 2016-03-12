angular.module('Blackjack').controller('BlackjackController', function($scope, $http, $interval){
    // Debugging
    window.$scope = $scope;

    $scope.gameState = {};

    /* --- On page load --- */

    // Get initial game state
    $http.post('/new_game/100').then(function(result){
        $scope.gameState = result.data;
    });

    /* --- Actions --- */

    $scope.hit = function(){
            $http.post('/hit', $scope.gameState).then(function(result){
                $scope.gameState = result.data;
            });
        };

    $scope.split = function(){
            $http.post('/dealer_turn', $scope.gameState).then(function(result){
                $scope.gameState = result.data;
            });
        };

    $scope.doubleDown = function(){
            $http.post('/double_down', $scope.gameState).then(function(result){
                $scope.gameState = result.data;
            });
        };

    $scope.split = function(){
            $http.post('/split', $scope.gameState).then(function(result){
                $scope.gameState = result.data;
            });
        };

    $scope.newGame = function(){
            $http.post('/new_game/{start_chips}', $scope.gameState).then(function(result){
                $scope.gameState = result.data;
            });
        };

    /* for "New Game" button */
    $scope.newGame = function() {
            $http.post('/new_game/100').then(function(result){
               $scope.gameState = result.data;
           });
        }

});