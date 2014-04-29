package org.adorsys.adpharma.client.jpa.stockmovementterminal;

import java.util.Arrays;

import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.javaext.description.Description;

@Description("StockMovementTerminal_description")
public enum StockMovementTerminal
{
   @Description("StockMovementTerminal_WAREHOUSE_description")
   WAREHOUSE, @Description("StockMovementTerminal_SUPPLIER_description")
   SUPPLIER, @Description("StockMovementTerminal_CUSTOMER_description")
   CUSTOMER, @Description("StockMovementTerminal_TRASH_description")
   TRASH;
   
   public static StockMovementTerminal[] valuesWithNull(){
	   StockMovementTerminal[] values = StockMovementTerminal.values();
	   StockMovementTerminal[] copyOf = Arrays.copyOf(values, values.length+1);
		return copyOf;

	}
}