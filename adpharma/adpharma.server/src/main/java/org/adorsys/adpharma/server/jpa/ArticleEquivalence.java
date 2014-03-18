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
import org.adorsys.adpharma.server.jpa.Article;
import javax.persistence.ManyToOne;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;
import javax.validation.constraints.NotNull;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.adorsys.javaext.format.DateFormatPattern;

@Entity
@Description("ArticleEquivalence_description")
@ToStringField({ "mainArticle.articleName", "equivalentArticle.articleName" })
@ListField({ "mainArticle.articleName", "equivalentArticle.articleName" })
public class ArticleEquivalence implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @ManyToOne
   @Description("ArticleEquivalence_mainArticle_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
   @NotNull(message = "ArticleEquivalence_mainArticle_NotNull_validation")
   private Article mainArticle;

   @ManyToOne
   @Description("ArticleEquivalence_equivalentArticle_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
   @NotNull(message = "ArticleEquivalence_equivalentArticle_NotNull_validation")
   private Article equivalentArticle;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("ArticleEquivalence_recordingDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private Date recordingDate;

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
         return id.equals(((ArticleEquivalence) that).id);
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

   public Article getMainArticle()
   {
      return this.mainArticle;
   }

   public void setMainArticle(final Article mainArticle)
   {
      this.mainArticle = mainArticle;
   }

   public Article getEquivalentArticle()
   {
      return this.equivalentArticle;
   }

   public void setEquivalentArticle(final Article equivalentArticle)
   {
      this.equivalentArticle = equivalentArticle;
   }

   public Date getRecordingDate()
   {
      return this.recordingDate;
   }

   public void setRecordingDate(final Date recordingDate)
   {
      this.recordingDate = recordingDate;
   }
}