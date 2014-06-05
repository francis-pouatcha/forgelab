package org.adorsys.adpharma.client.jpa.login;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.access.SecurityUtil;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.login.UserInfoRequestEvent;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class ModalUserInfoController {

	@Inject
	private ModalUserInfoView view ;

	@Inject
	private SecurityUtil securityUtil ;

	@Inject
	private LoginSalesKeyResetService loginSalesKeyResetService  ;

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler ;


	@Inject
	private Login login ;

	@PostConstruct
	public void ppostContruct(){
		view.bind(login);
		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

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

		view.getSaveButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				view.closeDialog();

			}
		});

		view.getSaleKeyButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				loginSalesKeyResetService.setLogin(login).start() ;
			}
		});

		loginSalesKeyResetService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				LoginSalesKeyResetService s = (LoginSalesKeyResetService) event.getSource();
				Login cs = s.getValue();
				event.consume();
				s.reset();
				PropertyReader.copy(cs, login);

			}
		});


		loginSalesKeyResetService.setOnFailed(callFailedEventHandler);

	}

	public void handleUserInfoRequestEvent(@Observes @UserInfoRequestEvent Object object){
		Login connectedUser = securityUtil.getConnectedUser();
		if(connectedUser!=null){
			if("sales".equals(connectedUser.getLoginName())){
				Dialogs.create().message("impossible de modifier les infos de cette utilisateur").nativeTitleBar().showError();
			}else {

				PropertyReader.copy(connectedUser, login);
				view.showDiaLog();
			}
		}
	}

}
