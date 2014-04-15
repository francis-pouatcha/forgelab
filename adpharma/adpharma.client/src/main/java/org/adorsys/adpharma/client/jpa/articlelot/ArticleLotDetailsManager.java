package org.adorsys.adpharma.client.jpa.articlelot;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import javafx.beans.property.SimpleObjectProperty;

import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfig;
import org.adorsys.javaext.description.Description;

public class ArticleLotDetailsManager {
	
	
	   @Description("ArtilceLotDetailsManager_lotToDetails_description")
	   private SimpleObjectProperty<ArticleLot> lotToDetails;
	   
	   @Description("ArtilceLotDetailsManager_detailConfig_description")
	   private SimpleObjectProperty<ProductDetailConfig> detailConfig;
	   
	   @Description("ArtilceLotDetailsManager_detailsQty_description")
	   private SimpleObjectProperty<BigDecimal> detailsQty;
	   
	   @Description("ArtilceLotDetailsManager_lotQty_description")
	   private SimpleObjectProperty<BigDecimal> lotQty;
	   
	   @Description("ArtilceLotDetailsManager_targetQty_description")
	   private SimpleObjectProperty<BigDecimal> targetQty;
	   
	   @Description("ArtilceLotDetailsManager_targetPrice_description")
	   private SimpleObjectProperty<BigDecimal> targetPrice;
	   
	   public SimpleObjectProperty<BigDecimal> targetQtyProperty()
	   {
	      if (targetQty == null)
	      {
	    	  targetQty = new SimpleObjectProperty<BigDecimal>();
	      }
	      return targetQty;
	   }

	   public BigDecimal getTargetQty()
	   {
	      return targetQtyProperty().get();
	   }

	   public final void setTargetQty(BigDecimal targetQty)
	   {
	      this.targetQtyProperty().set(targetQty);
	   }
	   
	   public SimpleObjectProperty<BigDecimal> targetPriceProperty()
	   {
	      if (targetPrice == null)
	      {
	    	  targetPrice = new SimpleObjectProperty<BigDecimal>();
	      }
	      return targetPrice;
	   }

	   public BigDecimal getTargetPrice()
	   {
	      return targetPriceProperty().get();
	   }

	   public final void setTargetPrice(BigDecimal targetPrice)
	   {
	      this.targetPriceProperty().set(targetPrice);
	   }
	   
	   public SimpleObjectProperty<ArticleLot> lotToDetailsProperty()
	   {
	      if (lotToDetails == null)
	      {
	    	  lotToDetails = new SimpleObjectProperty<ArticleLot>(new ArticleLot());
	      }
	      return lotToDetails;
	   }
	   
	   @NotNull
	   public ArticleLot getLotToDetails()
	   {
	      return lotToDetailsProperty().get();
	   }

	   public final void setLotToDetails(ArticleLot lotToDetails)
	   {
	      this.lotToDetailsProperty().set(lotToDetails);
	   }
	   
	   public SimpleObjectProperty<ProductDetailConfig> detailConfigProperty()
	   {
	      if (detailConfig == null)
	      {
	    	  detailConfig = new SimpleObjectProperty<ProductDetailConfig>(new ProductDetailConfig());
	      }
	      return detailConfig;
	   }

	   @NotNull
	   public ProductDetailConfig getDetailConfig()
	   {
	      return detailConfigProperty().get();
	   }

	   public final void setDetailConfig(ProductDetailConfig detailConfig)
	   {
	      this.detailConfigProperty().set(detailConfig);
	   }
	   
	   public SimpleObjectProperty<BigDecimal> lotQtyProperty()
	   {
	      if (lotQty == null)
	      {
	    	  lotQty = new SimpleObjectProperty<BigDecimal>();
	      }
	      return lotQty;
	   }

	   public BigDecimal getLotQty()
	   {
	      return lotQtyProperty().get();
	   }

	   public final void setLotQty(BigDecimal lotQty)
	   {
	      this.lotQtyProperty().set(lotQty);
	   }
	   
	   public SimpleObjectProperty<BigDecimal> detailsQtyProperty()
	   {
	      if (detailsQty == null)
	      {
	    	  detailsQty = new SimpleObjectProperty<BigDecimal>();
	      }
	      return detailsQty;
	   }

	   @NotNull
	   public BigDecimal getDetailsQty()
	   {
	      return detailsQtyProperty().get();
	   }

	   public final void setDetailsQty(BigDecimal detailsQty)
	   {
	      this.detailsQtyProperty().set(detailsQty);
	   }
	   
}
