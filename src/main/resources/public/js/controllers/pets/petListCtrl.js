'use strict';

angular.module(window.GLOBAL.appName)

/**
 * Pet list controller
 */
.controller('PetListCtrl', function($scope, $location, $window, $location, petSrv, utilSrv, dialogSrv) {
	console.log("inside pet list controller");
	
	// scope vars
	$scope.pets = undefined;
	
	// Sort & Pagination
	// TODO: put all this in a directive/service
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
    
    
    // current filter 
    // => a function applied to arguments
    var currentFilter = {
	    	func: undefined,
	    	args: undefined
	    },
	    
	    /**
	     * Configures the current filter
	     */
	    setFilter = function(func, args) {
    		currentFilter.func = func;
    		currentFilter.args = args ? args : [];
    	},
    	
    	/**
    	 * Applies the current filter if any; if it hasn't changed it just 
    	 * refreshes the list
    	 */
    	filter = function() {
    		if(currentFilter.func) {
    			currentFilter.apply(null, currentFilter.args);
    		}
    	},
    	
    	/**
    	 * Refreshes the current list
    	 */
    	refreshList = function() {
    		if(currentFilter.func) {
    			$scope.pets = undefined;
    			currentFilter.func.apply(null, currentFilter.args);
    			console.log("list refreshed.");
    		} else {
    			toastr.warn("Cannot refresh the list; no filter");
    		}
    	},
    	    
    	/**
    	 * Load and show all the pets
    	 */
    	listAllPets = function() {
	    	$scope.pets = undefined;
	    	petSrv.listPets(
				function onSuccessLoadPets(pets) {
					$scope.pets = pets;
				},
				utilSrv.handleError/*(message, exception)*/
			);
	    },
    
	    /**
	     * Load and show the pet with the given id
	     */
	    listPetWithId = function(id) {
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
	    },
    
	    /**
	     * Load and show the pets with any of the given statuses
	     */
	    listPetsWithStatuses = function(statuses) {
	    	$scope.pets = undefined;
	    	petSrv.findPetsByStatus(
	    		statuses,
				function onSuccessPetsWithStatuses(pets) {
	    			$scope.pets = pets;
				}, 
				utilSrv.handleError/*(message, exception)*/
			);
	    },
	
	    /**
		 * Deletes a pet for real
		 */
		deletePet = function(pet) {
			pet.busy = true;
			petSrv.deletePet(
				pet, 
				function onDeleteSuccess() {
					_.remove($scope.pets, pet);
					toastr.success(pet.name + ' (id=' + pet.id + ')' + ' deleted.');
				}, function onDeleteError(message, exception) {
					utilSrv.handleError(message, exception);
					pet.busy = false;
				});
		},
		
		/**
		 * Adds a pet
		 */
		addPet = function(pet) {
			return petSrv.addPet(pet, function(createdPed) {
				toastr.success(createdPed.name + " (id=" + createdPed.id+ ") successfully created.");
			});
		}
	    ; 
 	// end vars
    
    
	// load all the pets by default 
    listAllPets();
    setFilter(listAllPets);
	
	
	
	
	
	
	///// UI Functionalities /////
	/**
	 * Navigates the user to the detail page for the chosen pet
	 */
	$scope.petDetail = function(pet) {
		$location.path( "/pet/detail/" + pet.id + ".html");
	};
	
	/**
	 * Shows a dialog to create a pet and creates it upon confirmation
	 */
	$scope.addPet = function(ev) {
		dialogSrv.addPet(ev, function confirmAddPetCB(pet) {
			addPet(pet).then(function() {
				refreshList();
			});
		});
	};
	
	/**
	 * Shows a confirmation dialog asking if the user really wants to delete the pet.
	 * If he answers yes, the pet is deleted
	 */
	$scope.confirmDeletion = function(pet, ev) {
		dialogSrv.confirmPetDeletion(pet, ev, function confirmPetDeletionCB() {
			deletePet(pet);
		});
	};
	
	/**
	 * Lists all the pets
	 */
	$scope.listAllPets = function() {
		listAllPets();
		setFilter(listAllPets);
	};
	
	/**
	 * Opens a dialog to search a pet by id, and displays it
	 */
	$scope.findById = function(ev) {
		dialogSrv.findPetById(ev, function findPetByIdCB(id) {
			listPetWithId(id);
			setFilter(listPetWithId, [id]);
		});
	};
	
	/**
	 * Opens a dialog to search a pet by status, and list pets with the chosen status
	 */
	$scope.findByStatus = function(ev) {
		dialogSrv.findPetByStatus(ev, function findPetByStatusCB(statuses) {
			listPetsWithStatuses(statuses);
			setFilter(listPetsWithStatuses, [statuses]);
		})
	};
	
	/**
	 * Refreshes the current list
	 */
	$scope.refresh = refreshList;
	
});












