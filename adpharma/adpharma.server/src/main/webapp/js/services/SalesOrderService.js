'use strict';

angular.module('adpharma').service('SalesOrderService', ['$http', function($http){
	 var urlBase = '/adpharma.server/rest/salesorders';

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

       this.saveAndClose = function (salesOrder) {
        return $http.put(urlBase+'/saveAndClose/'+salesOrder.id,salesOrder);
       };

    // create a new sales order search input
      this.newSalesOrder = function(){
        return {
                salesOrderItems :[],
                cashDrawer : {},
                customer : {},
                insurance : {},
                vat : {},
                salesAgent : {},
                agency : {}
            } ;
       };
}]);

