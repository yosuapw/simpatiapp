var cartApp = angular.module('myApp', []);

var numbersController = function($scope, confirmPaymentService){

    $scope.loadMore = function () {
    	confirmPaymentService.loadData().then(function (data) {
    		$scope.bookings = data;
    	});
    }
    
    $scope.confirm = function (index, item) {
    	confirmPaymentService.confirm({link: item.payment.link}).then(function () {
    		
    	});
    }
    
    $scope.loadMore();
}
cartApp.controller('AdminConfirmPaymentsController', numbersController); 


// I act a repository for the remote friend collection.
cartApp.service(
    "confirmPaymentService",
    function( $http, $q ) {
        // Return public API.
        return({
            loadData: loadData,
            confirm: confirm
        });
        // ---
        // PUBLIC METHODS.
        // ---
        // I add a friend with the given name to the remote collection.
        function loadData( ) {
            var request = $http({
                method: "get",
                url: "/service/boss/confirm/payment/list"
            });
            return( request.then( handleSuccess, handleError ) );
        }
        function confirm(link) {
            var request = $http({
                method: "post",
                url: "/service/boss/confirm/payment",
                data: link
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

