package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.InvoiceItem;

@Repository(forEntity = InvoiceItem.class)
public interface InvoiceItemRepository extends EntityRepository<InvoiceItem, Long>
{
   @Modifying
   @Query("delete from InvoiceItem e WHERE e.id=?1")
   public void removeJPQL(Long id);

}