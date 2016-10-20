'use strict';

angular.module(window.GLOBAL.appName)

/**
 * Menu controller
 */
.controller('MenuCtrl', function($scope, $location, $window, $http, $route, MENU) {
	
	// scope
	$scope.menuItems = MENU.list();
	
	
});











