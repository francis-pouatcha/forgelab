package org.adorsys.adpharma.server.repo;

import java.util.Date;
import java.util.List;

import org.adorsys.adpharma.server.jpa.Agency;
import org.adorsys.adpharma.server.jpa.CustomerInvoice;
import org.adorsys.adpharma.server.utils.ApharmaDateUtil;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CustomerInvoice.class)
public abstract class CustomerInvoiceRepository extends AdpharmaEntityRepository<CustomerInvoice, Long>
{
	
	@Query("SELECT ci FROM CustomerInvoice AS ci WHERE ci.creationDate BETWEEN ?1 AND ?2 AND ci.agency = ?3")
	public abstract List<CustomerInvoice> findByAgencyAndDateBetween(Date fromDate ,Date toDate ,Agency agency);
	
}
