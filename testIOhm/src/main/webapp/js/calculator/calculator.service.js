angular.module('iohm.calculator').service('calculatorService', [ '$http', '$q','base64', function ( $http, $q, base64) {
    	
	
	this.calculateByPost = function (iOhmParameters) {        
		
        var deferred = $q.defer();
        $http.post('api/calculate', iOhmParameters)
			.success(function(response) {	
				console.log("iohm calculation succeeded ...");
				// promise is fulfilled
			    deferred.resolve(response);
		})
		.error(function(response) {
		    console.log("iohm calculation failed...");
		  	// the following line rejects the promise 
	        deferred.reject(response);
		});
        return deferred.promise; 
    };
    
    this.calculateByGet = function (iOhmParameters) {        
		
        var deferred = $q.defer();
        $http.get('api/calculate?bandAColor='+iOhmParameters.bandAColor+'&bandBColor='+iOhmParameters.bandBColor+'&bandCColor='+iOhmParameters.bandCColor+'&bandDColor='+iOhmParameters.bandDColor)
			.success(function(response) {	
				console.log("iohm calculation succeeded ...");
				// promise is fulfilled
			    deferred.resolve(response);
		})
		.error(function(response) {
			console.log("iohm calculation failed ...");
		  	// the following line rejects the promise 
	        deferred.reject(response);
		});
        return deferred.promise; 
    };
    
}]);
