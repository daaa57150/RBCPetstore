'use strict';

angular.module(window.GLOBAL.appName)

/**
 * Pet list controller
 */
.controller('PetListCtrl', function($scope, $location, $window, $mdDialog, $templateCache, $compile, petSrv) {
	console.log("inside pet list controller");
	
	// Scope preparation
	$scope.sortType     = 'id'; // set the default sort type
	$scope.sortReverse  = false;  // set the default sort order
	$scope.pets = undefined;
	
	/**
	 * Handles the errors
	 * TODO: move this in a utility service
	 */
	var handleError = function(message, exception) {
		var err = message ? message : (exception ? exception.message : '');
		toastr.error(err);
		if(exception) {
			console.log(exception);
		} else {
			console.log(message);
		}
	};
	
	// load the pets
	petSrv.listPets(
		function onSuccess(pets) {
			$scope.pets = pets;
		}, 
		function onError(message, exception) {
			toastr.error(message); //TODO: error service
		});
	
	
	/**
	 * Deletes a pet for real
	 */
	var deletePet = function(pet) {
		pet.busy = true;
		petSrv.deletePet(pet, 
			function onDeleteSuccess() {
				_.remove($scope.pets, pet);
				toastr.success("Pet " + pet.id + ' named ' + pet.name + ' deleted.');
			}, function onDeleteError(message, exception) {
				handleError(message, exception);
				pet.busy = false;
			});
	}
	
	///// Functionalities /////
	$scope.confirmDeletion = function(pet, ev) {
		
//		var deleteConfirmationTemplate = $templateCache.get("deleteConfirmation");
//		var dialogScope = $scope.$new();
//		dialogScope.pet = pet;
//		//var plop = $compile(deleteConfirmationTemplate)(dialogScope);
//		//console.log(deleteConfirmationTemplate);
//		//dialogScope.$digest();
		
		// TODO: load a template, compile it and apply a local scope to it
		var htmlContent = 
			'Id: ' + pet.id + '<br/>'+
			'Name: ' + pet.name + '<br/>' +
			'Status: ' + pet.status;
		
		// create the dialog
	    var confirm = $mdDialog.confirm()
	          .title('Are you sure you want to delete this ' + pet.category.name + ' ?')
	          //.textContent('Name: ' + pet.name)
	          .htmlContent(htmlContent)
	          .ariaLabel('Delete ' + pet.name)
	          .targetEvent(ev)
	          .clickOutsideToClose(true)
	          .ok('YES')
	          .cancel('NO');
	    
	    // show the dialog
	    $mdDialog.show(confirm).then(function() {
	    	deletePet(pet);
	    }, function() {
	    	console.log("nope");
	    });
	};
	
});












