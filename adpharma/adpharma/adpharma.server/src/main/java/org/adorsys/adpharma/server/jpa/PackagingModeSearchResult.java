package org.adorsys.adpharma.server.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PackagingModeSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<PackagingMode> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private PackagingModeSearchInput searchInput;

   public PackagingModeSearchResult()
   {
      super();
   }

   public PackagingModeSearchResult(Long count, List<PackagingMode> resultList,
         PackagingModeSearchInput searchInput)
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

   public List<PackagingMode> getResultList()
   {
      return resultList;
   }

   public PackagingModeSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<PackagingMode> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(PackagingModeSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
