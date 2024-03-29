package org.adorsys.adpharma.client.jpa.articleequivalence;

import java.util.Calendar;

import javafx.beans.property.SimpleObjectProperty;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.list.ListField;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.ObjectUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("ArticleEquivalence_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField({ "mainArticle.articleName", "equivalentArticle.articleName" })
@ListField({ "mainArticle.articleName", "equivalentArticle.articleName" })
public class ArticleEquivalence implements Cloneable
{

   private Long id;
   private int version;

   @Description("ArticleEquivalence_recordingDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> recordingDate;
   @Description("ArticleEquivalence_mainArticle_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
   private SimpleObjectProperty<ArticleEquivalenceMainArticle> mainArticle;
   @Description("ArticleEquivalence_equivalentArticle_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
   private SimpleObjectProperty<ArticleEquivalenceEquivalentArticle> equivalentArticle;

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

   public SimpleObjectProperty<Calendar> recordingDateProperty()
   {
      if (recordingDate == null)
      {
         recordingDate = new SimpleObjectProperty<Calendar>();
      }
      return recordingDate;
   }

   public Calendar getRecordingDate()
   {
      return recordingDateProperty().get();
   }

   public final void setRecordingDate(Calendar recordingDate)
   {
      this.recordingDateProperty().set(recordingDate);
   }

   public SimpleObjectProperty<ArticleEquivalenceMainArticle> mainArticleProperty()
   {
      if (mainArticle == null)
      {
         mainArticle = new SimpleObjectProperty<ArticleEquivalenceMainArticle>(new ArticleEquivalenceMainArticle());
      }
      return mainArticle;
   }

   @NotNull(message = "ArticleEquivalence_mainArticle_NotNull_validation")
   public ArticleEquivalenceMainArticle getMainArticle()
   {
      return mainArticleProperty().get();
   }

   public final void setMainArticle(ArticleEquivalenceMainArticle mainArticle)
   {
      if (mainArticle == null)
      {
         mainArticle = new ArticleEquivalenceMainArticle();
      }
      PropertyReader.copy(mainArticle, getMainArticle());
      mainArticleProperty().setValue(ObjectUtils.clone(getMainArticle()));
   }

   public SimpleObjectProperty<ArticleEquivalenceEquivalentArticle> equivalentArticleProperty()
   {
      if (equivalentArticle == null)
      {
         equivalentArticle = new SimpleObjectProperty<ArticleEquivalenceEquivalentArticle>(new ArticleEquivalenceEquivalentArticle());
      }
      return equivalentArticle;
   }

   @NotNull(message = "ArticleEquivalence_equivalentArticle_NotNull_validation")
   public ArticleEquivalenceEquivalentArticle getEquivalentArticle()
   {
      return equivalentArticleProperty().get();
   }

   public final void setEquivalentArticle(ArticleEquivalenceEquivalentArticle equivalentArticle)
   {
      if (equivalentArticle == null)
      {
         equivalentArticle = new ArticleEquivalenceEquivalentArticle();
      }
      PropertyReader.copy(equivalentArticle, getEquivalentArticle());
      equivalentArticleProperty().setValue(ObjectUtils.clone(getEquivalentArticle()));
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
      ArticleEquivalence other = (ArticleEquivalence) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "articleName", "articleName");
   }

   public void cleanIds()
   {
      id = null;
      version = 0;
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      ArticleEquivalence e = new ArticleEquivalence();
      e.id = id;
      e.version = version;

      e.recordingDate = recordingDate;
      e.mainArticle = mainArticle;
      e.equivalentArticle = equivalentArticle;
      return e;
   }
}