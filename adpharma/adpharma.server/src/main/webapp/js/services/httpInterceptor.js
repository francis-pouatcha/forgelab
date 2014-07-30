'use strict';

angular.module('adpharma').factory('httpInterceptor', function httpInterceptor ($q, $window, $location) {
  return function (promise) {
      var success = function (response) {
          return response;
      };

      var error = function (response) {
    	  // Adpharma server react on 403 forbiden and not on 401. We can keep both.
          if (response.status === 401 || response.status === 403) {
        	  // Forward to login page and return response after setting the location, if not
        	  // the application will display an error.
              $location.url('/login');
              return response;
          } else {
        	  return $q.reject(response);
          }
      };
      return promise.then(success, error);
  };
});
