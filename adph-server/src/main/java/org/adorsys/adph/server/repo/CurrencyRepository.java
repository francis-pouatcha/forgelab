package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.Currency;

@Repository(forEntity = Currency.class)
public interface CurrencyRepository extends EntityRepository<Currency, Long>
{
   @Modifying
   @Query("delete from Currency e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
