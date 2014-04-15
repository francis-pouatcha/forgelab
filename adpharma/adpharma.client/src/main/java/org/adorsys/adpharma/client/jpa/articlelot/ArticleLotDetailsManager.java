package org.adorsys.adpharma.client.jpa.articlelot;

import java.math.BigDecimal;

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
	   
	   public SimpleObjectProperty<ArticleLot> lotToDetailsProperty()
	   {
	      if (lotToDetails == null)
	      {
	    	  lotToDetails = new SimpleObjectProperty<ArticleLot>(new ArticleLot());
	      }
	      return lotToDetails;
	   }

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

	   public BigDecimal getDetailsQty()
	   {
	      return detailsQtyProperty().get();
	   }

	   public final void setDetailsQty(BigDecimal detailsQty)
	   {
	      this.detailsQtyProperty().set(detailsQty);
	   }
	   
}
