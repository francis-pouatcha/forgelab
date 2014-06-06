package org.adorsys.adpharma.client.jpa.productdetailconfig;

import java.math.BigDecimal;
import java.util.Calendar;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adpharma.client.jpa.article.Article;
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
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("ProductDetailConfig_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField({ "source.articleName", "target.articleName" })
@ListField({ "source.articleName", "target.articleName", "targetQuantity",
      "salesPrice", "active" })
public class ProductDetailConfig implements Cloneable
{

   private Long id;
   private int version;

   @Description("ProductDetailConfig_active_description")
   private SimpleBooleanProperty active;
   @Description("ProductDetailConfig_targetQuantity_description")
   private SimpleObjectProperty<BigDecimal> targetQuantity;
   @Description("ProductDetailConfig_salesPrice_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> salesPrice;
   @Description("ProductDetailConfig_recordingDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> recordingDate;
   @Description("ProductDetailConfig_source_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
   private SimpleObjectProperty<ProductDetailConfigSource> source;
   @Description("ProductDetailConfig_target_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
   private SimpleObjectProperty<ProductDetailConfigTarget> target;

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

   public SimpleObjectProperty<BigDecimal> targetQuantityProperty()
   {
      if (targetQuantity == null)
      {
         targetQuantity = new SimpleObjectProperty<BigDecimal>();
      }
      return targetQuantity;
   }

   @NotNull(message = "ProductDetailConfig_targetQuantity_NotNull_validation")
   public BigDecimal getTargetQuantity()
   {
      return targetQuantityProperty().get();
   }

   public final void setTargetQuantity(BigDecimal targetQuantity)
   {
      this.targetQuantityProperty().set(targetQuantity);
   }

   public SimpleObjectProperty<BigDecimal> salesPriceProperty()
   {
      if (salesPrice == null)
      {
         salesPrice = new SimpleObjectProperty<BigDecimal>();
      }
      return salesPrice;
   }

   @NotNull(message = "ProductDetailConfig_salesPrice_NotNull_validation")
   public BigDecimal getSalesPrice()
   {
      return salesPriceProperty().get();
   }

   public final void setSalesPrice(BigDecimal salesPrice)
   {
      this.salesPriceProperty().set(salesPrice);
   }

   public SimpleObjectProperty<Calendar> recordingDateProperty()
   {
      if (recordingDate == null)
      {
         recordingDate = new SimpleObjectProperty<Calendar>();
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

   public SimpleObjectProperty<ProductDetailConfigSource> sourceProperty()
   {
      if (source == null)
      {
         source = new SimpleObjectProperty<ProductDetailConfigSource>(new ProductDetailConfigSource());
      }
      return source;
   }

   @NotNull(message = "ProductDetailConfig_source_NotNull_validation")
   public ProductDetailConfigSource getSource()
   {
      return sourceProperty().get();
   }

   public final void setSource(ProductDetailConfigSource source)
   {
      if (source == null)
      {
         source = new ProductDetailConfigSource();
      }
      PropertyReader.copy(source, getSource());
      sourceProperty().setValue(ObjectUtils.clone(getSource()));
   }

   public SimpleObjectProperty<ProductDetailConfigTarget> targetProperty()
   {
      if (target == null)
      {
         target = new SimpleObjectProperty<ProductDetailConfigTarget>(new ProductDetailConfigTarget());
      }
      return target;
   }

   @NotNull(message = "ProductDetailConfig_target_NotNull_validation")
   public ProductDetailConfigTarget getTarget()
   {
      return targetProperty().get();
   }

   public final void setTarget(ProductDetailConfigTarget target)
   {
      if (target == null)
      {
         target = new ProductDetailConfigTarget();
      }
      PropertyReader.copy(target, getTarget());
      targetProperty().setValue(ObjectUtils.clone(getTarget()));
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
      ProductDetailConfig other = (ProductDetailConfig) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "target");
   }

   public void cleanIds()
   {
      id = null;
      version = 0;
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      ProductDetailConfig e = new ProductDetailConfig();
      e.id = id;
      e.version = version;

      e.active = active;
      e.targetQuantity = targetQuantity;
      e.salesPrice = salesPrice;
      e.recordingDate = recordingDate;
      e.source = source;
      e.target = target;
      return e;
   }
}