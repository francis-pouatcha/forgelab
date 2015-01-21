package org.adorsys.adpharma.server.rest;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adpharma.server.jpa.DeliveryItemArticleLotEvent;
import org.apache.commons.lang3.time.DateUtils;

@Singleton
public class DIArticleLotEventWorker {

	private static final int BATCH_SIZE = 100;

	@Inject 
	private ArticleLotEJB articleLotEJB ;

	@Inject
	private DeliveryItemArticleLotEventEJB deliveryItemArticleLotEventEJB;

	@Schedule(minute="7/5", hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void process()  {
		List<DeliveryItemArticleLotEvent> oldestEvent = deliveryItemArticleLotEventEJB.loadOldestEvent(BATCH_SIZE, new Date()) ;
		Date aMinuteAgo = DateUtils.addMinutes(new Date(), -1);
		while (!oldestEvent.isEmpty()) {
			for (DeliveryItemArticleLotEvent itemEvent : oldestEvent) {
				articleLotEJB.processStockChange(itemEvent);
				deliveryItemArticleLotEventEJB.deleteById(itemEvent.getId());
			}
			oldestEvent = deliveryItemArticleLotEventEJB.loadOldestEvent(BATCH_SIZE, aMinuteAgo) ;

		}
	}

}
