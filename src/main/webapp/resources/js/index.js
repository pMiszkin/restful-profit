angular.module('app', ['angularUtils.directives.dirPagination'])
.controller('getSharesTable', function($rootScope, $scope, $http) {
    $http.get('/shares/all').
        then(function(response) {
            $scope.shares = response.data;
        });

	$scope.isActive = function(user) {
		return user.User.Stats[0].active === "1";
	};
})
.controller('getStockIndices', function($rootScope, $scope, $http) {
    $http.get('/shares/indices/all').
        then(function(response) {
            $scope.indices = response.data;
        }); 
})
.controller('loginPageController', function($rootScope, $scope, $http, $location, $window) {
  
  	$scope.error = false;
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

  	var authenticate = function(credentials, callback) {
		var string = 'username=' + $scope.username + '&password=' + $scope.password;

	 	$http({
   			method: 'POST',
    		url: '/login',
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
	};
});