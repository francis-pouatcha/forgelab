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
import org.adorsys.adph.jpa.RoleName;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.ManyToMany;
import org.adorsys.adph.jpa.ProductFamily;
import javax.persistence.OneToMany;

@Entity
@Description("org.adorsys.adph.jpa.Agency.description")
public class Agency implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("org.adorsys.adph.jpa.Agency.agencyNumber.description")
   private String agencyNumber;

   @Column
   @Description("org.adorsys.adph.jpa.Agency.name.description")
   private String name;

   @Column
   @Description("org.adorsys.adph.jpa.Agency.description.description")
   private String description;

   @Column
   @Description("org.adorsys.adph.jpa.Agency.active.description")
   private boolean active;

   @ManyToMany
   private Set<RoleName> rolenames = new HashSet<RoleName>();

   @OneToMany
   private Set<ProductFamily> parentFamilly = new HashSet<ProductFamily>();

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
         return id.equals(((Agency) that).id);
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

   public String getAgencyNumber()
   {
      return this.agencyNumber;
   }

   public void setAgencyNumber(final String agencyNumber)
   {
      this.agencyNumber = agencyNumber;
   }

   public String getName()
   {
      return this.name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public String getDescription()
   {
      return this.description;
   }

   public void setDescription(final String description)
   {
      this.description = description;
   }

   public boolean getActive()
   {
      return this.active;
   }

   public void setActive(final boolean active)
   {
      this.active = active;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (agencyNumber != null && !agencyNumber.trim().isEmpty())
         result += "agencyNumber: " + agencyNumber;
      if (name != null && !name.trim().isEmpty())
         result += ", name: " + name;
      if (description != null && !description.trim().isEmpty())
         result += ", description: " + description;
      result += ", active: " + active;
      return result;
   }

   public Set<RoleName> getRolenames()
   {
      return this.rolenames;
   }

   public void setRolenames(final Set<RoleName> rolenames)
   {
      this.rolenames = rolenames;
   }

   public Set<ProductFamily> getParentFamilly()
   {
      return this.parentFamilly;
   }

   public void setParentFamilly(final Set<ProductFamily> parentFamilly)
   {
      this.parentFamilly = parentFamilly;
   }
}