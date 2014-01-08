package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.ClientDebt;

@Repository(forEntity = ClientDebt.class)
public interface ClientDebtRepository extends EntityRepository<ClientDebt, Long>
{
   @Modifying
   @Query("delete from ClientDebt e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
