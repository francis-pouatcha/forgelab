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
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;
import java.math.BigDecimal;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import javax.validation.constraints.Size;

@Entity
@Description("CustomerCategory_description")
@ToStringField("name")
@ListField({ "name", "discountRate" })
public class CustomerCategory implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("CustomerCategory_name_description")
   @NotNull(message = "CustomerCategory_name_NotNull_validation")
   private String name;

   @Column
   @Description("CustomerCategory_discountRate_description")
   @NumberFormatType(NumberType.PERCENTAGE)
   private BigDecimal discountRate;

   @Column
   @Description("CustomerCategory_description_description")
   @Size(max = 256, message = "CustomerCategory_description_Size_validation")
   private String description;

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
         return id.equals(((CustomerCategory) that).id);
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

   public BigDecimal getDiscountRate()
   {
      return this.discountRate;
   }

   public void setDiscountRate(final BigDecimal discountRate)
   {
      this.discountRate = discountRate;
   }

   public String getDescription()
   {
      return this.description;
   }

   public void setDescription(final String description)
   {
      this.description = description;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (name != null && !name.trim().isEmpty())
         result += "name: " + name;
      if (description != null && !description.trim().isEmpty())
         result += ", description: " + description;
      return result;
   }
}