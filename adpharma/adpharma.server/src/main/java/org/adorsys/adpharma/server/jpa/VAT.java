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
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;

@Entity
@Description("VAT_description")
@ToStringField("name")
@ListField({ "name", "rate", "active" })
public class VAT implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("VAT_name_description")
   private String name;

   @Column
   @Description("VAT_rate_description")
   @NumberFormatType(NumberType.PERCENTAGE)
   private BigDecimal rate;

   @Column
   @Description("VAT_active_description")
   private Boolean active;

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
         return id.equals(((VAT) that).id);
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

   public String getName()
   {
      return this.name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public BigDecimal getRate()
   {
      return this.rate;
   }
   
   public static BigDecimal getRawRate(BigDecimal rate){
	   if(rate==null) return BigDecimal.ZERO;
	   return rate.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_EVEN);
   }

   public void setRate(final BigDecimal rate)
   {
      this.rate = rate;
   }

   public Boolean getActive()
   {
      return this.active;
   }

   public void setActive(final Boolean active)
   {
      this.active = active;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (name != null && !name.trim().isEmpty())
         result += "name: " + name;
      if (active != null)
         result += ", active: " + active;
      return result;
   }
}