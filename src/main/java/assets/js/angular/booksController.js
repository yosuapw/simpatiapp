var myApp = angular.module('myApp');

var booksController = function($scope, $location, dataLoadService){
	
	$scope.data = {
			priceType: null,
			number: null,
			cart: {
				cartItems: []
			}
	}	
	
	$scope.newItem = function () {
		$scope.data.priceType = null;
		$scope.data.number = null;
	}
	
	
	$scope.data.availableTypes = [
	        {key:'adult', value: 'Adult'},
			{key:'children', value: 'Children'}]
	
	$scope.addItem = function () {
		$scope.data.cart.cartItems.push({priceType: $scope.data.priceType,
										number: $scope.data.number});
		$scope.newItem();
	}
	
	$scope.submitData = function () {
		dataLoadService.saveData($scope.data.cart, $location.absUrl());
	}

}

myApp.controller('booksController', booksController); 

//I act a repository for the remote friend collection.
myApp.service(
    "dataLoadService",
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
        	var path = "";
        	if($location.protocol()) path = path.concat($location.protocol());
        	if($location.host()) path = path.concat("://"+$location.host());
        	if($location.port()) path = path.concat(":"+$location.port());
         // path() is also a setter
        	$window.location.href = path + response.data;
        }
    }
);


