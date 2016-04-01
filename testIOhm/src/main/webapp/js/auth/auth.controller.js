angular.module('iohm.auth').controller('LoginController',[ '$scope', '$rootScope', '$location', '$timeout','authService', '$uibModal',  
                                                                   function ($scope, $rootScope, $location, $timeout, authService, $uibModal) {    	
    	
        $scope.login = function () {
        	
        	$rootScope.currentEvent = "Logging in";   
            
            var userName = angular.copy($scope.username);
            var passwd = angular.copy($scope.password);
            
            authService.authenticate(userName, passwd).then(function(data) {
				// promise was fullfilled (regardless of outcome)
				// checks for information will be peformed here
				console.log("user logged in ...");
			    //console.log(data);
				
			    $rootScope.authenticated = true;
	            $rootScope.name = data.username;
	            $rootScope.$broadcast('event:loginConfirmed');	            
	            
	            $rootScope.currentEvent = "Logged in";    
	            
	            $location.url('/calculate');
			  },
			  function(error) {
				  console.log("Error occurred..");
				  console.log(error);
				  $scope.alerts.push({ type: 'danger', msg: 'Please supply valid credentials and try submitting again.' });
			  }
    		);
        };
        

}]);