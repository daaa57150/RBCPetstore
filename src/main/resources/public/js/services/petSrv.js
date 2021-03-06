'use strict';

angular.module(window.GLOBAL.appName)

/**
 * @ngdoc service
 * @name petSrv
 * @description This service provides methods to manage pets.
 */
.service('petSrv', function ($http, utilSrv) {
	
	var service = {};
	
	/**
	 * @object method
	 * @name listPets
	 * @description Get all the pets
	 *
	 * @param {function} successCB - success callback with params:
	 * 								 	- pets {array} the list of pets
	 * @param {function} errorCB - error callback with params:
	 * 									- error {string} error message if any
	 * 									- exception {object} the exception if any
	 * @returns {object} the http promise
	 */
	service.listPets = function(successCB, errorCB) {
		return utilSrv.handleHttpResponse(
			$http.get(GLOBAL.contextRoot + '/pet/list'),
			successCB, errorCB
		);
	};
	
	/**
	 * @object method
	 * @name deletePet
	 * @description Delete the pet with the given id
	 *
	 * @param {object} pet - the pet to delete
	 * @param {function} successCB - success callback with params:
	 * 								 	- deleted {boolean} true if it was deleted, false it it didn't exist
	 * @param {function} errorCB - error callback with params:
	 * 									- error {string} error message if any
	 * 									- exception {object} the exception if any
	 * @returns {object} the http promise
	 */
	service.deletePet = function(pet, successCB, errorCB) {
		return utilSrv.handleHttpResponse(
			$http['delete'](GLOBAL.contextRoot + '/pet/' + pet.id),
			successCB, errorCB
		);
	}
	
	/**
	 * @object method
	 * @name addPet
	 * @description Adds a pet
	 *
	 * @param {object} pet - the pet to add
	 * @param {function} successCB - success callback with params:
	 * 								 	- deleted {boolean} true if it was deleted, false it it didn't exist
	 * @param {function} errorCB - error callback with params:
	 * 									- error {string} error message if any
	 * 									- exception {object} the exception if any
	 * @returns {object} the http promise
	 */
	service.addPet = function(pet, successCB, errorCB) {
		return utilSrv.handleHttpResponse(
			$http.post(GLOBAL.contextRoot + '/pet', pet),
			successCB, errorCB
		);
	}
	
	
	/**
	 * @object method
	 * @name findPetById
	 * @description Finds the pet with the given id
	 *
	 * @param {int} petId - the pet id
	 * @param {function} successCB - success callback with params:
	 * 								 	- pet {object} the pet if found, null otherwise 
	 * @param {function} errorCB - error callback with params:
	 * 									- error {string} error message if any
	 * 									- exception {object} the exception if any
	 * @returns {object} the http promise
	 */
	service.findPetById = function(petId, successCB, errorCB) {
		return utilSrv.handleHttpResponse(
			$http.get(GLOBAL.contextRoot + '/pet/' + petId),
			successCB, errorCB
		);
	}
	
	/**
	 * @object method
	 * @name findPetsByStatus
	 * @description Finds the pets with any of the given statuses
	 *
	 * @param {array} statuses - the statuses
	 * @param {function} successCB - success callback with params:
	 * 								 	- pets {array} the list of pets found
	 * @param {function} errorCB - error callback with params:
	 * 									- error {string} error message if any
	 * 									- exception {object} the exception if any
	 * @returns {object} the http promise
	 */
	service.findPetsByStatus = function(statuses, successCB, errorCB) {
		return utilSrv.handleHttpResponse(
			$http.get(GLOBAL.contextRoot + '/pet/findByStatus?status=' + _.join(statuses, ',')),
			successCB, errorCB
		);
	};
	
	/**
	 * @object method
	 * @name listCategories
	 * @description Lists the available pet categories
	 *
	 * @param {function} successCB - success callback with params:
	 * 								 	- categories {array} the list of categories found
	 * @param {function} errorCB - error callback with params:
	 * 									- error {string} error message if any
	 * 									- exception {object} the exception if any
	 * @returns {object} the http promise
	 */
	service.listCategories = function(successCB, errorCB) {
		return utilSrv.handleHttpResponse(
			$http.get(GLOBAL.contextRoot + '/pet/categories'),
			successCB, errorCB
		);
	};

	return service;
});














