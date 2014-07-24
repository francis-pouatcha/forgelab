'use strict';

angular.module('adpharma').factory('authorization', function ($http, config) {
  var url = config.analytics.url;

  return {
      login: function (credentials) {
          return $http.post(url + '/auth', credentials);
      }
  };
});
