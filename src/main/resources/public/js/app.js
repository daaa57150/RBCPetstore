'use strict';


/**
 * The application main module
 */
var app = angular.module(window.GLOBAL.appName, ['ngRoute', 'ngAnimate', 'ui.bootstrap']);

//Deferred Bootstrap initialization
angular.element(document).ready(
	function() {
		
		var initInjector = angular.injector(['ng']);
        var $http = initInjector.get('$http');
        var $window = initInjector.get('$window');
        var $q = initInjector.get('$q');
        
        console.log("asking for enums...")
        /*
        data – {string|Object} – The response body transformed with the transform functions.
		status – {number} – HTTP status code of the response.
		headers – {function([headerName])} – Header getter function.
		config – {Object} – The configuration object that was used to generate the request.
		statusText – {string} – HTTP status text of the response.
        */
        var statusPromise = $http.get('/pet/statuses').then( //start init then
            function sucess(response) {
        		var statuses = [],
        			data = response.data;
            	if(!data.success) {
            		var error = data.errorMessage ? data.errorMessage : data.exception; 
            		toastr.error(error);
            		console.log(error);
            	}
            	else {
            		statuses = data.content;
                	console.log("STATUS preloaded:");
                	console.log(statuses);
            	}
            	app.constant('STATUS', statuses);
            	return $q.when(statuses);
            },
            function error(response){
            	console.log("Error getting statuses:");
            	console.log(response);
            	throw "Error getting statuses";
            }
		);
                   
        statusPromise.then(function() {
        	app.config(function($logProvider, $httpProvider, $animateProvider, $parseProvider) {
        		
    			// Debug mode ?
    			$logProvider.debugEnabled(false);
    		
    			// Run angular animation only on elements with the specified class
    			$animateProvider.classNameFilter(/-anim/);
    		
    			// Each request will contain the following header to identify XHR on the server side
    			$httpProvider.defaults.headers.common['X-Requested-With'] = 'XmlHttpRequest';
    		})
    		
    		// Route configuration
    		.config(function($routeProvider, $locationProvider) {
    			
    			//var templateLoading = '<div ng-include="templateUrl">Loading...</div>';
    			
    			/* Route config */
    			$routeProvider
    			.when('/', {
    				redirectTo : '/main.html'
    			})
    			.when('/main.html', {
    				templateUrl : 'template/main.html',
    				controller : 'MainCtrl'
    			})
    			
    			// Default
    			.otherwise({
    				redirectTo : '/'
    			});
    		})
    		
    		// enables trusting sources, see http://stackoverflow.com/a/24519069
    		.filter('trusted', ['$sce', function ($sce) {
    			return function(url) {
    				return $sce.trustAsResourceUrl(url);
    			};
    		}])
    		
    		// Application core init
    		.run(function($rootScope, $window, $location, $routeParams, STATUS) {
    			
    			// Init global constants and services
        		$rootScope.STATUS = STATUS;
    			
    			// useful ?
    			$rootScope.$location = $location;
    		});

    		// manually bootstrapping the app (do not use the ng-app directive! )
            angular.bootstrap(document.body, [window.GLOBAL.appName]);
            
        }); // end status promise
	}
);