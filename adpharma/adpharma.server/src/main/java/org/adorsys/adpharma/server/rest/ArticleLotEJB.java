package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DestockingProcessedEvent;
import org.adorsys.adpharma.server.events.DirectSalesClosedEvent;
import org.adorsys.adpharma.server.events.DocumentClosedEvent;
import org.adorsys.adpharma.server.events.DocumentProcessedEvent;
import org.adorsys.adpharma.server.events.EntityEditDoneRequestEvent;
import org.adorsys.adpharma.server.events.ReturnSalesEvent;
import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.jpa.ArticleLot;
import org.adorsys.adpharma.server.jpa.ArticleLotDetailsManager;
import org.adorsys.adpharma.server.jpa.ArticleLotMovedToTrashData;
import org.adorsys.adpharma.server.jpa.ArticleLotSequence;
import org.adorsys.adpharma.server.jpa.ArticleLotSequence_;
import org.adorsys.adpharma.server.jpa.ArticleLotTransferManager;
import org.adorsys.adpharma.server.jpa.ArticleLot_;
import org.adorsys.adpharma.server.jpa.ArticleSearchInput;
import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.ProductDetailConfig;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.adorsys.adpharma.server.jpa.SalesOrderItem;
import org.adorsys.adpharma.server.repo.ArticleLotRepository;
import org.adorsys.adpharma.server.repo.ArticleLotSequenceRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;
import org.adorsys.adpharma.server.startup.ApplicationConfiguration;

@Stateless
public class ArticleLotEJB
{

	@Inject
	private EntityManager em ;

	@Inject
	private ArticleLotRepository repository;

	@Inject
	private AgencyMerger agencyMerger;

	@Inject
	private ArticleMerger articleMerger;

	@Inject
	private VATMerger vATMerger;


	@Inject
	private ApplicationConfiguration applicationConfiguration;

	@Inject
	private Event<ArticleLotMovedToTrashData> articleLotMovetTotrashRequestEvent ;


	@EJB
	private SecurityUtil securityUtil;

	public ArticleLot create(ArticleLot entity)
	{
		return repository.save(attach(entity));
	}

	public ArticleLot deleteById(Long id)
	{
		ArticleLot entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
		}
		return entity;
	}

	public ArticleLot update(ArticleLot entity)
	{
		return repository.save(attach(entity));
	}

	public ArticleLot findById(Long id)
	{
		return repository.findBy(id);
	}

	public List<ArticleLot> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<ArticleLot> findBy(ArticleLot entity, int start, int max, SingularAttribute<ArticleLot, Object>[] attributes)
	{
		ArticleLot articleLot = attach(entity);
		return repository.findBy(articleLot, start, max, attributes);

	}

	public Long countBy(ArticleLot entity, SingularAttribute<ArticleLot, ?>[] attributes)
	{
		ArticleLot articleLot = attach(entity);
		return repository.count(articleLot, attributes);
	}

	public List<ArticleLot> findByLike(ArticleLot entity, int start, int max, SingularAttribute<ArticleLot, Object>[] attributes)
	{
		ArticleLot articleLot = attach(entity);
		return repository.findByLike(articleLot, start, max, attributes);
	}

	public Long countByLike(ArticleLot entity, SingularAttribute<ArticleLot, ?>[] attributes)
	{
		ArticleLot articleLot = attach(entity);
		return repository.countLike(articleLot, attributes);
	}

	private ArticleLot attach(ArticleLot entity)
	{
		if (entity == null)
			return null;

		// aggregated
		entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

		// aggregated
		entity.setArticle(articleMerger.bindAggregated(entity.getArticle()));

		// aggregated
		entity.setVat(vATMerger.bindAggregated(entity.getVat()));

		return entity;
	}


	public List<ArticleLot> stockValue(ArticleSearchInput searchInput){
		List<ArticleLot> stockVAlues = new ArrayList<ArticleLot>();
		String query ="SELECT c FROM ArticleLot AS c WHERE c.stockQuantity != :stockQuantity  ";

		if(searchInput.getEntity().getAgency()!=null&&searchInput.getEntity().getAgency().getId()!=null)
			query = query+ " AND c.agency = :agency ";
		if(searchInput.getEntity().getSection()!=null&&searchInput.getEntity().getSection().getId()!=null)
			query = query+ " AND c.article.section = :section ";
		query = query+" ORDER BY c.article.articleName) " ;

		Query querys = em.createQuery(query,ArticleLot.class) ;

		querys.setParameter("stockQuantity",BigDecimal.ZERO);
		if(searchInput.getEntity().getAgency()!=null&&searchInput.getEntity().getAgency().getId()!=null)
			querys.setParameter("agency", searchInput.getEntity().getAgency());
		if(searchInput.getEntity().getSection()!=null&&searchInput.getEntity().getSection().getId()!=null)
			querys.setParameter("section", searchInput.getEntity().getSection());
		stockVAlues = querys.getResultList();

		return stockVAlues ;
	}

	public ArticleLot movetoTrash(ArticleLotMovedToTrashData data){
		ArticleLot articleLot = findById(data.getId());
		if(articleLot==null)
			throw new IllegalStateException("no Article Lot found with Id :"+data.getId());
		if(data.getQtyToMoved()!=null  && articleLot.getStockQuantity().compareTo(data.getQtyToMoved())>=0)
			articleLot.setStockQuantity(articleLot.getStockQuantity().subtract(data.getQtyToMoved()));

		articleLotMovetTotrashRequestEvent.fire(data);
		return update(articleLot);

	}

	@Inject
	@DocumentProcessedEvent
	private Event<ArticleLotDetailsManager> articleLotDetailsEvent ;

	/**
	 * 
	 *
	 */
	public ArticleLot processDetails(ArticleLotDetailsManager lotDetailsManager){
		ArticleLot lot = null ;

		Login login = securityUtil.getConnectedUser();
		ArticleLot source = lotDetailsManager.getLotToDetails();
		ProductDetailConfig detailConfig = lotDetailsManager.getDetailConfig();
		BigDecimal detailsQty = lotDetailsManager.getDetailsQty();
		Boolean isManagedLot = Boolean.valueOf( applicationConfiguration.getConfiguration().getProperty("managed_articleLot.config"));
		if(isManagedLot==null) throw new IllegalArgumentException("managed_articleLot.config  is required in application.properties files");

		if(isManagedLot){
			ArticleLot al = newArticleLot(lotDetailsManager);
			lot = create(al);
		}else {
			ArticleLot articleLot = new ArticleLot();
			articleLot.setArticle(detailConfig.getTarget());
			List<ArticleLot> found = findBy(articleLot, 0, 1, new SingularAttribute[]{ArticleLot_.article});
			if(!found.isEmpty()){
				articleLot = found.iterator().next();
				BigDecimal stockQuantity = articleLot.getStockQuantity();
				stockQuantity =stockQuantity.add(detailsQty.multiply(detailConfig.getTargetQuantity()));
				articleLot.setStockQuantity(stockQuantity);
				articleLot.setSalesPricePU(detailConfig.getSalesPrice());
				articleLot.calculateTotalAmout();
			}else {
				articleLot = newArticleLot(lotDetailsManager);
			}

			lot = update(articleLot);
		}
		source.setStockQuantity(source.getStockQuantity().subtract(detailsQty)); // remove details qty to lot stock
		source.calculateTotalAmout();
		source = update(source);
		articleLotDetailsEvent.fire(lotDetailsManager);
		return source ;
	}


	public ArticleLot newArticleLot(ArticleLotDetailsManager lotDetailsManager){
		ArticleLot lotToDetails = lotDetailsManager.getLotToDetails();
		ProductDetailConfig detailConfig = lotDetailsManager.getDetailConfig();
		BigDecimal detailsQty = lotDetailsManager.getDetailsQty();
		ArticleLot al = new  ArticleLot();
		al.setAgency(lotToDetails.getAgency());
		al.setArticle(detailConfig.getTarget());
		al.setArticleName(al.getArticle().getArticleName());
		al.setCreationDate(new Date());
		al.setExpirationDate(lotToDetails.getExpirationDate());
		al.setInternalPic(lotToDetails.getInternalPic());
		al.setMainPic(detailConfig.getTarget().getPic());
		al.setSecondaryPic(lotToDetails.getSecondaryPic());
		al.setStockQuantity(detailsQty.multiply(detailConfig.getTargetQuantity()));
		al.setPurchasePricePU(lotToDetails.getPurchasePricePU().divide(al.getStockQuantity(), 4, RoundingMode.HALF_EVEN));
		al.setSalesPricePU(detailConfig.getSalesPrice());
		al.calculateTotalAmout();
		return al ;
	}

	public void handleDelivery(@Observes @DocumentClosedEvent Delivery closedDelivery){
		Login creatingUser = securityUtil.getConnectedUser();
		Set<DeliveryItem> deliveryItems = closedDelivery.getDeliveryItems();
		Boolean isManagedLot = Boolean.valueOf( applicationConfiguration.getConfiguration().getProperty("managed_articleLot.config"));
		if(isManagedLot==null) throw new IllegalArgumentException("managed_articleLot.config  is required in application.properties files");

		// generate Article lot for each delivery item
		for (DeliveryItem deliveryItem : deliveryItems) {
			if(isManagedLot){
				createArticleLot(creatingUser, deliveryItem,isManagedLot);
			}else {
				upgradeArticleLotQtyOrCreate(deliveryItem, creatingUser);
			}
		}
	}
	public void createArticleLot(Login creatingUser ,DeliveryItem deliveryItem,boolean manageLot){
		ArticleLot al = new  ArticleLot();
		al.setAgency(deliveryItem.getDelivery().getReceivingAgency());
		Article article = deliveryItem.getArticle();
		al.setArticle(article);
		al.setArticleName(article.getArticleName());
		al.setVat(article.getVat());
		al.setCreationDate(new Date());
		al.setExpirationDate(deliveryItem.getExpirationDate());
		if(manageLot){
			al.setInternalPic(deliveryItem.getInternalPic());
		}else {
			al.setInternalPic(deliveryItem.getMainPic());
		}
		al.setMainPic(deliveryItem.getMainPic());
		al.setSecondaryPic(deliveryItem.getSecondaryPic());
		al.setPurchasePricePU(deliveryItem.getPurchasePricePU());
		al.setSalesPricePU(deliveryItem.getSalesPricePU());
		al.setStockQuantity(deliveryItem.getStockQuantity());
		al.calculateTotalAmout();
		al = create(al);
	}

	@SuppressWarnings("unchecked")
	public void upgradeArticleLotQtyOrCreate(DeliveryItem deliveryItem ,Login creatingUser ){
		ArticleLot articleLot = new ArticleLot();
		articleLot.setArticle(deliveryItem.getArticle());
		articleLot.setInternalPic(deliveryItem.getInternalPic());
		List<ArticleLot> found = findBy(articleLot, 0, 1, new SingularAttribute[]{ArticleLot_.internalPic,ArticleLot_.article});
		if(!found.isEmpty()){
			ArticleLot next = found.iterator().next();
			next.setMainPic(deliveryItem.getMainPic());
			next.setSecondaryPic(deliveryItem.getSecondaryPic());
			next.setPurchasePricePU(deliveryItem.getPurchasePricePU());
			next.setSalesPricePU(deliveryItem.getSalesPricePU());
			next.setStockQuantity(next.getStockQuantity().add(deliveryItem.getStockQuantity()));
			next.calculateTotalAmout();
			update(next);
		}else {
			createArticleLot(creatingUser, deliveryItem,false);
		}
	}
	
	public void handleArticleChange(@Observes @EntityEditDoneRequestEvent Article article){

		ArticleLot articleLot = new ArticleLot();
		articleLot.setArticle(article);
		List<ArticleLot> found = findBy(articleLot, 0, -1, new SingularAttribute[]{ArticleLot_.article});
		if(!found.isEmpty()){
			for (ArticleLot lot : found) {
				lot.setArticleName(article.getArticleName());
				lot.setMainPic(article.getPic());
				update(lot);
			}
		}
	
	}
	public void handleTransfer(@Observes @DocumentProcessedEvent ArticleLotTransferManager lotTransferManager){

		ArticleLot articleLot = lotTransferManager.getLotToTransfer();
		BigDecimal qtyToTransfer = lotTransferManager.getQtyToTransfer();
		articleLot.setStockQuantity(articleLot.getStockQuantity().subtract(qtyToTransfer));
		update(articleLot);
	}

	public void handleDestocking(@Observes @DestockingProcessedEvent ArticleLotTransferManager lotTransferManager){

		ArticleLot articleLot = lotTransferManager.getLotToTransfer();
		BigDecimal qtyToTransfer = lotTransferManager.getQtyToTransfer();
		articleLot.setStockQuantity(articleLot.getStockQuantity().add(qtyToTransfer));
		update(articleLot);
	}

	/**
	 * for each sales order item increase corresponding article lot quantity
	 * according to return quantity
	 * 
	 * @param salesOrder
	 */
	public void handleReturnSales(@Observes @ReturnSalesEvent SalesOrder salesOrder){
		Login creatingUser = securityUtil.getConnectedUser();
		Date creationDate = new Date();
		Set<SalesOrderItem> salesOrderItems = salesOrder.getSalesOrderItems();

		for (SalesOrderItem salesOrderItem : salesOrderItems) {
			if(salesOrderItem.hasReturnArticle()){
				ArticleLot articleLot = new ArticleLot();
				articleLot.setInternalPic(salesOrderItem.getInternalPic());
				articleLot.setArticle(salesOrderItem.getArticle());
				@SuppressWarnings("unchecked")
				List<ArticleLot> found = findByLike(articleLot, 0, 1, new SingularAttribute[]{ArticleLot_.internalPic,ArticleLot_.article});
				if(!found.isEmpty()){
					ArticleLot lot = found.iterator().next();
					lot.setStockQuantity(lot.getStockQuantity().add(salesOrderItem.getReturnedQty()));
					lot.calculateTotalAmout();
					update(lot);
				}

			}

		}
	}

	/**
	 * Process a completed sales. Will process with the known lot if provided if not
	 * will use the FIFO technique to manage stocks. 
	 * 
	 * @param closedSales
	 */
	public void handleSalesClosed(@Observes @DirectSalesClosedEvent SalesOrder salesOrder){
		Set<SalesOrderItem> salesOrderItems = salesOrder.getSalesOrderItems();

		for (SalesOrderItem salesOrderItem : salesOrderItems) {
			String internalPic = salesOrderItem.getInternalPic();
			ArticleLot articleLot = new ArticleLot();
			articleLot.setInternalPic(internalPic);
			@SuppressWarnings("unchecked")
			List<ArticleLot> found = findByLike(articleLot, 0, 1, new SingularAttribute[]{ArticleLot_.internalPic});
			articleLot = found.iterator().next();

			BigDecimal currenQtyInStock = articleLot.getStockQuantity()==null?BigDecimal.ZERO:articleLot.getStockQuantity();
			BigDecimal releasingQty = salesOrderItem.getOrderedQty()==null?BigDecimal.ZERO:salesOrderItem.getOrderedQty();
			BigDecimal returnedQty = salesOrderItem.getReturnedQty()==null?BigDecimal.ZERO:salesOrderItem.getReturnedQty();

			BigDecimal qtyInStock = currenQtyInStock.subtract(releasingQty).add(returnedQty);
			articleLot.setStockQuantity(qtyInStock);
			articleLot.setTotalPurchasePrice(qtyInStock.multiply(articleLot.getPurchasePricePU()));
			articleLot.setTotalSalePrice(qtyInStock.multiply(articleLot.getSalesPricePU()));

			update(articleLot);
		}
	}

	/**
	 * Process a canceled sales. Will process with the known lot if provided if not
	 * will use the FIFO technique to manage stocks. 
	 * 
	 * @param canceledSales
	public void handleSalesCanceled(@Observes @DocumentCanceledEvent SalesOrder salesOrder){
		Set<SalesOrderItem> salesOrderItems = salesOrder.getSalesOrderItems();

		for (SalesOrderItem salesOrderItem : salesOrderItems) {
			String internalPic = salesOrderItem.getInternalPic();
			ArticleLot articleLot = new ArticleLot();
			articleLot.setInternalPic(internalPic);
			@SuppressWarnings("unchecked")
			List<ArticleLot> found = findByLike(articleLot, 0, 1, new SingularAttribute[]{ArticleLot_.internalPic});
			articleLot = found.iterator().next();

			BigDecimal currenQtyInStock = articleLot.getStockQuantity()==null?BigDecimal.ZERO:articleLot.getStockQuantity();
			BigDecimal releasingQty = salesOrderItem.getOrderedQty()==null?BigDecimal.ZERO:salesOrderItem.getOrderedQty();
			BigDecimal returnedQty = salesOrderItem.getReturnedQty()==null?BigDecimal.ZERO:salesOrderItem.getReturnedQty();

			BigDecimal qtyInStock = currenQtyInStock.add(releasingQty).subtract(returnedQty);
			articleLot.setStockQuantity(qtyInStock);
			articleLot.setTotalPurchasePrice(qtyInStock.multiply(articleLot.getPurchasePricePU()));
			articleLot.setTotalSalePrice(qtyInStock.multiply(articleLot.getSalesPricePU()));

			update(articleLot);
		}
	}
	 */

	@Inject
	private ArticleLotSequenceRepository articleLotSequenceRepository;

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String newLotNumber(String mainPic){
		ArticleLotSequence articleLotSequence = new ArticleLotSequence();
		articleLotSequence.setMainPic(mainPic);
		List<ArticleLotSequence> found = articleLotSequenceRepository.findBy(articleLotSequence, new SingularAttribute[]{ArticleLotSequence_.mainPic});
		if(!found.isEmpty()){
			articleLotSequence = found.iterator().next();
			articleLotSequence.newLot();
			articleLotSequence = articleLotSequenceRepository.saveAndFlushAndRefresh(articleLotSequence);
		} else {
			articleLotSequence = new ArticleLotSequence();
			articleLotSequence.setMainPic(mainPic);
			articleLotSequence.newLot();
			articleLotSequence = articleLotSequenceRepository.saveAndFlushAndRefresh(articleLotSequence);
		}
		return articleLotSequence.getInternalPic();
	}
}
