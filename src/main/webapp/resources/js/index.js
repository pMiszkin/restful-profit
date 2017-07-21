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
.controller('mainController', function($rootScope, $scope, $http, $window) {
	$rootScope.authenticate();

	$scope.logout = function() {
		$http.post('/logout', {}).success(function() {
    		$rootScope.authenticated = false;
    		$window.location.href = "/";
		}).error(function(data) {
			$rootScope.authenticated = false;
		});
	}
})
	/**
		* homepage controller 
	**/
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
    		$scope.number = 1;
    		$scope.share = share;
    		$rootScope.getPocket().then(function(data) {
    			$scope.pocket = data.data;
    		});
    	}
    }

    $scope.buyShares = function() {
	 	$http({
   			method: 'POST',
    		url: '/transfer/purchases',
    		headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    		params: {'name': $scope.share.isin, 'number': $scope.number}
		}).success(function() {
			$window.location.href = "/user/profile/"+$rootScope.username;
		}).error(function(response) {
			$scope.buyingError = true;
			$scope.buyingErrorResponse = response;
		});
	}
})
/**
	* /login controller
**/
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
		});
	};
})
/**
	* /register controller
**/
.controller('registerPageController', function($rootScope, $scope, $http, $window) {


  	$scope.register = function(form, credentials, callback) {
  		if(!form.$valid)
  			return;

		var user = {'login' : $scope.username, 'email' : $scope.email, 'password' : $scope.password};

		$http.post('/user/add', user)
		.success(function(data, status) {
			$window.location.href = "/login";
		}).error(function(response) {
			console.log(response);
			$scope.error_msg = response;
			$scope.error = true;
		});
	};
})
/*
	* /share/company/{isin} controller (share site)
*/
.controller('shareController', function($rootScope, $scope, $http, $location, $window) {
	var share = $location.absUrl();
	$scope.number = 1;
	$scope.buyingError = false;


	$http({
		method: 'get',
    	url: '/shares/company',
    	headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    	params: {'isin': share.substr(share.length-12)}
	}).success(function(response) {
		$scope.share = response;
		$scope.show_table = true;
		var date = new Date($scope.share.currentQuotation.date);
		/*format date*/
		var hour = date.getHours();
		var min = date.getMinutes();
		var sec = date.getSeconds();

		hour = (hour < 10 ? "0" : "") + hour;
		min = (min < 10 ? "0" : "") + min;
		sec = (sec < 10 ? "0" : "") + sec;

		$scope.formattedDate = hour+':'+min+':'+sec;
	}).error(function(response) {
		$scope.share = {'name': 'Share "'+share.substr(share.length-12)+'" has not found.'};
		$scope.show_table = false;
	});
	
	$scope.buyShares = function() {
	 	$http({
   			method: 'POST',
    		url: '/transfer/purchases',
    		headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    		params: {'name': $scope.share.isin, 'number': $scope.number}
		}).success(function() {
			$window.location.href = "/user/profile/"+$rootScope.username;
		}).error(function(response) {
			$scope.buyingError = true;
			$scope.buyingErrorResponse = response;
		});
	}

	$scope.$watch('username', function (value) {
		if(value) {
			$rootScope.getPocket().then(function(data) {
	    		$scope.pocket = data.data;
			});
		}
	});
})
/*
	* user profile page /user/profile/{username}
*/
.controller('userController', function($rootScope, $scope, $http, $location, $window)  {
	$scope.shares = [];

	$scope.$watch('username', function (value) {
		if(value) {
			$http({
				method: 'GET',
				url: '/user/print/'+$rootScope.username,
				headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			}).success(function(response) {
				$scope.user = response;
				angular.forEach($scope.user.profile.pocket.shares, function(value, key){
					$http({
						method: 'get',
				    	url: '/shares/company',
				    	headers: {'Content-Type': 'application/x-www-form-urlencoded'},
				    	params: {'isin': key}
					}).success(function(response) {
						$scope.shares.push(response);
					});
				});
			});
		}
	});

	$scope.buyingError = false;

	$scope.buyShares = function() {
	 	$http({
   			method: 'POST',
    		url: '/transfer/purchases',
    		headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    		params: {'name': $scope.share.isin, 'number': $scope.number}
		}).success(function() {
			$window.location.href = "/user/profile/"+$rootScope.username;
		}).error(function(response) {
			$scope.buyingError = true;
			$scope.buyingErrorResponse = response;
		});
	}

    $scope.open = function(share) {
    	if($rootScope.authenticated!=true) {
    		$window.location.href = "/login";
    	}
    	else {
    		$scope.buyingError = false;
    		$scope.number = 1;
    		$scope.share = share;
    		$rootScope.getPocket().then(function(data) {
    			$scope.pocket = data.data;
    		});
    	}
    }

    $scope.sellingError = false;

	$scope.sellShares = function(share, number) {
	 	$http({
   			method: 'POST',
    		url: '/transfer/sales',
    		headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    		params: {'name': share.isin, 'number': number}
		}).success(function() {
			$window.location.href = "/user/profile/"+$rootScope.username;
		}).error(function(response) {
			$scope.sellingError = true;
			$scope.sellingErrorResponse = response;
		});
	}
    
});