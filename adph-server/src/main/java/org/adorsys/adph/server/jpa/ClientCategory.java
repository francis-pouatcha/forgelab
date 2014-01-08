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
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.ClientCategory.description")
@Audited
@XmlRootElement
public class ClientCategory implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClientCategory.categoryNumber.description")
   private String categoryNumber;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClientCategory.name.description")
   private String name;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClientCategory.note.description")
   @Size(max = 256)
   private String note;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClientCategory.discountRate.description")
   private BigDecimal discountRate;

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
         return id.equals(((ClientCategory) that).id);
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

   public String getCategoryNumber()
   {
      return this.categoryNumber;
   }

   public void setCategoryNumber(final String categoryNumber)
   {
      this.categoryNumber = categoryNumber;
   }

   public String getName()
   {
      return this.name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public String getNote()
   {
      return this.note;
   }

   public void setNote(final String note)
   {
      this.note = note;
   }

   public BigDecimal getDiscountRate()
   {
      return this.discountRate;
   }

   public void setDiscountRate(final BigDecimal discountRate)
   {
      this.discountRate = discountRate;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (categoryNumber != null && !categoryNumber.trim().isEmpty())
         result += "categoryNumber: " + categoryNumber;
      if (name != null && !name.trim().isEmpty())
         result += ", name: " + name;
      if (note != null && !note.trim().isEmpty())
         result += ", note: " + note;
      return result;
   }
}