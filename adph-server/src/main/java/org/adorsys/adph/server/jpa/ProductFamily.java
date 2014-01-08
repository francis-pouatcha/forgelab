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
import javax.persistence.ManyToOne;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.ProductFamily.description")
@Audited
@XmlRootElement
public class ProductFamily implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("org.adorsys.adph.server.jpa.ProductFamily.code.description")
   private String code;

   @Column
   @Description("org.adorsys.adph.server.jpa.ProductFamily.name.description")
   private String name;

   @Column
   @Description("org.adorsys.adph.server.jpa.ProductFamily.shortName.description")
   private String shortName;

   @Column
   @Size(max = 256)
   @Description("org.adorsys.adph.server.jpa.ProductFamily.note.description")
   private String note;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.ProductFamily.parentFamilly.description")
   private ProductFamily parentFamilly;

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
         return id.equals(((ProductFamily) that).id);
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

   public String getNote()
   {
      return this.note;
   }

   public void setNote(final String note)
   {
      this.note = note;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (code != null && !code.trim().isEmpty())
         result += "code: " + code;
      if (name != null && !name.trim().isEmpty())
         result += ", name: " + name;
      if (shortName != null && !shortName.trim().isEmpty())
         result += ", shortName: " + shortName;
      if (note != null && !note.trim().isEmpty())
         result += ", note: " + note;
      return result;
   }

   public ProductFamily getParentFamilly()
   {
      return this.parentFamilly;
   }

   public void setParentFamilly(final ProductFamily parentFamilly)
   {
      this.parentFamilly = parentFamilly;
   }
}