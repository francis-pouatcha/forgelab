'use strict';

angular.module('adpharma').controller('SalesOrderEditController', ['$scope','$window','$log','SalesOrderService','SalesOrderItemService','ArticleLotService','$modal', function($scope,$window,$log,SalesOrderService,SalesOrderItemService,ArticleLotService,$modal){
  var  discountRate = 0.05 ;
    $scope.articleLots = [];
    $scope.alerts = [];
	$scope.lot = {};
	$scope.status ;

	// init  processing sales order
	$scope.processingSales = SalesOrderService.newSalesOrder();

	// init  article lot search input
	$scope.articleLotSearchInput = ArticleLotService.newArticleLotSearchInput();

    //init sales order item search input
    $scope.orderItemSearchInput = SalesOrderItemService.newSalesOrderSearchInput();

    // init sales order Item
    $scope.salesOrderItem = SalesOrderItemService.newSalesOrderItem();
    newSalesOrder();

    /*
     create a new sales order
     */

   $scope.createSalesOrder = function (){
       $scope.salesOrderItem = SalesOrderItemService.newSalesOrderItem();
        newSalesOrder();
    };


    /*
     load processing sale order item
     */

    $scope.loadProcessingSaleItem = function(){
        $scope.orderItemSearchInput.entity.salesOrder = $scope.processingSales ;
        $scope.orderItemSearchInput.fieldNames= [] ;
        $scope.orderItemSearchInput.fieldNames.push("salesOrder") ;

        SalesOrderItemService.findBy($scope.orderItemSearchInput).success(function (searchResult) {
            $scope.processingSales.salesOrderItems = searchResult.resultList;
        }).error(function (data,status) {
                $scope.status = 'Unable to load sales orders item  data: ' + data.message;
                $window.alert(status+data);
            });
    };

	function newSalesOrder(){

            SalesOrderService.create(SalesOrderService.newSalesOrder())
                .success(function (salesOrder) {
                    if (salesOrder) {
                       angular.copy(salesOrder,$scope.processingSales);
                        //$scope.loadProcessingSaleItem();
                    }
                }).error(function (data, status) {
                    $window.alert("Error during sales order creation ");
                });

	};

	$scope.newSalesOrder = function(){
		newSalesOrder();
	};

    $scope.addSalesOrderItem = function (){
        if($scope.salesOrderItem.id){
            SalesOrderItemService.update($scope.salesOrderItem)
                .success(function (editedItem) {
                    handleSalesItemCreateOrEditDone(editedItem) ;
                })
                .error(function (data,status) {
                    $scope.status = 'Unable to delete item : ' + data.message;

                });
        }else{
            SalesOrderItemService.create($scope.salesOrderItem)
                .success(function (createdItem) {
                    handleSalesItemCreateOrEditDone(createdItem) ;
                })
                .error(function (data,status) {
                    $scope.status = 'Unable to delete item : ' + data.message;

                });
        }

    };

    function handleSalesItemCreateOrEditDone(salesOrderItem){
        $scope.processingSales.version = salesOrderItem.salesOrder.version ;
        $scope.processingSales.amountAfterTax = salesOrderItem.salesOrder.amountAfterTax ;
        $scope.processingSales.amountBeforeTax = salesOrderItem.salesOrder.amountBeforeTax ;
        $scope.processingSales.amountVAT = salesOrderItem.salesOrder.amountVAT ;
        $scope.processingSales.salesOrderStatus = salesOrderItem.salesOrder.salesOrderStatus ;
        $scope.loadProcessingSaleItem();
        // reset sales order item
        $scope.salesOrderItem = SalesOrderItemService.newSalesOrderItem();
    }
	
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
            $scope.salesOrderItem.salesOrder = $scope.processingSales ;
		}
	};

	$scope.calculateTotalSalesPrice =  function(){

		$scope.salesOrderItem.totalSalePrice = $scope.salesOrderItem.salesPricePU * $scope.salesOrderItem.orderedQty ;
	};

	$scope.handleItemSelection =  function(index){
        $log.info(index);
        index = index ? index : 0 ;
        angular.copy($scope.processingSales.salesOrderItems[index],$scope.salesOrderItem ) ;

	};

    $scope.saveAndClose = function(){
    	//$scope.open('sm');
        var salesKey = window.prompt('Saisir Votre cle de vente :');
        $scope.processingSales.salesKey = salesKey ;
        SalesOrderService.saveAndClose($scope.processingSales)
            .success(function (closedSalesOrder) {
                $scope.createSalesOrder();
                $scope.alerts = [];
            })
            .error(function (data,status) {
                $scope.addAlert({msg:'la cle de vente est incorect ',type:'danger'}) ;

            });
    };

	$scope.handleItemRevoved =  function(index){
        index = index ? index : 0 ;
			$scope.processingSales.salesOrderItems.splice(index,1);
            SalesOrderItemService.deleteById($scope.processingSales.salesOrderItems[index].id)
                .success(function (deletedItem) {
                    $scope.processingSales = deletedItem.salesOrder ;
                    $scope.loadProcessingSaleItem();
                })
                .error(function (data,status) {
                    $scope.addAlert({msg:'Unable to delete item : ',type:'danger'}) ;

                });
	};


	$scope.handleArticleNameChange =  function(articleName){
        $scope.articleLots = [];
		if(articleName){
			$scope.articleLotSearchInput.entity.articleName = $scope.salesOrderItem.article.articleName ;
			$scope.articleLotSearchInput.fieldNames= [] ;
			$scope.articleLotSearchInput.fieldNames.push("articleName") ;
			ArticleLotService.findByLike($scope.articleLotSearchInput)
			.success(function (articleLotSearchResult) {
                    $scope.articleLots = articleLotSearchResult.resultList ;
			})
			.error(function (data,status) {
				$scope.status = 'Unable to load sales orders data: ' + data.message;
                    $scope.addAlert({msg:'Unable to load sales orders data: ',type:'danger'}) ;
			});
		}else{
			$scope.articleLotSearchInput.fieldNames= [] ;
		}


	};
	
	$scope.handleInternalPicChange =  function(){
        $scope.articleLots = [];
        var internalPic = $scope.salesOrderItem.internalPic  ;
		if(internalPic){
			$scope.articleLotSearchInput.entity.internalPic = internalPic ;
			$scope.articleLotSearchInput.fieldNames= [] ;
			$scope.articleLotSearchInput.max= 5 ;
			$scope.articleLotSearchInput.fieldNames.push("internalPic") ;
			ArticleLotService.findBy($scope.articleLotSearchInput)
			.success(function (articleLotSearchResult) {
				if(articleLotSearchResult.resultList){
					$scope.salesItemFromLot(null,articleLotSearchResult.resultList[0],null);
				}
                   
			})
			.error(function (data,status) {
				$scope.status = 'Unable to load sales orders data: ' + data.message;
                    $scope.addAlert({msg:'Unable to load sales orders data: ',type:'danger'}) ;
			});
		}else{
			$scope.articleLotSearchInput.fieldNames= [] ;
		}


	};

    $scope.handleAmontDiscountChange =  function(){
      var discount =   $scope.processingSales.amountAfterTax * discountRate ;
        if (discount < $scope.processingSales.amountDiscount ){
            $scope.processingSales.amountDiscount = 0 ;
        }else{
            // calculate net to pay
        }
    };

    $scope.closeAlert = function(index){
        index = index ? index : 0 ;
        $scope.alerts.splice(index, 1);
    };

    $scope.addAlert = function(alert){
        $scope.alerts = [];
        $scope.alerts.push(alert);
    };
    
    // for modal dialog
    $scope.items = ['item1', 'item2', 'item3'];

    $scope.open = function (size) {

      var modalInstance = $modal.open({
        templateUrl: 'passowordDialog.html',
        controller: ModalInstanceCtrl,
        size: size,
        resolve: {
          items: function () {
            return $scope.items;
          }
        }
      });

      modalInstance.result.then(function (selectedItem) {
        $scope.selected = selectedItem;
      }, function () {
        $log.info('Modal dismissed at: ' + new Date());
      });
    };

  // Please note that $modalInstance represents a modal window (instance) dependency.
  // It is not the same as the $modal service used above.

  var ModalInstanceCtrl = function ($scope, $modalInstance, items) {

    $scope.items = items;
    $scope.selected = {
      item: $scope.items[0]
    };

    $scope.ok = function () {
      $modalInstance.close($scope.selected.item);
    };

    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };
  };

}]) ;