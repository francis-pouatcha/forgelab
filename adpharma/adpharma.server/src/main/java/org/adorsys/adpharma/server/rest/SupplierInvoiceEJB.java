package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DocumentClosedEvent;
import org.adorsys.adpharma.server.jpa.Agency;
import org.adorsys.adpharma.server.jpa.Agency_;
import org.adorsys.adpharma.server.jpa.ArticleLot;
import org.adorsys.adpharma.server.jpa.ArticleLot_;
import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.jpa.DeliveryStatisticsDataSearchResult;
import org.adorsys.adpharma.server.jpa.DeliveryStattisticsDataSearchInput;
import org.adorsys.adpharma.server.jpa.InvoiceType;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.SupplierInvoice;
import org.adorsys.adpharma.server.jpa.SupplierInvoiceItem;
import org.adorsys.adpharma.server.jpa.VAT;
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

	/**
	 * First group delivery items in agencies then produce the supplier invoice.
	 * 
	 * @param closedDelivery
	 */
	public void handleDelivery(@Observes @DocumentClosedEvent Delivery closedDelivery) {
		Set<DeliveryItem> deliveryItems = closedDelivery.getDeliveryItems();
		Map<String, List<DeliveryItem>> deliveryItemMap = new HashMap<String, List<DeliveryItem>>();
		Map<String, Agency> agencies = new HashMap<String, Agency>();
		Map<String, ArticleLot> articleLots =  new HashMap<String, ArticleLot>();
		// Group delivery items by agency
		for (DeliveryItem deliveryItem : deliveryItems) {
			ArticleLot articleLot = getArticleLot(deliveryItem.getInternalPic(), articleLots);
			List<DeliveryItem> list = deliveryItemMap.get(articleLot.getAgency().getAgencyNumber());
			if(list==null){
				list = new ArrayList<DeliveryItem>();
				deliveryItemMap.put(articleLot.getAgency().getAgencyNumber(), list);
			}
			list.add(deliveryItem);
		}
		Set<Entry<String,List<DeliveryItem>>> entrySet = deliveryItemMap.entrySet();
		for (Entry<String, List<DeliveryItem>> entry : entrySet) {
			String agencyNumber = entry.getKey();
			SupplierInvoice si = new SupplierInvoice();
			Login creatingUser = securityUtil.getConnectedUser();
			Date creationDate = new Date();
			si.setDelivery(closedDelivery);
			si.setAgency(getAgency(agencyNumber, agencies));
			si.setSupplier(closedDelivery.getSupplier());
			si.setCreatingUser(creatingUser);
			si.setCreationDate(creationDate);
			si.setInvoiceType(InvoiceType.CASHDRAWER);
			si = create(si);
			// Generate the supplier invoice items
			List<DeliveryItem> items = entry.getValue();
			BigDecimal afterTax = BigDecimal.ZERO;
			BigDecimal vat = BigDecimal.ZERO;
			BigDecimal discount = BigDecimal.ZERO;
			for (DeliveryItem deliveryItem : items) {
				SupplierInvoiceItem sii = new SupplierInvoiceItem();
				sii.setAmountReturn(BigDecimal.ZERO);
				sii.setArticle(deliveryItem.getArticle());
				sii.setDeliveryQty(deliveryItem.getStockQuantity());
				String internalPic = deliveryItem.getInternalPic();
				sii.setInternalPic(internalPic);
				sii.setInvoice(si);
				sii.setPurchasePricePU(deliveryItem.getPurchasePricePU());
				BigDecimal amountAfterTax = deliveryItem.getTotalPurchasePrice();
				sii.setTotalPurchasePrice(amountAfterTax);
				afterTax = afterTax.add(amountAfterTax);
				
				// Read the vat rate saed in the article lot.
				ArticleLot articleLot = getArticleLot(internalPic, articleLots);
				VAT ivat = articleLot.getVat();
				BigDecimal vatRate = ivat!=null?ivat.getRate():BigDecimal.ZERO;
				
				// IF the vat to be collected is waived in the delivery item, ignore vat computation here.
				if(BigDecimal.ZERO.compareTo(closedDelivery.getAmountVat())<=0)vatRate=BigDecimal.ZERO;
				BigDecimal amountBeforeTax = amountAfterTax.divide(BigDecimal.ONE.add(vatRate)); 
				
				BigDecimal amountVat = amountAfterTax.subtract(amountBeforeTax);
				vat = vat.add(amountVat);

				BigDecimal deliveryDiscount = closedDelivery.getAmountDiscount()==null?BigDecimal.ZERO:closedDelivery.getAmountDiscount();
				BigDecimal deliveryAmountBeforeTax = closedDelivery.getAmountBeforeTax();
				
				// Proportion on whole invoice before tax. Because not all item have vat.
				// Take the proportion of amount before tax
				BigDecimal amountDiscount = BigDecimal.ZERO;				
				if(deliveryDiscount.compareTo(BigDecimal.ZERO)>0){
					if(deliveryAmountBeforeTax.compareTo(BigDecimal.ZERO)>0){
						BigDecimal proportion = amountBeforeTax.divide(deliveryAmountBeforeTax);
						amountDiscount = deliveryDiscount.multiply(proportion);	
					}
				}
				discount = discount.add(amountDiscount);

				sii = supplierInvoiceItemEJB.create(sii);
			}
			
			si.setAmountAfterTax(afterTax);
			si.setTaxAmount(vat);
			si.setAmountBeforeTax(afterTax.subtract(vat));
			si.setAmountDiscount(discount);
			si.setNetToPay(afterTax.subtract(discount));
			si = update(si);
		}
	}
	
	@Inject
	private AgencyEJB agencyEJB;
	@SuppressWarnings("unchecked")
	private Agency getAgency(String agencyNumber, Map<String, Agency> agencyCache){
		if(agencyCache.containsKey(agencyNumber)) return agencyCache.get(agencyNumber);
		Agency model = new Agency();
		model.setAgencyNumber(agencyNumber);
		List<Agency> found = agencyEJB.findBy(model, 0, 1, new SingularAttribute[]{Agency_.agencyNumber});
		if(found.isEmpty()) throw new IllegalStateException("No agency with agency number: " + agencyNumber);
		Agency agency = found.iterator().next();
		agencyCache.put(agencyNumber, agency);
		return agency;
	}
	
	@Inject
	private ArticleLotEJB articleLotEJB;
	private ArticleLot getArticleLot(String internalPic, Map<String, ArticleLot> cache){
		if(cache.containsKey(internalPic)) return cache.get(internalPic);
		ArticleLot articleLot = new ArticleLot();
		articleLot.setInternalPic(internalPic);
		List<ArticleLot> found = articleLotEJB.findBy(articleLot, 0, 1, new SingularAttribute[]{ArticleLot_.internalPic});
		if(found.isEmpty()) throw new IllegalStateException("No article lot with article internal pic: " + internalPic);
		ArticleLot next = found.iterator().next();
		cache.put(internalPic, next);
		return next;
	}
}
