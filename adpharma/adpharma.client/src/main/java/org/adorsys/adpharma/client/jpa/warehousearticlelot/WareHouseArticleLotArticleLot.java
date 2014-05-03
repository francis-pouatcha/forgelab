package org.adorsys.adpharma.client.jpa.warehousearticlelot;

import java.math.BigDecimal;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.Association;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Login_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class WareHouseArticleLotArticleLot implements Association<WareHouseArticleLot, ArticleLot>, Cloneable
{

	  private Long id;
	   private int version;

	   private SimpleStringProperty articleName;
	   
	   private SimpleObjectProperty<BigDecimal> stockQuantity;

	   public WareHouseArticleLotArticleLot()
	   {
	   }

	   public WareHouseArticleLotArticleLot(ArticleLot entity)
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
	   
	   public SimpleObjectProperty<BigDecimal> stockQuantityProperty()
	   {
	      if (stockQuantity == null)
	      {
	         stockQuantity = new SimpleObjectProperty<BigDecimal>();
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
	   //		ArticleAgency other = (ArticleAgency) obj;
	   //      if(id==other.id) return true;
	   //      if (id== null) return other.id==null;
	   //      return id.equals(other.id);
	   //	}

	   public String toString()
	   {
	      return PropertyReader.buildToString(this, "articleName");
	   }

	   @Override
	   public Object clone() throws CloneNotSupportedException
	   {
		   WareHouseArticleLotArticleLot a = new WareHouseArticleLotArticleLot();
	      a.id = id;
	      a.version = version;
	      a.articleName = articleName;
	      return a;
	   }

}
