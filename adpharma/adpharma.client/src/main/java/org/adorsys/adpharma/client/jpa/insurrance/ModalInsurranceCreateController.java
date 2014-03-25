package org.adorsys.adpharma.client.jpa.insurrance;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.controlsfx.dialog.Dialogs;

public class ModalInsurranceCreateController {

	@Inject
	ModalInsurranceCreateView createView;

	@Inject 
	InsurranceCreateService createService;

	@Inject   
	ServiceCallFailedEventHandler callFailedEventHandler ;

	@PostConstruct
	public void postConstruct(){
		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().showException(exception);				
			}
		});

	}
}
