package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.ClientCategory;

@Repository(forEntity = ClientCategory.class)
public interface ClientCategoryRepository extends EntityRepository<ClientCategory, Long>
{
   @Modifying
   @Query("delete from ClientCategory e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
