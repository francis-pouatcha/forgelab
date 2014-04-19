package org.adorsys.adpharma.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adpharma.server.jpa.WareHouseArticleLot;

@Repository(forEntity = WareHouseArticleLot.class)
public interface WareHouseArticleLotRepository extends EntityRepository<WareHouseArticleLot, Long>
{
}
