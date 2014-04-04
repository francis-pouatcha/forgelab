package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.CustomerPaymentProcessingEvent;
import org.adorsys.adpharma.server.jpa.CashDrawer;
import org.adorsys.adpharma.server.jpa.Payment;
import org.adorsys.adpharma.server.jpa.PaymentMode;
import org.adorsys.adpharma.server.repo.CashDrawerRepository;

@Stateless
public class CashDrawerEJB
{

   @Inject
   private CashDrawerRepository repository;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private AgencyMerger agencyMerger;

   public CashDrawer create(CashDrawer entity)
   {
      return repository.save(attach(entity));
   }

   public CashDrawer deleteById(Long id)
   {
      CashDrawer entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CashDrawer update(CashDrawer entity)
   {
      return repository.save(attach(entity));
   }

   public CashDrawer findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<CashDrawer> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CashDrawer> findBy(CashDrawer entity, int start, int max, SingularAttribute<CashDrawer, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CashDrawer entity, SingularAttribute<CashDrawer, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CashDrawer> findByLike(CashDrawer entity, int start, int max, SingularAttribute<CashDrawer, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CashDrawer entity, SingularAttribute<CashDrawer, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CashDrawer attach(CashDrawer entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setCashier(loginMerger.bindAggregated(entity.getCashier()));

      // aggregated
      entity.setClosedBy(loginMerger.bindAggregated(entity.getClosedBy()));

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      return entity;
   }
   
   public void processPayment(@Observes @CustomerPaymentProcessingEvent Payment payment){
	   CashDrawer cashDrawer = payment.getCashDrawer();
	   PaymentMode paymentMode = payment.getPaymentMode();
	   BigDecimal amount = payment.getAmount();
	   cashDrawer.setTotalCashIn(amount);
	   switch (paymentMode) {
		case CASH:
			BigDecimal totalCash = cashDrawer.getTotalCash()==null?BigDecimal.ZERO:cashDrawer.getTotalCash();
			cashDrawer.setTotalCash(totalCash.add(amount));
			break;
		case CREDIT_CARD:
			BigDecimal totalCreditCard = cashDrawer.getTotalCreditCard()==null?BigDecimal.ZERO:cashDrawer.getTotalCreditCard();
			cashDrawer.setTotalCreditCard(totalCreditCard.add(amount));
			break;
		case CHECK:
			BigDecimal totalCheck = cashDrawer.getTotalCheck()==null?BigDecimal.ZERO:cashDrawer.getTotalCheck();
			cashDrawer.setTotalCheck(totalCheck.add(amount));
			break;
		case VOUCHER:
			BigDecimal totalClientVoucher = cashDrawer.getTotalClientVoucher()==null?BigDecimal.ZERO:cashDrawer.getTotalClientVoucher();
			cashDrawer.setTotalClientVoucher(totalClientVoucher.add(amount));
			break;
		default:
			throw new IllegalStateException("Unknown payment mode: "+paymentMode);
	   }
	   update(cashDrawer);
   }
}
