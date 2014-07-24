'use strict';

angular.module('adpharma').controller('auth', function ($scope, $location, $cookieStore, $window,authorization, api) {
  $scope.title = 'Likeastore. Analytics';
  $scope.user = {j_username: 'manager', j_password: 'test'};
var loginAdress = 'http://localhost:8080/adpharma.server/j_security_check';

  $scope.login = function () {

      var success = function (data) {
          var token = data;
          $window.alert(token) ;
          api.init(token);

          $cookieStore.put('token', token);
          $location.path('/salesOderList');
      };

      var error = function (status) {
           $window.alert(status) ;
           $location.path('/salesOrderEdit');
      };

      authorization.login($scope.user,loginAdress).success(success).error(error);
  };
});
