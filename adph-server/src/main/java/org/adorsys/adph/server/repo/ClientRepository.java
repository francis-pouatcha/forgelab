package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.Client;

@Repository(forEntity = Client.class)
public interface ClientRepository extends EntityRepository<Client, Long>
{
   @Modifying
   @Query("delete from Client e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
