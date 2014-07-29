'use strict';

angular.module('adpharma').controller('SalesOrderEditController', ['$scope','$window','SalesOrderService', function($scope,$window,SalesOrderService){
	$scope.salesOrder = [];
	$scope.lot = {};
	$scope.status ;

	// processing sales order
	$scope.processingSales = {};
	$scope.processingSales.salesOrderItems = [];
	$scope.processingSales.cashDrawer = {};
	$scope.processingSales.customer = {};
	$scope.processingSales.insurance = {};
	$scope.processingSales.vat = {};
	$scope.processingSales.salesAgent = {};
	$scope.processingSales.agency = {};
    $scope.processingSales.id = null;

	// article lot search input 
	$scope.articleLotSearchInput = {} ;
	$scope.articleLotSearchInput.entity = {} ;
	$scope.articleLotSearchInput.fieldNames = [];
	$scope.articleLotSearchInput.max = 30;
	$scope.articleLotSearchInput.start = 0;
	$scope.states = [] ;
	$scope.selected = undefined;


	listAll();
	salesOrderItem();
//	newSalesOrder();

	/*
create anew sales order item 
	 */
	function salesOrderItem(){
		$scope.salesOrderItem = {} ;
		$scope.salesOrderItem.article = {} ;
		$scope.salesOrderItem.vat={};
	};

	function newSalesOrder(){
		SalesOrderService.create($scope.processingSales)
		.success(function (salesOrder) {
			if(salesOrder){
				$scope.processingSales = salesOrder ;
         
			}
		}).error(function (data,status) {
	    	$window.alert(data);
    });

	};


	function listAll() {
		SalesOrderService.listAll(1,10)
		.success(function (SalesOrderSearchResult) {
			$scope.salesOrder = SalesOrderSearchResult.resultList;
		})
		.error(function (data,status) {
			$scope.status = 'Unable to load sales orders data: ' + data.message;
			$window.alert(status+data);
		});
	} ;
	/*
   reset sales order item object 
	 */
	$scope.newSalesOrderItem =  function(){
		salesOrderItem();
	};

	$scope.newSalesOrder = function(){
		newSalesOrder();
	};

	$scope.salesItemFromLot =  function(item,model,label){
		if(model){
			$scope.salesOrderItem.article.id = model.article.id;
			$scope.salesOrderItem.article.articleName = model.article.articleName;
			$scope.salesOrderItem.article.version = model.article.version;
			$scope.salesOrderItem.article.pic= model.article.pic;
			$scope.salesOrderItem.internalPic = model.internalPic;
			$scope.salesOrderItem.vat.id = model.vat.id;
			$scope.salesOrderItem.vat.version = model.vat.version;
			$scope.salesOrderItem.vat.name = model.vat.name;
			$scope.salesOrderItem.vat.active = model.vat.active;
			$scope.salesOrderItem.vat.rate = model.vat.rate;
			$scope.salesOrderItem.orderedQty = 1;
			$scope.salesOrderItem.salesPricePU = model.salesPricePU;
			$scope.salesOrderItem.purchasePricePU = model.purchasePricePU;
			$scope.salesOrderItem.totalSalePrice = model.salesPricePU;
		}
	};

	$scope.calculateTotalSalesPrice =  function(){

		$scope.salesOrderItem.totalSalePrice = $scope.salesOrderItem.salesPricePU * $scope.salesOrderItem.orderedQty ;
	};

	$scope.handleItemSelection =  function(index){

		if(index){
			$scope.salesOrderItem.article.articleName = $scope.salesOrder[index].customer.fullName ;
			$scope.salesOrderItem.internalPic = $scope.salesOrder[index].soNumber ;
			$scope.salesOrderItem.orderedQty = 1;
			$scope.salesOrderItem.salesPricePU =  $scope.salesOrder[index].amountBeforeTax ;
			$scope.salesOrderItem.purchasePricePU = $scope.salesOrder[index].amountAfterTax ;
		}
	};

	$scope.handleItemRevoved =  function(index){

		if(index){
			$scope.salesOrder.splice(index,1);
		}
	};

	$scope.handleArticleNameChange =  function(articleName){
		var articleLots = [];
		var addresses = [];
		if(articleName){
			$scope.articleLotSearchInput.entity.articleName = $scope.salesOrderItem.article.articleName ;
			$scope.articleLotSearchInput.fieldNames= [] ;
			$scope.articleLotSearchInput.fieldNames.push("articleName") ;
			SalesOrderService.findBy($scope.articleLotSearchInput)
			.success(function (articleLotSearchResult) {
				articleLots = articleLotSearchResult.resultList ;

				angular.forEach(articleLots, function(item){
					$scope.states.push(item);
				});
			})
			.error(function (data,status) {
				$scope.status = 'Unable to load sales orders data: ' + data.message;
				$scope.states =  [] ;
			});
		}else{
			$scope.articleLotSearchInput.fieldNames= [] ;
			addresses =  [] ;
		}


	};


}]) ;