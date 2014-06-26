package org.adorsys.adpharma.client.jpa.clearanceconfig;

import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import javafx.beans.property.SimpleBooleanProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.apache.commons.lang3.ObjectUtils;
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.format.DateFormatPattern;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.DecimalMax;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("ClearanceConfig_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField({ "startDate", "endDate", "discountRate", "clearanceState" })
@ListField({ "startDate", "endDate", "discountRate", "clearanceState", "active" })
public class ClearanceConfig implements Cloneable
{

   private Long id;
   private int version;

   @Description("ClearanceConfig_active_description")
   private SimpleBooleanProperty active;
   @Description("ClearanceConfig_clearanceState_description")
   private SimpleObjectProperty<DocumentProcessingState> clearanceState;
   @Description("ClearanceConfig_discountRate_description")
   @NumberFormatType(NumberType.PERCENTAGE)
   private SimpleObjectProperty<BigDecimal> discountRate;
   @Description("ClearanceConfig_startDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy")
   private SimpleObjectProperty<Calendar> startDate;
   @Description("ClearanceConfig_endDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy")
   private SimpleObjectProperty<Calendar> endDate;

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

   @NotNull(message = "ClearanceConfig_discountRate_NotNull_validation")
   @DecimalMin(value = "1.0", message = "ClearanceConfig_discountRate_DecimalMin_validation")
   @DecimalMax(value = "100.0", message = "ClearanceConfig_discountRate_DecimalMax_validation")
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

   @NotNull(message = "ClearanceConfig_startDate_NotNull_validation")
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

   @NotNull(message = "ClearanceConfig_endDate_NotNull_validation")
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
      ClearanceConfig other = (ClearanceConfig) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "discountRate");
   }

   public void cleanIds()
   {
      id = null;
      version = 0;
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      ClearanceConfig e = new ClearanceConfig();
      e.id = id;
      e.version = version;

      e.active = active;
      e.clearanceState = clearanceState;
      e.discountRate = discountRate;
      e.startDate = startDate;
      e.endDate = endDate;
      return e;
   }
}