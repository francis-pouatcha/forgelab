package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.jpa.ArticleLot;
import org.adorsys.adpharma.server.jpa.ArticleSearchInput;
import org.adorsys.adpharma.server.jpa.DocumentProcessingState;
import org.adorsys.adpharma.server.jpa.Inventory;
import org.adorsys.adpharma.server.jpa.InventoryItem;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.Section;
import org.adorsys.adpharma.server.repo.InventoryRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;
import org.adorsys.adpharma.server.utils.SequenceGenerator;

@Stateless
public class InventoryEJB
{

	@Inject
	private InventoryRepository repository;

	@Inject
	private InventoryItemMerger inventoryItemMerger;

	@Inject
	private LoginMerger loginMerger;

	@Inject
	private AgencyMerger agencyMerger;

	@Inject
	private InventoryItem inventoryItem ;

	@Inject
	private SectionMerger sectionMerger ;

	@Inject
	private ArticleLotEJB articleLotEJB;

	@Inject
	private InventoryItemEJB inventoryItemEJB ;

	@Inject
	private EntityManager em ;

	@Inject
	private SecurityUtil securityUtil ;

	public Inventory create(Inventory entity)
	{
		Login user = securityUtil.getConnectedUser();
		Inventory attach = attach(entity);
		attach.setRecordingUser(user);
		attach.setInventoryStatus(DocumentProcessingState.ONGOING);
		Inventory save = repository.save(attach);
		return initInventory(attach,user);
	}

	public Inventory deleteById(Long id)
	{
		Inventory entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
		}
		return entity;
	}

	public Inventory update(Inventory entity)
	{
		return repository.save(attach(entity));
	}

	public Inventory findById(Long id)
	{
		return repository.findBy(id);
	}

	public List<Inventory> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<Inventory> findBy(Inventory entity, int start, int max, SingularAttribute<Inventory, ?>[] attributes)
	{
		Inventory inventory = attach(entity);
		return repository.findBy(inventory, start, max, attributes);
	}

	public Long countBy(Inventory entity, SingularAttribute<Inventory, ?>[] attributes)
	{
		Inventory inventory = attach(entity);
		return repository.count(inventory, attributes);
	}

	public List<Inventory> findByLike(Inventory entity, int start, int max, SingularAttribute<Inventory, ?>[] attributes)
	{
		Inventory inventory = attach(entity);
		return repository.findByLike(inventory, start, max, attributes);
	}

	public Long countByLike(Inventory entity, SingularAttribute<Inventory, ?>[] attributes)
	{
		Inventory inventory = attach(entity);
		return repository.countLike(inventory, attributes);
	}

	private Inventory attach(Inventory entity)
	{
		if (entity == null)
			return null;

		// aggregated
		entity.setRecordingUser(loginMerger.bindAggregated(entity.getRecordingUser()));

		// aggregated
		entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

		// aggregated
		entity.setSection(sectionMerger.bindAggregated(entity.getSection()));

		// composed collections
		Set<InventoryItem> inventoryItems = entity.getInventoryItems();
		for (InventoryItem inventoryItem : inventoryItems)
		{
			inventoryItem.setInventory(entity);
		}

		return entity;
	}

	public Inventory initInventory(Inventory inventory,Login user){
		List<ArticleLot> lotForInventory = lotForInventory(inventory);
		for (ArticleLot articleLot : lotForInventory) {
			InventoryItem item = inventoryItemFromArticleLot(articleLot);
			item.setRecordingUser(user);
			item.setInventory(inventory);
			inventoryItemEJB.create(item);
			// update inventary amounts
			inventory.setGapSaleAmount(item.getGapTotalSalePrice().add(inventory.getGapSaleAmount()));
			inventory.setGapPurchaseAmount(item.getGapTotalPurchasePrice().add(inventory.getGapPurchaseAmount()));
			inventory.getInventoryItems().add(item);
		}
		inventory.setInventoryNumber(SequenceGenerator.CUSTOMER_INVENTORY_SEQUENCE_PREFIXE+inventory.getId());
		return repository.save(inventory) ;
	}


	public List<ArticleLot> lotForInventory(Inventory inventory){
		List<ArticleLot> lotForInventorys = new ArrayList<ArticleLot>();
		if(inventory.getCreateEmpty())
			return lotForInventorys;
		String query ="SELECT c FROM ArticleLot AS c ORDER BY c.article.articleName  ";
		Section section = inventory.getSection();

		if(section!=null && section.getId()!=null)
			query ="SELECT c FROM ArticleLot AS c WHERE c.article.section = :section ORDER BY c.article.articleName  "; 

		Query querys = em.createQuery(query,ArticleLot.class) ;

		if(section!=null && section.getId()!=null)
			querys.setParameter("section",section);


		lotForInventorys = querys.getResultList();

		return lotForInventorys ;
	}

	public InventoryItem inventoryItemFromArticleLot(ArticleLot lot){
		InventoryItem item = new InventoryItem() ;
		item.setArticle(lot.getArticle());
		item.setAsseccedQty(lot.getStockQuantity());
		item.setExpectedQty(lot.getStockQuantity());
		item.setGapPurchasePricePU(lot.getPurchasePricePU());
		item.setGapSalesPricePU(lot.getSalesPricePU());
		item.setInternalPic(lot.getInternalPic());
		item.setRecordingDate(new Date());
		item.setGap(item.getExpectedQty().subtract(item.getAsseccedQty()).longValue());
		item.setGapTotalPurchasePrice(item.getGapPurchasePricePU().multiply(BigDecimal.valueOf(item.getGap())));
		item.setGapTotalSalePrice(item.getGapSalesPricePU().multiply(BigDecimal.valueOf(item.getGap())));

		return item ;


	}
}
