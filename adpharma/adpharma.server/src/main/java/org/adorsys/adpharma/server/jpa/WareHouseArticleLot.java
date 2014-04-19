package org.adorsys.adpharma.server.jpa;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import org.adorsys.javaext.description.Description;
import java.math.BigDecimal;
import org.adorsys.adpharma.server.jpa.WareHouse;
import javax.persistence.ManyToOne;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.adpharma.server.jpa.ArticleLot;

@Entity
@Description("WareHouseArticleLot_description")
public class WareHouseArticleLot implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("WareHouseArticleLot_mainCip_description")
   private String mainCip;

   @Column
   @Description("WareHouseArticleLot_secondaryCip_description")
   private String secondaryCip;

   @Column
   @Description("WareHouseArticleLot_internalCip_description")
   private String internalCip;

   @Column
   @Description("WareHouseArticleLot_stockQuantity_description")
   private BigDecimal stockQuantity;

   @ManyToOne(optional = false)
   @Description("WareHouseArticleLot_wareHouse_description")
   @Association(associationType = AssociationType.AGGREGATION, targetEntity = WareHouse.class, selectionMode = SelectionMode.COMBOBOX, fields = "name")
   private WareHouse wareHouse;

   @ManyToOne(optional = false)
   @Description("WareHouseArticleLot_articleLot_description")
   @Association(associationType = AssociationType.AGGREGATION, targetEntity = ArticleLot.class, selectionMode = SelectionMode.COMBOBOX, fields = "internalPic")
   private ArticleLot articleLot;

   @Column
   @Description("WareHouseArticleLot_articleName_description")
   private String articleName;

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

   @Override
   public boolean equals(Object that)
   {
      if (this == that)
      {
         return true;
      }
      if (that == null)
      {
         return false;
      }
      if (getClass() != that.getClass())
      {
         return false;
      }
      if (id != null)
      {
         return id.equals(((WareHouseArticleLot) that).id);
      }
      return super.equals(that);
   }

   @Override
   public int hashCode()
   {
      if (id != null)
      {
         return id.hashCode();
      }
      return super.hashCode();
   }

   public String getMainCip()
   {
      return this.mainCip;
   }

   public void setMainCip(final String mainCip)
   {
      this.mainCip = mainCip;
   }

   public String getSecondaryCip()
   {
      return this.secondaryCip;
   }

   public void setSecondaryCip(final String secondaryCip)
   {
      this.secondaryCip = secondaryCip;
   }

   public String getInternalCip()
   {
      return this.internalCip;
   }

   public void setInternalCip(final String internalCip)
   {
      this.internalCip = internalCip;
   }

   public BigDecimal getStockQuantity()
   {
      return this.stockQuantity;
   }

   public void setStockQuantity(final BigDecimal stockQuantity)
   {
      this.stockQuantity = stockQuantity;
   }

   public WareHouse getWareHouse()
   {
      return this.wareHouse;
   }

   public void setWareHouse(final WareHouse wareHouse)
   {
      this.wareHouse = wareHouse;
   }

   public ArticleLot getArticleLot()
   {
      return this.articleLot;
   }

   public void setArticleLot(final ArticleLot articleLot)
   {
      this.articleLot = articleLot;
   }

   public String getArticleName()
   {
      return this.articleName;
   }

   public void setArticleName(final String articleName)
   {
      this.articleName = articleName;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (mainCip != null && !mainCip.trim().isEmpty())
         result += "mainCip: " + mainCip;
      if (secondaryCip != null && !secondaryCip.trim().isEmpty())
         result += ", secondaryCip: " + secondaryCip;
      if (internalCip != null && !internalCip.trim().isEmpty())
         result += ", internalCip: " + internalCip;
      if (articleName != null && !articleName.trim().isEmpty())
         result += ", articleName: " + articleName;
      return result;
   }
}