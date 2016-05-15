var cartApp = angular.module('myApp', []);

var numbersController = function($scope, loadTotalCart){

    $scope.loadMore = function () {
    	loadTotalCart.loadData().then(function (data) {
    		$scope.toursData = data;
    	});
    }
    
    $scope.$on('cancelItem', function() {
    	$scope.loadMore();
    });  
    
    $scope.loadMore();
}
cartApp.controller('CartsController', numbersController); 


// I act a repository for the remote friend collection.
cartApp.service(
    "loadTotalCart",
    function( $http, $q ) {
        // Return public API.
        return({
            loadData: loadData
        });
        // ---
        // PUBLIC METHODS.
        // ---
        // I add a friend with the given name to the remote collection.
        function loadData( ) {
            var request = $http({
                method: "get",
                url: "/service/totalcart"
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

