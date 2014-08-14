package org.adorsys.adpharma.server.repo;

import java.util.Date;
import java.util.List;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adpharma.server.jpa.Agency;
import org.adorsys.adpharma.server.jpa.Customer;
import org.adorsys.adpharma.server.jpa.CustomerInvoice;
import org.adorsys.adpharma.server.jpa.Insurrance;
import org.adorsys.adpharma.server.jpa.InvoiceType;

@Repository(forEntity = Insurrance.class)
public interface InsurranceRepository extends EntityRepository<Insurrance, Long>
{
	public abstract List<Insurrance> findByCustomerAndInsurer(Customer customer ,Customer insurrer);
	
}
