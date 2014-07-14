package org.adorsys.adpharma.server.repo;

import org.adorsys.adpharma.server.jpa.VAT;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = VAT.class)
public interface VATRepository extends EntityRepository<VAT, Long>
{
}
