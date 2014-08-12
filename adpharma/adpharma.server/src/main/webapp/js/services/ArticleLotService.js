'use strict';

angular.module('adpharma').service('ArticleLotService', ['$http', function($http){
	 var urlBase = '/adpharma.server/rest/articlelots';

        this.findBy = function (searchInput) {
        return $http.post(urlBase+'/findByLike' ,searchInput);
        };

    // create a new article lot Search input
    this.newArticleLotSearchInput = function(){
        return {
            entity : {},
            fieldNames : [],
            max : 100,
            start : 0
        } ;
    };
}]);




