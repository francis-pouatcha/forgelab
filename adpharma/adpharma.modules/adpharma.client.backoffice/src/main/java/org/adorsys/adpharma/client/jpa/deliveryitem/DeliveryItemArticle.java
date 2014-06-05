package org.adorsys.adpharma.client.jpa.deliveryitem;

import java.math.BigDecimal;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.Association;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Article_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliveryItemArticle implements Association<DeliveryItem, Article>, Cloneable
{

   private Long id;
   private int version;

   private SimpleStringProperty articleName;
   private SimpleStringProperty pic;
   private SimpleStringProperty manufacturer;
   private SimpleBooleanProperty active;
   private SimpleBooleanProperty authorizedSale;
   private SimpleObjectProperty<BigDecimal> qtyInStock;
   private SimpleObjectProperty<BigDecimal> sppu;

   public DeliveryItemArticle()
   {
   }

   public DeliveryItemArticle(Article entity)
   {
      PropertyReader.copy(entity, this);
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

   public SimpleStringProperty articleNameProperty()
   {
      if (articleName == null)
      {
         articleName = new SimpleStringProperty();
      }
      return articleName;
   }

   public String getArticleName()
   {
      return articleNameProperty().get();
   }

   public final void setArticleName(String articleName)
   {
      this.articleNameProperty().set(articleName);
   }

   public SimpleStringProperty picProperty()
   {
      if (pic == null)
      {
         pic = new SimpleStringProperty();
      }
      return pic;
   }

   public String getPic()
   {
      return picProperty().get();
   }

   public final void setPic(String pic)
   {
      this.picProperty().set(pic);
   }

   public SimpleStringProperty manufacturerProperty()
   {
      if (manufacturer == null)
      {
         manufacturer = new SimpleStringProperty();
      }
      return manufacturer;
   }

   public String getManufacturer()
   {
      return manufacturerProperty().get();
   }

   public final void setManufacturer(String manufacturer)
   {
      this.manufacturerProperty().set(manufacturer);
   }

   public SimpleBooleanProperty activeProperty()
   {
      if (active == null)
      {
         active = new SimpleBooleanProperty();
      }
      return active;
   }

   public Boolean getActive()
   {
      return activeProperty().get();
   }

   public final void setActive(Boolean active)
   {
      if (active == null)
         active = Boolean.FALSE;
      this.activeProperty().set(active);
   }

   public SimpleBooleanProperty authorizedSaleProperty()
   {
      if (authorizedSale == null)
      {
         authorizedSale = new SimpleBooleanProperty();
      }
      return authorizedSale;
   }

   public Boolean getAuthorizedSale()
   {
      return authorizedSaleProperty().get();
   }

   public final void setAuthorizedSale(Boolean authorizedSale)
   {
      if (authorizedSale == null)
         authorizedSale = Boolean.FALSE;
      this.authorizedSaleProperty().set(authorizedSale);
   }

   public SimpleObjectProperty<BigDecimal> qtyInStockProperty()
   {
      if (qtyInStock == null)
      {
         qtyInStock = new SimpleObjectProperty<BigDecimal>();
      }
      return qtyInStock;
   }

   public BigDecimal getQtyInStock()
   {
      return qtyInStockProperty().get();
   }

   public final void setQtyInStock(BigDecimal qtyInStock)
   {
      this.qtyInStockProperty().set(qtyInStock);
   }

   public SimpleObjectProperty<BigDecimal> sppuProperty()
   {
      if (sppu == null)
      {
         sppu = new SimpleObjectProperty<BigDecimal>();
      }
      return sppu;
   }

   public BigDecimal getSppu()
   {
      return sppuProperty().get();
   }

   public final void setSppu(BigDecimal sppu)
   {
      this.sppuProperty().set(sppu);
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

   //	@Override
   //	public boolean equals(Object obj) {
   //		if (this == obj)
   //			return true;
   //		if (obj == null)
   //			return false;
   //		if (getClass() != obj.getClass())
   //			return false;
   //		DeliveryItemArticle other = (DeliveryItemArticle) obj;
   //      if(id==other.id) return true;
   //      if (id== null) return other.id==null;
   //      return id.equals(other.id);
   //	}

   public String toString()
   {
      return PropertyReader.buildToString(this, "articleName", "pic");
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      DeliveryItemArticle a = new DeliveryItemArticle();
      a.id = id;
      a.version = version;

      a.articleName = articleName;
      a.pic = pic;
      a.manufacturer = manufacturer;
      a.active = active;
      a.authorizedSale = authorizedSale;
      a.qtyInStock = qtyInStock;
      a.sppu = sppu;
      return a;
   }

}
