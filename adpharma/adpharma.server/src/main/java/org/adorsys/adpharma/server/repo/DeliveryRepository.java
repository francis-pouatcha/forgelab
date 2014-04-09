package org.adorsys.adpharma.server.repo;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adpharma.server.jpa.Delivery;

@Repository(forEntity = Delivery.class)
public interface DeliveryRepository extends EntityRepository<Delivery, Long>
{
	
}
