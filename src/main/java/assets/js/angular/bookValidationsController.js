var cartApp = angular.module('myApp');

var numbersController = function($scope, bookValidationService, $location){
	
	$scope.message = "";
	
	$scope.state = {
			confirmed: false
	};

    $scope.loadMore = function () {
    	bookValidationService.loadData(getLink($location.absUrl())).then(function (data) {
    		$scope.cart = data;
    		if ($scope.cart.payment.status == "confirmPayment") {
    			paymentConfirmed();
    		}
    	});
    }
    
    var getLink = function(absUrl) {
    	var parser = absUrl.split("/");
    	return parser[5];
    };
    
    $scope.confirmPayment = function() {
    	bookValidationService.confirmPayment(getLink($location.absUrl())).then(function() {
			paymentConfirmed();
    	});
    };
    
    var paymentConfirmed = function() {
		$scope.message = "Please wait for 2/24 hours for confirmation. We will send you the payment confirmation."
    	$scope.state.confirmed = true;
    }
    
    $scope.loadMore();
}
cartApp.controller('BookValidationController', numbersController); 


// I act a repository for the remote friend collection.
cartApp.service(
    "bookValidationService",
    function( $http, $q ) {
        // Return public API.
        return({
            loadData: loadData,
            confirmPayment: confirmPayment
        });
        // ---
        // PUBLIC METHODS.
        // ---
        // I add a friend with the given name to the remote collection.
        function loadData(link) {
            var request = $http({
                method: "get",
                url: "/service/book/validate/" + link
            });
            return( request.then( handleSuccess, handleError ) );
        }
        
        function confirmPayment(link) {
            var request = $http({
                method: "get",
                url: "/service/book/confirm/" + link
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
            return( response.data );
        }
    }
);

