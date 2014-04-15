package org.adorsys.adpharma.client.jpa.articlelot;

import java.util.List;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfig;
import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfigSearchInput;
import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfigSearchResult;
import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfigSearchService;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
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
	
	
	@PostConstruct
	public void postConstruct(){
		lotDetailsCreateView.bind(model);
		
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
				Dialogs.create().message("not yet implemented !").nativeTitleBar().showInformation();
				
			}
		});
		
		detailConfigSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			
			@Override
			public void handle(WorkerStateEvent event) {
				ProductDetailConfigSearchService s = (ProductDetailConfigSearchService) event.getSource();
				ProductDetailConfigSearchResult searchResult = s.getValue();
				event.consume();
				s.reset();
				List<ProductDetailConfig> resultList = searchResult.getResultList();
				lotDetailsCreateView.getDetailsConfig().getItems().setAll(resultList);
				
			}
		});
		detailConfigSearchService.setOnFailed(callFailedEventHandler);
	}
	
	public void handleModalArticleCreateEvent(@Observes @ModalEntityCreateRequestedEvent ArticleLotDetailsManager lotDetailsManager){
		PropertyReader.copy(lotDetailsManager, model);
		lotDetailsCreateView.showDiaLog();
		detailConfigSearchService.setSearchInputs(new ProductDetailConfigSearchInput()).start();
	}

}
