package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.Section;

@Repository(forEntity = Section.class)
public interface SectionRepository extends EntityRepository<Section, Long>
{
   @Modifying
   @Query("delete from Section e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
