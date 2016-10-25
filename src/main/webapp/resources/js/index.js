angular.module('app', [])
.controller('searchSharesTable', function($rootScope, $scope, $http) {
    $http.get('http://localhost:8080/shares/stock/wig20').
        then(function(response) {
            $scope.shares = response.data;
        });
})
.controller('lolz', function($rootScope, $scope, $http, $location, $window) {
  
	$scope.error = false;

 	var authenticate = function(credentials, callback) {
		var string = 'username=' + $scope.username + '&password=' + $scope.password;

	 	$http({
   			method: 'POST',
    		url: 'http://localhost:8080/login',
    		headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    		data: string
		}).success(function(data) {
	    	if ($scope.username === data.login) {
				$rootScope.authenticated = true;
	    	} else {
	    		$rootScope.authenticated = false;
	   		}
	    	callback && callback();
	    }, function() {
	    	$rootScope.authenticated = false;
	    	callback && callback();
	    });
	}

  	authenticate();
  	var credentials = {};

  	$scope.login = function() {
      	authenticate($scope.credentials, function() {
	        if ($rootScope.authenticated) {
	        	$window.location.href = "/user/print/"+$scope.username;
	        	$scope.error = false;
	        } else {
	        	$scope.error = true;
	        }
     	});
  	};
});

/*
 	$scope.login = function() {
 		var string = 'username=' + $scope.username + '&password=' + $scope.password;
 		$http({
   			method: 'POST',
    		url: 'http://localhost:8080/login',
    		headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    		data: string
		}).then(function(data) {
			$scope.authenticated = true;
  			$scope.error = true;
		})
	}
*/


/*		.success(function (data, status, headers, config) {$scope.error = false; console.log(config.url) })
		.error(function (data, status, headers, config) {$scope.error = true; console.log(config.url)});*/
/*});*/