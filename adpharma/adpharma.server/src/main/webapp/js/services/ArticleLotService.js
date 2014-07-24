'use strict';

angular.module('adpharma').service('ArticleLotService', ['$http', function($http){
	 var url = '/adpharma.server/rest/articlelots';

        this.create = function (saleOrder) {
            return $http.post(url,saleOrder);
        };

        this.update = function (saleOrder) {
            return $http.put(url ,saleOrder);
        };

        this.findBy = function (searchInput) {
            return $http.post(url ,searchInput);
        };

        this.urlBase = function(){
           
        }


        
}]);




