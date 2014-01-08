package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.RoleName;

@Repository(forEntity = RoleName.class)
public interface RoleNameRepository extends EntityRepository<RoleName, Long>
{
   @Modifying
   @Query("delete from RoleName e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
