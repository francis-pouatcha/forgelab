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

@Entity
@Description("Currency_description")
@ToStringField("name")
@ListField("name")
public class Currency implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("Currency_name_description")
   @NotNull(message = "Currency_name_NotNull_validation")
   private String name;

   @Column
   @Description("Currency_shortName_description")
   @NotNull(message = "Currency_shortName_NotNull_validation")
   private String shortName;

   @Column
   @Description("Currency_cfaEquivalent_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal cfaEquivalent;

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
         return id.equals(((Currency) that).id);
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

   public String getShortName()
   {
      return this.shortName;
   }

   public void setShortName(final String shortName)
   {
      this.shortName = shortName;
   }

   public BigDecimal getCfaEquivalent()
   {
      return this.cfaEquivalent;
   }

   public void setCfaEquivalent(final BigDecimal cfaEquivalent)
   {
      this.cfaEquivalent = cfaEquivalent;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (name != null && !name.trim().isEmpty())
         result += "name: " + name;
      if (shortName != null && !shortName.trim().isEmpty())
         result += ", shortName: " + shortName;
      return result;
   }
}