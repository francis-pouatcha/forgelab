package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DocumentClosedEvent;
import org.adorsys.adpharma.server.events.DocumentProcessedEvent;
import org.adorsys.adpharma.server.jpa.Agency;
import org.adorsys.adpharma.server.jpa.Customer;
import org.adorsys.adpharma.server.jpa.CustomerInvoice;
import org.adorsys.adpharma.server.jpa.DebtStatement;
import org.adorsys.adpharma.server.jpa.DebtStatementCustomerInvoiceAssoc;
import org.adorsys.adpharma.server.jpa.DebtStatementCustomerInvoiceAssoc_;
import org.adorsys.adpharma.server.jpa.DebtStatement_;
import org.adorsys.adpharma.server.jpa.DocumentProcessingState;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.repo.DebtStatementRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;
import org.adorsys.adpharma.server.utils.SequenceGenerator;
import org.apache.commons.lang3.RandomStringUtils;

@Stateless
public class DebtStatementEJB
{

   @Inject
   private DebtStatementRepository repository;

   @Inject
   private CustomerMerger customerMerger;

   @Inject
   private DebtStatementCustomerInvoiceAssocMerger debtStatementCustomerInvoiceAssocMerger;

   @Inject
   private AgencyMerger agencyMerger;
   
   @EJB
   private SecurityUtil securityUtil;

   public DebtStatement create(DebtStatement entity)
   {
	   DebtStatement save = repository.save(attach(entity)); 
		save.setStatementNumber((SequenceGenerator.DEBTS_INVOICE_SEQUENCE_PREFIXE+save.getId()));
      return repository.save(attach(entity));
   }

   public DebtStatement deleteById(Long id)
   {
      DebtStatement entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public DebtStatement update(DebtStatement entity)
   {
      return repository.save(attach(entity));
   }

   public DebtStatement findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<DebtStatement> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<DebtStatement> findBy(DebtStatement entity, int start, int max, SingularAttribute<DebtStatement, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(DebtStatement entity, SingularAttribute<DebtStatement, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<DebtStatement> findByLike(DebtStatement entity, int start, int max, SingularAttribute<DebtStatement, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(DebtStatement entity, SingularAttribute<DebtStatement, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private DebtStatement attach(DebtStatement entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setInsurrance(customerMerger.bindAggregated(entity.getInsurrance()));

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      // aggregated collection
      debtStatementCustomerInvoiceAssocMerger.bindAggregated(entity.getInvoices());

      return entity;
   }

   @EJB
   private DebtStatementCustomerInvoiceAssocEJB debtStatementCustomerInvoiceAssocEJB;
   
   /**
    * Processes changes on customer invoices into debt statements.
    * 
    * If there is any debt and this invoice is not yet part of an open debt statement,
    * makes it part of one.
    * 
    * @param customerInvoice
    */
	public void handleCustomerInvoiceProcessed(@Observes @DocumentProcessedEvent CustomerInvoice customerInvoice){
		BigDecimal customerRestTopay = customerInvoice.getCustomerRestTopay();
		if(customerRestTopay!=null && customerRestTopay.compareTo(BigDecimal.ZERO)>0){
			addToDebtStatement(customerInvoice, customerRestTopay, customerInvoice.getCustomer());
		}
		BigDecimal insurranceRestTopay = customerInvoice.getInsurranceRestTopay();
		if(insurranceRestTopay!=null && insurranceRestTopay.compareTo(BigDecimal.ZERO)>0){
			addToDebtStatement(customerInvoice, insurranceRestTopay, customerInvoice.getInsurance().getInsurer());
		}
	}   
	
	@SuppressWarnings("unchecked")
	private void addToDebtStatement(CustomerInvoice customerInvoice, BigDecimal restTopay, Customer payer){
		DebtStatement debtStatement = null;
		DebtStatementCustomerInvoiceAssoc dcia = new DebtStatementCustomerInvoiceAssoc();
		dcia.setTarget(customerInvoice);
		List<DebtStatementCustomerInvoiceAssoc> dciaFound = debtStatementCustomerInvoiceAssocEJB.findBy(dcia, 0, -1, new SingularAttribute[]{DebtStatementCustomerInvoiceAssoc_.target});
		for (DebtStatementCustomerInvoiceAssoc d : dciaFound) {
			debtStatement = d.getSource();
			if(payer.equals(debtStatement.getInsurrance())) return; // This invoice is part of a debt statement of this payer.
		}

		// create the debt statement ofr this invoice
		Login connectedUser = securityUtil.getConnectedUser();
		Agency agency = connectedUser.getAgency();
		
		// find the debt statement associated with this customer
		debtStatement = new DebtStatement();
		debtStatement.setInsurrance(customerInvoice.getCustomer());
		debtStatement.setStatementStatus(DocumentProcessingState.ONGOING);
		// Todo we will make sure a debt statement is closed before a new one is opened.
		// So there shouldn't be more than one customer's deb statement with the status ongoing.
		List<DebtStatement> dsFound = findBy(debtStatement, 0, 1, new SingularAttribute[]{DebtStatement_.insurrance, DebtStatement_.statementStatus});
		if(!dsFound.isEmpty()) {
			debtStatement = dsFound.iterator().next();
		} else {
			// create a new DebtStatement
			debtStatement = new DebtStatement();
			debtStatement.setStatementNumber(RandomStringUtils.randomAlphanumeric(7));
			debtStatement.setAdvancePayment(BigDecimal.ZERO);
			debtStatement.setAgency(agency);
			debtStatement.setAmountFromVouchers(BigDecimal.ZERO);
			debtStatement.setCanceled(Boolean.FALSE);
			debtStatement.setInitialAmount(BigDecimal.ZERO);
			debtStatement.setInsurrance(payer);
			debtStatement.setRestAmount(BigDecimal.ZERO);
			debtStatement.setSettled(Boolean.FALSE);
			debtStatement.setUseVoucher(Boolean.TRUE);
			debtStatement.setStatementStatus(DocumentProcessingState.ONGOING);
			debtStatement = create(debtStatement);
		}
		
		// now add the invoice to the debt statement.
		DebtStatementCustomerInvoiceAssoc dcas = new DebtStatementCustomerInvoiceAssoc();
		dcas.setSource(debtStatement);
		dcas.setTarget(customerInvoice);
		dcas.setSourceQualifier("invoices");
		dcas.setTargetQualifier("source");
		debtStatementCustomerInvoiceAssocEJB.create(dcas);
		debtStatement.setInitialAmount(debtStatement.getInitialAmount().add(restTopay));
		debtStatement.setRestAmount(debtStatement.getInitialAmount().subtract(debtStatement.getAdvancePayment()).subtract(debtStatement.getAmountFromVouchers()));
		debtStatement.setPaymentDate(new Date());
		update(debtStatement);
	}
	
	@Inject
	@DocumentClosedEvent
	private Event<DebtStatement> debtStatementClosedEvent;
	
	/**
	 * This will close any open debt statement of the given customer.
	 * 
	 * Closing a debt statement means we can not add any invoice on it. But settlement 
	 * keeps going on.
	 * 
	 * @param customer
	 * @return 
	 */
	public List<DebtStatement> closeDebtStatement(Customer customer){
		DebtStatement debtStatement = new DebtStatement();
		debtStatement.setInsurrance(customer);
		debtStatement.setStatementStatus(DocumentProcessingState.ONGOING);
		List<DebtStatement> dsFound = findBy(debtStatement, 0, 1, new SingularAttribute[]{DebtStatement_.insurrance, DebtStatement_.statementStatus});
		List<DebtStatement> result = new ArrayList<DebtStatement>(dsFound.size());
		for (DebtStatement ds : dsFound) {
			ds.setStatementStatus(DocumentProcessingState.CLOSED);
			result.add(debtStatement);
			debtStatementClosedEvent.fire(debtStatement);
		}
		return result;
	}
}
