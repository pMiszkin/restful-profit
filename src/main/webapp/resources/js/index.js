angular.module('app', ['angularUtils.directives.dirPagination'])
.run(function($rootScope, $http) {
	$rootScope.authenticate = function(callback) {
		$http.get('/user').success(function(data) {
			if (data.name != "anonymousUser") {
				$rootScope.authenticated = true;
				$rootScope.username = data.name;
			} else {
				$rootScope.authenticated = false;
			}
			callback && callback();
		}).error(function() {
			$rootScope.authenticated = false;
			callback && callback();
		});
	}

	$rootScope.getPocket = function() {
		return $http.get('/pocket/'+$rootScope.username);
	}
})
.controller('mainController', function($rootScope) {
	$rootScope.authenticate();
})
.controller('getSharesAndIndices', function($rootScope, $scope, $http, $window) {
    $http.get('/shares/all').
        then(function(response) {
            $scope.shares = response.data;
        });

    $http.get('/shares/indices/all').
        then(function(response) {
            $scope.indices = response.data;
        });

	$scope.buyingError = false;

    $scope.open = function(share) {
    	if($rootScope.authenticated!=true) {
    		$window.location.href = "/login";
    	}
    	else {
    		$scope.buyingError = false;
    		$scope.share = share;
    		$rootScope.getPocket().then(function(data) {
    			$scope.pocket = data.data;
    		});
    	}
    }

    $scope.buyShares = function() {
	 	$http({
   			method: 'POST',
    		url: '/transfer/buy',
    		headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    		params: {'name': $scope.share.shortcut, 'number': $scope.number}
		}).success(function() {
			$window.location.href = "/pocket/"+$rootScope.username;
		}).error(function(response) {
			$scope.buyingError = true;
			$scope.buyingErrorResponse = response;
		});
   } 
})
.controller('loginPageController', function($rootScope, $scope, $http, $window) {

  	$scope.login = function(credentials, callback) {
		var user = 'username=' + $scope.username + '&password=' + $scope.password;

	 	$http({
   			method: 'POST',
    		url: '/login',
    		headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    		data: user
		}).success(function(data, status) {
			$rootScope.authenticate(function() {
				if ($rootScope.authenticated) {
					$window.location.href = "/";
					$scope.error = false;
				} else {
					$scope.error = true;
				}
			});
		}).error(function(data) {
			$window.location.href = "/login";
			$scope.error = true;
			$rootScope.authenticated = false;
		})
	};
});