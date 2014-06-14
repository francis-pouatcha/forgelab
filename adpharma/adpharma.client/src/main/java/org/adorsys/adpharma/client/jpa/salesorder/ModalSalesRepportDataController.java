package org.adorsys.adpharma.client.jpa.salesorder;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.access.SecurityUtil;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginAgency;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemPeriodicalSearchService;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.controlsfx.dialog.Dialogs;

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
	private SalesOrderItemPeriodicalSearchService salesOrderItemPeriodicalSearchService ;
	
	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler ;

	@PostConstruct
	public void postconstruct(){
		view.bind(model);
		
		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {
			
			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);
				
			}
		});
		
		salesOrderItemPeriodicalSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			
			@Override
			public void handle(WorkerStateEvent event) {
				SalesOrderItemPeriodicalSearchService source = (SalesOrderItemPeriodicalSearchService) event.getSource();
				List<SalesOrderItem> value = source.getValue().getResultList();
				event.consume();
				source.reset();
				
				try {
					Login login = securityUtil.getConnectedUser();
					LoginAgency agency = securityUtil.getAgency();
					SalesOrderReportPrintTemplatePDF worker = new SalesOrderReportPrintTemplatePDF(login,agency,model);
					worker.addItems(value);
					worker.closeDocument();
					File file = new File(worker.getFileName());
					openFile(file);
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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

		view.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override 
			public void handle(ActionEvent event) {
				Set<ConstraintViolation<PeriodicalDataSearchInput>> validate = view.validate(model);
				if(validate.isEmpty()){
					salesOrderItemPeriodicalSearchService.setSearchInputs(model).start();
				}
			}
		});
		view.addValidators();
	}
	
	
	public void handleSalesRepportSearchDataRequestEvent(@Observes @EntitySearchRequestedEvent PeriodicalDataSearchInput data){
		PropertyReader.copy(data, model);
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
