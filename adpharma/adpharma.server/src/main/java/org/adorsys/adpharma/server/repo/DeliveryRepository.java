package org.adorsys.adpharma.server.repo;

import org.adorsys.adpharma.server.jpa.Delivery;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository(forEntity = Delivery.class)
public  abstract class  DeliveryRepository  extends AdpharmaEntityRepository<Delivery, Long> 
{
}
