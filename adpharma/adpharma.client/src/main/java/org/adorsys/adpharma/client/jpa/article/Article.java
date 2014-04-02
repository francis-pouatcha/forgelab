package org.adorsys.adpharma.client.jpa.article;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.SimpleStringProperty;

import org.adorsys.adpharma.client.jpa.section.Section;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;

import org.adorsys.adpharma.client.jpa.productfamily.ProductFamily;

import java.math.BigDecimal;
import java.util.Calendar;

import org.adorsys.adpharma.client.jpa.salesmargin.SalesMargin;
import org.adorsys.adpharma.client.jpa.packagingmode.PackagingMode;

import javafx.beans.property.SimpleLongProperty;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.constraints.NotNull;

import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Article_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField({ "articleName", "pic" })
@ListField({ "articleName", "pic", "manufacturer", "active", "qtyInStock",
      "sppu", "authorizedSale", "agency.name" })
public class Article implements Cloneable
{

   private Long id;
   private int version;

   @Description("Article_articleName_description")
   private SimpleStringProperty articleName;
   @Description("Article_pic_description")
   private SimpleStringProperty pic;
   @Description("Article_manufacturer_description")
   private SimpleStringProperty manufacturer;
   @Description("Article_active_description")
   private SimpleBooleanProperty active;
   @Description("Article_authorizedSale_description")
   private SimpleBooleanProperty authorizedSale;
   @Description("Article_maxStockQty_description")
   private SimpleLongProperty maxStockQty;
   @Description("Article_qtyInStock_description")
   private SimpleObjectProperty<BigDecimal> qtyInStock;
   @Description("Article_pppu_description")
   private SimpleObjectProperty<BigDecimal> pppu;
   @Description("Article_sppu_description")
   private SimpleObjectProperty<BigDecimal> sppu;
   @Description("Article_maxDiscountRate_description")
   @NumberFormatType(NumberType.PERCENTAGE)
   private SimpleObjectProperty<BigDecimal> maxDiscountRate;
   @Description("Article_totalStockPrice_description")
   @NumberFormatType(NumberType.CURRENCY)
//   private SimpleObjectProperty<BigDecimal> totalStockPrice;
   ObjectBinding<BigDecimal> totalStockPrice;
   @Description("Article_lastStockEntry_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy")
   private SimpleObjectProperty<Calendar> lastStockEntry;
   @Description("Article_lastOutOfStock_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy")
   private SimpleObjectProperty<Calendar> lastOutOfStock;
   @Description("Article_recordingDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> recordingDate;
   @Description("Article_section_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Section.class)
   private SimpleObjectProperty<ArticleSection> section;
   @Description("Article_family_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = ProductFamily.class)
   private SimpleObjectProperty<ArticleFamily> family;
   @Description("Article_defaultSalesMargin_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = SalesMargin.class)
   private SimpleObjectProperty<ArticleDefaultSalesMargin> defaultSalesMargin;
   @Description("Article_packagingMode_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = PackagingMode.class)
   private SimpleObjectProperty<ArticlePackagingMode> packagingMode;
   @Description("Article_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   private SimpleObjectProperty<ArticleAgency> agency;
   @Description("Article_clearanceConfig_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = ClearanceConfig.class)
   private SimpleObjectProperty<ArticleClearanceConfig> clearanceConfig;

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

   @NotNull(message = "Article_articleName_NotNull_validation")
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
         active = new SimpleBooleanProperty(Boolean.TRUE);
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
         authorizedSale = new SimpleBooleanProperty(Boolean.TRUE);
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

   public SimpleLongProperty maxStockQtyProperty()
   {
      if (maxStockQty == null)
      {
         maxStockQty = new SimpleLongProperty(Long.valueOf(0));
      }
      return maxStockQty;
   }

   public Long getMaxStockQty()
   {
      return maxStockQtyProperty().get();
   }

   public final void setMaxStockQty(Long maxStockQty)
   {
      this.maxStockQtyProperty().set(maxStockQty);
   }

   public SimpleObjectProperty<BigDecimal> qtyInStockProperty()
   {
      if (qtyInStock == null)
      {
         qtyInStock = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO);
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

   public SimpleObjectProperty<BigDecimal> pppuProperty()
   {
      if (pppu == null)
      {
         pppu = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO);
      }
      return pppu;
   }

   public BigDecimal getPppu()
   {
      return pppuProperty().get();
   }

   public final void setPppu(BigDecimal pppu)
   {
      this.pppuProperty().set(pppu);
   }

   public SimpleObjectProperty<BigDecimal> sppuProperty()
   {
      if (sppu == null)
      {
         sppu = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO);
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

   public SimpleObjectProperty<BigDecimal> maxDiscountRateProperty()
   {
      if (maxDiscountRate == null)
      {
         maxDiscountRate = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO);
      }
      return maxDiscountRate;
   }

   public BigDecimal getMaxDiscountRate()
   {
      return maxDiscountRateProperty().get();
   }

   public final void setMaxDiscountRate(BigDecimal maxDiscountRate)
   {
      this.maxDiscountRateProperty().set(maxDiscountRate);
   }

   public ObjectBinding<BigDecimal> totalStockPriceProperty()
   {
      if (totalStockPrice == null)
      {
//         totalStockPrice = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO);
    	   totalStockPrice = new ObjectBinding<BigDecimal>() {
    		   
    		    { bind(qtyInStockProperty(),sppuProperty());}
    		    protected BigDecimal computeValue() {
    		        if (qtyInStockProperty().get() == null || sppuProperty().get() == null) return null;
    		        return qtyInStockProperty().get().multiply(sppuProperty().get());
    		    }
    		};
      }
      return totalStockPrice;
   }

   public BigDecimal getTotalStockPrice()
   {
      return totalStockPriceProperty().get();
   }

   public final void setTotalStockPrice(BigDecimal totalStockPrice)
   {
//      this.totalStockPriceProperty().set(totalStockPrice);
   }

   public SimpleObjectProperty<Calendar> lastStockEntryProperty()
   {
      if (lastStockEntry == null)
      {
         lastStockEntry = new SimpleObjectProperty<Calendar>(Calendar.getInstance());
      }
      return lastStockEntry;
   }

   public Calendar getLastStockEntry()
   {
      return lastStockEntryProperty().get();
   }

   public final void setLastStockEntry(Calendar lastStockEntry)
   {
      this.lastStockEntryProperty().set(lastStockEntry);
   }

   public SimpleObjectProperty<Calendar> lastOutOfStockProperty()
   {
      if (lastOutOfStock == null)
      {
         lastOutOfStock = new SimpleObjectProperty<Calendar>(Calendar.getInstance());
      }
      return lastOutOfStock;
   }

   public Calendar getLastOutOfStock()
   {
      return lastOutOfStockProperty().get();
   }

   public final void setLastOutOfStock(Calendar lastOutOfStock)
   {
      this.lastOutOfStockProperty().set(lastOutOfStock);
   }

   public SimpleObjectProperty<Calendar> recordingDateProperty()
   {
      if (recordingDate == null)
      {
         recordingDate = new SimpleObjectProperty<Calendar>(Calendar.getInstance());
      }
      return recordingDate;
   }

   public Calendar getRecordingDate()
   {
      return recordingDateProperty().get();
   }

   public final void setRecordingDate(Calendar recordingDate)
   {
      this.recordingDateProperty().set(recordingDate);
   }

   public SimpleObjectProperty<ArticleSection> sectionProperty()
   {
      if (section == null)
      {
         section = new SimpleObjectProperty<ArticleSection>(new ArticleSection());
      }
      return section;
   }

   @NotNull(message = "Article_section_NotNull_validation")
   public ArticleSection getSection()
   {
      return sectionProperty().get();
   }

   public final void setSection(ArticleSection section)
   {
      if (section == null)
      {
         section = new ArticleSection();
      }
      PropertyReader.copy(section, getSection());
      sectionProperty().setValue(ObjectUtils.clone(getSection()));
   }

   public SimpleObjectProperty<ArticleFamily> familyProperty()
   {
      if (family == null)
      {
         family = new SimpleObjectProperty<ArticleFamily>(new ArticleFamily());
      }
      return family;
   }

   public ArticleFamily getFamily()
   {
      return familyProperty().get();
   }

   public final void setFamily(ArticleFamily family)
   {
      if (family == null)
      {
         family = new ArticleFamily();
      }
      PropertyReader.copy(family, getFamily());
      familyProperty().setValue(ObjectUtils.clone(getFamily()));
   }

   public SimpleObjectProperty<ArticleDefaultSalesMargin> defaultSalesMarginProperty()
   {
      if (defaultSalesMargin == null)
      {
         defaultSalesMargin = new SimpleObjectProperty<ArticleDefaultSalesMargin>(new ArticleDefaultSalesMargin());
      }
      return defaultSalesMargin;
   }

   public ArticleDefaultSalesMargin getDefaultSalesMargin()
   {
      return defaultSalesMarginProperty().get();
   }

   public final void setDefaultSalesMargin(ArticleDefaultSalesMargin defaultSalesMargin)
   {
      if (defaultSalesMargin == null)
      {
         defaultSalesMargin = new ArticleDefaultSalesMargin();
      }
      PropertyReader.copy(defaultSalesMargin, getDefaultSalesMargin());
      defaultSalesMarginProperty().setValue(ObjectUtils.clone(getDefaultSalesMargin()));
   }

   public SimpleObjectProperty<ArticlePackagingMode> packagingModeProperty()
   {
      if (packagingMode == null)
      {
         packagingMode = new SimpleObjectProperty<ArticlePackagingMode>(new ArticlePackagingMode());
      }
      return packagingMode;
   }

   public ArticlePackagingMode getPackagingMode()
   {
      return packagingModeProperty().get();
   }

   public final void setPackagingMode(ArticlePackagingMode packagingMode)
   {
      if (packagingMode == null)
      {
         packagingMode = new ArticlePackagingMode();
      }
      PropertyReader.copy(packagingMode, getPackagingMode());
      packagingModeProperty().setValue(ObjectUtils.clone(getPackagingMode()));
   }

   public SimpleObjectProperty<ArticleAgency> agencyProperty()
   {
      if (agency == null)
      {
         agency = new SimpleObjectProperty<ArticleAgency>(new ArticleAgency());
      }
      return agency;
   }

   @NotNull(message = "Article_agency_NotNull_validation")
   public ArticleAgency getAgency()
   {
      return agencyProperty().get();
   }

   public final void setAgency(ArticleAgency agency)
   {
      if (agency == null)
      {
         agency = new ArticleAgency();
      }
      PropertyReader.copy(agency, getAgency());
      agencyProperty().setValue(ObjectUtils.clone(getAgency()));
   }

   public SimpleObjectProperty<ArticleClearanceConfig> clearanceConfigProperty()
   {
      if (clearanceConfig == null)
      {
         clearanceConfig = new SimpleObjectProperty<ArticleClearanceConfig>(new ArticleClearanceConfig());
      }
      return clearanceConfig;
   }

   public ArticleClearanceConfig getClearanceConfig()
   {
      return clearanceConfigProperty().get();
   }

   public final void setClearanceConfig(ArticleClearanceConfig clearanceConfig)
   {
      if (clearanceConfig == null)
      {
         clearanceConfig = new ArticleClearanceConfig();
      }
      PropertyReader.copy(clearanceConfig, getClearanceConfig());
      clearanceConfigProperty().setValue(ObjectUtils.clone(getClearanceConfig()));
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
      Article other = (Article) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "articleName", "pic");
   }

   public void cleanIds()
   {
      id = null;
      version = 0;
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      Article e = new Article();
      e.id = id;
      e.version = version;

      e.articleName = articleName;
      e.pic = pic;
      e.manufacturer = manufacturer;
      e.active = active;
      e.authorizedSale = authorizedSale;
      e.maxStockQty = maxStockQty;
      e.qtyInStock = qtyInStock;
      e.pppu = pppu;
      e.sppu = sppu;
      e.maxDiscountRate = maxDiscountRate;
      e.totalStockPrice = totalStockPrice;
      e.lastStockEntry = lastStockEntry;
      e.lastOutOfStock = lastOutOfStock;
      e.recordingDate = recordingDate;
      e.section = section;
      e.family = family;
      e.defaultSalesMargin = defaultSalesMargin;
      e.packagingMode = packagingMode;
      e.agency = agency;
      e.clearanceConfig = clearanceConfig;
      return e;
   }
}