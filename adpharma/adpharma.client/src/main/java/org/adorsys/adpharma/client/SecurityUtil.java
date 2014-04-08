package org.adorsys.adpharma.client;

import java.util.List;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginAgency;
import org.adorsys.adpharma.client.jpa.login.LoginSearchInput;
import org.adorsys.adpharma.client.jpa.login.LoginSearchService;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.LoginSucceededEvent;
import org.adorsys.javafx.crud.extensions.login.LogoutSucceededEvent;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class SecurityUtil {

	@Inject
	private LoginSearchService loginSearchService;

	@Inject
	private ServiceCallFailedEventHandler serviceCallFailedEventHandler ;

	private Login connectedUser = null;

	public Login getConnectedUser() {
		return connectedUser;
	}

	public void setConnectedUser(Login connectedUser) {
		this.connectedUser = connectedUser;
	}
	
	public LoginAgency getAgency(){
		if(connectedUser!=null)
			return connectedUser.getAgency();
		return null;
	}

	@PostConstruct
	public void postConstruct(){
		serviceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().showException(exception);

			}
		});

		loginSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				LoginSearchService s = (LoginSearchService) event.getSource();
				List<Login> resultList = s.getValue().getResultList();
				event.consume();
				s.reset();
				if(!resultList.isEmpty())
					connectedUser = resultList.iterator().next();


			}
		});

		loginSearchService.setOnFailed(serviceCallFailedEventHandler);


	}

	public void handleLoginSucceedEvent(@Observes(notifyObserver=Reception.ALWAYS) @LoginSucceededEvent String loginName){
		LoginSearchInput loginSearchInput = new LoginSearchInput();
		loginSearchInput.setMax(1);
		loginSearchInput.getEntity().setLoginName(loginName);
		loginSearchInput.getFieldNames().add("loginName");
		loginSearchService.setSearchInputs(loginSearchInput).start();
	}


	public void handleLogoutSucceedEvent(@Observes(notifyObserver=Reception.ALWAYS) @LogoutSucceededEvent Object object){
		this.connectedUser = null;
	}

}
