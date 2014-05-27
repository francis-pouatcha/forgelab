package org.adorsys.adpharma.server.repo;

import java.math.BigDecimal;

import javax.persistence.metamodel.SingularAttribute;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.Criteria;
import org.adorsys.adpharma.server.jpa.ArticleLot;
import org.adorsys.adpharma.server.jpa.ArticleLot_;
import org.adorsys.adpharma.server.utils.PropertyReader;

@Repository(forEntity = ArticleLot.class)
public abstract class ArticleLotRepository extends AdpharmaEntityRepository<ArticleLot, Long>
{
	
}
