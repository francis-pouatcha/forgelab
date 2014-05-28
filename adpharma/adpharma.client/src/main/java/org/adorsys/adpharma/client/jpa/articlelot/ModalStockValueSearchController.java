package org.adorsys.adpharma.client.jpa.articlelot;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.SecurityUtil;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchInput;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchResult;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchService;
import org.adorsys.adpharma.client.jpa.article.ArticleAgency;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchInput;
import org.adorsys.adpharma.client.jpa.article.ArticleSection;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceByAgencySearchService;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceListPrintTemplatePdf;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoicePrintTemplate;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSearchResult;
import org.adorsys.adpharma.client.jpa.customerinvoice.InvoiceByAgencyPrintInput;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrderReportPrintTemplate;
import org.adorsys.adpharma.client.jpa.section.Section;
import org.adorsys.adpharma.client.jpa.section.SectionSearchInput;
import org.adorsys.adpharma.client.jpa.section.SectionSearchResult;
import org.adorsys.adpharma.client.jpa.section.SectionSearchService;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.controlsfx.dialog.Dialogs;

import com.lowagie.text.DocumentException;
@Singleton
public class ModalStockValueSearchController {
	@Inject
	private ModalStockValueSearchView view ;

	@Inject
	private SecurityUtil securityUtil ;

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler ;


	@Inject
	private ArticleSearchInput searchInput ;

	@Inject
	private StockValueSearchService searchService ;

	@Inject
	private AgencySearchService agencySearchService;

	@Inject
	private SectionSearchService sectionSearchService;

	private StockValuRepportTemplatePdf stockValuRepportTemplatePdf ;

	@Inject
	@Bundle({CustomerInvoicePrintTemplate.class,CustomerInvoice.class,ProcurementOrderReportPrintTemplate.class})
	private ResourceBundle resourceBundle ;

	@Inject
	private Locale locale ;


	@PostConstruct
	public void postContruct(){
		view.bind(searchInput);
		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);

			}
		});
		view.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				view.closeDialog();

			}
		});

		view.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Set<ConstraintViolation<ArticleSearchInput>> violations = view.getView().validate(searchInput);
				if (violations.isEmpty())
				{
					if(view.getView().getGroupByArticle().isSelected()){
						// TODO write service here 
					}else {
						searchService.setStockValue(Boolean.TRUE);
						System.out.println(searchInput.getEntity().getAgency()+" agence");
						System.out.println(searchInput.getEntity().getSection()+" section ");
						searchService.setSearchInputs(searchInput).start();
					}
				}

			}
		});
		searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				StockValueSearchService s = (StockValueSearchService) event.getSource();
				ArticleLotSearchResult cs = s.getValue();
				event.consume();
				s.reset();
				List<ArticleLot> resultList = cs.getResultList();
				ArticleAgency agency = searchInput.getEntity().getAgency();
				try {
					stockValuRepportTemplatePdf = new StockValuRepportTemplatePdf(agency,securityUtil.getConnectedUser(), resourceBundle);
					stockValuRepportTemplatePdf.addItems(resultList);
					stockValuRepportTemplatePdf.closeDocument();
					File file = new File(stockValuRepportTemplatePdf.getFileName());
					if(file.exists()) {
						openFile(file);
					} 
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});


		searchService.setOnFailed(callFailedEventHandler);
		view.getView().getAgency().armedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if(newValue)
					agencySearchService.setSearchInputs(new AgencySearchInput()).start();
			}
		});

		agencySearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				AgencySearchService s = (AgencySearchService) event.getSource();
				AgencySearchResult cs = s.getValue();
				event.consume();
				s.reset();
				List<Agency> resultList = cs.getResultList();
				ArrayList<ArticleAgency> agencys = new ArrayList<ArticleAgency>();
				for (Agency agency : resultList) {
					agencys.add(new ArticleAgency(agency));
				}
				agencys.add(0, new ArticleAgency());
				view.getView().getAgency().getItems().setAll(agencys);

			}
		});


		agencySearchService.setOnFailed(callFailedEventHandler);

		view.getView().getSection().armedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if(newValue)
					sectionSearchService.setSearchInputs(new SectionSearchInput()).start();
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
				view.getView().getSection().getItems().setAll(sections);

			}
		});


		sectionSearchService.setOnFailed(callFailedEventHandler);
	}

	public void handleArticleSearchRequestEvent(@Observes @EntitySearchRequestedEvent ArticleSearchInput searchInput){
//		view.bind(searchInput);
		view.showDiaLog();
	}
	private void openFile(File file){
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
