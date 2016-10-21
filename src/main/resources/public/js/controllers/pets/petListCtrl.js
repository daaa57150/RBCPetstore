'use strict';

angular.module(window.GLOBAL.appName)

/**
 * Pet list controller
 */
.controller('PetListCtrl', function($scope, $location, $window, $mdDialog, $templateCache, $compile, petSrv) {
	console.log("inside pet list controller");
	

	$scope.pets = undefined;
	
	// Sort & Pagination
	// TODO: put all this in a directive
	$scope.sortType     = 'id'; 	// set the default sort type
	$scope.sortReverse  = false;  	// set the default sort order
	$scope.currentPage 	= 1;
	$scope.numPerPage 	= 10; 
	
	$scope.paginate = function(pet) {  
		if(!$scope.pets) return false;
		
		var begin, end, index;  
        begin = ($scope.currentPage - 1) * $scope.numPerPage;  
        end = begin + $scope.numPerPage;  
        index = $scope.pets.indexOf(pet);  
        return (begin <= index && index < end);  
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
				toastr.success(pet.name + ' (id=' + pet.id + ')' + ' deleted.');
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












