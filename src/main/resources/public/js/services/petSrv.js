'use strict';

/**
 * Manages pets
 */
angular.module(window.GLOBAL.appName)

/**
 * @ngdoc service
 * @name petSrv
 * @description This service provides methods to manage pets.
 */
.service('petSrv', function ($http) {
	
	var service = {},
	
		/**
		 * Handles the http responses that return a dk.rbc.petstore.web.Response
		 * TODO: move this in a utility service
		 */
		handleResponse = function(httpPromise, successCB, errorCB) {
			httpPromise.success(function(response) {
				if(response.success) {
					successCB(response.content);
				} 
				else if(errorCB) {
					errorCB(response.errorMessage, response.exception);
				}
			}).error(function(response) {
				if(errorCB) {
					errorCB(response ? response.statusText : undefined);
				}
			});
		
			return httpPromise; 
		};
	
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
		return handleResponse(
			$http.get(GLOBAL.contextRoot + '/pet/list'),
			successCB, errorCB
		);
	};
	
	/**
	 * @object method
	 * @name deletePet
	 * @description Delete the pet with the given id
	 *
	 * @param {int} pet - the pet to delete
	 * @param {function} successCB - success callback with params:
	 * 								 	- deleted {boolean} true if it was deleted, false it it didn't exist
	 * @param {function} errorCB - error callback with params:
	 * 									- error {string} error message if any
	 * 									- exception {object} the exception if any
	 * @returns {object} the http promise
	 */
	service.deletePet = function(pet, successCB, errorCB) {
		return handleResponse(
			$http['delete'](GLOBAL.contextRoot + '/pet/' + pet.id),
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
		return handleResponse(
			$http.get(GLOBAL.contextRoot + '/pet/' + petId),
			successCB, errorCB
		);
	}
	
	/**
	 * @object method
	 * @name findPetByStatus
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
	service.findPetByStatus = function(statuses, successCB, errorCB) {
		return handleResponse(
			$http.get(GLOBAL.contextRoot + '/pet/findByStatus?status=' + _.join(statuses, ',')),
			successCB, errorCB
		);
	}
	
	
	
	
	

	return service;
});














