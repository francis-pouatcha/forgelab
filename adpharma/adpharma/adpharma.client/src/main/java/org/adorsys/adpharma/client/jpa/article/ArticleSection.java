package org.adorsys.adpharma.client.jpa.article;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.section.SectionAgency;
import javafx.beans.property.SimpleObjectProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.Association;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import org.adorsys.adpharma.client.jpa.section.Section;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Section_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleSection implements Association<Article, Section>
{

   private Long id;
   private int version;

   private SimpleStringProperty sectionCode;
   private SimpleStringProperty name;
   private SimpleStringProperty displayName;
   private SimpleStringProperty position;

   public ArticleSection()
   {
   }

   public ArticleSection(Section entity)
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

   public String getName()
   {
      return nameProperty().get();
   }

   public final void setName(String name)
   {
      this.nameProperty().set(name);
   }

   public SimpleStringProperty displayNameProperty()
   {
      if (displayName == null)
      {
         displayName = new SimpleStringProperty();
      }
      return displayName;
   }

   public String getDisplayName()
   {
      return displayNameProperty().get();
   }

   public final void setDisplayName(String displayName)
   {
      this.displayNameProperty().set(displayName);
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
      ArticleSection other = (ArticleSection) obj;
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