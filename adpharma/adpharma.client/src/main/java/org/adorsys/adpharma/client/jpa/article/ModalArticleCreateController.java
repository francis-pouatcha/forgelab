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
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateRequestedEvent;
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
	ServiceCallFailedEventHandler serviceCallFailedEventHandler ;

	@Inject
	@Bundle({ CrudKeys.class, Article.class })
	private ResourceBundle resourceBundle;

	@Inject
	@CreateModelEvent
	private Event<Article> createModelEvent;

	@Inject
	Article model ;

	@Inject
	@ModalEntityCreateDoneEvent
	private Event<Article> modalArticleCreateDoneEvent;

	@PostConstruct
	public void postConstruct(){          

		modalArticleCreateView.bind(model);
		//		handle cancel action
		modalArticleCreateView.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				modalArticleCreateView.closeDialog();

			}
		});
		//  handele create action
		modalArticleCreateView.getResetButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				PropertyReader.copy(new Article(), model);
				modalArticleCreateView.closeDialog();
			}
		});
		//  handle save action
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


		serviceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().title(resourceBundle.getString("Entity_create_error.title")).showException(exception);

			}
		});

		//		  handle created call 
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

		articleCreateService.setOnFailed(serviceCallFailedEventHandler);

		createModelEvent.fire(model);

	}


	public Article getModel() {
		return model;
	}
	public void handleModalArticleCreateEvent(@Observes @ModalEntityCreateRequestedEvent Article article){
		PropertyReader.copy(article, model);
		modalArticleCreateView.showDiaLog();
	}

}
