'use strict';

angular.module(window.GLOBAL.appName)

/**
 * @ngdoc service
 * @name utilSrv
 * @description This service provides some general/common methods 
 */
.service('utilSrv', function () {
	
	var service = {};
	
	/**
	 * Handles the http responses that return a dk.rbc.petstore.web.Response
	 */
	service.handleHttpResponse = function(httpPromise, successCB, errorCB) {
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
	 * Handles the errors returned by the http calls
	 */
	service.handleError = function(message, exception) {
		var err = message ? message : (exception ? exception.message : '');
		toastr.error(err);
		if(exception) {
			console.log(exception);
		} else {
			console.log(message);
		}
	};
		
	
	
	
	
	

	return service;
});














