package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.SalesOrderItem;

@Repository(forEntity = SalesOrderItem.class)
public interface SalesOrderItemRepository extends EntityRepository<SalesOrderItem, Long>
{
   @Modifying
   @Query("delete from SalesOrderItem e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
