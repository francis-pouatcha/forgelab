package org.adorsys.adpharma.client.jpa.procmtordertriggermode;

import org.adorsys.javaext.description.Description;

@Description("ProcmtOrderTriggerMode_description")
public enum ProcmtOrderTriggerMode
{
   @Description("ProcmtOrderTriggerMode_MANUAL_description")
   MANUAL, @Description("ProcmtOrderTriggerMode_STOCK_SHORTAGE_description")
   STOCK_SHORTAGE, @Description("ProcmtOrderTriggerMode_MOST_SOLD_description")
   MOST_SOLD
}