'use strict';

angular.module('adpharma').service('ArticleLotService', ['$http', function($http){
	 var urlBase = '/adpharma.server/rest/articlelots';

        this.findByLike = function (searchInput) {
        return $http.post(urlBase+'/findByLike' ,searchInput);
        };
        
        this.findBy = function (searchInput) {
            return $http.post(urlBase+'/findArticleLotByInternalPicWhitRealPrice' ,searchInput);
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




