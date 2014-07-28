package org.adorsys.adpharma.client.jpa.article;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.access.SecurityUtil;
import org.adorsys.adpharma.client.events.ArticlelotMovedDoneRequestEvent;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.section.Section;
import org.adorsys.adpharma.client.jpa.section.SectionSearchInput;
import org.adorsys.adpharma.client.jpa.section.SectionSearchResult;
import org.adorsys.adpharma.client.jpa.section.SectionSearchService;
import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.adpharma.client.jpa.vat.VATSearchInput;
import org.adorsys.adpharma.client.jpa.vat.VATSearchResult;
import org.adorsys.adpharma.client.jpa.vat.VATSearchService;
import org.adorsys.javaext.admin.EditRoles;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.EntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditCanceledEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityListPageIndexChangedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveConfirmedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.HideProgressBarRequestEvent;
import org.adorsys.javafx.crud.extensions.events.ShowProgressBarRequestEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.dialog.Dialogs;

import com.lowagie.text.DocumentException;

@Singleton
public class ArticleListController implements EntityController
{

	@Inject
	private ArticleListView listView;

	@Inject
	@EntityEditRequestedEvent
	private Event<Article> selectionEvent;

	@Inject
	@EntitySearchRequestedEvent
	private Event<Article> searchRequestedEvent;

	@Inject
	@EntityCreateRequestedEvent
	private Event<Article> createRequestedEvent;

	@Inject
	@EntityListPageIndexChangedEvent
	private Event<ArticleSearchResult> entityListPageIndexChangedEvent;

	@Inject
	@ShowProgressBarRequestEvent
	private Event<Object> showProgress;

	@Inject
	@HideProgressBarRequestEvent
	private Event<Object> hideProgress;


	@Inject
	private ArticleSearchService searchService;

	@Inject
	private ArticleSearchService searchForPrintService;

	@Inject
	private ArticleEditService articleEditService ;

	@Inject
	private SectionSearchService sectionSearchService;

	@Inject
	private VATSearchService  vatSearchService;

	private ArticleSearchResult searchResult;

	@Inject
	private ArticleSearchInput searchInput;

	@Inject
	@EntityRemoveConfirmedEvent
	private  Event<Article> removeRequestedEvent;
	
	@Inject
	private ArticleRegistration registration;

	@Inject
	private SecurityUtil securityUtil ;

	private List<ArticleSection> sectionList = new ArrayList<ArticleSection>();

	private List<ArticleVat> vatList = new ArrayList<ArticleVat>();

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler ;

	@PostConstruct
	public void postConstruct()

	{


		listView.bind(searchInput);
		listView.getCreateButton().disableProperty().bind(registration.canCreateProperty().not());
		listView.getEditButton().disableProperty().bind(registration.canCreateProperty().not());
		listView.getPrintButton().disableProperty().bind(searchForPrintService.runningProperty());

		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);

			}
		});

		//
		//		listView.getDataList().getSelectionModel().selectedItemProperty()
		//		.addListener(new ChangeListener<Article>()
		//				{
		//			@Override
		//			public void changed(
		//					ObservableValue<? extends Article> property,
		//					Article oldValue, Article newValue)
		//			{
		//				if (newValue != null)
		//					selectionEvent.fire(newValue);
		//			}
		//				});

		/*
		 * listen to section change button 
		 */
		listView.getSectionChangeBtn().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				Article selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					ArticleSection section = selectedItem.getSection();
					ArticleSection selectedSection = Dialogs.create().message("Changer Le rayon").showChoices(section,sectionList);
					if(selectedSection !=null && !section.equals(selectedSection)){
						selectedItem.setSection(selectedSection);
						articleEditService.setArticle(selectedItem).start();
					}
				}
			}
				});

		/*
		 * listen to vat change button 
		 */
		listView.getVatChangeBtn().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				Article selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null){
					ArticleVat vat = selectedItem.getVat();
					ArticleVat selectedVat = Dialogs.create().message("Taxe : "+vat).showChoices(vat,vatList);
					if(selectedVat!=null && !vat.equals(selectedVat)){
						selectedItem.setVat(selectedVat);
						articleEditService.setArticle(selectedItem).start();

					}

				}
			}
				});

		/*
		 * listen to delete button and 
		 */
		listView.getDeleteButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				Article selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null)
					removeRequestedEvent.fire(selectedItem);
			}
				});


		/*
		 * listen to search button and fire search activated event.
		 */
		listView.getEditButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				Article selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null)
					selectionEvent.fire(selectedItem);
			}
				});

		/*
		 * listen to search button and fire search activated event.
		 */

		listView.getPrintButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				handlePrintAction();
				showProgress.fire(new Object());
			}
				});
		articleEditService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ArticleEditService s = (ArticleEditService) event.getSource();
				Article editArticle = s.getValue();
				event.consume();
				s.reset();
				if(editArticle!=null)
					handleEditDoneEvent(editArticle);

			}
		});

		articleEditService.setOnFailed(callFailedEventHandler);
		searchForPrintService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ArticleSearchService s = (ArticleSearchService) event.getSource();
				ArticleSearchResult result = s.getValue();
				event.consume();
				s.reset();
				List<Article> articles = result.getResultList();
				articles.sort(new Comparator<Article>() {

					@Override
					public int compare(Article o1, Article o2) {
						return o1.getArticleName().compareTo(o2.getArticleName());
					}

				});
				ArticleSection section = searchInput.getEntity().getSection();
				Login login = securityUtil.getConnectedUser();
				ArticleRepportTemplatePdf	worker ;
				try {
					worker = new ArticleRepportTemplatePdf(login, login.getAgency(), section!=null?section.getName():"");
					worker.addItems(articles);
					worker.closeDocument();
					File file = new File(worker.getFileName());
					openFile(file);
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				hideProgress.fire(new Object());

			}
		});
		searchForPrintService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SectionSearchService s = (SectionSearchService) event.getSource();
				s.reset();				
			}
		});

		sectionSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SectionSearchService s = (SectionSearchService) event.getSource();
				SectionSearchResult result = s.getValue();
				event.consume();
				s.reset();
				List<Section> resultList = result.getResultList();
				listView.getSection().getItems().clear();
				sectionList.clear();
				for (Section section : resultList) {
					sectionList.add(new ArticleSection(section));
				}
				sectionList.add(0, null);
				listView.getSection().getItems().setAll(sectionList);
				listView.getSection().getSelectionModel().select(0);
			}
		});
		sectionSearchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SectionSearchService s = (SectionSearchService) event.getSource();
				s.reset();				
			}
		});

		vatSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				VATSearchService s = (VATSearchService) event.getSource();
				VATSearchResult result = s.getValue();
				event.consume();
				s.reset();
				List<VAT> resultList = result.getResultList();
				listView.getSection().getItems().clear();
				vatList.clear();
				for (VAT vat : resultList) {
					vatList.add(new ArticleVat(vat));
				}
				ArticleVat articleVat = new ArticleVat();
				articleVat.setName("TAXE");
				vatList.add(0,articleVat);
				listView.getVat().getItems().setAll(vatList);
				listView.getVat().getSelectionModel().select(0);
			}
		});
		vatSearchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				VATSearchService s = (VATSearchService) event.getSource();
				s.reset();				
			}
		});

		listView.getMainPic().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(KeyCode.ENTER.equals(event.getCode())){
					handleSearchAction();
				}

			}
		});

		listView.getArticleName().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(KeyCode.ENTER.equals(event.getCode())){
					handleSearchAction();
				}

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
				handleSearchAction();
			}
				});


		searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ArticleSearchService s = (ArticleSearchService) event.getSource();
				searchResult = s.getValue();
				event.consume();
				s.reset();
				handleSearchResult(searchResult);

			}
		});
		searchService.setOnFailed(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ArticleSearchService s = (ArticleSearchService) event.getSource();
				s.reset();				
			}
		});

		listView.getCreateButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				Article selectedItem = listView.getDataList().getSelectionModel().getSelectedItem();
				if (selectedItem == null)
					selectedItem = new Article();
				createRequestedEvent.fire(selectedItem);
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
					searchResult.setSearchInput(new ArticleSearchInput());
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

	@Override
	public void display(Pane parent)
	{
		SectionSearchInput sectionSearchInput = new SectionSearchInput();
		sectionSearchInput.setMax(-1);
		sectionSearchService.setSearchInputs(sectionSearchInput).start();

		VATSearchInput vatSearchInput = new VATSearchInput();
		vatSearchInput.setMax(-1);
		vatSearchService.setSearchInputs(vatSearchInput).start();

		BorderPane rootPane = listView.getRootPane();
		ObservableList<Node> children = parent.getChildren();
		if (!children.contains(rootPane))
		{
			children.add(rootPane);
		}
	}

	@Override
	public ViewType getViewType()
	{
		return ViewType.LIST;
	}

	private void handleSearchAction() {
		searchInput.setFieldNames(readSearchAttributes());
		searchInput.setMax(30);
		searchService.setSearchInputs(searchInput).start();

	}

	private void handlePrintAction() {
		searchInput.setFieldNames(readSearchAttributes());
		searchInput.setMax(-1);
		searchForPrintService.setSearchInputs(searchInput).start();

	}

	/**
	 * Handle search results. But the switch of displays is centralized
	 * in the main article controller.
	 * 
	 * @param entities
	 */
	public void handleSearchResult(@Observes @EntitySearchDoneEvent ArticleSearchResult searchResult)
	{
		this.searchResult = searchResult;
		List<Article> entities = searchResult.getResultList();
		entities.sort(new Comparator<Article>() {

			@Override
			public int compare(Article o1, Article o2) {
				return o1.getArticleName().compareTo(o2.getArticleName());
			}

		});
		if (entities == null)
			entities = new ArrayList<Article>();
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(entities);
		int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 5;
		int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
		listView.getPagination().setPageCount(pageCount);
		int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
		int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
		listView.getPagination().setCurrentPageIndex(pageIndex);

	}

	public void handleCreatedEvent(@Observes @EntityCreateDoneEvent Article createdEntity)
	{
		listView.getDataList().getItems().add(0, createdEntity);
	}

	public void handleRemovedEvent(@Observes @EntityRemoveDoneEvent Article removedEntity)
	{
		listView.getDataList().getItems().remove(removedEntity);
	}

	public void handleEditDoneEvent(@Observes @EntityEditDoneEvent Article selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		Article entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<Article> arrayList = new ArrayList<Article>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent Article selectedEntity)
	{
		int selectedIndex = listView.getDataList().getItems().indexOf(selectedEntity);
		if (selectedIndex <= -1)
			return;
		Article entity = listView.getDataList().getItems().get(selectedIndex);
		PropertyReader.copy(selectedEntity, entity);

		ArrayList<Article> arrayList = new ArrayList<Article>(listView.getDataList().getItems());
		listView.getDataList().getItems().clear();
		listView.getDataList().getItems().addAll(arrayList);
		listView.getDataList().getSelectionModel().select(selectedEntity);
	}

	public List<String> readSearchAttributes(){
		ArrayList<String> seachAttributes = new ArrayList<String>() ;
		String pic = searchInput.getEntity().getPic();
		String articleName = searchInput.getEntity().getArticleName();
		ArticleSection section = searchInput.getEntity().getSection();
		ArticleVat vat = searchInput.getEntity().getVat();
		if(StringUtils.isNotBlank(pic)) seachAttributes.add("pic");
		if(StringUtils.isNotBlank(articleName)) seachAttributes.add("articleName");
		if(section!=null && section.getId()!=null)seachAttributes.add("section");
		if(vat!=null && vat.getId()!=null)seachAttributes.add("vat");

		return seachAttributes;

	}


	public void reset() {
		listView.getDataList().getItems().clear();
	}


	private void openFile(File file){
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}