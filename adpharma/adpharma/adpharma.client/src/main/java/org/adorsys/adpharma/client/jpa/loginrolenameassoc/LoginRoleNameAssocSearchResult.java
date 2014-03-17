package org.adorsys.adpharma.client.jpa.loginrolenameassoc;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class LoginRoleNameAssocSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<LoginRoleNameAssoc> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private LoginRoleNameAssocSearchInput searchInput;

   public LoginRoleNameAssocSearchResult()
   {
      super();
   }

   public LoginRoleNameAssocSearchResult(Long count, List<LoginRoleNameAssoc> resultList,
         LoginRoleNameAssocSearchInput searchInput)
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

   public List<LoginRoleNameAssoc> getResultList()
   {
      return resultList;
   }

   public LoginRoleNameAssocSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<LoginRoleNameAssoc> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(LoginRoleNameAssocSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
