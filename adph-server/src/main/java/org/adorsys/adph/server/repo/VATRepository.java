package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.VAT;

@Repository(forEntity = VAT.class)
public interface VATRepository extends EntityRepository<VAT, Long>
{
   @Modifying
   @Query("delete from VAT e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
