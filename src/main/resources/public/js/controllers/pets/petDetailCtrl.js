'use strict';

angular.module(window.GLOBAL.appName)

/**
 * Pet detail controller
 */
.controller('PetDetailCtrl', function($scope, $location, $window, $http, $routeParams, petSrv, utilSrv) {
	console.log("inside pet detail controller: " + $routeParams.id);
	
	// scope
	$scope.id = $routeParams.id;
	$scope.pet = undefined;
	
	// load the pet
	petSrv.findPetById(
		$scope.id, 
		function onSuccessLoadPet(pet) {
			$scope.pet = pet;
		}, 
		function onErrorLoadPet(message, exception) {
			utilSrv.handleError(message, exception);
		});
	
	
	
	
});











