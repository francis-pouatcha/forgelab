package org.adorsys.adpharma.client.jpa.salesmargin;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class SalesMarginSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<SalesMargin> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SalesMarginSearchInput searchInput;

   public SalesMarginSearchResult()
   {
      super();
   }

   public SalesMarginSearchResult(Long count, List<SalesMargin> resultList,
         SalesMarginSearchInput searchInput)
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

   public List<SalesMargin> getResultList()
   {
      return resultList;
   }

   public SalesMarginSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<SalesMargin> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SalesMarginSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
