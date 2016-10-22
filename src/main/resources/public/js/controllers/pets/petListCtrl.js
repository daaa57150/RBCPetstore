'use strict';

angular.module(window.GLOBAL.appName)

/**
 * Pet list controller
 */
.controller('PetListCtrl', function($scope, $location, $window, $mdDialog, /*$templateCache,*/ $compile, $location, petSrv, utilSrv) {
	console.log("inside pet list controller");
	
	// scope vars
	$scope.pets = undefined;
	
	// Sort & Pagination
	// TODO: put all this in a directive
	$scope.sortType     = 'id'; 	// set the default sort type
	$scope.sortReverse  = false;  	// set the default sort order
	$scope.currentPage 	= 1;
	$scope.numPerPage 	= 8; 
	
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
				handleError(message, exception);
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
	
	/**
	 * Shows a confirmation dialog asking if the user really wants to delete the pet.
	 * If he answers yes, the pet is deleted
	 */
	$scope.confirmDeletion = function(pet, ev) {
		
		// TODO: load a template, compile it and apply a local scope to it
		var htmlContent = 
			'<dl class="pet-identity">' +
			    '<dt>Id</dt><dd>' +pet.id+ '</dd>' +
                '<dt>Name</dt><dd>' +pet.name+ '</dd>' +
                '<dt>Category</dt><dd>' +pet.category.name+ '</dd>' +
                '<dt>Status</dt><dd>' +pet.status+ '</dd>' +
			'</dl>';
		
		// create the dialog
	    var confirm = $mdDialog.confirm()
	          .title('Are you sure you want to delete this ' + pet.category.name + ' ?')
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
	
	/**
	 * Opens a dialog to search a pet by id
	 */
	$scope.findById = function(ev) {
		$mdDialog.show({
			controller: function ($scope, $mdDialog) {
				$scope.petId = undefined;
				$scope.cancel = $mdDialog.cancel;
			    $scope.answer = function() {
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
	
});












