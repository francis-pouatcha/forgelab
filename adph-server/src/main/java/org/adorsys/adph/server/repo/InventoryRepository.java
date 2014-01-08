package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.Inventory;

@Repository(forEntity = Inventory.class)
public interface InventoryRepository extends EntityRepository<Inventory, Long>
{
   @Modifying
   @Query("delete from Inventory e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
