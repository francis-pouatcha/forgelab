package org.adorsys.adpharma.server.repo;

import java.util.Date;
import java.util.List;

import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.jpa.DeliveryItemArticleLotEvent;
import org.adorsys.adpharma.server.jpa.Gender;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = DeliveryItemArticleLotEvent.class)
public interface DeliveryItemArticleLotEventRepository extends EntityRepository<DeliveryItemArticleLotEvent, Long>
{
	@Query("SELECT e FROM DeliveryItemArticleLotEvent AS e WHERE e.created <?1")
	public QueryResult<DeliveryItemArticleLotEvent> loadOldestEvent(Date date) ;
}
