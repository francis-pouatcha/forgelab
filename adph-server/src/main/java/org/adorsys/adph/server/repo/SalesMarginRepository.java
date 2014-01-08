package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.SalesMargin;

@Repository(forEntity = SalesMargin.class)
public interface SalesMarginRepository extends EntityRepository<SalesMargin, Long>
{
   @Modifying
   @Query("delete from SalesMargin e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
