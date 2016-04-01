var as = angular.module('iohm.controllers', []);

as.controller('MainController', [ '$q', '$scope', '$rootScope', '$http', 'i18n', '$location', '$uibModal', 
                                  function ($q, $scope, $rootScope, $http, i18n, $location, $uibModal) {
    var load = function () {
    	/*
    	$timeout(function() {
    		  $scope.message().show = false;
    		}, 20); // X in milliseconds
    		*/
    };

    load();

    $scope.language = function () {
        return i18n.language;
    };
    $scope.setLanguage = function (lang) {
        i18n.setLanguage(lang);
    };
    $scope.activeWhen = function (value) {
        return value ? 'active' : '';
    };

    $scope.path = function () {
        return $location.url();
    };

    $scope.logout = function () {
        $rootScope.user = null;
        $scope.username = $scope.password = null;
        $scope.$emit('event:logoutRequest');
        $location.url('/login');
    };
    
    
}]);