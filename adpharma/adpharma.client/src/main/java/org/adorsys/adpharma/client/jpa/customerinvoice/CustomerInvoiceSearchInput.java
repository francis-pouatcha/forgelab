package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;

/**
 * Holds an entity and corresponding field descriptions for a search by example
 * call.
 * 
 * @author francis pouatcha
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CustomerInvoiceSearchInput
{

   /**
    * The entity holding search inputs.
    */
   private CustomerInvoice entity = new CustomerInvoice();

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

   public CustomerInvoice getEntity()
   {
      return entity;
   }

   public void setEntity(CustomerInvoice entity)
   {
      this.entity = entity;
      if(this.entity==null) this.entity = new CustomerInvoice();
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
