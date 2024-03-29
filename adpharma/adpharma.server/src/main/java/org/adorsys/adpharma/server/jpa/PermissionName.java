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
import org.adorsys.javaext.admin.PermissionTable;
import org.adorsys.adpharma.server.jpa.PermissionActionEnum;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;
import javax.validation.constraints.NotNull;
import javax.persistence.Enumerated;

@Entity
@Description("PermissionName_description")
@PermissionTable(actionEnumClass = PermissionActionEnum.class, permissionNameField = "name", permissionActionField = "action")
@ToStringField({ "name", "action" })
@ListField({ "name", "action" })
public class PermissionName implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("PermissionName_name_description")
   @NotNull(message = "PermissionName_name_NotNull_validation")
   private String name;

   @Column
   @Description("PermissionName_action_description")
   @Enumerated
   @NotNull(message = "PermissionName_action_NotNull_validation")
   private PermissionActionEnum action;

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
         return id.equals(((PermissionName) that).id);
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

   public PermissionActionEnum getAction()
   {
      return this.action;
   }

   public void setAction(final PermissionActionEnum action)
   {
      this.action = action;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (name != null && !name.trim().isEmpty())
         result += "name: " + name;
      return result;
   }
}