package org.adorsys.adpharma.server.repo;

import org.adorsys.adpharma.server.jpa.CustomerInvoice;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

@Repository(forEntity = CustomerInvoice.class)
public interface CustomerInvoiceRepository extends EntityRepository<CustomerInvoice, Long>
{
	
}
