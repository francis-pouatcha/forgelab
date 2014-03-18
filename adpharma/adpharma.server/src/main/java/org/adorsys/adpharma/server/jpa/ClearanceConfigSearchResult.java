package org.adorsys.adpharma.server.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClearanceConfigSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<ClearanceConfig> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private ClearanceConfigSearchInput searchInput;

   public ClearanceConfigSearchResult()
   {
      super();
   }

   public ClearanceConfigSearchResult(Long count, List<ClearanceConfig> resultList,
         ClearanceConfigSearchInput searchInput)
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

   public List<ClearanceConfig> getResultList()
   {
      return resultList;
   }

   public ClearanceConfigSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<ClearanceConfig> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(ClearanceConfigSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
