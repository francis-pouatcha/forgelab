package org.adorsys.adpharma.server.repo;

import java.util.Date;
import java.util.List;

import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DocumentProcessingState;
import org.adorsys.adpharma.server.jpa.Supplier;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = Delivery.class)
public interface DeliveryRepository extends EntityRepository<Delivery, Long>
{
	@Query("select d FROM Delivery as d where d.deliveryDate between ?1 and ?2 OR d.supplier = ?3 OR d.deliveryProcessingState = ?4")
	public List<Delivery> findByDeliveryDateBetween(Date dateFrom,Date dateTo,Supplier supplier,DocumentProcessingState status );
}
