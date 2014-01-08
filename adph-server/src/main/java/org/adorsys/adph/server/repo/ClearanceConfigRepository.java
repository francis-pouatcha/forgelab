package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.ClearanceConfig;

@Repository(forEntity = ClearanceConfig.class)
public interface ClearanceConfigRepository extends EntityRepository<ClearanceConfig, Long>
{
   @Modifying
   @Query("delete from ClearanceConfig e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
