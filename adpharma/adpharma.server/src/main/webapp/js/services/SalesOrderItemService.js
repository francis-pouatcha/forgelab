'use strict';
 angular.module('adpharma').service('SalesOrderItemService', ['$http', function($http){
	 var urlBase = '/adpharma.server/rest/salesorderitems';

         this.create = function (saleOrderItem) {
            return $http.post(urlBase,saleOrderItem);
         };

         this.update = function (saleOrderItem) {
            return $http.put(urlBase+'/'+saleOrderItem.id ,saleOrderItem);
         };

         this.findBy = function (salesOrderItemSearchInput) {

             return $http.post(urlBase+'/findBy' ,salesOrderItemSearchInput);
        };

       this.deleteById = function (id) {

         return $http.delete(urlBase+'/'+id);
       };

     // create a new sales order item
     this.newSalesOrderItem = function(){
         return {
             article : {},
             vat : {},
             salesOrder : {}
         } ;
     };

     // create a new sales order item
     this.newSalesOrderSearchInput = function(){
         return {
             entity : {},
             fieldNames : [],
             max : 100,
             start : 0
         } ;
     };
}]);




