package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.PackagingMode;

@Repository(forEntity = PackagingMode.class)
public interface PackagingModeRepository extends EntityRepository<PackagingMode, Long>
{
   @Modifying
   @Query("delete from PackagingMode e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
