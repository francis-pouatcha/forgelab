package org.adorsys.adpharma.client.jpa.supplier;

import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import javafx.beans.property.SimpleBooleanProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.Association;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfig;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("ClearanceConfig_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierClearanceConfig implements Association<Supplier, ClearanceConfig>
{

   private Long id;
   private int version;

   private SimpleBooleanProperty active;
   private SimpleObjectProperty<DocumentProcessingState> clearanceState;
   private SimpleObjectProperty<BigDecimal> discountRate;
   private SimpleObjectProperty<Calendar> startDate;
   private SimpleObjectProperty<Calendar> endDate;

   public SupplierClearanceConfig()
   {
   }

   public SupplierClearanceConfig(ClearanceConfig entity)
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

   public SimpleObjectProperty<DocumentProcessingState> clearanceStateProperty()
   {
      if (clearanceState == null)
      {
         clearanceState = new SimpleObjectProperty<DocumentProcessingState>();
      }
      return clearanceState;
   }

   public DocumentProcessingState getClearanceState()
   {
      return clearanceStateProperty().get();
   }

   public final void setClearanceState(DocumentProcessingState clearanceState)
   {
      this.clearanceStateProperty().set(clearanceState);
   }

   public SimpleObjectProperty<BigDecimal> discountRateProperty()
   {
      if (discountRate == null)
      {
         discountRate = new SimpleObjectProperty<BigDecimal>();
      }
      return discountRate;
   }

   public BigDecimal getDiscountRate()
   {
      return discountRateProperty().get();
   }

   public final void setDiscountRate(BigDecimal discountRate)
   {
      this.discountRateProperty().set(discountRate);
   }

   public SimpleObjectProperty<Calendar> startDateProperty()
   {
      if (startDate == null)
      {
         startDate = new SimpleObjectProperty<Calendar>();
      }
      return startDate;
   }

   public Calendar getStartDate()
   {
      return startDateProperty().get();
   }

   public final void setStartDate(Calendar startDate)
   {
      this.startDateProperty().set(startDate);
   }

   public SimpleObjectProperty<Calendar> endDateProperty()
   {
      if (endDate == null)
      {
         endDate = new SimpleObjectProperty<Calendar>();
      }
      return endDate;
   }

   public Calendar getEndDate()
   {
      return endDateProperty().get();
   }

   public final void setEndDate(Calendar endDate)
   {
      this.endDateProperty().set(endDate);
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
      SupplierClearanceConfig other = (SupplierClearanceConfig) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "startDate", "endDate", "discountRate", "clearanceState");
   }
}