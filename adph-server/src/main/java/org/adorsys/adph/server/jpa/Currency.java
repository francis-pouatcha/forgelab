package org.adorsys.adph.server.jpa;

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
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.Currency.description")
@Audited
@XmlRootElement
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
   @Description("org.adorsys.adph.server.jpa.Currency.currencyNumber.description")
   private String currencyNumber;

   @Column
   @Description("org.adorsys.adph.server.jpa.Currency.name.description")
   @NotNull
   private String name;

   @Column
   @Description("org.adorsys.adph.server.jpa.Currency.shortName.description")
   @NotNull
   private String shortName;

   @Column
   @Description("org.adorsys.adph.server.jpa.Currency.cfaEquivalent.description")
   private String cfaEquivalent;

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

   public String getCurrencyNumber()
   {
      return this.currencyNumber;
   }

   public void setCurrencyNumber(final String currencyNumber)
   {
      this.currencyNumber = currencyNumber;
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

   public String getCfaEquivalent()
   {
      return this.cfaEquivalent;
   }

   public void setCfaEquivalent(final String cfaEquivalent)
   {
      this.cfaEquivalent = cfaEquivalent;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (currencyNumber != null && !currencyNumber.trim().isEmpty())
         result += "currencyNumber: " + currencyNumber;
      if (name != null && !name.trim().isEmpty())
         result += ", name: " + name;
      if (shortName != null && !shortName.trim().isEmpty())
         result += ", shortName: " + shortName;
      if (cfaEquivalent != null && !cfaEquivalent.trim().isEmpty())
         result += ", cfaEquivalent: " + cfaEquivalent;
      return result;
   }
}