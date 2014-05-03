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
	@Query("select c from CashDrawer c where c.closingDate between ?1 and ?2")
	public List<CashDrawer> findByClosingDateBetween(Date minClosingDate, Date maxClosingDate, @FirstResult int start, @MaxResults int max);
	
	@Query("select count(c) from CashDrawer c where c.closingDate between ?1 and ?2")
	public Long countByClosingDateBetween(Date minClosingDate, Date maxClosingDate);
}
