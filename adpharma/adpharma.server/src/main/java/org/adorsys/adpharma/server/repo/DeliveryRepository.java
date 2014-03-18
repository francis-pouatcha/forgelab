package org.adorsys.adpharma.server.repo;

import java.util.Date;
import java.util.List;

import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DocumentProcessingState;
import org.adorsys.adpharma.server.jpa.Supplier;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = Delivery.class)
public interface DeliveryRepository extends EntityRepository<Delivery, Long>
{
}
