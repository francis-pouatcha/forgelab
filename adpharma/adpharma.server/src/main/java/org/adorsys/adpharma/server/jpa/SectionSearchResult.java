package org.adorsys.adpharma.server.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SectionSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<Section> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SectionSearchInput searchInput;

   public SectionSearchResult()
   {
      super();
   }

   public SectionSearchResult(Long count, List<Section> resultList,
         SectionSearchInput searchInput)
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

   public List<Section> getResultList()
   {
      return resultList;
   }

   public SectionSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<Section> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SectionSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
