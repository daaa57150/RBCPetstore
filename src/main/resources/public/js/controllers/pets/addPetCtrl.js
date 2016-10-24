'use strict';

angular.module(window.GLOBAL.appName)

/**
 * Controller for the dialog to add a pet.
 * Can be used later on a complete page to add a pet when it gets more complicated.
 * Is used in conjunction with dialogSrv.addPet and the pets/dialogs :: addPet fragment
 */
.controller('AddPetCtrl', function($scope, $mdDialog, petSrv, STATUS) {
	console.log("inside add pet controller");
	
	// scope init
	$scope.pet = { // the pet we're creating
		status: STATUS.AVAILABLE // available by default
	}; 
	$scope.categories = undefined; // we'll load the available categories asynchronously
	$scope.STATUS = STATUS;
	
	// ok & cancel buttons
	$scope.cancel = $mdDialog.cancel;
    $scope.ok = function() {
    	if($scope.addPetForm.$valid) {
    		$mdDialog.hide($scope.pet);
    	} else {
    		toastr.error("");
    	}
    };
    
    
    // load the categories asynchronously
    petSrv.listCategories(function onSuccessListCategories(categories) {
    	console.log("got categroies:");
    	console.log(categories);
    	$scope.categories = categories;
    });
    
    
});











