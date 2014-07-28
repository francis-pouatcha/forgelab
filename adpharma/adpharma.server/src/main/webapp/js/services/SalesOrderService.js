'use strict';

angular.module('adpharma').service('SalesOrderService', ['$http', function($http){
	 var urlBase = '/adpharma.server/rest/salesorders';
   var url = '/adpharma.server/rest/articlelots/findByLike';

        this.create = function (saleOrder) {
            return $http.post(urlBase,saleOrder);
        };

        this.listAll = function (start, max) {

            return $http({
                          url: urlBase, 
                          method: "GET",
                          params: {start: start, max: max}
                          });
        };

        this.findBy = function (searchInput) {
            return $http.post(url ,searchInput);
        };


}]);

