var
    //the HTTP headers to be used by all requests
    httpHeaders,
    //the message to be shown to the user
    message,
    //Define the main module.
    //The module is accessible everywhere using "angular.module('angularspring')", therefore global variables can be avoided totally.
    as = angular.module('iohm', ['ngRoute', 'ngResource', 'ngCookies', 'ui.bootstrap', 'ngMessages', 'iohm.i18n', 'iohm.controllers', 'iohm.services','iohm.filters', 'iohm.auth', 'iohm.calculator','ngAnimate']);

    as.config(['$routeProvider', '$httpProvider',function ($routeProvider, $httpProvider) {
        //configure the rounting of ng-view
        $routeProvider
                .when('/',
                        {templateUrl: 'partials/home.html',
                            publicAccess: true})
                .when('/home',
                        {templateUrl: 'partials/home.html',
                            publicAccess: true})
                .when('/login',
                        {templateUrl: 'partials/login.html',
                            publicAccess: true})
                .when('/calculate',
                        {templateUrl: 'partials/calculator/calculator.html',
                	        controller: 'calculatorController',
                            publicAccess: false});
                

        //configure $http to catch message responses and show them
        $httpProvider.interceptors.push(function ($q) {
            var setMessage = function (response) {
                //if the response has a text and a type property, it is a message to be shown
                if (response.data.text && response.data.type) {
                    message = {
                        text: response.data.text,
                        type: response.data.type,
                        show: true
                    };
                }
            };

            return {
                //this is called after each successful server request
                'response': function (response) {
                    // console.log('request:' + response);
                    setMessage(response);
                    return response || $q.when(response);
                },
                //this is called after each unsuccessful server request
                'responseError': function (response) {
                    //console.log('requestError:');
                    //console.log(response);
                    setMessage(response);
                    return $q.reject(response);
                }

            };
        });

        $httpProvider.interceptors.push(function ($rootScope, $q) {
        	'use strict';
        	
        	var setMessage = function (response) {
                //if the response has a text and a type property, it is a message to be shown
        		if (response.text && response.type) {
                    message = {
                        text: response.text,
                        type: response.type,
                        show: true
                    };
                }
            };
        	 
            var xhrCreations = 0;
            var xhrResolutions = 0;
         
            function isLoading() {
                return xhrResolutions < xhrCreations;
            }
         
            function updateStatus() {
                $rootScope.loading = isLoading();
            }
        	
            return {
                'request': function (config) {
                    console.log('request:');
                    console.log(config);
                    xhrCreations++;
                    updateStatus();
                    return config || $q.when(config);
                },
                'requestError': function (rejection) {
                    // console.log('requestError:' + rejection);
                	xhrResolutions++;
                    updateStatus();
                    return rejection;
                },
                //success -> don't intercept
                'response': function (response) {
                    // console.log('response:' + response);
                	xhrResolutions++;
                    updateStatus();
                    return  response || $q.when(response);
                },
                //error -> if 401 save the request and broadcast an event
                'responseError': function (response) {
                    console.log('responseError:');
                    console.log(response);
                    xhrResolutions++;
                    updateStatus();
                    var response2 = {};
                    //response2.text = angular.copy(response.data);
                    response2.type='danger';
                    response2.text = "An Error has occurred";//angular.copy(response.data);
                    console.log("####### Error -> Start:");
                    console.log(response.data);
                    console.log("####### Error -> End:");
                    
                    setMessage(response2);
                    if (response.status === 401) {
                        var deferred = $q.defer(),
                                req = {
                                    config: response.config,
                                    deferred: deferred
                                };
                        // don't push the bad login request
                        if (req.config.url!="api/login") {
                        	$rootScope.requests401.push(req);
                        }
                        response2.text = "Please login with valid credentials";
                        setMessage(response2);
                        $rootScope.$broadcast('event:loginRequired');
                        return deferred.promise;
                    }
                    return $q.reject(response);
                }

            };
        });


        httpHeaders = $httpProvider.defaults.headers;
    }]);


    as.run(['$rootScope', '$http', '$route', '$location', 'base64', function ($rootScope, $http, $route, $location, base64) {
    	//make current message accessible to root scope and therefore all scopes
        $rootScope.message = function () {
            return message;
        };

        /**
         * Holds all the requests which failed due to 401 response.
         */
        $rootScope.requests401 = [];

        $rootScope.$on('event:loginRequired', function () {
            $location.path('/login');
        });

        /**
         * On 'event:loginConfirmed', resend all the 401 requests.
         */
        $rootScope.$on('event:loginConfirmed', function () {
            var i,
                    requests = $rootScope.requests401,
                    retry = function (req) {
                        $http(req.config).then(function (response) {
                            req.deferred.resolve(response);
                        });
                    };

            for (i = 0; i < requests.length; i += 1) {
                retry(requests[i]);
            }

            $rootScope.requests401 = [];
            if (!angular.isUndefinedOrNull(message)) {
	            message.text="Logged in successfully...";
	            message.type="success";
            }
        	
            
        });

        /**
         * On 'event:loginRequest' send credentials to the server.
         */
        $rootScope.$on('event:loginRequest', function (event, username, password) {
        	
        	console.log("authenticated...");        	
                
        });

        /**
         * On 'logoutRequest' invoke logout on the server and broadcast 'event:loginRequired'.
         */
        $rootScope.$on('event:logoutRequest', function () {
            $rootScope.authenticated = false;
            delete $rootScope.name;
            delete httpHeaders.common['Authorization'];
        });

        var routesOpenToPublic = [];
        angular.forEach($route.routes, function (route, path) {
            // push route onto routesOpenToPublic if it has a truthy publicAccess value
            route.publicAccess && (routesOpenToPublic.push(path));
        });

        $rootScope.$on('$routeChangeStart', function (event, nextLoc, currentLoc) {
            console.log('fire event@$routeChangeStart');
            var closedToPublic = (-1 === routesOpenToPublic.indexOf($location.path()));
            if (closedToPublic && !$rootScope.authenticated) {
                //console.log('login required...');             
                $rootScope.$broadcast('event:loginRequired');
            } else if (!!$rootScope.authenticated) {
                //console.log('already logged in...'); 
                if (!!nextLoc && nextLoc.templateUrl == 'partials/login.html') {
                    $location.path('/calculate');
                } else {
                    //do nothing...
                }
            }
        });
        
        $rootScope.$on('$routeChangeSuccess', function(ev, data){
        	console.log('fire event@$routeChangeSuccess');
        });
        
        
        //check the networking connection.        
        $http.get('api/ping')
            .success(function (data) {
                console.log("ping result@"+data);
            })
            .error(function (data) {
            	console.log("Unable to ping");
                 $rootScope.message={text:'Network connection eror!', type:'danger', show:true};
            });
        
        angular.isUndefinedOrNull = function(val) {
            return angular.isUndefined(val) || val === null 
        }
      
    }]);
    
    as.filter('lengthOfObject', function() {
  	  return function( obj ) {
  	    var size = 0, key;
  	    for (key in obj) {
  	      if (obj.hasOwnProperty(key)) size++;
  	    }
  	   return size;
  	 }
  	});