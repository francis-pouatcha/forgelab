package org.adorsys.adpharma.client.jpa.rolenamepermissionnameassoc;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class RoleNamePermissionNameAssocSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<RoleNamePermissionNameAssoc> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private RoleNamePermissionNameAssocSearchInput searchInput;

   public RoleNamePermissionNameAssocSearchResult()
   {
      super();
   }

   public RoleNamePermissionNameAssocSearchResult(Long count, List<RoleNamePermissionNameAssoc> resultList,
         RoleNamePermissionNameAssocSearchInput searchInput)
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

   public List<RoleNamePermissionNameAssoc> getResultList()
   {
      return resultList;
   }

   public RoleNamePermissionNameAssocSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<RoleNamePermissionNameAssoc> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(RoleNamePermissionNameAssocSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
