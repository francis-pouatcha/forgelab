package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.CashDrawer;

@Repository(forEntity = CashDrawer.class)
public interface CashDrawerRepository extends EntityRepository<CashDrawer, Long>
{
   @Modifying
   @Query("delete from CashDrawer e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
