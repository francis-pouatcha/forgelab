package org.adorsys.adpharma.client.jpa.section;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import javafx.beans.property.SimpleObjectProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Section_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField("sectionCode")
@ListField({ "sectionCode", "name", "position", "agency.name" })
public class Section
{

   private Long id;
   private int version;

   @Description("Section_sectionCode_description")
   private SimpleStringProperty sectionCode;
   @Description("Section_name_description")
   private SimpleStringProperty name;
   @Description("Section_position_description")
   private SimpleStringProperty position;
   @Description("Section_geoCode_description")
   private SimpleStringProperty geoCode;
   @Description("Section_description_description")
   private SimpleStringProperty description;
   @Description("Section_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   private SimpleObjectProperty<SectionAgency> agency;

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

   public SimpleStringProperty sectionCodeProperty()
   {
      if (sectionCode == null)
      {
         sectionCode = new SimpleStringProperty();
      }
      return sectionCode;
   }

   public String getSectionCode()
   {
      return sectionCodeProperty().get();
   }

   public final void setSectionCode(String sectionCode)
   {
      this.sectionCodeProperty().set(sectionCode);
   }

   public SimpleStringProperty nameProperty()
   {
      if (name == null)
      {
         name = new SimpleStringProperty();
      }
      return name;
   }

   @NotNull(message = "Section_name_NotNull_validation")
   public String getName()
   {
      return nameProperty().get();
   }

   public final void setName(String name)
   {
      this.nameProperty().set(name);
   }

   public SimpleStringProperty positionProperty()
   {
      if (position == null)
      {
         position = new SimpleStringProperty();
      }
      return position;
   }

   public String getPosition()
   {
      return positionProperty().get();
   }

   public final void setPosition(String position)
   {
      this.positionProperty().set(position);
   }

   public SimpleStringProperty geoCodeProperty()
   {
      if (geoCode == null)
      {
         geoCode = new SimpleStringProperty();
      }
      return geoCode;
   }

   public String getGeoCode()
   {
      return geoCodeProperty().get();
   }

   public final void setGeoCode(String geoCode)
   {
      this.geoCodeProperty().set(geoCode);
   }

   public SimpleStringProperty descriptionProperty()
   {
      if (description == null)
      {
         description = new SimpleStringProperty();
      }
      return description;
   }

   @Size(max = 256, message = "Section_description_Size_validation")
   public String getDescription()
   {
      return descriptionProperty().get();
   }

   public final void setDescription(String description)
   {
      this.descriptionProperty().set(description);
   }

   public SimpleObjectProperty<SectionAgency> agencyProperty()
   {
      if (agency == null)
      {
         agency = new SimpleObjectProperty<SectionAgency>(new SectionAgency());
      }
      return agency;
   }

   @NotNull(message = "Section_agency_NotNull_validation")
   public SectionAgency getAgency()
   {
      return agencyProperty().get();
   }

   public final void setAgency(SectionAgency agency)
   {
      if (agency == null)
      {
         agency = new SectionAgency();
      }
      PropertyReader.copy(agency, getAgency());
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
      Section other = (Section) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "sectionCode");
   }
}