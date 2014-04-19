package org.adorsys.adpharma.server.repo;

import org.adorsys.adpharma.server.jpa.ArticleLotSequence;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = ArticleLotSequence.class)
public interface ArticleLotSequenceRepository extends EntityRepository<ArticleLotSequence, Long>
{
}
