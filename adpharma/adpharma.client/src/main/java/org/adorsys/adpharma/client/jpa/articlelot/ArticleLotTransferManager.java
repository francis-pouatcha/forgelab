package org.adorsys.adpharma.client.jpa.articlelot;

import java.math.BigDecimal;

import javafx.beans.property.SimpleObjectProperty;

import javax.validation.constraints.NotNull;

import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfig;
import org.adorsys.adpharma.client.jpa.warehouse.WareHouse;
import org.adorsys.javaext.description.Description;

public class ArticleLotTransferManager {
	
	
	   @Description("ArtilceLotDetailsManager_lotToTransfer_description")
	   private SimpleObjectProperty<ArticleLot> lotToTransfer;
	   
	   @Description("ArtilceLotDetailsManager_wareHouse_description")
	   private SimpleObjectProperty<WareHouse> wareHouse;
	   
	   @Description("ArtilceLotDetailsManager_qtyToTransfer_description")
	   private SimpleObjectProperty<BigDecimal> qtyToTransfer;
	   
	   @Description("ArtilceLotDetailsManager_lotQty_description")
	   private SimpleObjectProperty<BigDecimal> lotQty;
	   
	   public SimpleObjectProperty<BigDecimal> qtyToTransferProperty()
	   {
	      if (qtyToTransfer == null)
	      {
	    	  qtyToTransfer = new SimpleObjectProperty<BigDecimal>();
	      }
	      return qtyToTransfer;
	   }

	   @NotNull
	   public BigDecimal getQtyToTransfer()
	   {
	      return qtyToTransferProperty().get();
	   }

	   public SimpleObjectProperty<ArticleLot> lotToTransferProperty()
	   {
	      if (lotToTransfer == null)
	      {
	    	  lotToTransfer = new SimpleObjectProperty<ArticleLot>(new ArticleLot());
	      }
	      return lotToTransfer;
	   }
	   
	   @NotNull
	   public ArticleLot getLotToTransfer()
	   {
	      return lotToTransferProperty().get();
	   }

	   public final void setLotToTransfer(ArticleLot lotToTransfer)
	   {
	      this.lotToTransferProperty().set(lotToTransfer);
	   }
	   
	   public SimpleObjectProperty<WareHouse> wareHouseProperty()
	   {
	      if (wareHouse == null)
	      {
	    	  wareHouse = new SimpleObjectProperty<WareHouse>(new WareHouse());
	      }
	      return wareHouse;
	   }

	   @NotNull
	   public WareHouse getWareHouse()
	   {
	      return wareHouseProperty().get();
	   }

	   public final void setWareHouse(WareHouse wareHouse)
	   {
	      this.wareHouseProperty().set(wareHouse);
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
	   
	   
	   
}
