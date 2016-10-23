'use strict';

angular.module(window.GLOBAL.appName)

/**
 * Pet list controller
 */
.controller('PetListCtrl', function($scope, $location, $window, $mdDialog, /*$templateCache,*/ $compile, $location, petSrv, utilSrv, $rootScope) {
	console.log("inside pet list controller");
	
	// scope vars
	$scope.pets = undefined;
	
	// Sort & Pagination
	// TODO: put all this in a directive
	$scope.sortType     = 'id'; 	// set the default sort type
	$scope.sortReverse  = false;  	// set the default sort order
	$scope.currentPage 	= 1;
	$scope.numPerPage 	= 10; 
	
	$scope.paginate = function(pet, $index) {  
		if(!$scope.pets) return false;
		
		var begin, end;  
        begin = ($scope.currentPage - 1) * $scope.numPerPage;  
        end = begin + $scope.numPerPage;  
        //index = $scope.pets.indexOf(pet);  
        return (begin <= $index && $index < end);  
    };  
    $scope.sortBy = function(property) {
    	if(!$scope.pets) return;
    	
    	if(property == $scope.sortType) {
    		$scope.sortReverse = !$scope.sortReverse;
    	} else {
    		$scope.sortReverse  = false;
    	}
    	
    	var sorted = _.sortBy($scope.pets, property);
    	if($scope.sortReverse) {
    		sorted.reverse();
    	}
    	$scope.pets.length = 0;
    	Array.prototype.push.apply($scope.pets, sorted); //adds all items from sorted in pets
    	$scope.sortType = property;
    };
    
    /**
     * Load and show all the pets
     */
    $scope.listAllPets = function() {
    	$scope.pets = undefined;
    	petSrv.listPets(
			function onSuccessLoadPets(pets) {
				$scope.pets = pets;
			},
			utilSrv.handleError/*(message, exception)*/
		);
    };
    
    /**
     * Load and show the pet with the given id
     */
    var listPetWithId = function(id) {
    	$scope.pets = undefined;
    	petSrv.findPetById(
    		id,
			function onSuccessListPetWithId(pet) {
    			$scope.pets = [];
    			if(pet) {
					$scope.pets.push(pet);
				}
			}, 
			utilSrv.handleError/*(message, exception)*/
		);
    }
    
    /**
     * Load and show the pets with any of the given statuses
     */
    var listPetsWithStatuses = function(statuses) {
    	$scope.pets = undefined;
    	petSrv.findPetsByStatus(
    		statuses,
			function onSuccessPetsWithStatuses(pets) {
    			$scope.pets = pets;
			}, 
			utilSrv.handleError/*(message, exception)*/
		);
    }
	
    
    
    
	// load all the pets by default 
    $scope.listAllPets();
	
    
    
    
	
	/**
	 * Deletes a pet for real
	 */
	var deletePet = function(pet) {
		pet.busy = true;
		petSrv.deletePet(pet, 
			function onDeleteSuccess() {
				_.remove($scope.pets, pet);
				toastr.success(pet.name + ' (id=' + pet.id + ')' + ' deleted.');
			}, function onDeleteError(message, exception) {
				utilSrv.handleError/*(message, exception)*/
				pet.busy = false;
			});
	};
	
	
	
	
	
	
	///// UI Functionalities /////
	/**
	 * Navigates the user to the detail page for the chosen pet
	 */
	$scope.petDetail = function(pet) {
		$location.path( "/pet/detail/" + pet.id + ".html");
	};
	
	
	//TODO: put dialogs in a service
	
	/**
	 * Shows a confirmation dialog asking if the user really wants to delete the pet.
	 * If he answers yes, the pet is deleted
	 */
	$scope.confirmDeletion = function(pet, ev) {
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
			deletePet(pet);
		}, function() {
		    console.log("nope");
		});
	};
	
	/**
	 * Opens a dialog to search a pet by id
	 */
	$scope.findById = function(ev) {
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
			listPetWithId(id);
		}, function() {
		    console.log("nope");
		});
		
	};
	
	/**
	 * Opens a dialog to search a pet by status
	 */
	$scope.findByStatus = function(ev) {
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
			listPetsWithStatuses(selectedStatuses);
		}, function() {
		    console.log("nope");
		});
		
	};
	
});












