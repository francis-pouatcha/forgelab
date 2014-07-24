package org.adorsys.adpharma.server.repo;

import java.util.Date;
import java.util.List;

import org.adorsys.adpharma.server.jpa.CashDrawer;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.FirstResult;
import org.apache.deltaspike.data.api.MaxResults;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CashDrawer.class)
public interface CashDrawerRepository extends EntityRepository<CashDrawer, Long>
{
	@Query("select c from CashDrawer AS c where c.openingDate >= ?1 AND c.openingDate <= ?2 And c.opened = ?3")
	public List<CashDrawer> findByOpeningDateBetween(Date minOpeningDate, Date maxOpeningDate,Boolean opened,@FirstResult int start, @MaxResults int max);
	
	@Query("select count(c) from CashDrawer AS c where  c.openingDate >= ?1 AND c.openingDate <= ?2 And c.opened = ?3")
	public Long countByOpeningDateBetween(Date minOpeningDate, Date maxOpeningDate,Boolean opened);
}
