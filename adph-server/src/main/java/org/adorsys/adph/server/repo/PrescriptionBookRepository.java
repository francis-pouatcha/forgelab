package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.PrescriptionBook;

@Repository(forEntity = PrescriptionBook.class)
public interface PrescriptionBookRepository extends EntityRepository<PrescriptionBook, Long>
{
   @Modifying
   @Query("delete from PrescriptionBook e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
