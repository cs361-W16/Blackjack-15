angular.module('AcesUp').controller('AcesUpController', function($scope, $http, $interval){
    // Debugging
    window.$scope = $scope;

    $scope.gameState = {};

    /* --- On page load --- */

    // Get initial game state
    $http.get('').then(function(result){

    });

    /* --- Actions --- */

    //
});