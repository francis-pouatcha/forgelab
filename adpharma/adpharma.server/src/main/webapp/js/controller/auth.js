'use strict';

angular.module('adpharma').controller('auth', ['$scope','$window','$http','$location', function($scope,$window,$http,$location){
  var loginAdress = 'http://localhost:8080/adpharma.server/j_security_check';
    
    $scope.user = {j_username: 'manager', j_password: 'test'};
    $scope.message = '';

  $scope.submit = function () {
    $http({
    method: 'POST',
    url: loginAdress,
    data: $scope.user,
    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
}).success(function (data, status, headers, config) {
        $window.sessionStorage.token = data;
        $scope.message = 'Welcome';
        $location.path('/salesOderList');
      })
      .error(function (data, status, headers, config) {
        // Erase the token if the user fails to log in
        delete $window.sessionStorage.token;

        // Handle login errors here
        $scope.message = 'Error: Invalid user or password';
        $location.path('/login');
      });
    } ;

}]) ;