var myApp = angular.module('myApp', []);

var transportsController = function($scope){
	
	$scope.data = {
			service: null,
			person: null,
			destination: null,
			price: 0
	}
	
	$scope.data.availableServices = ['One Way Transportation from Airport',
	                   'Car Rental'];
	
	$scope.data.availableDestinations = [
	                           {key:0,val:'Kuta, Legian, Seminyak'},
	                           {key:1,val:'Sanur, Nusa Dua, Kerobokan'},
	                           {key:2,val:'Ubud, Tanah Lot'},
	                           {key:3,val:'Candidasa, Bedugul'},
	                           {key:4,val:'Lovina, Amplapura'},
	                           {key:5,val:'Amed, Pemuteran'},
	                           {key:6,val:'Tulamben, Gilimanuk'}];
	
	$scope.data.availablePersons = [
	                                {key:0,val:'2 pax'},
	                                {key:1,val:'3 to 4 pax'},
	                                {key:2,val:'5 to 8 pax'},
	                                {key:3,val:'9 to 13 pax'}];
	
	$scope.data.availableVipPersons = [{key:0,val:'2 pax'},
	                                   {key:1,val:'3 pax'},
	                                   {key:2,val:'4 pax'},
	                                   {key:3,val:'5 pax'},
	                                   {key:4,val:'6 pax'},
	                                   {key:5,val:'7 pax'},
	                                   {key:6,val:'8 pax'},
	                                   {key:7,val:'9 pax'},
	                                   {key:8,val:'10 pax'},
	                                   {key:9,val:'11 pax'},
	                                   {key:10,val:'12 pax'}];

	$scope.data.availableLanguagues = [{key:0,val:'English'},
	                                   {key:0,val:'France'},
	                                   {key:0,val:'Germany'},
	                                   {key:0,val:'Holand'},
	                                   {key:0,val:'Italy'},
	                                   {key:0,val:'Indonesia'},
	                                   {key:0,val:'Malaysia'}
	                                   ]
	function initData() {
		
		function createArray(length) {
		    var arr = new Array(length || 0),
		        i = length;

		    if (arguments.length > 1) {
		        var args = Array.prototype.slice.call(arguments, 1);
		        while(i--) arr[length-1 - i] = createArray.apply(this, args);
		    }

		    return arr;
		}
		
		var array = createArray(7, 4);
		array[0][0] = 13; array[0][1] = 17; array[0][2] = 30; array[0][3] = 38;
		array[1][0] = 17; array[1][1] = 20; array[1][2] = 34; array[1][3] = 42;
		array[2][0] = 25; array[2][1] = 33; array[2][2] = 45; array[2][3] = 58;
		array[3][0] = 37; array[3][1] = 40; array[3][2] = 60; array[3][3] = 74;
		array[4][0] = 50; array[4][1] = 58; array[4][2] = 65; array[4][3] = 78;
		array[5][0] = 53; array[5][1] = 62; array[5][2] = 80; array[5][3] = 88;
		array[6][0] = 57; array[6][1] = 65; array[6][2] = 90; array[6][3] = 104;
		
		$scope.data.defaultPriceArray = array;
		

		
		var array = createArray(7, 4);
		array[0][0] = 20; array[0][1] = 24; array[0][2] = 38; array[0][3] = 48;
		array[1][0] = 24; array[1][1] = 28; array[1][2] = 42; array[1][3] = 52;
		array[2][0] = 35; array[2][1] = 43; array[2][2] = 55; array[2][3] = 68;
		array[3][0] = 47; array[3][1] = 52; array[3][2] = 70; array[3][3] = 84;
		array[4][0] = 60; array[4][1] = 68; array[4][2] = 75; array[4][3] = 88;
		array[5][0] = 66; array[5][1] = 75; array[5][2] = 95; array[5][3] = 100;
		array[6][0] = 70; array[6][1] = 80; array[6][2] = 100; array[6][3] = 118;
		
		$scope.data.guidePriceArray = array;
		

		
		var array = createArray(7, 12);
		array[0][0] = 34; array[0][1] = 45; array[0][2] = 52; array[0][3] = 75; array[0][4] = 80;
		array[0][5] = 85; array[0][6] = 92; array[0][7] = 106; array[0][8] = 114; array[0][9] = 120;
		array[0][10] = 126;

		array[1][0] = 38; array[1][1] = 48; array[1][2] = 56; array[1][3] = 78; array[1][4] = 84;
		array[1][5] = 90; array[1][6] = 96; array[1][7] = 110; array[1][8] = 118; array[1][9] = 125;
		array[1][10] = 130;

		array[2][0] = 46; array[2][1] = 60; array[2][2] = 66; array[2][3] = 90; array[2][4] = 96;
		array[2][5] = 100; array[2][6] = 108; array[2][7] = 126; array[2][8] = 134; array[2][9] = 140;
		array[2][10] = 146;

		array[3][0] = 62; array[3][1] = 68; array[3][2] = 78; array[3][3] = 105; array[3][4] = 112;
		array[3][5] = 120; array[3][6] = 125; array[3][7] = 145; array[3][8] = 150; array[3][9] = 158;
		array[3][10] = 164;

		array[4][0] = 74; array[4][1] = 88; array[4][2] = 95; array[4][3] = 110; array[4][4] = 116;
		array[4][5] = 125; array[4][6] = 130; array[4][7] = 148; array[4][8] = 155; array[4][9] = 162;
		array[4][10] = 168;

		array[5][0] = 78; array[5][1] = 94; array[5][2] = 100; array[5][3] = 128; array[5][4] = 135;
		array[5][5] = 140; array[5][6] = 148; array[5][7] = 162; array[5][8] = 168; array[5][9] = 175;
		array[5][10] = 182;

		array[6][0] = 82; array[6][1] = 98; array[6][2] = 104; array[6][3] = 136; array[6][4] = 142;
		array[6][5] = 148; array[6][6] = 155; array[6][7] = 180; array[6][8] = 184; array[6][9] = 190;
		array[6][10] = 198;
		
		$scope.data.guideVipPriceArray = array;
		
		
	}
	
	initData();
	
	$scope.calculate = function () {
		if ($scope.data.destination && $scope.data.person && $scope.data.guide != true && $scope.data.vip != true) {
			$scope.data.price = $scope.data.defaultPriceArray[$scope.data.destination][$scope.data.person];
		} else if ($scope.data.destination && $scope.data.person && $scope.data.guide == true && $scope.data.vip != true) {
			$scope.data.price = $scope.data.guidePriceArray[$scope.data.destination][$scope.data.person];
		} else if ($scope.data.destination && $scope.data.vipPerson && $scope.data.vip == true) {
			$scope.data.price = $scope.data.guideVipPriceArray[$scope.data.destination][$scope.data.vipPerson];
		} else {
			$scope.data.price = 0;
		}
	}
	
	$scope.guideChange = function () {
		if ($scope.data.guide == false) $scope.data.vip = false;
		$scope.calculate();
	}
	
	$scope.vipChange = function () {
		$scope.data.person = null;
		$scope.data.vipPerson = null;
		$scope.calculate();
	}
	

}
myApp.controller('transportsController', transportsController); 
