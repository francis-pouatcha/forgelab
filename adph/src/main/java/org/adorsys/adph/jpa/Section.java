package org.adorsys.adph.jpa;

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
import javax.validation.constraints.Size;
import org.adorsys.adph.jpa.Site;
import javax.persistence.ManyToOne;

@Entity
@Description("org.adorsys.adph.jpa.Section.description")
public class Section implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("org.adorsys.adph.jpa.Section.sectionCode.description")
   private String sectionCode;

   @Column
   @Description("org.adorsys.adph.jpa.Section.name.description")
   @NotNull
   private String name;

   @Column
   @Description("org.adorsys.adph.jpa.Section.displayName.description")
   private String displayName;

   @Column
   @Description("org.adorsys.adph.jpa.Section.position.description")
   private String position;

   @Column
   @Description("org.adorsys.adph.jpa.Section.geoCode.description")
   private String geoCode;

   @Column
   @Description("org.adorsys.adph.jpa.Section.note.description")
   @Size(max = 256)
   private String note;

   @ManyToOne
   @NotNull
   @Description("org.adorsys.adph.jpa.Section.site.description")
   private Site site;

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
         return id.equals(((Section) that).id);
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

   public String getSectionCode()
   {
      return this.sectionCode;
   }

   public void setSectionCode(final String sectionCode)
   {
      this.sectionCode = sectionCode;
   }

   public String getName()
   {
      return this.name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public String getDisplayName()
   {
      return this.displayName;
   }

   public void setDisplayName(final String displayName)
   {
      this.displayName = displayName;
   }

   public String getPosition()
   {
      return this.position;
   }

   public void setPosition(final String position)
   {
      this.position = position;
   }

   public String getGeoCode()
   {
      return this.geoCode;
   }

   public void setGeoCode(final String geoCode)
   {
      this.geoCode = geoCode;
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
      if (sectionCode != null && !sectionCode.trim().isEmpty())
         result += "sectionCode: " + sectionCode;
      if (name != null && !name.trim().isEmpty())
         result += ", name: " + name;
      if (displayName != null && !displayName.trim().isEmpty())
         result += ", displayName: " + displayName;
      if (position != null && !position.trim().isEmpty())
         result += ", position: " + position;
      if (geoCode != null && !geoCode.trim().isEmpty())
         result += ", geoCode: " + geoCode;
      if (note != null && !note.trim().isEmpty())
         result += ", note: " + note;
      return result;
   }

   public Site getSite()
   {
      return this.site;
   }

   public void setSite(final Site site)
   {
      this.site = site;
   }
}