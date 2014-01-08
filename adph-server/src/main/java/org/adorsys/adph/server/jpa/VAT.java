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
import java.math.BigDecimal;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.VAT.description")
@Audited
@XmlRootElement
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
   @Description("org.adorsys.adph.server.jpa.VAT.code.description")
   private String code;

   @Column
   @Description("org.adorsys.adph.server.jpa.VAT.rate.description")
   private BigDecimal rate;

   @Column
   @Description("org.adorsys.adph.server.jpa.VAT.valid.description")
   private boolean valid;

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

   public String getCode()
   {
      return this.code;
   }

   public void setCode(final String code)
   {
      this.code = code;
   }

   public BigDecimal getRate()
   {
      return this.rate;
   }

   public void setRate(final BigDecimal rate)
   {
      this.rate = rate;
   }

   public boolean getValid()
   {
      return this.valid;
   }

   public void setValid(final boolean valid)
   {
      this.valid = valid;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (code != null && !code.trim().isEmpty())
         result += "code: " + code;
      result += ", valid: " + valid;
      return result;
   }
}