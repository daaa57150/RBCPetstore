'use strict';

angular.module(window.GLOBAL.appName)

/**
 * @ngdoc service
 * @name dialogSrv
 * @description This service provides methods to open dialogs. 
 * Be sure to include the relevant html fragment in the pages using them. 
 */
.service('dialogSrv', function ($mdDialog) {
	
	var service = {},
		cancelCB = function() {
			console.log("cancelled");
		};
	
	/**
	 * Shows a confirmation dialog asking if the user really wants to delete the pet.
	 * Needs the pets/dialogs :: confirmDeletion fragment
	 */
	service.confirmPetDeletion = function(pet, ev, okCB) {
		$mdDialog.show({
			controller: function ($scope, $mdDialog) {
				$scope.pet = pet;
				$scope.cancel = $mdDialog.cancel;
			    $scope.ok = function() {
			    	$mdDialog.hide(true);
			    };
			},
		    templateUrl: 'confirmDeletion.tmpl.html',
		    parent: angular.element(document.body),
		    targetEvent: ev,
		    clickOutsideToClose:true
		})
		.then(function(confirmed) {
			okCB(pet);
		}, function() {
			cancelCB();
		});
	};
	
	/**
	 * Opens a dialog to search a pet by id.
	 * Needs the pets/dialogs :: findById fragment
	 */
	service.findPetById = function(ev, okCB) {
		$mdDialog.show({
			controller: function ($scope, $mdDialog) {
				$scope.petId = undefined;
				$scope.cancel = $mdDialog.cancel;
			    $scope.ok = function() {
			    	if($scope.petId) {
			    		$mdDialog.hide($scope.petId);
			    	} else {
			    		toastr.warning("Please choose an id"); 
			    	}
			    };
			},
		    templateUrl: 'findById.tmpl.html',
		    parent: angular.element(document.body),
		    targetEvent: ev,
		    clickOutsideToClose:true
		})
		.then(function(id) {
			okCB(id);
		}, function() {
			cancelCB();
		});
		
	};
	
	/**
	 * Opens a dialog to search a pet by status
	 * Needs the pets/dialogs :: findByStatus fragment
	 */
	service.findPetByStatus = function(ev, okCB) {
		$mdDialog.show({
			controller: function ($scope, $mdDialog, STATUS) {
				
				// the model for the checkboxes
				$scope.selectedStatuses = {};
				_.each(STATUS, function(stat) {
					$scope.selectedStatuses[stat] = false;
				});

				$scope.cancel = $mdDialog.cancel;
			    $scope.ok = function() {
			    	if(_.some($scope.selectedStatuses)) {
			    		var flatStatuses = _.reduce($scope.selectedStatuses, function(res, val, key) {
			    			if(val) {
			    				res.push(key);
			    			}
			    			return res;
			    		}, []);
			    		$mdDialog.hide(flatStatuses);
			    	} else {
			    		toastr.warning("Please choose at least one status"); 
			    	}
			    };
			},
		    templateUrl: 'findByStatus.tmpl.html',
		    parent: angular.element(document.body),
		    targetEvent: ev,
		    clickOutsideToClose:true
		})
		.then(function(selectedStatuses) {
			okCB(selectedStatuses);
		}, function() {
			cancelCB();
		});
		
	};

	return service;
});














