package org.adorsys.adpharma.client.jpa.article;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchResult;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchService;
import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfig;
import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfigSearchResult;
import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfigSearchService;
import org.adorsys.adpharma.client.jpa.packagingmode.PackagingMode;
import org.adorsys.adpharma.client.jpa.packagingmode.PackagingModeSearchResult;
import org.adorsys.adpharma.client.jpa.packagingmode.PackagingModeSearchService;
import org.adorsys.adpharma.client.jpa.productfamily.ProductFamily;
import org.adorsys.adpharma.client.jpa.productfamily.ProductFamilySearchResult;
import org.adorsys.adpharma.client.jpa.productfamily.ProductFamilySearchService;
import org.adorsys.adpharma.client.jpa.salesmargin.SalesMargin;
import org.adorsys.adpharma.client.jpa.salesmargin.SalesMarginSearchResult;
import org.adorsys.adpharma.client.jpa.salesmargin.SalesMarginSearchService;
import org.adorsys.adpharma.client.jpa.section.Section;
import org.adorsys.adpharma.client.jpa.section.SectionSearchResult;
import org.adorsys.adpharma.client.jpa.section.SectionSearchService;
import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;
import org.adorsys.javafx.crud.extensions.events.EntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.controlsfx.dialog.Dialogs;
@Singleton
public class ModalArticleCreateController {

	@Inject
	private ModalArticleCreateView  modalArticleCreateView ;

	@Inject
	ArticleCreateService articleCreateService ;

	@Inject
	@Bundle({ CrudKeys.class, Article.class })
	private ResourceBundle resourceBundle;


	@Inject
	Article model ;
	
	@Inject
	@ModalEntityCreateDoneEvent
	private Event<Article> modalArticleCreateDoneEvent;

	@Inject
	private ServiceCallFailedEventHandler createServiceCallFailedEventHandler;

	@PostConstruct
	public void postConstruct(){          

		bindOnSucceedService();
		bindViewButtonAction();
		modalArticleCreateView.bind(model);

		modalArticleCreateView.getView().getArticleAgencySelection().getAgency().valueProperty().addListener(new ChangeListener<Agency>()
				{
			@Override
			public void changed(ObservableValue<? extends Agency> ov, Agency oldValue,
					Agency newValue)
			{
				if(newValue!=null){
					model.setAgency(new ArticleAgency(newValue));
				}
			}
				});

		modalArticleCreateView.getView().getArticleSectionSelection().getSection().valueProperty().addListener(new ChangeListener<Section>()
				{
			@Override
			public void changed(ObservableValue<? extends Section> ov, Section oldValue,
					Section newValue)
			{
				if(newValue!=null){
					model.setSection(new ArticleSection(newValue));
				}
			}
				});

		createServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().title(resourceBundle.getString("Entity_create_error.title")).showException(exception);

			}
		});
		articleCreateService.setOnFailed(createServiceCallFailedEventHandler);


	}


	public void bindViewButtonAction(){
		modalArticleCreateView.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				modalArticleCreateView.closeDialog();

			}
		});

		modalArticleCreateView.getResetButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				PropertyReader.copy(new Article(), model);
			}
		});

		modalArticleCreateView.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Set<ConstraintViolation<Article>> violations = modalArticleCreateView.getView().validate(model);
				if (violations.isEmpty())
				{
					articleCreateService.setModel(model).start();
				}
				else
				{
					Dialogs.create().title(resourceBundle.getString("Entity_create_error.title"))
					.nativeTitleBar().message(model.getArticleName()).showError();
				}
			}

		});
	}

	public void bindOnSucceedService(){
		articleCreateService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ArticleCreateService s = (ArticleCreateService) event.getSource();
				Article articleCreateResult = s.getValue();
				event.consume();
				s.reset();
				modalArticleCreateView.closeDialog();
				modalArticleCreateDoneEvent.fire(articleCreateResult);

			}
		});
		modalArticleCreateView.getSectionSearchService().setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SectionSearchService s = (SectionSearchService) event.getSource();
				SectionSearchResult searchResult = s.getValue();
				event.consume();
				s.reset();
				List<Section> resultList = searchResult.getResultList();
				modalArticleCreateView.getView().getArticleSectionSelection().getSection().getItems().setAll(resultList);
			}
		});

		modalArticleCreateView.getPackagingModeSearchService().setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				PackagingModeSearchService s = (PackagingModeSearchService) event.getSource();
				PackagingModeSearchResult searchResult = s.getValue();
				event.consume();
				s.reset();
				List<PackagingMode> resultList = searchResult.getResultList();
				modalArticleCreateView.getView().getArticlePackagingModeSelection().getPackagingMode().getItems().setAll(resultList);
			}
		});
		modalArticleCreateView.getAgencySearchService().setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				AgencySearchService s = (AgencySearchService) event.getSource();
				AgencySearchResult searchResult = s.getValue();
				event.consume();
				s.reset();
				List<Agency> resultList = searchResult.getResultList();
				modalArticleCreateView.getView().getArticleAgencySelection().getAgency().getItems().setAll(resultList);
			}
		});
		modalArticleCreateView.getFamilySearchService().setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ProductFamilySearchService s = (ProductFamilySearchService) event.getSource();
				ProductFamilySearchResult searchResult = s.getValue();
				event.consume();
				s.reset();
				List<ProductFamily> resultList = searchResult.getResultList();
				modalArticleCreateView.getView().getArticleFamilySelection().getFamily().getItems().setAll(resultList);
			}
		});
		modalArticleCreateView.getMarginSearchService().setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				SalesMarginSearchService s = (SalesMarginSearchService) event.getSource();
				SalesMarginSearchResult searchResult = s.getValue();
				event.consume();
				s.reset();
				List<SalesMargin> resultList = searchResult.getResultList();
				modalArticleCreateView.getView().getArticleDefaultSalesMarginSelection().getDefaultSalesMargin().getItems().setAll(resultList);
			}
		});
		modalArticleCreateView.getClearanceConfigSearchService().setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ClearanceConfigSearchService s = (ClearanceConfigSearchService) event.getSource();
				ClearanceConfigSearchResult searchResult = s.getValue();
				event.consume();
				s.reset();
				List<ClearanceConfig> resultList = searchResult.getResultList();
				modalArticleCreateView.getView().getArticleClearanceConfigSelection().getClearanceConfig().getItems().setAll(resultList);
			}
		});
	}

	public Article getModel() {
		return model;
	}
public void handleModalArticleCreateEvent(@Observes @ModalEntityCreateRequestedEvent Article article){
	PropertyReader.copy(article, model);
	modalArticleCreateView.showDiaLog();
}

}
