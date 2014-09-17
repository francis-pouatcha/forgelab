package org.adorsys.adpharma.client.jpa.articlelot;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfig;
import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfigSearchInput;
import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfigSearchResult;
import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfigSearchService;
import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfigSource;
import org.adorsys.adpharma.client.utils.ArticleLotDetailResultHolder;
import org.adorsys.javafx.crud.extensions.events.EntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.login.WorkingInformationEvent;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

public class ModalArticleLotDetailsCreateController {

	@Inject
	private ModalArticleLotDetailsCreateView lotDetailsCreateView;

	@Inject
	ArticleLotDetailsManager model ;

	@Inject
	private ProductDetailConfigSearchService detailConfigSearchService;

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler;

	@Inject
	private ProductDetailConfigSearchInput searchInput;

	@Inject
	private ArticleLotDetailsService articleLotDetailsService;

	@Inject
	@EntityEditDoneEvent
	private Event<ArticleLotDetailResultHolder> detailRequestDoneEvent;

	@Inject
	@WorkingInformationEvent
	private Event<String> workingEvent;

	@Inject
	@Bundle({ CrudKeys.class, ArticleLot.class })
	private ResourceBundle resourceBundle;

	@PostConstruct
	public void postConstruct(){
		lotDetailsCreateView.bind(model);
		searchInput.setMax(-1);
		searchInput.getFieldNames().add("source");

		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().showException(exception);

			}
		});

		lotDetailsCreateView.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				lotDetailsCreateView.closeDialog();

			}
		});

		lotDetailsCreateView.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Set<ConstraintViolation<ArticleLotDetailsManager>> validate = lotDetailsCreateView.validate(model);
				if(validate.isEmpty()){
					Action showConfirm = Dialogs.create().nativeTitleBar().message(resourceBundle.getString("ArticleLot_details_confirmation_description.title")).showConfirm();
					if(Dialog.Actions.YES.equals(showConfirm)){
						articleLotDetailsService.setModel(model).start();
					}else {
						lotDetailsCreateView.closeDialog();
					}
				}

			}
		});
		articleLotDetailsService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ArticleLotDetailsService s = (ArticleLotDetailsService) event.getSource();
				ArticleLotDetailResultHolder ent = s.getValue();
				event.consume();
				s.reset();
				detailRequestDoneEvent.fire(ent);
				workingEvent.fire("Article details successfuly !");
				lotDetailsCreateView.closeDialog();

			}
		});
		articleLotDetailsService.setOnFailed(callFailedEventHandler);

		detailConfigSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ProductDetailConfigSearchService s = (ProductDetailConfigSearchService) event.getSource();
				ProductDetailConfigSearchResult searchResult = s.getValue();
				event.consume();
				s.reset();
				List<ProductDetailConfig> resultList = searchResult.getResultList();
				lotDetailsCreateView.getDetailsConfig().getItems().setAll(resultList);
				if(!resultList.isEmpty())
					lotDetailsCreateView.getDetailsConfig().getSelectionModel().select(0);

			}
		});
		detailConfigSearchService.setOnFailed(callFailedEventHandler);

		lotDetailsCreateView.getDetailsConfig().valueProperty().addListener(new ChangeListener<ProductDetailConfig>() {

			@Override
			public void changed(
					ObservableValue<? extends ProductDetailConfig> observable,
					ProductDetailConfig oldValue, ProductDetailConfig newValue) {
				if(newValue!=null){
					lotDetailsCreateView.getTargetPrice().setNumber(newValue.getSalesPrice());
					lotDetailsCreateView.getTargetQty().setNumber(newValue.getTargetQuantity());
				}

			}
		});

		lotDetailsCreateView.addValidators();
	}

	public void handleModalArticleCreateEvent(@Observes @ModalEntityCreateRequestedEvent ArticleLotDetailsManager lotDetailsManager){
		PropertyReader.copy(lotDetailsManager, model);
		lotDetailsCreateView.showDiaLog();

		ArticleLotArticle articleLotArticle = model.getLotToDetails().getArticle();
		ProductDetailConfigSource configSource = new ProductDetailConfigSource();
		PropertyReader.copy(articleLotArticle, configSource);
		searchInput.getEntity().setSource(configSource);
		detailConfigSearchService.setSearchInputs(searchInput).start();
	}

}
