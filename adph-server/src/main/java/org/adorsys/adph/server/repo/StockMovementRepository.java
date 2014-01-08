package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.StockMovement;

@Repository(forEntity = StockMovement.class)
public interface StockMovementRepository extends EntityRepository<StockMovement, Long>
{
   @Modifying
   @Query("delete from StockMovement e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
