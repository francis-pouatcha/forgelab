package org.adorsys.adpharma.client.jpa.workgroup;

import org.adorsys.javaext.description.Description;

@Description("WorkGroup_description")
public enum WorkGroup
{
   @Description("WorkGroup_ADMIN_description")
   ADMIN, @Description("WorkGroup_LOGIN_description")
   LOGIN, @Description("WorkGroup_CASHIER_description")
   CASHIER, @Description("WorkGroup_STOCKS_description")
   STOCKS, @Description("WorkGroup_MANAGER_description")
   MANAGER, @Description("WorkGroup_SALES_description")
   SALES
}