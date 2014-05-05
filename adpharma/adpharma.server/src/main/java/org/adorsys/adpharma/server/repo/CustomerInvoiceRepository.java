package org.adorsys.adpharma.server.repo;

import org.adorsys.adpharma.server.jpa.CustomerInvoice;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CustomerInvoice.class)
public interface CustomerInvoiceRepository extends EntityRepository<CustomerInvoice, Long>
{
	
}
