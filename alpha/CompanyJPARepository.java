package org.adorsys.tsheet.jpa;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity=CompanyJPA.class)
public interface CompanyJPARepository extends EntityRepository<CompanyJPA, Long>{
}
