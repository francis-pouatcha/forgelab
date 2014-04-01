package org.adorsys.adpharma.client.jpa.rolename;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.permissionname.PermissionName;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.apache.commons.lang3.ObjectUtils;
import org.adorsys.javaext.relation.Relationship;
import org.adorsys.javaext.relation.RelationshipEnd;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("RoleName_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField("name")
@ListField("name")
public class RoleName implements Cloneable
{

   private Long id;
   private int version;

   @Description("RoleName_name_description")
   private SimpleStringProperty name;
   @Relationship(end = RelationshipEnd.SOURCE, sourceEntity = RoleName.class, targetEntity = PermissionName.class, sourceQualifier = "permissions")
   @Description("RoleName_permissions_description")
   @Association(associationType = AssociationType.AGGREGATION, targetEntity = PermissionName.class, selectionMode = SelectionMode.TABLE)
   private SimpleObjectProperty<ObservableList<PermissionName>> permissions;

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

   public String getName()
   {
      return nameProperty().get();
   }

   public final void setName(String name)
   {
      this.nameProperty().set(name);
   }

   public SimpleObjectProperty<ObservableList<PermissionName>> permissionsProperty()
   {
      if (permissions == null)
      {
         ObservableList<PermissionName> observableArrayList = FXCollections.observableArrayList();
         permissions = new SimpleObjectProperty<ObservableList<PermissionName>>(observableArrayList);
      }
      return permissions;
   }

   public List<PermissionName> getPermissions()
   {
      return new ArrayList<PermissionName>(permissionsProperty().get());
   }

   public final void setPermissions(List<PermissionName> permissions)
   {
      this.permissionsProperty().get().clear();
      if (permissions != null)
         this.permissionsProperty().get().addAll(permissions);
   }

   public final void addToPermissions(PermissionName entity)
   {
      this.permissionsProperty().get().add(entity);
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
      RoleName other = (RoleName) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "name");
   }

   public void cleanIds()
   {
      id = null;
      version = 0;
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      RoleName e = new RoleName();
      e.id = id;
      e.version = version;

      e.name = name;
      e.permissions = permissions;
      return e;
   }
}