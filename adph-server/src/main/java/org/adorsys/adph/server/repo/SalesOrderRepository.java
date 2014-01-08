package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.SalesOrder;

@Repository(forEntity = SalesOrder.class)
public interface SalesOrderRepository extends EntityRepository<SalesOrder, Long>
{
   @Modifying
   @Query("delete from SalesOrder e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
