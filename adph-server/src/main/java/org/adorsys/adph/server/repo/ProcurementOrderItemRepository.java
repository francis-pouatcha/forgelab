package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.ProcurementOrderItem;

@Repository(forEntity = ProcurementOrderItem.class)
public interface ProcurementOrderItemRepository extends EntityRepository<ProcurementOrderItem, Long>
{
   @Modifying
   @Query("delete from ProcurementOrderItem e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
