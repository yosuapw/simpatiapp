var myApp = angular.module('myApp');

var booksController = function($scope, $location, checkoutService, $window){
	
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
	        {key:'creditcard', value: 'Credit Card'}];
	
	
	$scope.submitData = function () {
		checkoutService.saveData($scope.data.personDetail, $location.absUrl()).then(function (data) {
			debugger
			if (data.status === 200) {
				if (data.data.statusCode == 201) {
					$scope.message = "processing connection to the 3rd party payment system, please wait..";
					$scope.enableField = false;
					$window.location.href = data.data.redirectUrl;
				} else if (data.data.statusMessage) {
					$scope.message = data.data.statusMessage;
				}
				else {
					$scope.message = "we are experiencing a connection issue to the 3rd party payment system, please kindly retry it 10 minutes later.";
				}
			} else {
				$scope.message = "we are experiencing a connection issue to the 3rd party payment system, please kindly retry it 10 minutes later.";
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
        	return response;
        }
    }
);


