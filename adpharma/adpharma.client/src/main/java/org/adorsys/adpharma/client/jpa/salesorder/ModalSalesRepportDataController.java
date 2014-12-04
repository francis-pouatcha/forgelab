package org.adorsys.adpharma.client.jpa.salesorder;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.New;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.access.SecurityUtil;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchInput;
import org.adorsys.adpharma.client.jpa.article.ArticleSection;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginAgency;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemPeriodicalSearchService;
import org.adorsys.adpharma.client.jpa.section.Section;
import org.adorsys.adpharma.client.jpa.section.SectionSearchInput;
import org.adorsys.adpharma.client.jpa.section.SectionSearchResult;
import org.adorsys.adpharma.client.jpa.section.SectionSearchService;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.HideProgressBarRequestEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.ShowProgressBarRequestEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.controlsfx.dialog.Dialogs;
import org.jboss.weld.exceptions.IllegalArgumentException;

import com.lowagie.text.DocumentException;

@Singleton
public class ModalSalesRepportDataController {

	@Inject
	private ModalSalesRepportDataView view ;

	@Inject
	private PeriodicalDataSearchInput model ;

	@Inject
	private SecurityUtil securityUtil;
	
	@Inject
	@ModalEntitySearchRequestedEvent
	private Event<ArticleSearchInput> articleSearchInput;
	
	@Inject
	@ShowProgressBarRequestEvent
	private Event<Object> showProgressionRequestEvent ;

	@Inject
	@HideProgressBarRequestEvent
	private Event<Object> hideProgressionRequestEvent ;
	
	@Inject
	private ErrorMessageDialog errorMessageDialog;
	
	@Inject
	private SectionSearchService sectionSearchService;

	@Inject
	private SalesOrderItemPeriodicalSearchService salesOrderItemPeriodicalSearchService ;
	
	@Inject
	private SalesOrderPeriodicalSearchService salesOrderPeriodicalSearchService;

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler ;
	
	@Inject
	@Bundle(CrudKeys.class)
	private ResourceBundle resourceBundle;

	@PostConstruct
	public void postconstruct(){
		view.bind(model);
		view.getSaveButton().disableProperty().bind(salesOrderItemPeriodicalSearchService.runningProperty());
		view.getResetButton().disableProperty().bind(salesOrderItemPeriodicalSearchService.runningProperty());
		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);

			}
		});

		view.getTaxableSalesOnly().selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if(newValue)
					view.getNonTaxableSalesOnly().setSelected(false);

			}
		});
		
		view.getClearButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				view.getArticle().setValue(null);
			}
		});
		
		view.getSection().armedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if(newValue)
					sectionSearchService.setSearchInputs(new SectionSearchInput()).start();
			}
		});
		
		view.getArticle().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				articleSearchInput.fire(new ArticleSearchInput());
			}
		});
		
		sectionSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				SectionSearchService s = (SectionSearchService) event.getSource();
				SectionSearchResult cs = s.getValue();
				event.consume();
				s.reset();
				List<Section> resultList = cs.getResultList();
				ArrayList<ArticleSection> sections = new ArrayList<ArticleSection>();
				for (Section section : resultList) {
					sections.add(new ArticleSection(section));
				}
				sections.add(0, new ArticleSection());
				view.getSection().getItems().setAll(sections);
			}
		});


		sectionSearchService.setOnFailed(callFailedEventHandler);
		

		view.getNonTaxableSalesOnly().selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if(newValue)
					view.getTaxableSalesOnly().setSelected(false);

			}
		});
		
		view.getPerVendorAndDiscount().selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean oldValue, Boolean newValue) {
				if(newValue)
				view.getPerVendorAndDiscount().setSelected(true);
			}
		});
		
		view.getTwentyOverHeightySalesOnly().selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean oldValue, Boolean newValue) {
				if(newValue) {
					view.getTwentyOverHeightySalesOnly().setSelected(true);
					view.getTwentyOverHeightyInQty().setDisable(Boolean.TRUE);
				}
				if(oldValue) {
					view.getTwentyOverHeightySalesOnly().setSelected(false);
					view.getTwentyOverHeightyInQty().setDisable(Boolean.FALSE);
				}
				
			}
		});
		
		view.getTwentyOverHeightyInQty().selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean oldValue, Boolean newValue) {
				if(newValue) {
					view.getTwentyOverHeightyInQty().setSelected(true);
					view.getTwentyOverHeightySalesOnly().setDisable(Boolean.TRUE);	
				}
				if(oldValue) {
					view.getTwentyOverHeightyInQty().setSelected(false);
					view.getTwentyOverHeightySalesOnly().setDisable(Boolean.FALSE);	
				}
			}
		});
		
		view.getPrintXls().selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean oldValue, Boolean newValue) {
				if(newValue)
				view.getPrintXls().setSelected(true);
			}
		});
				
			
		

		salesOrderItemPeriodicalSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				hideProgressionRequestEvent.fire(new Object());
				SalesOrderItemPeriodicalSearchService source = (SalesOrderItemPeriodicalSearchService) event.getSource();
				List<SalesOrderItem> value = source.getValue().getResultList();
				event.consume();
				source.reset();

				if(model.getTwentyOverHeightySalesOnly()) {
					value = getTwentyHeigth(value);
				}
				
				if(model.getTwentyOverHeightyInQty()) {
					value = getTwentyHeigthInQty(value);
				}

				if(model.getPrintXls()) {
					SalesOrderXlsExporter.exportSalesOrderItemsToXls(value, model);
				}else {
					try {
						Login login = securityUtil.getConnectedUser();
						LoginAgency agency = securityUtil.getAgency();
						SalesOrderReportPrintTemplatePDF worker = new SalesOrderReportPrintTemplatePDF(login,agency,model,getHeaderName());
						worker.addItems(value);
						worker.closeDocument();
						File file = new File(worker.getFileName());
						openFile(file);
					} catch (DocumentException e) {
						e.printStackTrace();
					}
				}
				}
		});
		salesOrderItemPeriodicalSearchService.setOnFailed(callFailedEventHandler);
		
		view.getResetButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				view.closeDialog();
			}
		});
		
		salesOrderPeriodicalSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				hideProgressionRequestEvent.fire(new Object());
				SalesOrderPeriodicalSearchService source= (SalesOrderPeriodicalSearchService)event.getSource();
				List<SalesOrderDiscount> items = source.getValue().getResultList();
				event.consume();
				source.reset();
				
				Login login = securityUtil.getConnectedUser();
				LoginAgency agency = securityUtil.getAgency();
				try {
					SalesOrderDiscountReportPrintTemplatePDF worker = new SalesOrderDiscountReportPrintTemplatePDF(agency, login, model, getHeaderName());
					worker.addItems(items);
					worker.closeDocument();
					File file = new File(worker.getPdfFileName());
					openFile(file);
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			}
		});

		view.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override 
			public void handle(ActionEvent event) {
				Set<ConstraintViolation<PeriodicalDataSearchInput>> validate = view.validate(model);
				if(validate.isEmpty()){
					if(model.getPerVendorAndDiscount().equals(Boolean.TRUE)) {
						salesOrderPeriodicalSearchService.setSearchInputs(model).start();
						showProgressionRequestEvent.fire(new Object());
					}else {
						salesOrderItemPeriodicalSearchService.setSearchInputs(model).start();
						showProgressionRequestEvent.fire(new Object());
					}
				}else {
					errorMessageDialog.getTitleText().setText(resourceBundle.getString("Entity_create_error.title"));
					errorMessageDialog.getDetailText().setText(resourceBundle.getString("Entity_click_to_see_error"));
					errorMessageDialog.display();
				}
			}
		});
		view.addValidators();
	}


	public void handleSalesRepportSearchDataRequestEvent(@Observes @EntitySearchRequestedEvent PeriodicalDataSearchInput data){
		PropertyReader.copy(data, model);
		try {
			view.showDiaLog();
		} catch (Exception e) {
			errorMessageDialog.getTitleText().setText("Erreur d'affichage de l'interface: "+e.getMessage());
			errorMessageDialog.display();
		}
	}

	
	// Open a File in the desktop UI
	private void openFile(File file){
		Desktop desktop=null;
		if(!Desktop.isDesktopSupported()) {
			throw new IllegalArgumentException("Desktop is not supported on this OS");
		}
		try {
			desktop= Desktop.getDesktop();
			desktop.open(file);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private List<SalesOrderItem> getTwentyHeigth(List<SalesOrderItem> source){
		List<SalesOrderItem> twentyHeigthy = new ArrayList<SalesOrderItem>() ;
		//sort by total sales price desc
		source.sort(new Comparator<SalesOrderItem>() {

			@Override
			public int compare(SalesOrderItem o1, SalesOrderItem o2) {
				return o2.getTotalSalePrice().compareTo(o1.getTotalSalePrice());
			}

		});

		BigDecimal twentyHeigthyValue = BigDecimal.ZERO;

		for (SalesOrderItem item : source) {
			twentyHeigthyValue =twentyHeigthyValue.add(item.getTotalSalePrice());
		}
		// get tewnty heighty value
		twentyHeigthyValue = twentyHeigthyValue.multiply(BigDecimal.valueOf(0.8d));

		BigDecimal salesPrice = BigDecimal.ZERO;
		for (SalesOrderItem item : source) {
			salesPrice = salesPrice.add(item.getTotalSalePrice()) ;
			if(salesPrice.compareTo(twentyHeigthyValue) > 0)
				break ;
			twentyHeigthy.add(item);

		}
		int size = twentyHeigthy.size();
		if(size!= 0){
			int intValue = BigDecimal.valueOf(0.2d).multiply(BigDecimal.valueOf(size)).intValue()+1;
			twentyHeigthy = twentyHeigthy.subList(0, intValue) ;
		}
		return twentyHeigthy ;
	}
	
	private List<SalesOrderItem> getTwentyHeigthInQty(List<SalesOrderItem> source){
		List<SalesOrderItem> twentyHeigthy = new ArrayList<SalesOrderItem>() ;
		//sort by total sales price desc
		source.sort(new Comparator<SalesOrderItem>() {

			@Override
			public int compare(SalesOrderItem o1, SalesOrderItem o2) {
				return o2.getDeliveredQty().compareTo(o1.getDeliveredQty());
			}

		});

		BigDecimal twentyHeigthyValue = BigDecimal.ZERO;

		for (SalesOrderItem item : source) {
			twentyHeigthyValue =twentyHeigthyValue.add(item.getTotalSalePrice());
		}
		// get tewnty heighty value
		twentyHeigthyValue = twentyHeigthyValue.multiply(BigDecimal.valueOf(0.8d));

		BigDecimal salesPrice = BigDecimal.ZERO;
		for (SalesOrderItem item : source) {
			salesPrice = salesPrice.add(item.getTotalSalePrice()) ;
			if(salesPrice.compareTo(twentyHeigthyValue) > 0)
				break ;
			twentyHeigthy.add(item);

		}
		int size = twentyHeigthy.size();
		if(size!= 0){
			int intValue = BigDecimal.valueOf(0.2d).multiply(BigDecimal.valueOf(size)).intValue()+1;
			twentyHeigthy = twentyHeigthy.subList(0, intValue) ;
		}
		return twentyHeigthy ;
	}
	
	// HAndle the rssult of the search of article
	public void handleArticleSearchDone(@Observes @ModalEntitySearchDoneEvent Article article){
			view.getArticle().setValue(article);
	}
	
	private String getHeaderName (){
		String headerName = "";
		if(model.getTaxableSalesOnly())
			headerName = " PRODUITS TAXABLES";
		if(model.getNonTaxableSalesOnly())
			headerName = " PRODUITS NON TAXABLES";
		if(model.getTwentyOverHeightySalesOnly())
			headerName = " 20 / 80 Uniquement";
		if(model.getPerVendorAndDiscount()) {
			headerName="REMISES / VENDEURS";
		}
		return headerName ;
	}



}
