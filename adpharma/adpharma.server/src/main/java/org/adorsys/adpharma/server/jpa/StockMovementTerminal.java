package org.adorsys.adpharma.server.jpa;

import org.adorsys.javaext.description.Description;

@Description("StockMovementTerminal_description")
public enum StockMovementTerminal
{
   @Description("StockMovementTerminal_WAREHOUSE_description")
   WAREHOUSE, @Description("StockMovementTerminal_SUPPLIER_description")
   SUPPLIER, @Description("StockMovementTerminal_CUSTOMER_description")
   CUSTOMER, @Description("StockMovementTerminal_TRASH_description")
   TRASH
}