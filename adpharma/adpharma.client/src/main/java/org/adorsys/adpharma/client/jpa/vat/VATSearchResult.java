package org.adorsys.adpharma.client.jpa.vat;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class VATSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<VAT> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private VATSearchInput searchInput;

   public VATSearchResult()
   {
      super();
   }

   public VATSearchResult(Long count, List<VAT> resultList,
         VATSearchInput searchInput)
   {
      super();
      this.count = count;
      this.resultList = resultList;
      this.searchInput = searchInput;
   }

   public Long getCount()
   {
      return count;
   }

   public List<VAT> getResultList()
   {
      return resultList;
   }

   public VATSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<VAT> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(VATSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
