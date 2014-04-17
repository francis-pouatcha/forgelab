package org.adorsys.adpharma.client.jpa.loader;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.delivery.DeliveryService;

public class LoaderCheck extends Service<Long> {

	@Inject
	private DeliveryService remoteService;

	/*
	 * Check if there is any delivery in the system and quick if necessary.
	 */
	private Long check() {
		return remoteService.count();
	}

	@Override
	protected Task<Long> createTask() {
		return new Task<Long>() {
			@Override
			protected Long call() throws Exception {
				return check();
			}
		};
	}
}
