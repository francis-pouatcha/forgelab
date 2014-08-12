'use strict';

angular.module('adpharma').controller('SalesOrderListController', ['$scope','$window','SalesOrderService', function($scope,$window,SalesOrderService){
  $scope.salesOrders = [] ;
  $scope.selectedSalesOrder = {} ;
  $scope.status ;

}]) ;