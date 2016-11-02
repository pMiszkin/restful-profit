angular.module('app', ['angularUtils.directives.dirPagination'])
.run(function($rootScope, $http) {
	$rootScope.authenticate = function(callback) {
		$http.get('/user').success(function(data) {
			if (data.name != "anonymousUser") {
				$rootScope.authenticated = true;
			} else {
				$rootScope.authenticated = false;
			}
			callback && callback();
		}).error(function() {
			$rootScope.authenticated = false;
			callback && callback();
		});
	}
})
.controller('mainController', function($rootScope, $scope, $http) {
	$rootScope.authenticate();
})
.controller('getSharesAndIndices', function($rootScope, $scope, $http) {
    $http.get('/shares/all').
        then(function(response) {
            $scope.shares = response.data;
        });

    $http.get('/shares/indices/all').
        then(function(response) {
            $scope.indices = response.data;
        }); 

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