package org.adorsys.adpharma.client.jpa.warehousearticlelot;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.adorsys.adpharma.client.jpa.documentstore.DocumentStore;

/**
 * Holds an entity and corresponding field descriptions for a search by example
 * call.
 * 
 * @author francis pouatcha
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class WareHouseArticleLotSearchInput
{

   /**
    * The entity holding search inputs.
    */
   private DocumentStore entity;

   /**
    * The start cursor
    */
   private int start = 0;

   /**
    * The max number of records to return.
    */
   private int max = 5;

   /**
    * The field names to be included in the search.
    */
   private List<String> fieldNames = new ArrayList<String>();

   public DocumentStore getEntity()
   {
      return entity;
   }

   public void setEntity(DocumentStore entity)
   {
      this.entity = entity;
   }

   public List<String> getFieldNames()
   {
      return fieldNames;
   }

   public void setFieldNames(List<String> fieldNames)
   {
      this.fieldNames = fieldNames;
   }

   public int getStart()
   {
      return start;
   }

   public void setStart(int start)
   {
      this.start = start;
   }

   public int getMax()
   {
      return max;
   }

   public void setMax(int max)
   {
      this.max = max;
   }
}
