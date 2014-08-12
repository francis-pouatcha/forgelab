'use strict';

angular.module('adpharma').factory('authorization', function ($http) {
  return {
      login: function (user,loginAdress) {
          return $http({
    method: 'POST',
    url: loginAdress,
    data: user,
    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
});
      }
  };
});
