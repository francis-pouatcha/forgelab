package org.adorsys.adpharma.server.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RoleNameSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<RoleName> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private RoleNameSearchInput searchInput;

   public RoleNameSearchResult()
   {
      super();
   }

   public RoleNameSearchResult(Long count, List<RoleName> resultList,
         RoleNameSearchInput searchInput)
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

   public List<RoleName> getResultList()
   {
      return resultList;
   }

   public RoleNameSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<RoleName> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(RoleNameSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
