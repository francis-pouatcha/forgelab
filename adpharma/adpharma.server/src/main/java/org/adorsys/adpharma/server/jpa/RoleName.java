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
import org.adorsys.javaext.admin.RoleTable;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;
import org.adorsys.javaext.relation.Relationship;
import org.adorsys.javaext.relation.RelationshipEnd;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;

@Entity
@Description("RoleName_description")
@RoleTable(enumClass = "AccessRoleEnum.class", roleNameField = "name")
@ToStringField("name")
@ListField("name")
public class RoleName implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("RoleName_name_description")
   private String name;

   @OneToMany(mappedBy = "source", targetEntity = RoleNamePermissionNameAssoc.class)
   @Relationship(end = RelationshipEnd.SOURCE, sourceEntity = RoleName.class, targetEntity = PermissionName.class, sourceQualifier = "permissions")
   @Description("RoleName_permissions_description")
   @Association(associationType = AssociationType.AGGREGATION, targetEntity = PermissionName.class, selectionMode = SelectionMode.TABLE)
   private Set<RoleNamePermissionNameAssoc> permissions = new HashSet<RoleNamePermissionNameAssoc>();

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
         return id.equals(((RoleName) that).id);
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

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (name != null && !name.trim().isEmpty())
         result += "name: " + name;
      return result;
   }

   public Set<RoleNamePermissionNameAssoc> getPermissions()
   {
      return this.permissions;
   }

   public void setPermissions(final Set<RoleNamePermissionNameAssoc> permissions)
   {
      this.permissions = permissions;
   }
}