angular.module('Blackjack', ['ngMaterial', 'ngAnimate'])
    .config(function($mdThemingProvider){
        var greenMap = $mdThemingProvider.extendPalette('green', {
            '100': '1abc9c',
            '300': '1abc9c',
            '500': '1abc9c',
            '700': '1abc9c',
            '900': '1abc9c'
        });

        var darkGreenMap = $mdThemingProvider.extendPalette('green', {
            '100': '16a085',
            '300': '16a085',
            '500': '16a085',
            '700': '16a085',
            '900': '16a085'
        });

        $mdThemingProvider.definePalette('greenMap', greenMap);
        $mdThemingProvider.definePalette('darkGreenMap', darkGreenMap);

        $mdThemingProvider.theme('default')
            .primaryPalette('greenMap')
            .accentPalette('darkGreenMap');
    });
