package org.adorsys.adph.server.jpa;

import org.adorsys.javaext.description.Description;

@Description("org.adorsys.adph.server.jpa.SalesOrderType.description")
public enum SalesOrderType
{
   @Description("org.adorsys.adph.server.jpa.SalesOrderType.CASH_SALE.description")
   CASH_SALE, @Description("org.adorsys.adph.server.jpa.SalesOrderType.CREDIT_SALE.description")
   CREDIT_SALE, @Description("org.adorsys.adph.server.jpa.SalesOrderType.PROFORMAT_SALE.description")
   PROFORMAT_SALE
}