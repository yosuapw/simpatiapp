var myApp = angular.module('myApp');

var booksController = function($scope, $location, dataLoadService){
	
	$scope.message = "";
	
	$scope.dataLoaded = false;
	
	$scope.dateMinLimit = moment(new Date()).add(30, 'days').format("YYYY-MM-DD");
	
	$scope.data = {
			priceType: null,
			bookDate: null,
			number: null,
			item: null,
			cart: {
				cartItems: []
			}
	}	    
	
	$scope.loadPrice = function () {
		var id = getId($location.absUrl());
		var link = getLink($location.absUrl());
    	dataLoadService.loadPrice(id, link).then(function (data) {
    		$scope.tourData = data;
    		$scope.dataLoaded = true;
    	});
    }
    
    var getId = function(absUrl) {
    	var parser = absUrl.split("/");
    	return parser[4];
    };
    
    var getLink = function(absUrl) {
    	var parser = absUrl.split("/");
    	return parser[5];
    };
	
    $scope.loadPrice();
	
	$scope.newItem = function () {
		$scope.data.priceType = null;
		$scope.data.bookDate = null;
		$scope.data.number = null;
	}
	
	
	$scope.data.availableTypes = [
	        {key:'adult', value: 'adult'},
			{key:'children', value: 'children'}]
	
	$scope.addItem = function () {
		
		var checkResult = checkItemData($scope.data.priceType, $scope.data.number, $scope.data.bookDate);
		if (checkResult) {
			var total = calculatePrice($scope.data.priceType, $scope.data.number);
			if (total && total > 0) {
				$scope.data.cart.cartItems.push({priceType: $scope.data.priceType,
												number: $scope.data.number, 
												item: $scope.tourData.headTitle,
												bookDate: $scope.data.bookDate,
												total: total});
				$scope.newItem();
			} else {
				$scope.message = "type for this destination is not provided, please change to children/adult";
			}
		}
	}
	
	$scope.removeItem = function (index) {
		$scope.data.cart.cartItems.splice(index, 1);
	}
	
	var checkItemData = function(priceType, number, bookDate) {
		if(!priceType || !number || !bookDate) {
			$scope.message = "please complete all data";
			return false;
		} else {
			$scope.message = "";
			return true;
		}
	}
	
	var calculatePrice = function (priceType, number) {
		return $scope.tourData.prices[priceType] * number;
	}
	
	$scope.submitData = function () {
		dataLoadService.saveData($scope.data.cart, $location.absUrl());
	}

}

myApp.controller('booksController', booksController); 

//I act a repository for the remote friend collection.
myApp.service(
    "dataLoadService",
    function( $http, $q, $window) {
        // Return public API.
        return({
        	saveData: saveData,
        	loadPrice: loadPrice
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
        

        function loadPrice(id, link) {
            var request = $http({
                method: "get",
                url: "/service/tours/"+id+"/"+link
            });
            return( request.then( handlePriceSuccess, handleError ) );
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
         // path() is also a setter
        	$window.location.href =  response.data;
        }
        function handlePriceSuccess( response ) {
            return( response.data );
        }
    }
);


