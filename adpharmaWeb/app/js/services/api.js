'use strict';

angular.module('adpharma').factory('api', function ($http, $cookies) {
  return {
      init: function (token) {
          $http.defaults.headers.common['X-Access-Token'] = token || $cookies.token;
      }
  };
});
