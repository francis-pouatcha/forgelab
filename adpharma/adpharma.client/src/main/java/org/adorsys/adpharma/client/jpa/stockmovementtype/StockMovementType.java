package org.adorsys.adpharma.client.jpa.stockmovementtype;

import java.util.Arrays;

import org.adorsys.javaext.description.Description;

@Description("StockMovementType_description")
public enum StockMovementType
{
   @Description("StockMovementType_OUT_description")
   OUT, @Description("StockMovementType_IN_description")
   IN;

 public static StockMovementType[] valuesWithNull(){
	 StockMovementType[] values = StockMovementType.values();
	 StockMovementType[] copyOf = Arrays.copyOf(values, values.length+1);
		return copyOf;

	}
}