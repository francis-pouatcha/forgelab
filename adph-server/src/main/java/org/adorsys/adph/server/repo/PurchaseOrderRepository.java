package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.PurchaseOrder;

@Repository(forEntity = PurchaseOrder.class)
public interface PurchaseOrderRepository extends EntityRepository<PurchaseOrder, Long>
{
   @Modifying
   @Query("delete from PurchaseOrder e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
