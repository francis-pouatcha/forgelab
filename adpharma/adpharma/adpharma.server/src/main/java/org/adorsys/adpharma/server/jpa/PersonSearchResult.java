package org.adorsys.adpharma.server.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PersonSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<Person> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private PersonSearchInput searchInput;

   public PersonSearchResult()
   {
      super();
   }

   public PersonSearchResult(Long count, List<Person> resultList,
         PersonSearchInput searchInput)
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

   public List<Person> getResultList()
   {
      return resultList;
   }

   public PersonSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<Person> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(PersonSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
