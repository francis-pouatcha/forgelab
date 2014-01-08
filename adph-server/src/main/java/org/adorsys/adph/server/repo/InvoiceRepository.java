package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.Invoice;

@Repository(forEntity = Invoice.class)
public interface InvoiceRepository extends EntityRepository<Invoice, Long>
{
   @Modifying
   @Query("delete from Invoice e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
