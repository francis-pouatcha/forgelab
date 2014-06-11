package org.adorsys.adpharma.client.jpa.articlelot;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.ObjectUtils;

public class ArticleLotMovedToTrashData {


	  private Long id;
	   private int version;

	   private SimpleStringProperty internalPic;
	   private SimpleStringProperty mainPic;
	   private SimpleStringProperty articleName;
	   private SimpleObjectProperty<BigDecimal> stockQuantity;
	   private SimpleObjectProperty<BigDecimal> qtyToMoved;

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

	   @Size(min = 7, message = "ArticleLot_internalPic_Size_validation")
	   @NotNull(message = "ArticleLot_internalPic_NotNull_validation")
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

	   @Size(min = 7, message = "ArticleLot_mainPic_Size_validation")
	   public String getMainPic()
	   {
	      return mainPicProperty().get();
	   }

	   public final void setMainPic(String mainPic)
	   {
	      this.mainPicProperty().set(mainPic);
	   }

	   public SimpleStringProperty articleNameProperty()
	   {
	      if (articleName == null)
	      {
	         articleName = new SimpleStringProperty();
	      }
	      return articleName;
	   }

	   @NotNull(message = "ArticleLot_articleName_NotNull_validation")
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

	   public SimpleObjectProperty<BigDecimal> qtyToMovedProperty()
	   {
	      if (qtyToMoved == null)
	      {
	    	  qtyToMoved = new SimpleObjectProperty<BigDecimal>(BigDecimal.ONE);
	      }
	      return qtyToMoved;
	   }

	   @NotNull
	   public BigDecimal getQtyToMoved()
	   {
	      return qtyToMovedProperty().get();
	   }

	   public final void setQtyToMoved(BigDecimal salesPricePU)
	   {
	      this.qtyToMovedProperty().set(salesPricePU);
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

	
}
