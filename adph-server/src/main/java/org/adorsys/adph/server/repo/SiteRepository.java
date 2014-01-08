package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.Site;

@Repository(forEntity = Site.class)
public interface SiteRepository extends EntityRepository<Site, Long>
{
   @Modifying
   @Query("delete from Site e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
