package org.adorsys.adpharma.client.jpa.articlelot;

import java.math.BigDecimal;

import javafx.beans.property.SimpleObjectProperty;

import javax.validation.constraints.NotNull;

import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfig;
import org.adorsys.adpharma.client.jpa.warehouse.WareHouse;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.ObjectUtils;

public class ArticleLotTransferManager  {
	
	
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
	   
	   public final void setQtyToTransfer(BigDecimal qtyToTransfer)
	   {
	      this.qtyToTransferProperty().set(qtyToTransfer);
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
	      if (lotToTransfer == null)
	      {
	    	  lotToTransfer = new ArticleLot();
	      }
	      PropertyReader.copy(lotToTransfer, getLotToTransfer());
	      lotToTransferProperty().setValue(ObjectUtils.clone(getLotToTransfer()));
	
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
	      if (wareHouse == null)
	      {
	    	  wareHouse = new WareHouse();
	      }
	      PropertyReader.copy(wareHouse, getWareHouse());
	      wareHouseProperty().setValue(ObjectUtils.clone(wareHouse));
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
	   
	  

	   public String toString()
	   {
	      return PropertyReader.buildToString(this, "lotToTransfer","wareHouse");
	   }

	 
	   
	   
}
