'use strict';

angular.module(window.GLOBAL.appName)

/**
 * Pet list controller
 */
.controller('PetListCtrl', function($scope, $location, $window, $http, menuItem) {
	console.log("inside pet list controller, yeah");
	console.log(menuItem);
	
	toastr.success("inside pet list controller!");
});
