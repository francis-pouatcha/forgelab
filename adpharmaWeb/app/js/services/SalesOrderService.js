'use strict';

angular.module('adpharma').service('SalesOrderService', ['$http', function($http){
	 var urlBase = 'http://localhost:8080/adpharma.server/rest/salesorders';

        this.create = function (saleOrder) {
            return $http.post(urlBase,saleOrder);
        };

        this.update = function (saleOrder) {
            return $http.put(urlBase ,saleOrder);
        };

        this.listAll = function (start, max) {

            return $http.get(urlBase);
        };

        this.urlBase = function(){
           
        }


        
}]);

