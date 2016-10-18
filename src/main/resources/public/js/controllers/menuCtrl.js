'use strict';

angular.module(window.GLOBAL.appName)

/**
 * Menu controller
 */
.controller('MenuCtrl', function($scope, $location, $window, $http, $route) {
	console.log("inside menu controller");
	console.log($route);
	console.log($route.current);

	
	toastr.success("inside menu controller");
});











