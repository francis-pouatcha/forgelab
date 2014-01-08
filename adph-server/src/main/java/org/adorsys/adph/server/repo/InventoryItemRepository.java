package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.InventoryItem;

@Repository(forEntity = InventoryItem.class)
public interface InventoryItemRepository extends EntityRepository<InventoryItem, Long>
{
   @Modifying
   @Query("delete from InventoryItem e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
