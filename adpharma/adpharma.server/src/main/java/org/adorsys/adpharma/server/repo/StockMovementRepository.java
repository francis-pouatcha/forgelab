package org.adorsys.adpharma.server.repo;

import org.adorsys.adpharma.server.jpa.StockMovement;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StockMovement.class)
public abstract class StockMovementRepository extends AdpharmaEntityRepository<StockMovement, Long>
{
}
