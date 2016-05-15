var cartApp = angular.module('myApp');

var numbersController = function($scope, $rootScope, cartsService){

    $scope.loadCarts = function () {
    	cartsService.getCarts().then(function (data) {
    		$scope.cart = data;
    	});
    }
    
    $scope.cancelItem = function (index, item) {
    	cartsService.deleteItem(item).then(function () {
    		$scope.cart.cartItems.splice(index, 1);
            $rootScope.$broadcast('cancelItem');
    	});
    }
    $scope.loadCarts();
}
cartApp.controller('CartsDetailController', numbersController); 


// I act a repository for the remote friend collection.
cartApp.service(
    "cartsService",
    function( $http, $q ) {
        // Return public API.
        return({
            getCarts: getCarts,
            deleteItem: deleteItem
        });
        // ---
        // PUBLIC METHODS.
        // ---
        // I add a friend with the given name to the remote collection.
        function getCarts( ) {
            var request = $http({
                method: "get",
                url: "/service/cart"
            });
            return( request.then( handleSuccess, handleError ) );
        }
        function deleteItem(data) {
            var request = $http({
                method: "post",
                url: "/service/cart/delete",
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
            return( response.data );
        }
    }
);

