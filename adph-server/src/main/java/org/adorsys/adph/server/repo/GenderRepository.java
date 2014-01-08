package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.Gender;

@Repository(forEntity = Gender.class)
public interface GenderRepository extends EntityRepository<Gender, Long>
{
   @Modifying
   @Query("delete from Gender e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
