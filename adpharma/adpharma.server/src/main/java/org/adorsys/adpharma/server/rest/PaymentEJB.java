package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DirectSalesClosedEvent;
import org.adorsys.adpharma.server.events.DocumentProcessedEvent;
import org.adorsys.adpharma.server.jpa.CashDrawer;
import org.adorsys.adpharma.server.jpa.CashDrawer_;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.Payment;
import org.adorsys.adpharma.server.repo.PaymentRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;

@Stateless
public class PaymentEJB
{

   @Inject
   private PaymentRepository repository;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private CustomerMerger customerMerger;

   @Inject
   private CashDrawerMerger cashDrawerMerger;

   @Inject
   private PaymentCustomerInvoiceAssocMerger paymentCustomerInvoiceAssocMerger;

   @Inject
   private AgencyMerger agencyMerger;

	@EJB
	private SecurityUtil securityUtil;   
	@EJB
	private CashDrawerEJB cashDrawerEJB;
	
	@Inject
	@DirectSalesClosedEvent
	private Event<Payment> directSalesClosedEvent;
	
	@Inject
	@DocumentProcessedEvent
	private Event<Payment> paymentProcessedEvent;
   
   public Payment create(Payment entity)
   {
      return repository.save(attach(entity));
   }

   public Payment deleteById(Long id)
   {
      Payment entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public Payment update(Payment entity)
   {
      return repository.save(attach(entity));
   }

   public Payment findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<Payment> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<Payment> findBy(Payment entity, int start, int max, SingularAttribute<Payment, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(Payment entity, SingularAttribute<Payment, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<Payment> findByLike(Payment entity, int start, int max, SingularAttribute<Payment, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(Payment entity, SingularAttribute<Payment, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private Payment attach(Payment entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      // aggregated
      entity.setCashier(loginMerger.bindAggregated(entity.getCashier()));

      // aggregated
      entity.setCashDrawer(cashDrawerMerger.bindAggregated(entity.getCashDrawer()));

      // aggregated
      entity.setPaidBy(customerMerger.bindAggregated(entity.getPaidBy()));

      // aggregated collection
      paymentCustomerInvoiceAssocMerger.bindAggregated(entity.getInvoices());

      return entity;
   }

	@SuppressWarnings("unchecked")
	public Payment directSalesClosed(Payment entity)
   {
		entity = attach(entity);
		
		// Set the cash drawer
		Login connectedUser = securityUtil.getConnectedUser();
		CashDrawer cashDrawer = new CashDrawer();
		cashDrawer.setCashier(connectedUser);
		cashDrawer.setOpened(Boolean.TRUE);
		List<CashDrawer> found = cashDrawerEJB.findBy(cashDrawer, 0, 1, new SingularAttribute[]{CashDrawer_.cashier, CashDrawer_.opened});
		cashDrawer = found.iterator().next();
		entity.setCashDrawer(cashDrawer);
		entity.setAgency(connectedUser.getAgency());
		entity.setCashier(connectedUser);
		
		entity = repository.save(entity);
		
		directSalesClosedEvent.fire(entity);
		
		return entity;
   }
	
	public Payment processPayment(Payment entity){

		entity = attach(entity);
		
		// Set the cash drawer
		Login connectedUser = securityUtil.getConnectedUser();
		CashDrawer cashDrawer = new CashDrawer();
		cashDrawer.setCashier(connectedUser);
		cashDrawer.setOpened(Boolean.TRUE);
		List<CashDrawer> found = cashDrawerEJB.findBy(cashDrawer, 0, 1, new SingularAttribute[]{CashDrawer_.cashier, CashDrawer_.opened});
		cashDrawer = found.iterator().next();
		entity.setCashDrawer(cashDrawer);
		entity.setAgency(connectedUser.getAgency());
		entity.setCashier(connectedUser);
		
		entity = repository.save(entity);
		
		paymentProcessedEvent.fire(entity);
		
		return entity;
	}

}
