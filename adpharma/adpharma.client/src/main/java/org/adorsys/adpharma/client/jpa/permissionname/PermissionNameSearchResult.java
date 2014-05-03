package org.adorsys.adpharma.client.jpa.permissionname;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class PermissionNameSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<PermissionName> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private PermissionNameSearchInput searchInput;

   public PermissionNameSearchResult()
   {
      super();
   }

   public PermissionNameSearchResult(Long count, List<PermissionName> resultList,
         PermissionNameSearchInput searchInput)
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

   public List<PermissionName> getResultList()
   {
      return resultList;
   }

   public PermissionNameSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<PermissionName> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(PermissionNameSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
