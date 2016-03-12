angular.module('Blackjack', ['ngMaterial', 'ngAnimate'])
    .config(function($mdThemingProvider){
        $mdThemingProvider.theme('default')
            .primaryPalette('green')
            .accentPalette('red');
    });