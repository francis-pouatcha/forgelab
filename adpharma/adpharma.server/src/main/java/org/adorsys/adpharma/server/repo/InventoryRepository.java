package org.adorsys.adpharma.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adpharma.server.jpa.Inventory;

@Repository(forEntity = Inventory.class)
public interface InventoryRepository extends EntityRepository<Inventory, Long>
{
}
