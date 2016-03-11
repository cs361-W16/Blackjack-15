angular.module('AcesUp', ['ngMaterial', 'ngAnimate'])
    .config(function($mdThemingProvider){
        $mdThemingProvider.theme('default')
            .primaryPalette('indigo')
            .accentPalette('red');
    });