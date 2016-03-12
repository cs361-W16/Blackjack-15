angular.module('Blackjack').controller('BlackjackController', function($scope, $http, $interval){
    // Debugging
    window.$scope = $scope;

    $scope.gameState = {};

    /* --- On page load --- */

    // Get initial game state
    $http.get('/new_game').then(function(result){
        $scope.gameState = result.data;
    });

    /* --- Actions --- */

    //
});