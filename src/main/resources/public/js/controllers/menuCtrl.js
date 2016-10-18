'use strict';

angular.module(window.GLOBAL.appName)

/**
 * Menu controller
 */
.controller('MenuCtrl', function($scope, $location, $window, $http, $route, MENU) {
	console.log("inside menu controller");
	console.log($route);
	console.log($route.current);

	// scope
	$scope.menuItems = MENU.list();
	
	toastr.success("inside menu controller");
});











