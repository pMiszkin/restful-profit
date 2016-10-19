angular.module('app', [])
.controller('searchSharesTable', function($scope, $http) {
    $http.get('http://localhost:8080/shares/stock/wig20').
        then(function(response) {
            $scope.shares = response.data;
        });
});