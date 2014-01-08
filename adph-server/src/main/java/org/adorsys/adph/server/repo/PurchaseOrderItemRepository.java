package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.PurchaseOrderItem;

@Repository(forEntity = PurchaseOrderItem.class)
public interface PurchaseOrderItemRepository extends EntityRepository<PurchaseOrderItem, Long>
{
   @Modifying
   @Query("delete from PurchaseOrderItem e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
