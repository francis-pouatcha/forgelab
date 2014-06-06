package org.adorsys.adpharma.client.access;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginAgency;
import org.adorsys.adpharma.client.jpa.login.LoginSearchInput;
import org.adorsys.adpharma.client.jpa.login.LoginSearchService;
import org.adorsys.adpharma.client.jpa.loginrolenameassoc.LoginRoleNameAssoc;
import org.adorsys.adpharma.client.jpa.loginrolenameassoc.LoginRoleNameAssocSearchResult;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.LoginSucceededEvent;
import org.adorsys.javafx.crud.extensions.login.LogoutSucceededEvent;
import org.adorsys.javafx.crud.extensions.login.PermissionsEvent;
import org.adorsys.javafx.crud.extensions.login.RolesEvent;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class SecurityUtil {

	private Login connectedUser = null;
	@Inject
	private LoginSearchService loginSearchService;
	@Inject
	private ServiceCallFailedEventHandler loginSearchServiceCallFailedEventHandler;

	private Set<String> roles = Collections.emptySet();
	@Inject
	private RolesTask rolesTask;
	@Inject
	private ServiceCallFailedEventHandler rolesTaskCallFailedEventHandler;
	@Inject
	@RolesEvent
	private Event<Set<String>> rolesEvent;

	Set<String> permissions = Collections.emptySet();
	@Inject
	private PermsTask dcTask;
	@Inject
	private ServiceCallFailedEventHandler dcTaskCallFailedEventHandler;
	@Inject
	@PermissionsEvent
	private Event<Set<String>> permissionsEvent;
	@Inject
	private ErrorMessageDialog dcTaskErrorMessageDialog;

	@Inject
	private ErrorMessageDialog errorMessageDialog;

	@Inject
	@Bundle(CrudKeys.class)
	private ResourceBundle resourceBundle;

	public Login getConnectedUser() {
		return connectedUser;
	}

	public boolean hasRole(String role) {
		return roles.contains(role);
	}
	
	public boolean hasPermission(String permission){
		return permissions.contains(permission);
	}

	public void setConnectedUser(Login connectedUser) {
		this.connectedUser = connectedUser;
	}

	public LoginAgency getAgency() {
		if (connectedUser != null)
			return connectedUser.getAgency();
		return null;
	}

	@PostConstruct
	public void postConstruct() {
		loginSearchServiceCallFailedEventHandler
				.setErrorDisplay(new ErrorDisplay() {

					@Override
					protected void showError(Throwable exception) {
						Dialogs.create().nativeTitleBar()
								.showException(exception);

					}
				});

		loginSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				LoginSearchService s = (LoginSearchService) event.getSource();
				List<Login> resultList = s.getValue().getResultList();
				event.consume();
				s.reset();
				if (!resultList.isEmpty()) {
					connectedUser = resultList.iterator().next();
					rolesTask.setLogin(connectedUser).start();
				}
			}
		});

		loginSearchService
				.setOnFailed(loginSearchServiceCallFailedEventHandler);

		// send search result event.
		rolesTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				RolesTask s = (RolesTask) event.getSource();
				LoginRoleNameAssocSearchResult searchResult = s.getValue();
				event.consume();
				s.reset();
				List<LoginRoleNameAssoc> resultList = searchResult
						.getResultList();
				Set<String> set = new HashSet<String>();
				for (LoginRoleNameAssoc loginRoleNameAssoc : resultList) {
					set.add(loginRoleNameAssoc.getTarget().getName());
				}
				roles = Collections.unmodifiableSet(set);
			    dcTask.setLoginName(connectedUser.getLoginName()).start();
			}
		});

		rolesTaskCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {
			@Override
			protected void showError(Throwable exception) {
				String message = exception.getMessage();
				errorMessageDialog.getTitleText().setText(
						resourceBundle.getString("Entity_search_error.title"));
				if (!StringUtils.isBlank(message))
					errorMessageDialog.getDetailText().setText(message);
				errorMessageDialog.display();
			}
		});
		rolesTask.setOnFailed(rolesTaskCallFailedEventHandler);
		errorMessageDialog.getOkButton().setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						errorMessageDialog.closeDialog();
					}
				});
		
	      // send search result event.
	      dcTask.setOnSucceeded(new EventHandler<WorkerStateEvent>()
	      {
	         @Override
	         public void handle(WorkerStateEvent event)
	         {
	            PermsTask s = (PermsTask) event.getSource();
	            String perms = s.getValue();
	            event.consume();
	            s.reset();
	            String[] split = perms.split(":");
	            permissions = new HashSet<String>();
	            for (String string : split)
	            {
	               if (StringUtils.isNotBlank(string))
	            	   permissions.add(string);
	            }
	            rolesEvent.fire(Collections.unmodifiableSet(roles));
	            permissionsEvent.fire(Collections.unmodifiableSet(permissions));
	         }
	      });

	      dcTaskCallFailedEventHandler.setErrorDisplay(new ErrorDisplay()
	      {
	         @Override
	         protected void showError(Throwable exception)
	         {
	            String message = exception.getMessage();
	            dcTaskErrorMessageDialog.getTitleText().setText(
	                  resourceBundle.getString("Entity_search_error.title"));
	            if (!StringUtils.isBlank(message))
	               dcTaskErrorMessageDialog.getDetailText().setText(message);
	            dcTaskErrorMessageDialog.display();
	         }
	      });
	      dcTask.setOnFailed(dcTaskCallFailedEventHandler);
	      dcTaskErrorMessageDialog.getOkButton().setOnAction(
	            new EventHandler<ActionEvent>()
	            {
	               @Override
	               public void handle(ActionEvent event)
	               {
	                  dcTaskErrorMessageDialog.closeDialog();
	               }
	            });		

	}

	public void handleLoginSucceedEvent(
			@Observes(notifyObserver = Reception.ALWAYS) @LoginSucceededEvent String loginName) {
		LoginSearchInput loginSearchInput = new LoginSearchInput();
		loginSearchInput.setMax(1);
		loginSearchInput.getEntity().setLoginName(loginName);
		loginSearchInput.getFieldNames().add("loginName");
		loginSearchService.setSearchInputs(loginSearchInput).start();
	}

	public void handleLogoutSucceedEvent(
			@Observes(notifyObserver = Reception.ALWAYS) @LogoutSucceededEvent Object object) {
		this.connectedUser = null;
		this.roles = Collections.emptySet();
		rolesEvent.fire(Collections.unmodifiableSet(roles));
		this.permissions = Collections.emptySet();
		permissionsEvent.fire(Collections.unmodifiableSet(permissions));
	}

}
