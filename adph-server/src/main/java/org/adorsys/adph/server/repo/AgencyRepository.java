package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.Agency;

@Repository(forEntity = Agency.class)
public interface AgencyRepository extends EntityRepository<Agency, Long>
{
   @Modifying
   @Query("delete from Agency e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
