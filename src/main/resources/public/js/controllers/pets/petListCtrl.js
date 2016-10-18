'use strict';

angular.module(window.GLOBAL.appName)

/**
 * Pet list controller
 */
.controller('PetListCtrl', function($scope, $location, $window, petSrv) {
	console.log("inside pet list controller");
	
	// Scope preparation
	$scope.sortType     = 'id'; // set the default sort type
	$scope.sortReverse  = false;  // set the default sort order
	$scope.pets = undefined;
	
	// load the pets
	petSrv.getPets(
		function onSuccess(pets) {
			$scope.pets = pets;
		}, 
		function onError(message, exception) {
			toastr.error(message); //TODO: error service
		});
	
	
	
});












