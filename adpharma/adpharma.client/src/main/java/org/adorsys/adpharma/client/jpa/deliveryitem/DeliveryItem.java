package org.adorsys.adpharma.client.jpa.deliveryitem;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.list.ListField;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("DeliveryItem_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ListField({ "internalPic", "mainPic", "secondaryPic", "articleName",
	"article.articleName", "expirationDate", "qtyOrdered", "freeQuantity",
	"stockQuantity", "salesPricePU", "purchasePricePU",
"totalPurchasePrice" })
@ToStringField({ "articleName", "article.articleName", "qtyOrdered" })
public class DeliveryItem implements Cloneable
{

	private Long id;
	private int version;

	@Description("DeliveryItem_internalPic_description")
	private SimpleStringProperty internalPic;
	
	@Description("DeliveryItem_mainPic_description")
	private SimpleStringProperty mainPic;
	
	@Description("DeliveryItem_secondaryPic_description")
	private SimpleStringProperty secondaryPic;
	
	@Description("DeliveryItem_articleName_description")
	private SimpleStringProperty articleName;
	
	@Description("DeliveryItem_qtyOrdered_description")
	private SimpleObjectProperty<BigDecimal> qtyOrdered;
	
	@Description("DeliveryItem_availableQty_description")
	private SimpleObjectProperty<BigDecimal> availableQty;
	
	@Description("DeliveryItem_freeQuantity_description")
	private SimpleObjectProperty<BigDecimal> freeQuantity;
	
	@Description("DeliveryItem_stockQuantity_description")
	private SimpleObjectProperty<BigDecimal> stockQuantity;
	
	@Description("DeliveryItem_salesPricePU_description")
	@NumberFormatType(NumberType.CURRENCY)
	private SimpleObjectProperty<BigDecimal> salesPricePU;
	
	@Description("DeliveryItem_purchasePricePU_description")
	@NumberFormatType(NumberType.CURRENCY)
	private SimpleObjectProperty<BigDecimal> purchasePricePU;
	
	@Description("DeliveryItem_totalPurchasePrice_description")
	@NumberFormatType(NumberType.CURRENCY)
	private SimpleObjectProperty<BigDecimal> totalPurchasePrice;
	
	@Description("DeliveryItem_creationDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private SimpleObjectProperty<Calendar> creationDate;
	
	@Description("DeliveryItem_expirationDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy")
	private SimpleObjectProperty<Calendar> expirationDate;
	
	@Description("DeliveryItem_delivery_description")
	@Association(associationType = AssociationType.COMPOSITION, targetEntity = Delivery.class)
	private SimpleObjectProperty<DeliveryItemDelivery> delivery;
	
	@Description("DeliveryItem_article_description")
	@Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
	private SimpleObjectProperty<DeliveryItemArticle> article;
	
	@Description("DeliveryItem_creatingUser_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
	private SimpleObjectProperty<DeliveryItemCreatingUser> creatingUser;

	public static DeliveryItem fromArticle(Article article){
		DeliveryItem deliveryItem = new DeliveryItem();	
		deliveryItem.setMainPic(article.getPic());
		deliveryItem.setSecondaryPic(article.getPic());
		deliveryItem.setInternalPic(article.getPic());
		deliveryItem.setArticleName(article.getArticleName());
		deliveryItem.setArticle(new DeliveryItemArticle(article));
		deliveryItem.setPurchasePricePU(article.getPppu());
		deliveryItem.setSalesPricePU(article.getSppu());
		return deliveryItem;
	}
	public void calculateTotalAmout(){
		BigDecimal pppu = purchasePricePU.get();
		pppu = pppu.multiply(stockQuantity.get().subtract(freeQuantity.get()));
		totalPurchasePrice.set(pppu);
	}
	public Long getId()
	{
		return id;
	}

	public final void setId(Long id)
	{
		this.id = id;
	}

	public int getVersion()
	{
		return version;
	}

	public final void setVersion(int version)
	{
		this.version = version;
	}

	public SimpleStringProperty internalPicProperty()
	{
		if (internalPic == null)
		{
			internalPic = new SimpleStringProperty();
		}
		return internalPic;
	}

	@Size(min = 7, message = "DeliveryItem_internalPic_Size_validation")
	@NotNull(message = "DeliveryItem_internalPic_NotNull_validation")
	public String getInternalPic()
	{
		return internalPicProperty().get();
	}

	public final void setInternalPic(String internalPic)
	{
		this.internalPicProperty().set(internalPic);
	}

	public SimpleStringProperty mainPicProperty()
	{
		if (mainPic == null)
		{
			mainPic = new SimpleStringProperty();
		}
		return mainPic;
	}

	@Size(min = 7, message = "DeliveryItem_mainPic_Size_validation")
	public String getMainPic()
	{
		return mainPicProperty().get();
	}

	public final void setMainPic(String mainPic)
	{
		this.mainPicProperty().set(mainPic);
	}

	public SimpleStringProperty secondaryPicProperty()
	{
		if (secondaryPic == null)
		{
			secondaryPic = new SimpleStringProperty();
		}
		return secondaryPic;
	}

	@Size(min = 7, message = "DeliveryItem_secondaryPic_Size_validation")
	public String getSecondaryPic()
	{
		return secondaryPicProperty().get();
	}

	public final void setSecondaryPic(String secondaryPic)
	{
		this.secondaryPicProperty().set(secondaryPic);
	}

	public SimpleStringProperty articleNameProperty()
	{
		if (articleName == null)
		{
			articleName = new SimpleStringProperty();
		}
		return articleName;
	}

	@NotNull(message = "DeliveryItem_articleName_NotNull_validation")
	public String getArticleName()
	{
		return articleNameProperty().get();
	}

	public final void setArticleName(String articleName)
	{
		this.articleNameProperty().set(articleName);
	}

	public SimpleObjectProperty<BigDecimal> qtyOrderedProperty()
	{
		if (qtyOrdered == null)
		{
			qtyOrdered = new SimpleObjectProperty<BigDecimal>(BigDecimal.ONE);
		}
		return qtyOrdered;
	}

	public BigDecimal getQtyOrdered()
	{
		return qtyOrderedProperty().get();
	}

	public final void setQtyOrdered(BigDecimal qtyOrdered)
	{
		this.qtyOrderedProperty().set(qtyOrdered);
	}

	public SimpleObjectProperty<BigDecimal> availableQtyProperty()
	{
		if (availableQty == null)
		{
			availableQty = new SimpleObjectProperty<BigDecimal>(BigDecimal.ONE);
		}
		return availableQty;
	}

	public BigDecimal getAvailableQty()
	{
		return availableQtyProperty().get();
	}

	public final void setAvailableQty(BigDecimal availableQty)
	{
		this.availableQtyProperty().set(availableQty);
	}

	public SimpleObjectProperty<BigDecimal> freeQuantityProperty()
	{
		if (freeQuantity == null)
		{
			freeQuantity = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO);
		}
		return freeQuantity;
	}

	public BigDecimal getFreeQuantity()
	{
		return freeQuantityProperty().get();
	}

	public final void setFreeQuantity(BigDecimal freeQuantity)
	{
		this.freeQuantityProperty().set(freeQuantity);
	}

	public SimpleObjectProperty<BigDecimal> stockQuantityProperty()
	{
		if (stockQuantity == null)
		{
			stockQuantity = new SimpleObjectProperty<BigDecimal>(BigDecimal.ONE);
		}
		return stockQuantity;
	}

	public BigDecimal getStockQuantity()
	{
		return stockQuantityProperty().get();
	}

	public final void setStockQuantity(BigDecimal stockQuantity)
	{
		this.stockQuantityProperty().set(stockQuantity);
	}

	public SimpleObjectProperty<BigDecimal> salesPricePUProperty()
	{
		if (salesPricePU == null)
		{
			salesPricePU = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO);
		}
		return salesPricePU;
	}

	public BigDecimal getSalesPricePU()
	{
		return salesPricePUProperty().get();
	}

	public final void setSalesPricePU(BigDecimal salesPricePU)
	{
		this.salesPricePUProperty().set(salesPricePU);
	}

	public SimpleObjectProperty<BigDecimal> purchasePricePUProperty()
	{
		if (purchasePricePU == null)
		{
			purchasePricePU = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO);
		}
		return purchasePricePU;
	}

	public BigDecimal getPurchasePricePU()
	{
		return purchasePricePUProperty().get();
	}

	public final void setPurchasePricePU(BigDecimal purchasePricePU)
	{
		this.purchasePricePUProperty().set(purchasePricePU);
	}

	public SimpleObjectProperty<BigDecimal> totalPurchasePriceProperty()
	{
		if (totalPurchasePrice == null)
		{
			totalPurchasePrice = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO);
		}
		return totalPurchasePrice;
	}

	public BigDecimal getTotalPurchasePrice()
	{
		return totalPurchasePriceProperty().get();
	}

	public final void setTotalPurchasePrice(BigDecimal totalPurchasePrice)
	{
		this.totalPurchasePriceProperty().set(totalPurchasePrice);
	}

	public SimpleObjectProperty<Calendar> creationDateProperty()
	{
		if (creationDate == null)
		{
			creationDate = new SimpleObjectProperty<Calendar>(Calendar.getInstance());
		}
		return creationDate;
	}

	public Calendar getCreationDate()
	{
		return creationDateProperty().get();
	}

	public final void setCreationDate(Calendar creationDate)
	{
		this.creationDateProperty().set(creationDate);
	}

	public SimpleObjectProperty<Calendar> expirationDateProperty()
	{
		if (expirationDate == null)
		{
			Calendar instance = Calendar.getInstance();
			instance.setTime(DateUtils.addYears(new Date(), 10));
			expirationDate = new SimpleObjectProperty<Calendar>(instance);
		}
		return expirationDate;
	}

	public Calendar getExpirationDate()
	{
		return expirationDateProperty().get();
	}

	public final void setExpirationDate(Calendar expirationDate)
	{
		this.expirationDateProperty().set(expirationDate);
	}

	public SimpleObjectProperty<DeliveryItemDelivery> deliveryProperty()
	{
		if (delivery == null)
		{
			delivery = new SimpleObjectProperty<DeliveryItemDelivery>(new DeliveryItemDelivery());
		}
		return delivery;
	}

	public DeliveryItemDelivery getDelivery()
	{
		return deliveryProperty().get();
	}

	public final void setDelivery(DeliveryItemDelivery delivery)
	{
		if (delivery == null)
		{
			delivery = new DeliveryItemDelivery();
		}
		PropertyReader.copy(delivery, getDelivery());
		deliveryProperty().setValue(ObjectUtils.clone(getDelivery()));
		getDelivery().setSupplier(delivery.getSupplier());
	}

	public SimpleObjectProperty<DeliveryItemArticle> articleProperty()
	{
		if (article == null)
		{
			article = new SimpleObjectProperty<DeliveryItemArticle>(new DeliveryItemArticle());
		}
		return article;
	}

	@NotNull(message = "DeliveryItem_article_NotNull_validation")
	public DeliveryItemArticle getArticle()
	{
		return articleProperty().get();
	}

	public final void setArticle(DeliveryItemArticle article)
	{
		if (article == null)
		{
			article = new DeliveryItemArticle();
		}
		PropertyReader.copy(article, getArticle());
		articleProperty().setValue(ObjectUtils.clone(getArticle()));
	}

	public SimpleObjectProperty<DeliveryItemCreatingUser> creatingUserProperty()
	{
		if (creatingUser == null)
		{
			creatingUser = new SimpleObjectProperty<DeliveryItemCreatingUser>(new DeliveryItemCreatingUser());
		}
		return creatingUser;
	}

	@NotNull(message = "DeliveryItem_creatingUser_NotNull_validation")
	public DeliveryItemCreatingUser getCreatingUser()
	{
		return creatingUserProperty().get();
	}

	public final void setCreatingUser(DeliveryItemCreatingUser creatingUser)
	{
		if (creatingUser == null)
		{
			creatingUser = new DeliveryItemCreatingUser();
		}
		PropertyReader.copy(creatingUser, getCreatingUser());
		creatingUserProperty().setValue(ObjectUtils.clone(getCreatingUser()));
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeliveryItem other = (DeliveryItem) obj;
		if (id == other.id)
			return true;
		if (id == null)
			return other.id == null;
		return id.equals(other.id);
	}

	public String toString()
	{
		return PropertyReader.buildToString(this, "articleName", "internalPic");
	}

	public void cleanIds()
	{
		id = null;
		version = 0;
		DeliveryItemDelivery f = delivery.get();
		f.setId(null);
		f.setVersion(0);
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		DeliveryItem e = new DeliveryItem();
		e.id = id;
		e.version = version;

		e.internalPic = internalPic;
		e.mainPic = mainPic;
		e.secondaryPic = secondaryPic;
		e.articleName = articleName;
		e.qtyOrdered = qtyOrdered;
		e.availableQty = availableQty;
		e.freeQuantity = freeQuantity;
		e.stockQuantity = stockQuantity;
		e.salesPricePU = salesPricePU;
		e.purchasePricePU = purchasePricePU;
		e.totalPurchasePrice = totalPurchasePrice;
		e.creationDate = creationDate;
		e.expirationDate = expirationDate;
		e.delivery = delivery;
		e.article = article;
		e.creatingUser = creatingUser;
		return e;
	}
}