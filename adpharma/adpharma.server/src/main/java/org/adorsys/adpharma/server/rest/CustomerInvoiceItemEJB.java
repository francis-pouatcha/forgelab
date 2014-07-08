package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.jpa.CustomerInvoiceItem;
import org.adorsys.adpharma.server.jpa.InvoiceType;
import org.adorsys.adpharma.server.jpa.ProcurementOrderPreparationData;
import org.adorsys.adpharma.server.jpa.SalesStatisticsDataSearchInput;
import org.adorsys.adpharma.server.jpa.SalesStatisticsDataSearchResult;
import org.adorsys.adpharma.server.repo.CustomerInvoiceItemRepository;
import org.adorsys.adpharma.server.utils.ChartData;

@Stateless
public class CustomerInvoiceItemEJB
{

	@Inject
	private CustomerInvoiceItemRepository repository;

	@Inject
	private CustomerInvoiceMerger customerInvoiceMerger;

	@Inject
	private ArticleMerger articleMerger;

	public List<CustomerInvoiceItem> findPreparationDataItem(ProcurementOrderPreparationData data){
		return repository.findPreparationDataItem(data.getFromDate(), data.getToDate(), true);
	}

	public CustomerInvoiceItem create(CustomerInvoiceItem entity)
	{
		return repository.save(attach(entity));
	}

	public CustomerInvoiceItem deleteById(Long id)
	{
		CustomerInvoiceItem entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
		}
		return entity;
	}

	public CustomerInvoiceItem update(CustomerInvoiceItem entity)
	{
		return repository.save(attach(entity));
	}

	public CustomerInvoiceItem findById(Long id)
	{
		return repository.findBy(id);
	}

	public List<CustomerInvoiceItem> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<CustomerInvoiceItem> findBy(CustomerInvoiceItem entity, int start, int max, SingularAttribute<CustomerInvoiceItem, ?>[] attributes)
	{
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(CustomerInvoiceItem entity, SingularAttribute<CustomerInvoiceItem, ?>[] attributes)
	{
		return repository.count(entity, attributes);
	}

	public List<CustomerInvoiceItem> findByLike(CustomerInvoiceItem entity, int start, int max, SingularAttribute<CustomerInvoiceItem, ?>[] attributes)
	{
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(CustomerInvoiceItem entity, SingularAttribute<CustomerInvoiceItem, ?>[] attributes)
	{
		return repository.countLike(entity, attributes);
	}

	@Inject
	private EntityManager em ;

	public SalesStatisticsDataSearchResult findSalesStatistics(SalesStatisticsDataSearchInput dataSearchInput){
		List<ChartData> chartDataSearchResult = new ArrayList<ChartData>();
		String query ="SELECT SUM(c.totalSalesPrice) , MONTHNAME(c.invoice.creationDate) FROM CustomerInvoiceItem AS c WHERE YEAR(c.invoice.creationDate) = :creationDate " ;
		if(dataSearchInput.getCustomer()!=null)
			query = query+" AND c.invoice.customer  = :customer " ;
		if(dataSearchInput.getArticle()!=null)
			query = query+" AND c.article  = :article " ;
		query = query+" AND (c.invoice.cashed = :cashed OR c.invoice.invoiceType = :invoiceType) GROUP BY MONTHNAME(c.invoice.creationDate) " ;
		Query querys = em.createQuery(query) ;

		if(dataSearchInput.getCustomer()!=null)
			querys.setParameter("customer", dataSearchInput.getCustomer());
		if(dataSearchInput.getArticle()!=null)
			querys.setParameter("article", dataSearchInput.getArticle());

		querys.setParameter("creationDate", dataSearchInput.getYears());
		querys.setParameter("cashed", Boolean.TRUE);
		querys.setParameter("invoiceType", InvoiceType.VOUCHER);
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = querys.getResultList();
		for (Object[] objects : resultList) {
			BigDecimal netTopay = (BigDecimal) objects[0];
			String month =  (String) objects[1];
			ChartData chartData = new ChartData(month, netTopay);
			chartDataSearchResult.add(chartData);
		}

		SalesStatisticsDataSearchResult searchResult = new SalesStatisticsDataSearchResult();
		searchResult.setChartData(chartDataSearchResult);

		return searchResult ;

	}

	private CustomerInvoiceItem attach(CustomerInvoiceItem entity)
	{
		if (entity == null)
			return null;

		// composed

		// aggregated
		entity.setArticle(articleMerger.bindAggregated(entity.getArticle()));

		return entity;
	}
}
