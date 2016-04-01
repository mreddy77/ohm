angular.module('iohm.calculator').controller('calculatorController',[ '$scope', '$rootScope', '$location', '$timeout','calculatorService', '$uibModal', '$filter',  
                                                                   function ($scope, $rootScope, $location, $timeout, calculatorService, $uibModal, $filter) {    	
    	    
	
	$scope.calculateByPost = function () {
    	
	  	$rootScope.currentEvent = 'Calculating IOhm value';
    	
        var iOhmParameters = {};
                    
        iOhmParameters.bandAColor=$scope.bandAColor;
        iOhmParameters.bandBColor=$scope.bandBColor;
        iOhmParameters.bandCColor=$scope.bandCColor;
        iOhmParameters.bandDColor=$scope.bandDColor;
        
        console.log("iOhmParameters...");
        console.log(iOhmParameters);
        
        calculatorService.calculateByPost(iOhmParameters).then(function(data) {
			// promise was fullfilled (regardless of outcome)
			// checks for information will be peformed here
			console.log("Calculate IOhm value ...")
		    console.log(data);
			$scope.calculatedOhmValue = data;
			
			$rootScope.currentEvent = 'Calculated IOhm Value';
			
		  }
		);
    };
    
    $scope.calculateByGet = function () {
    	
	  	$rootScope.currentEvent = 'Calculating IOhm value';
    	
        var iOhmParameters = {};
                    
        iOhmParameters.bandAColor=$scope.bandAColor;
        iOhmParameters.bandBColor=$scope.bandBColor;
        iOhmParameters.bandCColor=$scope.bandCColor;
        iOhmParameters.bandDColor=$scope.bandDColor;
        
        console.log("iOhmParameters...");
        console.log(iOhmParameters);
        
        calculatorService.calculateByGet(iOhmParameters).then(function(data) {
			// promise was fullfilled (regardless of outcome)
			// checks for information will be peformed here
			console.log("Calculate IOhm value ...")
		    console.log(data);
			$scope.calculatedOhmValue = data;
			
			$rootScope.currentEvent = 'Calculated IOhm Value';
			
		  }
		);
    };
    
}]);