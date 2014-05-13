package org.adorsys.adpharma.server.repo;

import org.adorsys.adpharma.server.jpa.CustomerInvoice;
import org.adorsys.adpharma.server.utils.ApharmaDateUtil;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CustomerInvoice.class)
public abstract class CustomerInvoiceRepository extends AdpharmaEntityRepository<CustomerInvoice, Long>
{
	
}
