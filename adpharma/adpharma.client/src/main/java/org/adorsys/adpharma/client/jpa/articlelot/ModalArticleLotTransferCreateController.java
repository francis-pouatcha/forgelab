package org.adorsys.adpharma.client.jpa.articlelot;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchInput;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchResult;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchService;
import org.adorsys.adpharma.client.jpa.section.Section;
import org.adorsys.adpharma.client.jpa.warehousearticlelot.WareHouseArticleLot;
import org.adorsys.javafx.crud.extensions.events.EntityCreateDoneEvent;
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

public class ModalArticleLotTransferCreateController {

	@Inject
	private ModalArticleLotTransferCreateView lotTransferCreateView;

	@Inject
	ArticleLotTransferManager model ;

	@Inject
	private ArticleSearchService articleSearchService;

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler;

	@Inject
	private ArticleSearchInput searchInput;

	@Inject
	private ArticleLotTransferService articleLotTransferService;

	@Inject
	@EntityCreateDoneEvent
	private Event<WareHouseArticleLot> wareHouseArticleLotCreateDoneEvent;

	@Inject
	@WorkingInformationEvent
	private Event<String> workingEvent;

	@Inject
	@Bundle({ CrudKeys.class, ArticleLot.class })
	private ResourceBundle resourceBundle;

	@PostConstruct
	public void postConstruct(){

		searchInput.setMax(-1);

		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().showException(exception);

			}
		});

		lotTransferCreateView.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				lotTransferCreateView.closeDialog();

			}
		});

		lotTransferCreateView.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Set<ConstraintViolation<ArticleLotTransferManager>> validate = lotTransferCreateView.validate(model);
				if(validate.isEmpty()&& isValide(model)){
					Action showConfirm = Dialogs.create().nativeTitleBar().message(resourceBundle.getString("ArticleLot_details_confirmation_description.title")).showConfirm();
					if(Dialog.Actions.YES.equals(showConfirm)){
						articleLotTransferService.setModel(model).start();
					}else {
						lotTransferCreateView.closeDialog();
					}
				}

			}
		});

		articleLotTransferService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ArticleLotTransferService s = (ArticleLotTransferService) event.getSource();
				ArticleLot ent = s.getValue();
				event.consume();
				s.reset();
//				wareHouseArticleLotCreateDoneEvent.fire(ent);
				workingEvent.fire("Lot transfered successfuly !");
				lotTransferCreateView.closeDialog();

			}
		});
		articleLotTransferService.setOnFailed(callFailedEventHandler);

		articleSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				ArticleSearchService s = (ArticleSearchService) event.getSource();
				ArticleSearchResult searchResult = s.getValue();
				event.consume();
				s.reset();
				List<Article> resultList = searchResult.getResultList();
				Section sourceSection = model.getLotToTransfer().getArticle().getSection();
				List<Section> targetSections = new ArrayList<Section>();
				for (Article article : resultList) {
					Section section = new Section();
					PropertyReader.copy(article.getSection(), section);
					if(sourceSection.equals(section))
						continue ;
					targetSections.add(section);
				}
				lotTransferCreateView.getTargetSection().getItems().setAll(targetSections);

			}
		});
		articleSearchService.setOnFailed(callFailedEventHandler);

		lotTransferCreateView.addValidators();
	}

	public void handleModalArticleCreateEvent(@Observes @ModalEntityCreateRequestedEvent ArticleLotTransferManager lotTransferManager){
		PropertyReader.copy(lotTransferManager, model);
		lotTransferCreateView.bind(model);

		lotTransferCreateView.showDiaLog();
		searchInput.getEntity().setPic(model.getLotToTransfer().getMainPic());
		searchInput.getFieldNames().add("pic");
		articleSearchService.setSearchInputs(searchInput).start();
	}
	
	public boolean isValide(ArticleLotTransferManager data){
		
		if(data .getQtyToTransfer()!=null && data.getQtyToTransfer().compareTo(data.getLotQty())>0){
			Dialogs.create().message("La quantite a transferer dois etre inferieur a : "+ data.getLotQty()).showError() ;
			return false ;
		}
		Section section = data .getLotToTransfer().getArticle().getSection();
		if(section!=null && section.equals(data.getTargetSection())){
			Dialogs.create().message("Impossible de transfere vers le meme emplacement ! "+ data.getLotQty()).showError() ;
			return false ;
		}
		return true ;
	}

}
