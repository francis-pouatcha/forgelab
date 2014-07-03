package org.adorsys.adpharma.client.jpa.article;

import java.util.ResourceBundle;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.events.EntityRemoveConfirmedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveRequestEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ConfirmDialog;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

public class ArticleRemoveControler
{

	@Inject
	@EntityRemoveDoneEvent
	private Event<Article> removedEvent;

	@Inject
	private ArticleRemoveService removeService;
	@Inject
	private ServiceCallFailedEventHandler removeServiceCallFailedEventHandler;

	@Inject
	@Bundle({ CrudKeys.class, Article.class })
	private ResourceBundle resourceBundle;

	private Article entity;

	@Inject
	private ErrorMessageDialog errorMessageDialog;

	public void handleRemoveRequest(
			@Observes @EntityRemoveRequestEvent Article entity)
	{
		this.entity = entity;
	}

	public void handleRemoveOkEvent(
			@Observes @EntityRemoveConfirmedEvent Article entity)
	{
		Action showConfirm = Dialogs.create().message(resourceBundle.getString("Entity_confirm_remove.title")
				+ " "
				+ resourceBundle.getString("Article_description.title")).showConfirm();
		if(Dialog.Actions.YES.equals(showConfirm)){
			removeService.setEntity(entity).start();
		}
	}

	@PostConstruct
	public void postConstruct()
	{

		removeService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
				{
			@Override
			public void handle(WorkerStateEvent wse)
			{
				ArticleRemoveService service = (ArticleRemoveService) wse
						.getSource();
				Article p = service.getValue();
				wse.consume();
				service.reset();
				removedEvent.fire(p);
			}
				});
		removeServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay()
		{
			@Override
			protected void showError(Throwable exception)
			{
				Dialogs.create().title(resourceBundle.getString("Entity_remove_error.title"))
				.message("Impossible de suprimer :"+exception.getMessage()).showError();
			}
		});
		removeService.setOnFailed(removeServiceCallFailedEventHandler);
		errorMessageDialog.getOkButton().setOnAction(
				new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent event)
					{
						errorMessageDialog.closeDialog();
					}
				});
	}
}
