package org.adorsys.adpharma.client.jpa.payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.access.SecurityUtil;
import org.adorsys.adpharma.client.events.PaymentId;
import org.adorsys.adpharma.client.events.PrintPaymentReceiptRequestedEvent;
import org.adorsys.adpharma.client.jpa.accessroleenum.AccessRoleEnum;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchResult;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class ModalPaymentSearchController {
	@Inject
	private	ModalPaymentSearchView view;

	@Inject 
	private PaymentSearchService paymentSearchService;

	@Inject
	private ServiceCallFailedEventHandler paymentSearchServiceCallFailedEventHandler;

//	@Inject 
//	@ModalEntitySearchRequestedEvent
//	private Event<PaymentSearchInput> modalPaymentSearchEvent;

	@Inject 
	@ModalEntitySearchDoneEvent
	private Event<Payment> modalPayemntSearchDoneEvent;

	@Inject
	private Payment payment;

	private PaymentSearchResult searchResult;

	@Inject
	@PrintPaymentReceiptRequestedEvent
	private Event<PaymentId> printPaymentReceiptRequestedEvent;
	
	@Inject
	private SecurityUtil securityUtil;

	@PostConstruct
	public void postConstruct(){
		view.getPagination().currentPageIndexProperty().addListener(new ChangeListener<Number>()
				{
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
			{
				if (searchResult == null)
					return;
				if (searchResult.getSearchInput() == null)
					searchResult.setSearchInput(new PaymentSearchInput());
				int start = 0;
				int max = searchResult.getSearchInput().getMax();
				if (newValue != null)
				{
					start = new BigDecimal(newValue.intValue()).multiply(new BigDecimal(max)).intValue();
				}
				searchResult.getSearchInput().setStart(start);
				paymentSearchService.setSearchInputs(searchResult.getSearchInput()).start();

			}
				});
		paymentSearchServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

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

		view.getPrintButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Payment selectedItem = view.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					PaymentId paymentId = new PaymentId(selectedItem.getId());
					// Print receipt here.
					paymentId.setPrintWhithoutDiscount(false);
					printPaymentReceiptRequestedEvent.fire(paymentId);
				}

			}
		});
		
		view.getunDiscountReceiptBtn().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Payment selectedItem = view.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					PaymentId paymentId = new PaymentId(selectedItem.getId());
					// Print receipt here.
					paymentId.setPrintWhithoutDiscount(true);
					printPaymentReceiptRequestedEvent.fire(paymentId);
				}

			}
		});

		view.getPaymentNumber().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(code== KeyCode.ENTER){
					String paymentNumber = view.getPaymentNumber().getText();
					if(StringUtils.isBlank(paymentNumber)) return;
					Payment entity = new Payment();
					entity.setPaymentNumber(paymentNumber);
					PaymentSearchInput asi = new PaymentSearchInput();
					asi.setEntity(entity);
					asi.setMax(30);
					asi.getFieldNames().add("paymentNumber");
					paymentSearchService.setSearchInputs(asi).start();
				}
			}
		});

		paymentSearchService.setOnFailed(paymentSearchServiceCallFailedEventHandler);
		paymentSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>(){

			@Override
			public void handle(WorkerStateEvent event) {
				PaymentSearchService s = (PaymentSearchService) event.getSource();
				PaymentSearchResult searchResult = s.getValue();
				event.consume();
				s.reset();
				if(securityUtil.hasRole(AccessRoleEnum.MANAGER.name())){
					view.getFooter().setVisible(true);
					List<Payment> resultList = searchResult.getResultList();
					BigDecimal total = BigDecimal.ZERO;
					for (Payment payment : resultList) {
						total = total.add(payment.getAmount());
						view.getTotal().setText(total+"");
					}
				}else {
					view.getFooter().setVisible(false);
				}
				
				handleArticleSearchResult(searchResult);
			}



		});
	}

	public ModalPaymentSearchView getView() {
		return view;
	}

	/**
	 * Handle search results. But the switch of displays is centralized
	 * in the main article controller.
	 * 
	 * @param entities
	 */
	public void handleArticleSearchResult( PaymentSearchResult searchResult)
	{

		this.searchResult = searchResult;
		List<Payment> entities = searchResult.getResultList();
		entities.sort(new Comparator<Payment>() {

			@Override
			public int compare(Payment o1, Payment o2) {
				return o2.getRecordDate().getTime().compareTo(o1.getRecordDate().getTime());
			}

		});
		if (entities == null)
			entities = new ArrayList<Payment>();
		view.getDataList().getItems().clear();
		view.getDataList().getItems().addAll(entities);
//		int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 5;
//		int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
//		view.getPagination().setPageCount(pageCount);
//		int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
//		int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
//		view.getPagination().setCurrentPageIndex(pageIndex);
//		view.getDataList().getItems().setAll(entities);
		if(!view.isDisplayed())
			view.showDiaLog();
	}




	public void handleArticleLotSearchRequestEvent(@Observes @ModalEntitySearchRequestedEvent PaymentSearchInput searchInput){
		searchInput.setMax(-1);
		paymentSearchService.setSearchInputs(searchInput).start();
	}

}
