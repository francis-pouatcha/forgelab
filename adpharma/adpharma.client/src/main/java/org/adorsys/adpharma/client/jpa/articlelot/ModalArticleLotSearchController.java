package org.adorsys.adpharma.client.jpa.articlelot;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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

import org.adorsys.javafx.crud.extensions.events.EntityListPageIndexChangedEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.dialog.Dialogs;

public class ModalArticleLotSearchController  {

	@Inject
	ModalArticleLotSearchView view;

	@Inject 
	private ArticleLotSearchService articleSearchService;

	@Inject
	private ServiceCallFailedEventHandler articleSearchServiceCallFailedEventHandler;

//	@Inject 
//	@ModalEntitySearchRequestedEvent
//	private Event<ArticleLotSearchInput> modalArticleLotSearchEvent1;

	@Inject 
	@ModalEntitySearchDoneEvent
	private Event<InvAticleLotEvt> invModalArticleLotSearchDoneEvent;

	@Inject 
	@ModalEntitySearchDoneEvent
	private Event<SlsAticleLotEvt> slsModalArticleLotSearchDoneEvent;
	
	@Inject
	ArticleLot articleLot;

	@Inject
	@EntityListPageIndexChangedEvent
	private Event<ArticleLotSearchResult> entityListPageIndexChangedEvent;

	private ArticleLotSearchResult searchResult;

//	@Inject
//	private ArticleLotSearchInput lotSearchInput ;


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
					searchResult.setSearchInput(new ArticleLotSearchInput());
				int start = 0;
				int max = searchResult.getSearchInput().getMax();
				if (newValue != null)
				{
					start = new BigDecimal(newValue.intValue()).multiply(new BigDecimal(max)).intValue();
				}
				searchResult.getSearchInput().setStart(start);
				articleSearchService.setSearchInputs(searchResult.getSearchInput()).start();


			}
				});

		articleSearchServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);

			}
		});
		view.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				articleSearchService.setSearchByName(Boolean.FALSE);
				articleSearchService.setSearchRealPrice(Boolean.FALSE);
				view.closeDialog();
			}
		});



		view.getDataList().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ArticleLot>() {

			@Override
			public void changed(
					ObservableValue<? extends ArticleLot> observable,
					ArticleLot oldValue, ArticleLot newValue) {
				if(newValue!=null){
					if(SRC_INVENTORY.equals(view.getInputSrc())){
						invModalArticleLotSearchDoneEvent.fire(new InvAticleLotEvt(newValue));
					} else if (SRC_SALES_ORDER.equals(view.getInputSrc())){
						slsModalArticleLotSearchDoneEvent.fire(new SlsAticleLotEvt(newValue));
					}

					view.closeDialog();

				}


			}
		});
		view.getArticleName().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(code== KeyCode.ENTER){
					handleSearchAction();
				}
			}
		});

		view.getSearchButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				handleSearchAction();

			}


		});

		articleSearchService.setOnFailed(articleSearchServiceCallFailedEventHandler);
		articleSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>(){

			@Override
			public void handle(WorkerStateEvent event) {
				ArticleLotSearchService s = (ArticleLotSearchService) event.getSource();
				ArticleLotSearchResult articleSearchResult = s.getValue();
				event.consume();
				s.reset();
				s.setSearchRealPrice(Boolean.FALSE);
				if(articleSearchResult.getResultList().isEmpty()){
					String internalPic = articleSearchResult.getSearchInput().getEntity().getInternalPic();
					String articleName = articleSearchResult.getSearchInput().getEntity().getArticle().getArticleName();
					String message = "Aucun article trouve ";
					if(StringUtils.isNotBlank(internalPic))
						message = "Aucun article avec  Pour CIPM : "+internalPic ;
					if(StringUtils.isNotBlank(articleName))
						message = "Aucun article avec  Pour Designation : "+articleName ;
					
					Dialogs.create().message(message).showInformation();
					return ;
				}
				handleArticleSearchResult(articleSearchResult);
			}



		});


	}

	private void handleSearchAction() {
		String articleName = view.getArticleName().getText();
		if(StringUtils.isBlank(articleName)) return;
		ArticleLot entity = new ArticleLot();
		entity.setArticleName(articleName);
		ArticleLotSearchInput asi = new ArticleLotSearchInput();
		asi.setEntity(entity);
		asi.setMax(30);
		asi.getFieldNames().add("articleName");
		asi.setIncludeBreack(view.getOnlyBreackButton().isSelected());
		articleSearchService.setSearchInputs(asi).setSearchByName(Boolean.TRUE).start();

	}

	public void handleArticleSearchResult(ArticleLotSearchResult articleLotSearchResult) {
		if(articleLotSearchResult.getResultList().isEmpty())
			Dialogs.create().message("Aucun Article Trouve").showInformation();

		if(articleLotSearchResult.getResultList().size()==1){
			String inputSrc = articleLotSearchResult.getSearchInput().getSrc();
			ArticleLot articleLot2 = articleLotSearchResult.getResultList().iterator().next();
			PropertyReader.copy(new ArticleLot(), articleLot);
			view.closeDialog();
			if(Boolean.FALSE.equals(articleLot2.getArticle().getSection().getWareHouse())){
				if(SRC_INVENTORY.equals(inputSrc) || SRC_INVENTORY.equals(view.getInputSrc())){
					invModalArticleLotSearchDoneEvent.fire(new InvAticleLotEvt(articleLot2));
				} else if (SRC_SALES_ORDER.equals(inputSrc) || SRC_SALES_ORDER.equals(view.getInputSrc())){
					slsModalArticleLotSearchDoneEvent.fire(new SlsAticleLotEvt(articleLot2));
				}
			}
		}else {
			this.searchResult = articleLotSearchResult;
			List<ArticleLot> resultList = articleLotSearchResult.getResultList();
			resultList.sort(new Comparator<ArticleLot>() {

				@Override
				public int compare(ArticleLot o1, ArticleLot o2) {
					return o1.getArticle().getArticleName().compareToIgnoreCase(o2.getArticle().getArticleName()) ;
				}
			});
			List<String> resultOrder = new ArrayList<String>();
			Map<String, Set<ArticleLot>> lotMap = new HashMap<String, Set<ArticleLot>>();
			// Group by internal pic always taking the article
			for (ArticleLot articleLot : resultList) {
				if(articleLot.getArticle()!=null&& articleLot.getArticle().getSection()!=null){
					if(	Boolean.TRUE.equals(articleLot.getArticle().getSection().getWareHouse()))
						continue;
				}
				//				if(BigDecimal.ZERO.compareTo(articleLot.getStockQuantity())>=0)
				//					continue ;
				String internalPic = articleLot.getInternalPic();
				if(!resultOrder.contains(internalPic)) resultOrder.add(internalPic);
				// the internal pic is unique per product.
				Set<ArticleLot> set = lotMap.get( internalPic);
				if(set==null){
					set = new TreeSet<ArticleLot>();
					lotMap.put(internalPic, set);
				}
				set.add(articleLot);
			}
			ArrayList<ArticleLot> lotList = new ArrayList<ArticleLot>(resultOrder.size());
			for (String internalPic : resultOrder) {
				Set<ArticleLot> set = lotMap.get(internalPic);
				ArticleLot articleLot = null;
				for (ArticleLot lot : set) {
					if(articleLot==null) {
						articleLot=lot;
					} else if(BigDecimal.ZERO.compareTo(articleLot.getStockQuantity())>=0){
					} else {
						break;
					}
					articleLot=lot;
				}
				if(articleLot!=null)lotList.add(articleLot);
			}


			int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 5;
			int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
			view.getPagination().setPageCount(pageCount);
			int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
			int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
			view.getPagination().setCurrentPageIndex(pageIndex);
			//			view.getDataList().getItems().setAll(lotList);
			view.getDataList().getItems().setAll(resultList);
			if(!view.isDisplayed())
				view.showDiaLog();
		}
	}


	public ModalArticleLotSearchView getView() {
		return view;
	}

	public void handleInvArticleLotSearchRequestEvent(@Observes @ModalEntitySearchRequestedEvent InvArticleLotSearchInputEvt lotSearchInputEvt){
		ArticleLotSearchInput lotSearchInput = lotSearchInputEvt.getModel();
		lotSearchInput.setSrc(SRC_INVENTORY);
		view.setInputSrc(SRC_INVENTORY);
		if(lotSearchInput.getFieldNames().contains("internalPic")){
			articleSearchService.setSearchInputs(lotSearchInput).setSearchRealPrice(Boolean.TRUE).start();
		}else{
			articleSearchService.setSearchInputs(lotSearchInput).setSearchByName(Boolean.TRUE).start();
		}
		view.getArticleName().setText(lotSearchInput.getEntity().getArticleName());
	}

	public void handleSlsArticleLotSearchRequestEvent(@Observes @ModalEntitySearchRequestedEvent SlsArticleLotSearchInputEvt lotSearchInputEvt){
		ArticleLotSearchInput lotSearchInput = lotSearchInputEvt.getModel();
		lotSearchInput.setSrc(SRC_SALES_ORDER);
		view.setInputSrc(SRC_SALES_ORDER);
		if(lotSearchInput.getFieldNames().contains("internalPic")){
			articleSearchService.setSearchInputs(lotSearchInput).setSearchRealPrice(Boolean.TRUE).start();
		}else{
			articleSearchService.setSearchInputs(lotSearchInput).setSearchByName(Boolean.TRUE).start();
		}
		view.getArticleName().setText(lotSearchInput.getEntity().getArticleName());
	}
	
	private static final String SRC_SALES_ORDER= "Sls";
	private static final String SRC_INVENTORY= "Inv";
}
