package org.adorsys.adpharma.server.repo;

import java.util.Date;
import java.util.List;

import org.adorsys.adpharma.server.jpa.CashDrawer;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.FirstResult;
import org.apache.deltaspike.data.api.MaxResults;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CashDrawer.class)
public interface CashDrawerRepository extends EntityRepository<CashDrawer, Long>
{
	public List<CashDrawer> findByClosingDateBetween(Date startClosingDate, Date endClosingDate, @FirstResult int start, @MaxResults int max);
	public Long countByClosingDateBetween(Date startClosingDate, Date endClosingDate);
}
