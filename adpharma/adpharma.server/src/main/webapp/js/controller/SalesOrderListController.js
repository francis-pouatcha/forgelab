'use strict';

angular.module('adpharma').controller('SalesOrderListController', ['$scope','$window','SalesOrderService', function($scope,$window,SalesOrderService){
  $scope.salesOrders = [] ;
  $scope.selectedSalesOrder = {} ;
  $scope.status ;

  listAll();

  function listAll() {
        SalesOrderService.listAll(1,20)
            .success(function (SalesOrderSearchResult) {
                $scope.salesOrders = SalesOrderSearchResult.resultList;
            })
            .error(function (data,status) {
                $scope.status = 'Unable to load sales orders data: ' + data.message;
                $window.alert(status+data);
            });
    }

}]) ;