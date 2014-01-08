package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.ProcurementOrder;

@Repository(forEntity = ProcurementOrder.class)
public interface ProcurementOrderRepository extends EntityRepository<ProcurementOrder, Long>
{
   @Modifying
   @Query("delete from ProcurementOrder e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
