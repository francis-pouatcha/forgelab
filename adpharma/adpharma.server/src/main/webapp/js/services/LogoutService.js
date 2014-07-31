'use strict';

angular.module('adpharma').service('LogoutService', ['$http','$location','$log', function($http,$location,$log){
    var urlBase = '/adpharma.server/logout';
//logout
    this.logout = function () {
        $log.info("Handle logout");
       $http.get(urlBase).success(function (data, status, headers, config) {
          $location.path("/login")
       }).error(function (data, status, headers, config) {
           $log.info("Handle logout Error");
       });
    };
}]);




