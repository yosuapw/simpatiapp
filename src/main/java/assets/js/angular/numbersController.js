var myApp = angular.module('numbersApp', ['infinite-scroll']);

var numbersController = function($scope, dataLoadService){

    $scope.loadMore = function () {
    	dataLoadService.loadData().then(function (data) {
    		$scope.toursData = data;
    	});
    }
    $scope.loadMore();
}
myApp.controller('numbersController', numbersController); 


// I act a repository for the remote friend collection.
myApp.service(
    "dataLoadService",
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
                url: "service/tours/all"
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


angular.bootstrap($('#numbersApp'),['numbersApp']);