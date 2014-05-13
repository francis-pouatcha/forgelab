package org.adorsys.adpharma.server.repo;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.utils.PropertyReader;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.Criteria;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
public abstract class AdpharmaEntityRepository<T, PK extends Serializable>    implements EntityRepository<T, PK>, CriteriaSupport<T>{
	
	public Criteria<T, T> criteriafindBy(T entity ,SingularAttribute<T, Object>[] attributes)
	{
		Criteria<T, T> criteria = criteria();
		for (SingularAttribute<T, Object> s : attributes) {
			criteria
			.eq(s, PropertyReader.getPropertyValue(entity, s.getName())) ;
		}
		return criteria;
	}


}
