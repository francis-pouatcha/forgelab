package org.adorsys.adpharma.client.jpa.inventory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.access.SecurityUtil;
import org.adorsys.adpharma.client.events.PrintRequestedEvent;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLotSearchInput;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemDelivery;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemSearchInput;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemSearchResult;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemSearchService;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItem;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItemArticle;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItemCreateService;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItemEditService;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItemInventory;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItemRecordingUser;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItemRemoveService;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItemSearchInput;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItemSearchResult;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItemSearchService;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionRequestEvent;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionResponseEvent;
import org.adorsys.javafx.crud.extensions.events.ComponentSelectionRequestData;
import org.adorsys.javafx.crud.extensions.events.ComponentSelectionRequestEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveRequestEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.HideProgressBarRequestEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;
import org.adorsys.javafx.crud.extensions.events.ShowProgressBarRequestEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.LogoutSucceededEvent;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class InventoryDisplayController implements EntityController
{

	@Inject
	private InventoryDisplayView displayView;

	@Inject
	@EntitySearchRequestedEvent
	private Event<Inventory> searchRequestedEvent;

	@Inject
	@EntityEditRequestedEvent
	private Event<Inventory> editRequestEvent;

	@Inject
	@EntityEditDoneEvent
	private Event<Inventory>  inventoryEditDoneRequestEVent ;

	@Inject
	@EntityRemoveRequestEvent
	private Event<Inventory> removeRequest;

	@Inject
	@PrintRequestedEvent
	private Event<InventoryRepportData> printRequest;

	@Inject
	@ShowProgressBarRequestEvent
	private Event<Object> showProgressRequestEvent;

	@Inject
	@HideProgressBarRequestEvent
	private Event<Object> hideProgressRequestEvent;
	

	@Inject
	@EntitySelectionEvent
	private Event<Inventory> inventorySelectionRequestEvent;

	
	@Inject
	@AssocSelectionResponseEvent
	private Event<AssocSelectionEventData<Inventory>> selectionResponseEvent;

	private ObjectProperty<AssocSelectionEventData<Inventory>> pendingSelectionRequestProperty = new SimpleObjectProperty<AssocSelectionEventData<Inventory>>();

	@Inject
	@ComponentSelectionRequestEvent
	private Event<ComponentSelectionRequestData> componentSelectionRequestEvent;

	@Inject
	private Inventory displayedEntity;

	@Inject 
	@ModalEntitySearchRequestedEvent
	private Event<ArticleLotSearchInput> modalArticleLotSearchEvent;

	@Inject
	private InventoryItemCreateService inventoryItemCreateService;

	@Inject
	private InventoryItemEditService inventoryItemEditService;

	@Inject
	private InventoryItemRemoveService inventoryItemRemoveService;

	@Inject
	private InventoryItemSearchService inventoryItemSearchService;

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler;

	@Inject
	private InventoryRegistration registration;

	@Inject
	private InventoryItem inventoryItem;

	@Inject
	private SecurityUtil securityUtil ;

	@Inject
	private InventoryCloseService inventoryCloseService;

	Stage dialog = new Stage();
	
	@Inject
	private InventoryResultLoaderService inventoryResultLoaderService ;

	@PostConstruct
	public void postConstruct()
	{
		dialog.initModality(Modality.APPLICATION_MODAL);
		//      displayView.getEditButton().disableProperty().bind(registration.canEditProperty().not());
		//      displayView.getRemoveButton().disableProperty().bind(registration.canEditProperty().not());
		displayView.getCloseButton().disableProperty().bind(inventoryCloseService.runningProperty());
		displayView.getImportXlsButton().disableProperty().bind(inventoryResultLoaderService.runningProperty());
		/*
		 * listen to search button and fire search requested event.
		 * 
		 */
		displayView.bind(displayedEntity);
		displayView.bind(inventoryItem);
		displayView.getCancelButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				
				searchRequestedEvent.fire(displayedEntity);
			}
				});
		displayView.getCloseButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				inventoryCloseService.setModel(displayedEntity).start();
				showProgressRequestEvent.fire(new Object());

			}
		});

		displayView.getImportXlsButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Selection un fichier de resultat inventaire");
			fileChooser.getExtensionFilters().add(new ExtensionFilter("Fichier Excel", "*.xls"));
			String userDirName = System.getProperty("user.dir");
			File userDir = new File(userDirName);
			if(!userDir.exists()){
				userDir = new File("test").getParentFile();
			}
			fileChooser.setInitialDirectory(userDir);// working directory
			File selectedFile = fileChooser.showOpenDialog(dialog);
			if(selectedFile!=null){
				HSSFWorkbook workbook;
				ArrayList<InventoryItem> inventoryItemFromSheet = new ArrayList<InventoryItem>();
				try {
					FileInputStream dataStream = FileUtils.openInputStream(selectedFile);
					workbook = new HSSFWorkbook(dataStream);
					HSSFSheet sheet = workbook.getSheetAt(0);
					Iterator<Row> rowIterator = sheet.rowIterator();
					rowIterator.next();
					int i = 0 ;
					while (rowIterator.hasNext()) {
						System.out.println("i = "+i++);
						Row row = rowIterator.next();
						InventoryItem item = new InventoryItem();
						Cell cell = row.getCell(0);
						if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue())){
							item.setInternalPic(cell.getStringCellValue());
						}else {
							break ;
						}

						cell = row.getCell(1);
						if (cell != null){
							item.setAsseccedQty(BigDecimal.valueOf(cell.getNumericCellValue()));
						}
						inventoryItemFromSheet.add(item);
					}
					
					showProgressRequestEvent.fire(new Object());
					inventoryResultLoaderService.setItemFromResult(inventoryItemFromSheet).setInventory(displayedEntity).start();
				}catch(IOException ioe){
					Dialogs.create().message("Une erruer c'est produite durant le charchement ").showInformation();
				}
			}	

			}
		});
		
		inventoryResultLoaderService.setOnFailed(callFailedEventHandler);
		inventoryResultLoaderService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			
			@Override
			public void handle(WorkerStateEvent event) {
				InventoryResultLoaderService s = (InventoryResultLoaderService) event.getSource();
				Inventory editedInventory = s.getValue();
				event.consume();
				s.reset();
				hideProgressRequestEvent.fire(new Object());
				inventorySelectionRequestEvent.fire(editedInventory);

			}
		});

		displayView.getPrintButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(displayedEntity!=null){
					InventoryRepportData inventoryRepportData = new InventoryRepportData(displayedEntity);
					printRequest.fire(inventoryRepportData);
				}

			}
		});

		displayView.getPrintRepportButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(displayedEntity!=null){
					InventoryRepportData inventoryRepportData = new InventoryRepportData(displayedEntity);
					inventoryRepportData.setCountRepport(false);
					printRequest.fire(inventoryRepportData);
				}

			}
		});
		inventoryCloseService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				InventoryCloseService s = (InventoryCloseService) event.getSource();
				Inventory closedInventory = s.getValue();
				event.consume();
				s.reset();
				handleSelectionEvent(closedInventory);
				hideProgressRequestEvent.fire(new Object());

			}
		});
		inventoryCloseService.setOnFailed(callFailedEventHandler);

		displayView.getArticleName().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(code== KeyCode.ENTER){
					String articleName = displayView.getArticleName().getText();
					if(StringUtils.isBlank(articleName)) return;
					ArticleLot entity = new ArticleLot();
					entity.setArticleName(articleName);
					entity.setStockQuantity(BigDecimal.ONE);
					ArticleLotSearchInput asi = new ArticleLotSearchInput();
					asi.setEntity(entity);
					asi.setMax(30);
					asi.getFieldNames().add("articleName");
					modalArticleLotSearchEvent.fire(asi);
				}
			}
		});

		//		
		displayView.getInternalPic().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(code== KeyCode.ENTER){
					String internalPic = displayView.getInternalPic().getText();
					if(StringUtils.isBlank(internalPic)) return;
					ArticleLot entity = new ArticleLot();
					entity.setSecondaryPic(internalPic);
					entity.setInternalPic(internalPic);
					entity.setMainPic(internalPic);
					ArticleLotSearchInput asi = new ArticleLotSearchInput();
					asi.setEntity(entity);
					asi.setMax(30);
					asi.getFieldNames().add("secondaryPic");
					asi.getFieldNames().add("mainPic");
					asi.getFieldNames().add("internalPic");
					modalArticleLotSearchEvent.fire(asi);

				}
			}
		});

		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().showException(exception);

			}
		});

		displayView.getAsseccedQtyColumn().setOnEditCommit(new EventHandler<CellEditEvent<InventoryItem,BigDecimal>>() {
			@Override
			public void handle(CellEditEvent<InventoryItem, BigDecimal> orderedQtyCell) {
				InventoryItem selectedItem = orderedQtyCell.getRowValue();
				BigDecimal newValue = orderedQtyCell.getNewValue();
				BigDecimal oldValue = orderedQtyCell.getOldValue();
				if(newValue!=null){
					if(newValue.compareTo(BigDecimal.ZERO)<0){
						if(oldValue!=null)
							selectedItem.setAsseccedQty(oldValue);
						inventoryItemRemoveService.setEntity(selectedItem).start();

					}else {
						selectedItem.setAsseccedQty(newValue);
						// update article
						inventoryItemEditService.setInventoryItem(selectedItem).start();
					}
				}
			}
		});
		inventoryItemEditService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				InventoryItemEditService s = (InventoryItemEditService) event.getSource();
				InventoryItem editedItem = s.getValue();
				event.consume();
				s.reset();
				int index = displayView.getDataList().getItems().indexOf(editedItem);
				if(index>-1){
					InventoryItem displayed = displayView.getDataList().getItems().get(index);
					PropertyReader.copy(editedItem, displayed);
				}
				updateInventory(editedItem);

			}
		});
		inventoryItemEditService.setOnFailed(callFailedEventHandler);
		inventoryItemRemoveService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				InventoryItemRemoveService s = (InventoryItemRemoveService) event.getSource();
				InventoryItem removeddItem = s.getValue();
				event.consume();
				s.reset();
				displayView.getDataList().getItems().remove(removeddItem);
				updateInventory(removeddItem);
			}
		});
		inventoryItemRemoveService.setOnFailed(callFailedEventHandler);
		inventoryItemCreateService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				InventoryItemCreateService s = (InventoryItemCreateService) event.getSource();
				InventoryItem createdItem = s.getValue();
				event.consume();
				s.reset();
				int indexOf = displayView.getDataList().getItems().indexOf(createdItem);
				if(indexOf>=0){
					PropertyReader.copy(createdItem, displayView.getDataList().getItems().get(indexOf));
				}else {
					displayView.getDataList().getItems().add(0,createdItem);
				}
				PropertyReader.copy(new InventoryItem(), inventoryItem);
				updateInventory(createdItem);
				displayView.getInternalPic().requestFocus();

			}
		});
		inventoryItemCreateService.setOnFailed(callFailedEventHandler);

		/*
		 * listen to Ok button.
		 */
		displayView.getOkButton().disableProperty().bind(inventoryItemCreateService.runningProperty());
		displayView.getOkButton().disableProperty().bind(inventoryItemCreateService.runningProperty());
		displayView.getOkButton().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(code== KeyCode.ENTER){
					if(isValidInventoryItem())
						handleAddDeliveryItem(inventoryItem);
				}
			}

		});
		displayView.getOkButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(isValidInventoryItem())
					handleAddDeliveryItem(inventoryItem);
			}
		});


		displayView.getAsseccedQty().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(code== KeyCode.ENTER){
					if(isValidInventoryItem())
						handleAddDeliveryItem(inventoryItem);
				}
			}
		});

		inventoryItemSearchService.setOnFailed(callFailedEventHandler);

		inventoryItemSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
				{
			@Override
			public void handle(WorkerStateEvent event)
			{
				InventoryItemSearchService s = (InventoryItemSearchService) event.getSource();
				InventoryItemSearchResult inventoryItemSearchResult = s.getValue();
				event.consume();
				s.reset();
				List<InventoryItem> resultList = inventoryItemSearchResult.getResultList();
				displayedEntity.setInventoryItems(resultList);
				//				displayView.getDataList().getItems().addAll(resultList);
				calculateProcessAmont();
			}
				});

		//      displayView.getEditButton().setOnAction(new EventHandler<ActionEvent>()
		//      {
		//         @Override
		//         public void handle(ActionEvent e)
		//         {
		//            editRequestEvent.fire(displayedEntity);
		//         }
		//      });
		//
		//      displayView.getRemoveButton().setOnAction(new EventHandler<ActionEvent>()
		//      {
		//         @Override
		//         public void handle(ActionEvent e)
		//         {
		//            removeRequest.fire(displayedEntity);
		//         }
		//      });
		//
		//      displayView.getConfirmSelectionButton().setOnAction(new EventHandler<ActionEvent>()
		//      {
		//         @Override
		//         public void handle(ActionEvent e)
		//         {
		//            final AssocSelectionEventData<Inventory> pendingSelectionRequest = pendingSelectionRequestProperty.get();
		//            if (pendingSelectionRequest == null)
		//               return;
		//            pendingSelectionRequestProperty.set(null);
		//            pendingSelectionRequest.setTargetEntity(displayedEntity);
		//            selectionResponseEvent.fire(pendingSelectionRequest);
		//         }
		//      });
		//
		//      displayView.getConfirmSelectionButton().visibleProperty().bind(pendingSelectionRequestProperty.isNotNull());
	}

	public void display(Pane parent)
	{
		BorderPane rootPane = displayView.getRootPane();
		ObservableList<Node> children = parent.getChildren();
		if (!children.contains(rootPane))
		{
			children.add(rootPane);
		}
	}

	@Override
	public ViewType getViewType()
	{
		return ViewType.DISPLAY;
	}

	/**
	 * Listens to list selection events and bind selected to pane
	 */
	public void handleSelectionEvent(@Observes @EntitySelectionEvent Inventory selectedEntity)
	{
		PropertyReader.copy(selectedEntity, displayedEntity);
		inventoryItemSearchService.setSearchInputs(getInventoryItemSearchInput(selectedEntity)).start();

	}



	public void handleAssocSelectionRequest(@Observes(notifyObserver = Reception.ALWAYS) @AssocSelectionRequestEvent AssocSelectionEventData<Inventory> eventData)
	{
		pendingSelectionRequestProperty.set(eventData);
		componentSelectionRequestEvent.fire(new ComponentSelectionRequestData(Inventory.class.getName()));
		searchRequestedEvent.fire(eventData.getTargetEntity() != null ? eventData.getTargetEntity() : new Inventory());
	}

	public void handleArticleLotSearchDone(@Observes @ModalEntitySearchDoneEvent ArticleLot model)
	{
		if(!isValidateOrderedQty(model))
			return ;
		PropertyReader.copy(inventoryItemfromArticle(model), inventoryItem);
		//		if(isValidInventoryItem())
		//			handleAddDeliveryItem(inventoryItem);
		displayView.getAsseccedQty().requestFocus();
	}

	private void handleAddDeliveryItem(InventoryItem iItem) {
		//				salesOrderItem.calculateTotalAmout();
		if(iItem.getId()==null){
			if(iItem.getInventory()==null) iItem.setInventory(new InventoryItemInventory(displayedEntity)); 
			iItem.setAsseccedQty(iItem.getAsseccedQty());
			inventoryItemCreateService.setModel(iItem).start();
		}
	}





	public boolean isValidInventoryItem(){
		BigDecimal realQty = inventoryItem.getExpectedQty();
		InventoryItemArticle article = inventoryItem.getArticle();
		if(article==null || article.getId()==null){
			Dialogs.create().nativeTitleBar().message("vous devez selection un article ").showError();
			return false;
		}
		if(realQty==null){
			Dialogs.create().nativeTitleBar().message("la Qte est Requis ").showError();
			displayView.getAsseccedQty().requestFocus();
			return false;
		}

		return true;

	}


	public boolean isValidateOrderedQty(ArticleLot model){
		//		Iterator<InventoryItem> iterator = displayView.getDataList().getItems().iterator();
		//		while (iterator.hasNext()) {
		//			SalesOrderItem next = iterator.next();
		//			if(next.getInternalPic().equals(model.getInternalPic())){
		//				BigDecimal salableStock = model.getStockQuantity();
		//				BigDecimal orderedStock = next.getOrderedQty().add(BigDecimal.ONE);
		//				if(salableStock.compareTo(orderedStock)< 0 ){
		//					Dialogs.create().message("La Quantite Commandee ne peux etre superieur a "+ salableStock).showError();
		//				displayView.getInternalPic().setText(null);
		//					return false ;
		//					
		//				}
		//			}
		return true ;
	}

	public InventoryItem inventoryItemfromArticle(ArticleLot al){
		InventoryItemRecordingUser recordingUser = new InventoryItemRecordingUser();
		Login connectedUser = securityUtil.getConnectedUser();
		PropertyReader.copy(connectedUser, recordingUser);
		InventoryItem invItem = new InventoryItem();
		InventoryItemArticle article = new InventoryItemArticle();
		article.setId(al.getArticle().getId());
		article.setArticleName(al.getArticle().getArticleName());
		article.setVersion(al.getArticle().getVersion());
		article.setPic(al.getArticle().getPic());
		invItem.setInternalPic((al.getInternalPic()));
		invItem.setArticle(article);
		invItem.setExpectedQty(al.getStockQuantity());
		invItem.setAsseccedQty(al.getStockQuantity());
		invItem.setGapSalesPricePU((al.getSalesPricePU()));
		invItem.setGapPurchasePricePU((al.getPurchasePricePU()));
		invItem.setInventory(new InventoryItemInventory(displayedEntity));
		invItem.setInternalPic(al.getInternalPic());
		invItem.setRecordingUser(recordingUser);
		return invItem;
	}

	//	private static final BigDecimal HUNDRED = new BigDecimal(100); 
	public void updateInventory(InventoryItem item){
		InventoryItemInventory inventory = item.getInventory();
		PropertyReader.copy(inventory, displayedEntity);
	}


	/**
	 * This is the only time where the bind method is called on this object.
	 * @param model
	 */
	public void handleNewModelEvent(@Observes @SelectedModelEvent Inventory model)
	{
		this.displayedEntity = model;
		displayView.bind(this.displayedEntity);

	}
	public void calculateProcessAmont(){
		List<InventoryItem> inventoryItems = displayedEntity.getInventoryItems();
		BigDecimal gapPA = BigDecimal.ZERO;
		BigDecimal gapPV = BigDecimal.ZERO;
		for (InventoryItem inventoryItem : inventoryItems) {
			gapPA =gapPA.add(inventoryItem.getGapTotalPurchasePrice());
			gapPV =gapPV.add(inventoryItem.getGapTotalSalePrice());
		}
		displayedEntity.setGapPurchaseAmount(gapPA);
		displayedEntity.setGapSaleAmount(gapPV);
	}
	public InventoryItemSearchInput getInventoryItemSearchInput(Inventory inventory){
		InventoryItemSearchInput inventoryItemSearchInput = new InventoryItemSearchInput();
		inventoryItemSearchInput.getFieldNames().add("inventory");
		InventoryItem inventoryItem2 = new InventoryItem();
		inventoryItem2.setInventory(new InventoryItemInventory(inventory));
		inventoryItemSearchInput.setEntity(inventoryItem2);
		inventoryItemSearchInput.setMax(-1);
		return inventoryItemSearchInput;
	}
	public void reset() {
		PropertyReader.copy(new Inventory(), displayedEntity);
	}
	
	
//	public void handleLogoutSucceedEvent(@Observes(notifyObserver=Reception.ALWAYS) @LogoutSucceededEvent Object object){
//		searchRequestedEvent.fire(displayedEntity);
//	}
}