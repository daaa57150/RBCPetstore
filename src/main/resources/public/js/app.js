'use strict';


/**
 * The application main module
 */
var app = angular.module(window.GLOBAL.appName, ['ngRoute', 'ngAnimate', 'ui.bootstrap', 'ngMaterial', 'ngMessages', 'ngSanitize', 'ui.bootstrap']);

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
        		var statuses = {},
        			data = response.data;
            	if(!data.success) {
            		var error = data.errorMessage ? data.errorMessage : data.exception; 
            		toastr.error(error);
            		console.log(error);
            	}
            	else {
            		//statuses = data.content;
            		_.each(data.content, function(stat) {
            			statuses[stat] = stat;
            		});
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
        	var pets = {
        			id: 'PETS',
        			label: 'Pets',
        			mainRoute: '/pet/list.html'
        		},
        		orders = {
        			id: 'ORDERS',
        			label: 'Orders',
        			mainRoute: '/order/list.html'
        		},
        		admin = {
        			id: 'ADMIN',
        			label: 'Admin',
        			mainRoute: '/admin.html'
        		};
        	
        	// this is the menu constant
        	return {
        		
        		// the constants
        		PETS: pets, 
        		ORDERS: orders, 
        		ADMIN: admin,

        		// returns the items in the wanted order 
        		list: function() {
        			return [pets, orders, admin];
        		}
        	};
        }());
                   
        statusPromise.then(function() {
        	app.config(function($logProvider, $httpProvider, $animateProvider, $parseProvider, $mdThemingProvider) {
        		
    			// Debug mode ?
    			$logProvider.debugEnabled(false);
    		
    			// Run angular animation only on elements with the specified class
    			$animateProvider.classNameFilter(/-anim/);
    		
    			// Each request will contain the following header to identify XHR on the server side
    			$httpProvider.defaults.headers.common['X-Requested-With'] = 'XmlHttpRequest';
    			
    			// angular material theme
    			$mdThemingProvider
    				.theme('default')
    				//.primaryPalette('blue')
    				//.dark()
    			;
    			
    		})
    		
    		// Route configuration
    		.config(function($routeProvider, $locationProvider, MENU) {
    			
    			//var templateLoading = '<div ng-include="templateUrl">Loading...</div>';
    			
    			/* Route config */
    			$routeProvider
    			.when('/', {
    				redirectTo : MENU.PETS.mainRoute
    			})
    			.when('/pet/list.html', {
    				templateUrl : 'template/petList.html',
    				controller : 'PetListCtrl',
    				menu: MENU.PETS
    			})
    			.when('/pet/detail/:id.html', {
    				templateUrl : 'template/petDetail.html',
    				controller : 'PetDetailCtrl',
    				menu: MENU.PETS
    			})
    			.when('/order/list.html', {
    				templateUrl : 'template/orderList.html',
    				controller : 'OrderListCtrl',
    				menu: MENU.ORDERS
    			})
    			.when('/admin.html', {
    				templateUrl : 'template/admin.html',
    				controller : 'AdminCtrl',
    				menu: MENU.ADMIN
    			})
    			
    			// Default
    			.otherwise({
    				redirectTo : '/'
    			});
    		})
    		
    		// enables trusting sources, see http://stackoverflow.com/a/24519069
//    		.filter('trusted', ['$sce', function ($sce) {
//    			return function(url) {
//    				return $sce.trustAsResourceUrl(url);
//    			};
//    		}])
    		
    		// Application core init
    		.run(function($rootScope, $window, $location, $routeParams, STATUS, MENU) {
    			
    			// Init global constants and services
        		$rootScope.STATUS = STATUS;
    			
    			// useful ?
    			$rootScope.$location = $location;
    			
    			// activate the menus
    			$rootScope.$on('$routeChangeSuccess', function(event, current) {
        			if(!current.redirectTo){
        				
        				if(current.menu) {
        					// deactivate all
        					_.forEach(MENU.list(), function(item) {
        						item.active = false;
        					});
        					// activate the current
        					current.menu.active=true;
        				}
        			}
        		});
    			
    		});

    		// manually bootstrapping the app (do not use the ng-app directive! )
            angular.bootstrap(document.body, [window.GLOBAL.appName]);
            
        }); // end status promise
	}
);