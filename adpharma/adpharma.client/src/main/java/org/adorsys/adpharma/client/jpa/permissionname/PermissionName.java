package org.adorsys.adpharma.client.jpa.permissionname;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.permissionactionenum.PermissionActionEnum;
import javafx.beans.property.SimpleObjectProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("PermissionName_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField({ "name", "action" })
@ListField({ "name", "action" })
public class PermissionName
{

   private Long id;
   private int version;

   @Description("PermissionName_name_description")
   private SimpleStringProperty name;
   @Description("PermissionName_action_description")
   private SimpleObjectProperty<PermissionActionEnum> action;

   public Long getId()
   {
      return id;
   }

   public final void setId(Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return version;
   }

   public final void setVersion(int version)
   {
      this.version = version;
   }

   public SimpleStringProperty nameProperty()
   {
      if (name == null)
      {
         name = new SimpleStringProperty();
      }
      return name;
   }

   @NotNull(message = "PermissionName_name_NotNull_validation")
   public String getName()
   {
      return nameProperty().get();
   }

   public final void setName(String name)
   {
      this.nameProperty().set(name);
   }

   public SimpleObjectProperty<PermissionActionEnum> actionProperty()
   {
      if (action == null)
      {
         action = new SimpleObjectProperty<PermissionActionEnum>();
      }
      return action;
   }

   @NotNull(message = "PermissionName_action_NotNull_validation")
   public PermissionActionEnum getAction()
   {
      return actionProperty().get();
   }

   public final void setAction(PermissionActionEnum action)
   {
      this.actionProperty().set(action);
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result
            + ((id == null) ? 0 : id.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      PermissionName other = (PermissionName) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "name", "action");
   }
}