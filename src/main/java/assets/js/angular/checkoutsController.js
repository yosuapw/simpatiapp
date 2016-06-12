var myApp = angular.module('myApp');

var booksController = function($scope, $location, checkoutService){
	
	$scope.data = {
			priceType: null,
			number: null
	}	
	
	$scope.newItem = function () {
		$scope.data.priceType = null;
		$scope.data.number = null;
	}
	
	$scope.message = "";
	
	$scope.enableField = true;
	
	$scope.data.availablePaymentTypes = [
	        {key:'banktransfer', value: 'Bank Transfer'},
			{key:'paypal', value: 'paypal'}];
	
	
	$scope.submitData = function () {
		checkoutService.saveData($scope.data.personDetail, $location.absUrl()).then(function (data) {
			if (data === 200) {
				$scope.message = "success";
				$scope.enableField = false;
			} else {
				$scope.message = "failed";
			}
		});
	}

}

myApp.controller('CheckoutsController', booksController); 

//I act a repository for the remote friend collection.
myApp.service(
    "checkoutService",
    function( $http, $q, $location, $window) {
        // Return public API.
        return({
        	saveData: saveData
        });
        // ---
        // PUBLIC METHODS.
        // ---
        // I add a friend with the given name to the remote collection.
        function saveData(data, param) {
            var request = $http({
                method: "post",
                url: param,
                data: data
            });
            return( request.then( handleSuccess, handleError ) );
        }
        
        
        function handleError( response ) {
            // The API response from the server should be returned in a
            // nomralized format. However, if the request was not handled by the
            // server (or what not handles properly - ex. server error), then we
            // may have to normalize it on our end, as best we can.
            if (
                ! angular.isObject( response.data ) ||
                ! response.data.message
                ) {
                return( $q.reject( "An unknown error occurred." ) );
            }
            // Otherwise, use expected error message.
            return( $q.reject( response.data.message ) );
        }
        
        // I transform the successful response, unwrapping the application data
        // from the API response payload.
        function handleSuccess( response ) {
        	return response.status;
        }
    }
);


