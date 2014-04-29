package org.adorsys.adpharma.client.jpa.customer;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

public class ModalCustomerCustomerCategoryCreateController extends CustomerCustomerCategoryCreateController {

	@Inject
	ModalCustomerCreateView modalCustomerCreateView;

	@PostConstruct
	public void postConstruct()
	{
	}

	public void handleNewModelEvent(@Observes @CreateModelEvent Customer model)
	{
		this.sourceEntity = model;
		bind(modalCustomerCreateView.getView().getCustomerCustomerCategorySelection(), modalCustomerCreateView.getView().getCustomerCustomerCategoryForm());
		activateButton(createView.getView().getCustomerCustomerCategorySelection());
	}
}
