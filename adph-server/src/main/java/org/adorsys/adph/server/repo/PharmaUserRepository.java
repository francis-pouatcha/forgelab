package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.PharmaUser;

@Repository(forEntity = PharmaUser.class)
public interface PharmaUserRepository extends EntityRepository<PharmaUser, Long>
{
   @Modifying
   @Query("delete from PharmaUser e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
