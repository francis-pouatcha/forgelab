package org.adorsys.adpharma.server.repo;

import java.util.Date;

import org.adorsys.adpharma.server.jpa.DeliveryItemArticleEvent;
import org.adorsys.adpharma.server.jpa.DeliveryItemArticleLotEvent;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = DeliveryItemArticleEvent.class)
public interface DeliveryItemArticleEventRepository extends EntityRepository<DeliveryItemArticleEvent, Long>
{

	@Query("SELECT e FROM DeliveryItemArticleEvent AS e WHERE e.created <?1")
	public QueryResult<DeliveryItemArticleEvent> loadOldestEvent(Date date) ;
}
