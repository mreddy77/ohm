angular.module('iohm.auth').service('authService', [ '$http', '$q','base64', function ( $http, $q, base64) {
    	
	
	this.authenticate = function (username, password) {
        
		$http.defaults.headers.common.Authorization  = 'Basic ' + base64.encode(username + ':' + password);
        // console.log('httpHeaders.common[\'Authorization\']@' + httpHeaders.common['Authorization'] + ':::' + username + ':' + password);
       
        var deferred = $q.defer();
        $http.get('api/login')
		.success(function(response) {	
			console.log("login successful...")
			// promise is fulfilled
		    deferred.resolve(response);
		})
		.error(function(response) {
		    console.log("login failed...")
		  	// the following line rejects the promise 
	        deferred.reject(response);
		});
        return deferred.promise; 
    }
    
}]);