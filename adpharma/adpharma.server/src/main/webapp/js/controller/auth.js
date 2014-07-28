'use strict';

angular.module('adpharma').controller('auth', ['$scope','$window','$http','$location','$cookies', function($scope,$window,$http,$location,$cookies){
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
  $window.alert("Suces"+headers+$cookies.JSESSIONID);
      })
      .error(function (data, status, headers, config) {
         $window.alert("Error " +headers+$cookies.JSESSIONID);
      });
    } ;

}]) ;