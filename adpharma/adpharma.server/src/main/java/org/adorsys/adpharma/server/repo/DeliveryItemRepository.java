package org.adorsys.adpharma.server.repo;

import java.util.List;

import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.jpa.Gender;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = DeliveryItem.class)
public interface DeliveryItemRepository extends EntityRepository<DeliveryItem, Long>
{// Francis 01/15/2015 this is not good
//	@Query("SELECT d from DeliveryItem as d where d.delivery = ?1 order by d.article.section.id , d.article.articleName ")
//	List<DeliveryItem> findByDelivery(Delivery delivery);
}
