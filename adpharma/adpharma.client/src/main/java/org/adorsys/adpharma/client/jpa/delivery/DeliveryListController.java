package org.adorsys.adpharma.client.jpa.delivery;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilders;
import net.sf.dynamicreports.report.exception.DRException;

import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemDelivery;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemSearchInput;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemSearchResult;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemSearchService;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchInput;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchResult;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchService;
import org.adorsys.adpharma.client.utils.ChartData;
import org.adorsys.adpharma.client.utils.DateHelper;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.EntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditCanceledEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityListPageIndexChangedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import com.google.common.collect.Lists;

@Singleton
public class DeliveryListController implements EntityController
{
	@Inject
	private DeliveryListView listView;

	@Inject
	@EntityCreateRequestedEvent
	private Event<Delivery> createRequestedEvent;

	@Inject
	@EntitySelectionEvent
	private Event<Delivery> precessDeliveryRequestedEvent;

	@Inject
	@EntityEditRequestedEvent
	private Event<Delivery> deliveryEditEvent;

	@Inject
	SupplierSearchService supplierSearchService;

	@Inject
	DeliverySearchService searchService;

	@Inject
	DeliveryItemSearchService itemsearchService;

	@Inject
	private DeliveryRemoveService deliveryRemoveService;



	@Inject
	@EntityListPageIndexChangedEvent
	private Event<DeliverySearchResult> entityListPageIndexChangedEvent;

	private DeliverySearchResult searchResult;

	@Inject 
	DeliverySearchInput searchInput;
	
	@Inject
	private SupplierInvoiceChartDataService supplierInvoiceChartDataService;
	

	@Inject
	private DeliveryRegistration registration;

	@Inject
	@Bundle({ CrudKeys.class})
	private ResourceBundle resourceBundle;
	
	@Inject
	private ServiceCallFailedEventHandler chartDataSearchServiceCallFailedEventHandler;



	@PostConstruct
	public void postConstruct()
	{

		listView.getCreateButton().disableProperty().bind(registration.canCreateProperty().not());
		listView.bind(searchInput);
		listView.getYearList().getItems().setAll(DateHelper.getYears());
		
		chartDataSearchServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().showException(exception);

			}
		});
		
		listView.getExportToXlsButton().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				exportDeliveryToXls();
				
			}
		});
		
		listView.getDataList().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Delivery>() {
			@Override
			public void changed(ObservableValue<? extends Delivery> observable,
					Delivery oldValue, Delivery newValue) {
				if(newValue!=null){
					listView.getRemoveButton().disableProperty().unbind();
					listView.getUpdateButton().disableProperty().unbind();
					listView.getRemoveButton().disableProperty().bind(newValue.deliveryProcessingStateProperty().isEqualTo(DocumentProcessingState.CLOSED));
					listView.getUpdateButton().disableProperty().bind(newValue.deliveryProcessingStateProperty().isEqualTo(DocumentProcessingState.CLOSED));
					DeliveryItemSearchInput dsi = new DeliveryItemSearchInput();
					dsi.getEntity().setDelivery(new DeliveryItemDelivery(newValue));
					dsi.setMax(-1);
					dsi.getFieldNames().add("delivery");
					itemsearchService.setSearchInputs(dsi).start();
				}

			}
		});

		itemsearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				DeliveryItemSearchService s = (DeliveryItemSearchService) event.getSource();
				DeliveryItemSearchResult result = s.getValue();
				event.consume();
				s.reset();
				List<DeliveryItem> resultList = result.getResultList();
				listView.getDataListItem().getItems().setAll(resultList);

			}
		});
		listView.getComputeButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Integer selectedYears = listView.getYearList().getSelectionModel().getSelectedItem();
				DeliverySupplier selectedSupplier = listView.getChartSupplierList().getSelectionModel().getSelectedItem();
				if(selectedYears!=null){
					DeliveryStattisticsDataSearchInput chartDataSearchInput = new DeliveryStattisticsDataSearchInput();
					chartDataSearchInput.setYears(selectedYears);
					if(selectedSupplier!=null&&selectedSupplier.getId()!=null)
						chartDataSearchInput.setDeliverySupplier(selectedSupplier);
					supplierInvoiceChartDataService.setModel(chartDataSearchInput).start();
				}

			}
		});
		
		supplierInvoiceChartDataService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SupplierInvoiceChartDataService s = (SupplierInvoiceChartDataService) event.getSource();
				DeliveryStatisticsDataSearchResult result = s.getValue();
				event.consume();
				s.reset();
				Iterator<ChartData> iterator = result.getChartData().iterator();
				List<Data> pieChartData = ChartData.toPieChartData( result.getChartData());
				listView.getPieChart().getData().setAll(pieChartData);
				listView.getPieChartData().getItems().setAll( result.getChartData());
			}
		});
		supplierInvoiceChartDataService.setOnFailed(chartDataSearchServiceCallFailedEventHandler);
		itemsearchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				DeliveryItemSearchService s = (DeliveryItemSearchService) event.getSource();
				s.reset();				
			}
		});
		listView.getProcessButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Delivery selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem== null) return ;
				precessDeliveryRequestedEvent.fire(selectedItem);
			}
		});

		/*
		 * listen to search button and fire search activated event.
		 */
		listView.getSearchButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{							
				searchInput.setFieldNames(readSearchAttributes());
				searchInput.setMax(30);
				searchService.setSearchInputs(searchInput).start();

			}


				});
		/*
		 * listen to remove button .
		 */
		listView.getRemoveButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{							
				Delivery selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					Action showConfirm = Dialogs.create().
							nativeTitleBar().
							message(resourceBundle.getString("Entity_confirm_remove.title")).showConfirm();
					if(showConfirm==Dialog.Actions.YES){
						deliveryRemoveService.setEntity(selectedItem).start();
					}
				}

			}


				});
		supplierSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SupplierSearchService s = (SupplierSearchService) event.getSource();
				SupplierSearchResult result = s.getValue();
				event.consume();
				s.reset();
				ArrayList<DeliverySupplier> ds = new ArrayList<DeliverySupplier>();
				List<Supplier> resultList = result.getResultList();
				for (Supplier supplier : resultList) {
					ds.add(new DeliverySupplier(supplier));
				}
				ds.add(0, null);
				listView.getSupplier().getItems().setAll(ds);
				listView.getSupplier().getSelectionModel().select(0);
				listView.getChartSupplierList().getItems().setAll(ds);

			}
		});
		supplierSearchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SupplierSearchService s = (SupplierSearchService) event.getSource();
				s.reset();				
			}
		});
		deliveryRemoveService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				DeliveryRemoveService s = (DeliveryRemoveService) event.getSource();
				Delivery result = s.getValue();
				event.consume();
				s.reset();
				listView.getDataList().getItems().remove(result);

			}
		});
		deliveryRemoveService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				DeliveryRemoveService s = (DeliveryRemoveService) event.getSource();
				s.reset();				
			}
		});
		searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				DeliverySearchService s = (DeliverySearchService) event.getSource();
				searchResult = s.getValue();
				event.consume();
				s.reset();
				handleSearchResult(searchResult);

			}
		});

		searchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				DeliverySearchService s = (DeliverySearchService) event.getSource();
				s.reset();				
			}
		});

		listView.getCreateButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				createRequestedEvent.fire(new Delivery());
			}
				});

		listView.getUpdateButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				Delivery selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if (selectedItem != null)
					deliveryEditEvent.fire(selectedItem);
			}
				});

		listView.getPagination().currentPageIndexProperty().addListener(new ChangeListener<Number>()
				{
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
			{
				if (searchResult == null)
					return;
				if (searchResult.getSearchInput() == null)
					searchResult.setSearchInput(new DeliverySearchInput());
				int start = 0;
				int max = searchResult.getSearchInput().getMax();
				if (newValue != null)
				{
					start = new BigDecimal(newValue.intValue()).multiply(new BigDecimal(max)).intValue();
				}
				searchResult.getSearchInput().setStart(start);
				entityListPageIndexChangedEvent.fire(searchResult);

			}
				});
	}

	public void onDisplayed(){
		SupplierSearchInput supplierSearchInput = new SupplierSearchInput();
		supplierSearchInput.setMax(-1);
		supplierSearchService.setSearchInputs(supplierSearchInput).start();

	}
	@Override
	public void display(Pane parent)
	{
		onDisplayed();
		BorderPane rootPane = listView.getRootPane();
		ObservableList<Node> children = parent.getChildren();
		if (!children.contains(rootPane))
		{
			children.add(rootPane);
		}
	}

	public void beforeShowing(){

	}

	@Override
	public ViewType getViewType()
	{
		return ViewType.LIST;
	}

	/**
	 * Handle search results. But the switch of displays is centralized
	 * in the main delivery controller.
	 * 
	 * @param entities
	 */
	public void handleSearchResult(@Observes @EntitySearchDoneEvent DeliverySearchResult searchResult)
	{
		this.searchResult = searchResult;
		List<Delivery> entities = searchResult.getResultList();
		if (entities == null)
			entities = new ArrayList<Delivery>();
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(entities);
		int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 50;
		int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
		listView.getPagination().setPageCount(pageCount);
		int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
		int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
		listView.getPagination().setCurrentPageIndex(pageIndex);

	}

	public void handleCreatedEvent(@Observes @EntityCreateDoneEvent Delivery createdEntity)
	{
		listView.getDataList().getItems().add(0, createdEntity);
	}

	public void handleRemovedEvent(@Observes @EntityRemoveDoneEvent Delivery removedEntity)
	{
		listView.getDataList().getItems().remove(removedEntity);
	}

	public void handleEditDoneEvent(@Observes @EntityEditDoneEvent Delivery selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		Delivery entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<Delivery> arrayList = new ArrayList<Delivery>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent Delivery selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		Delivery entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<Delivery> arrayList = new ArrayList<Delivery>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public List<String> readSearchAttributes(){
		ArrayList<String> seachAttributes = new ArrayList<String>() ;
		String deliveryNumber = searchInput.getEntity().getDeliveryNumber();
		DeliverySupplier supplier = searchInput.getEntity().getSupplier();
		DocumentProcessingState state = searchInput.getEntity().getDeliveryProcessingState();

		if(StringUtils.isNotBlank(deliveryNumber)) seachAttributes.add("deliveryNumber");
		if(supplier!=null && supplier.getId()!=null) seachAttributes.add("supplier");
		if(state!=null) seachAttributes.add("deliveryProcessingState") ;
		return seachAttributes;

	}

	public void reset() {
		listView.getDataList().getItems().clear();
	}
	
	public void exportDeliveryToXls(){
		Delivery selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
		if( selectedItem!=null){
		Iterator<DeliveryItem> iterator = listView.getDataListItem().getItems().iterator();
		List<DeliveryItem> items = Lists.newArrayList(iterator);
		try {
			report()
			.columns(
					col.column("CIPM", "internalPic", type.stringType()), 
					col.column("CIP", "mainPic", type.stringType()),
					col.column("Designation", "articleName", type.stringType()),
					col.column("Qte", "stockQuantity", type.bigDecimalType()),
					col.column("P.V", "salesPricePU", type.bigDecimalType())
					)

					.setDataSource(items)
					.print();
		} catch (DRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
}