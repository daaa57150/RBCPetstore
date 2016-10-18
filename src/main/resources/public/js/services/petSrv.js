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
	
	var service = {};
	
	/**
	 * @object method
	 * @name getPets
	 * @description Get the pets
	 *
	 * @param {function} successCB - success callback with params:
	 * 								 	- pets {Array} the list of pets
	 * @param {function} errorCB - error callback with params:
	 * 									- error {string} error message if any
	 * 									- exception {object} the exception if any
	 * @returns {object} the promise
	 */
	service.getPets = function(successCB, errorCB) {
		return $http.get(GLOBAL.contextRoot + '/pet/list')
			.success(function(response) {
				if(response.success) {
					successCB(response.content);
				} 
				else {
					if(errorCB) {
						errorCB(response.errorMessage, response.exception);
					}
				}
			}).error(function(response) {
				if(errorCB) {
					errorCB(response.statusText);
				}
			});
	};

	return service;
});
