package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DocumentClosedEvent;
import org.adorsys.adpharma.server.jpa.Agency;
import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.jpa.DeliveryStatisticsDataSearchResult;
import org.adorsys.adpharma.server.jpa.DeliveryStattisticsDataSearchInput;
import org.adorsys.adpharma.server.jpa.InvoiceType;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.SalesStatisticsDataSearchResult;
import org.adorsys.adpharma.server.jpa.SupplierInvoice;
import org.adorsys.adpharma.server.jpa.SupplierInvoiceItem;
import org.adorsys.adpharma.server.repo.SupplierInvoiceRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;
import org.adorsys.adpharma.server.utils.ChartData;

@Stateless
public class SupplierInvoiceEJB
{

	@Inject
	private SupplierInvoiceRepository repository;

	@Inject
	private LoginMerger loginMerger;

	@Inject
	private SupplierMerger supplierMerger;

	@Inject
	private DeliveryMerger deliveryMerger;

	@Inject
	private SupplierInvoiceItemMerger supplierInvoiceItemMerger;

	@Inject
	private AgencyMerger agencyMerger;

	@Inject
	private SupplierInvoiceItemEJB supplierInvoiceItemEJB;

	@Inject
	private SecurityUtil securityUtil;
	
	@Inject
	private EntityManager em ;


	public SupplierInvoice create(SupplierInvoice entity)
	{
		return repository.save(attach(entity));
	}

	public SupplierInvoice deleteById(Long id)
	{
		SupplierInvoice entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
		}
		return entity;
	}

	public SupplierInvoice update(SupplierInvoice entity)
	{
		return repository.save(attach(entity));
	}

	public SupplierInvoice findById(Long id)
	{
		return repository.findBy(id);
	}

	public List<SupplierInvoice> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<SupplierInvoice> findBy(SupplierInvoice entity, int start, int max, SingularAttribute<SupplierInvoice, ?>[] attributes)
	{
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(SupplierInvoice entity, SingularAttribute<SupplierInvoice, ?>[] attributes)
	{
		return repository.count(entity, attributes);
	}

	public List<SupplierInvoice> findByLike(SupplierInvoice entity, int start, int max, SingularAttribute<SupplierInvoice, ?>[] attributes)
	{
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(SupplierInvoice entity, SingularAttribute<SupplierInvoice, ?>[] attributes)
	{
		return repository.countLike(entity, attributes);
	}

	private SupplierInvoice attach(SupplierInvoice entity)
	{
		if (entity == null)
			return null;

		// aggregated
		entity.setSupplier(supplierMerger.bindAggregated(entity.getSupplier()));

		// aggregated
		entity.setCreatingUser(loginMerger.bindAggregated(entity.getCreatingUser()));

		// aggregated
		entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

		// aggregated
		entity.setDelivery(deliveryMerger.bindAggregated(entity.getDelivery()));

		// composed collections
		Set<SupplierInvoiceItem> invoiceItems = entity.getInvoiceItems();
		for (SupplierInvoiceItem supplierInvoiceItem : invoiceItems)
		{
			supplierInvoiceItem.setInvoice(entity);
		}

		return entity;
	}
	public DeliveryStatisticsDataSearchResult findDeliveryStatistics(DeliveryStattisticsDataSearchInput dataSearchInput){
		List<ChartData> chartDataSearchResult = new ArrayList<ChartData>();
		String query ="SELECT SUM(c.netToPay) , MONTHNAME(c.creationDate) FROM SupplierInvoice AS c WHERE YEAR(c.creationDate) = :creationDate " ;
		if(dataSearchInput.getDeliverySupplier()!=null)
			query = query+" AND c.supplier  = :supplier " ;
		query = query+"  GROUP BY MONTHNAME(c.creationDate) " ;
		Query querys = em.createQuery(query) ;

		if(dataSearchInput.getDeliverySupplier()!=null)
			querys.setParameter("supplier", dataSearchInput.getDeliverySupplier());

		querys.setParameter("creationDate", dataSearchInput.getYears());
		List<Object[]> resultList = querys.getResultList();
		for (Object[] objects : resultList) {
			BigDecimal netTopay = (BigDecimal) objects[0];
			String month =  (String) objects[1];
			ChartData chartData = new ChartData(month, netTopay);
			chartDataSearchResult.add(chartData);
		}

		DeliveryStatisticsDataSearchResult searchResult = new DeliveryStatisticsDataSearchResult();
		searchResult.setChartData(chartDataSearchResult);

		return searchResult ;

	}
	public void handleDelivery(@Observes @DocumentClosedEvent Delivery closedDelivery){
		SupplierInvoice si = new SupplierInvoice();
		Login creatingUser = securityUtil.getConnectedUser();
		Date creationDate = new Date();
		Agency agency = creatingUser.getAgency();
		si.setDelivery(closedDelivery);
		si.setAgency(agency);
		si.setSupplier(closedDelivery.getSupplier());
		si.setCreatingUser(creatingUser);
		si.setCreationDate(creationDate);
		si.setInvoiceType(InvoiceType.CASHDRAWER);
		si = create(si);
		// Generate the supplier invoice items
		Set<DeliveryItem> deliveryItems = closedDelivery.getDeliveryItems();
		for (DeliveryItem deliveryItem : deliveryItems) {
			SupplierInvoiceItem sii = new SupplierInvoiceItem();
			sii.setAmountReturn(BigDecimal.ZERO);
			sii.setArticle(deliveryItem.getArticle());
			sii.setDeliveryQty(deliveryItem.getStockQuantity());
			sii.setInternalPic(deliveryItem.getInternalPic());
			sii.setInvoice(si);
			sii.setPurchasePricePU(deliveryItem.getPurchasePricePU());
			sii.setTotalPurchasePrice(deliveryItem.getTotalPurchasePrice());
			sii = supplierInvoiceItemEJB.create(sii);
		}

		si.setAmountDiscount(closedDelivery.getAmountDiscount());
		si.setAmountBeforeTax(closedDelivery.getAmountBeforeTax());
		si.setAmountAfterTax(closedDelivery.getAmountAfterTax());
		si.setAmountDiscount(closedDelivery.getAmountDiscount());
		si.setNetToPay(closedDelivery.getNetAmountToPay());
		si.setTaxAmount(closedDelivery.getAmountVat());
		si = update(si);
	}
}
