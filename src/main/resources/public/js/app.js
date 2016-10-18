'use strict';


/**
 * The application main module
 */
var app = angular.module(window.GLOBAL.appName, ['ngRoute', 'ngAnimate', 'ui.bootstrap']);

//Deferred Bootstrap initialization
angular.element(document).ready(
	function() {
		
		// some debug
		console.log("GLOBAL config:");
		console.log(window.GLOBAL);
		
		
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
        
        // menu constants
        app.constant('MENU', function() {
        	
        	// TODO: use keys for labels 
        	// items
        	var petList = {
        			id: 'PET_LIST',
        			label: 'Pets',
        			route:'/pets.html'
        		},
        		orderList = {
        			id: 'ORDER_LIST',
        			label: 'Orders',
        			route:'/orders.html'
        		},
        		admin = {
        			id: 'ADMIN',
        			label: 'Admin',
        			route:'/admin.html'
        		};
        	
        	// this is the menu constant
        	return {
        		
        		// the constants
        		PET_LIST: petList, 
        		ORDER_LIST: orderList, 
        		ADMIN: admin,

        		// returns the items in the wanted order 
        		list: function() {
        			return [PET_LIST, ORDER_LIST, ADMIN];
        		}
        	};
        }());
                   
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
    		.config(function($routeProvider, $locationProvider, MENU) {
    			
    			//var templateLoading = '<div ng-include="templateUrl">Loading...</div>';
    			
    			/* Route config */
    			$routeProvider
    			.when('/', {
    				redirectTo : MENU.PET_LIST.route
    			})
    			.when(MENU.PET_LIST.route, {
    				templateUrl : 'template/petList.html',
    				controller : 'PetListCtrl',
    				menu: MENU.PET_LIST,
    				resolve: {
    					menuItem: function(){
    						return $q.when(MENU.PET_LIST);
    					}
    				}
    			})
    			.when(MENU.ORDER_LIST.route, {
    				templateUrl : 'template/orderList.html',
    				controller : 'OrderListCtrl',
    				menu: MENU.ORDER_LIST
    			})
    			.when(MENU.ADMIN.route, {
    				templateUrl : 'template/petList.html',
    				controller : 'AdminCtrl',
    				menu: MENU.ADMIN
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