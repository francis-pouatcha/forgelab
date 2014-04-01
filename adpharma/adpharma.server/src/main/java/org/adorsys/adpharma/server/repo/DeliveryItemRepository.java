package org.adorsys.adpharma.server.repo;

import java.util.List;

import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.jpa.Gender;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = DeliveryItem.class)
public interface DeliveryItemRepository extends EntityRepository<DeliveryItem, Long>
{
	List<DeliveryItem> findByDelivery(Delivery delivery);
}
