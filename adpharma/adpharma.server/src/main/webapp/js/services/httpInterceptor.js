'use strict';

angular.module('adpharma').factory('httpInterceptor', function($q, $window, $location) {
  return {
    request: function (config) {
      config.headers = config.headers || {};
      if ($window.sessionStorage.token) {
        config.headers.Authorization = 'Bearer ' + $window.sessionStorage.token;
      }
      return config;
    },
    response: function (response) {
      if (response.status === 401) {
        $window.alert("401");
      }
      if (response.status === 412) {
        $window.alert("412");
      }
      return response || $q.when(response);
    }
  };
});
