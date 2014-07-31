'use strict';

angular.module('adpharma').controller('auth', ['$scope','$window','$http','$location','$cookies', function($scope,$window,$http,$location,$cookies){
  var loginAdress = '/adpharma.server/j_security_check';
    
    $scope.user = {j_username: 'sales', j_password: 'sales'};
    $scope.message = '';

     $scope.submit = function () {

     $http({
     method: 'POST',
     url: loginAdress,
//    data: $scope.user, use jquery to send form url encoded. Not yet supported by angular.
     data: jQuery.param($scope.user),
     headers: {'Content-Type': 'application/x-www-form-urlencoded'}

     }).success(function (data, status, headers, config) {
		// Form authentication uses the cookie named jsessionid.
//        $window.sessionStorage.token = data;
        $scope.message = 'Welcome';
        $location.path('/salesOrderEdit');
      })
      .error(function (data, status, headers, config) {
    	// Form authentication uses the cookie named jsessionid.
        // Erase the token if the user fails to log in
//        delete $window.sessionStorage.token;

        // Handle login errors here
        $scope.message = 'Error: Invalid user or password';
        $location.path('/login');
      });
    } ;
}]) ;