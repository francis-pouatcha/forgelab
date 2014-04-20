package org.adorsys.adpharma.client.jpa.warehousearticlelot;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.warehouse.WareHouse;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.Association;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Login_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class WareHouseArticleLotWareHouse implements Association<WareHouseArticleLot, WareHouse>, Cloneable
{

	  private Long id;
	   private int version;

	   private SimpleStringProperty name;

	   public WareHouseArticleLotWareHouse()
	   {
		  
	   }

	   public WareHouseArticleLotWareHouse(WareHouse entity)
	   {
	      PropertyReader.copy(entity, this);
	   }

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
	         name = new SimpleStringProperty("ALL WAREHOUSE");
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

	   @Override
	   public int hashCode()
	   {
	      final int prime = 31;
	      int result = 1;
	      result = prime * result
	            + ((id == null) ? 0 : id.hashCode());
	      return result;
	   }

	   //	@Override
	   //	public boolean equals(Object obj) {
	   //		if (this == obj)
	   //			return true;
	   //		if (obj == null)
	   //			return false;
	   //		if (getClass() != obj.getClass())
	   //			return false;
	   //		ArticleAgency other = (ArticleAgency) obj;
	   //      if(id==other.id) return true;
	   //      if (id== null) return other.id==null;
	   //      return id.equals(other.id);
	   //	}

	   public String toString()
	   {
	      return PropertyReader.buildToString(this, "name");
	   }

	   @Override
	   public Object clone() throws CloneNotSupportedException
	   {
		   WareHouseArticleLotWareHouse a = new WareHouseArticleLotWareHouse();
	      a.id = id;
	      a.version = version;
	      a.name = name;
	      return a;
	   }

}
