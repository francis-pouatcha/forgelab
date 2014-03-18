package org.adorsys.adpharma.server.jpa;

import org.adorsys.javaext.description.Description;

@Description("SalesOrderType_description")
public enum SalesOrderType
{
   @Description("SalesOrderType_CASH_SALE_description")
   CASH_SALE, @Description("SalesOrderType_PARTIAL_SALE_description")
   PARTIAL_SALE, @Description("SalesOrderType_PROFORMA_SALE_description")
   PROFORMA_SALE
}