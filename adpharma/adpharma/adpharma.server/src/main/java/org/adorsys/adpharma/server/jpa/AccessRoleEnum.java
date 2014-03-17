package org.adorsys.adpharma.server.jpa;

import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.admin.LoginRole;

@Description("AccessRoleEnum_description")
public enum AccessRoleEnum
{
   @Description("AccessRoleEnum_ADMIN_description")
   ADMIN, @Description("AccessRoleEnum_LOGIN_description")
   @LoginRole
   LOGIN, @Description("AccessRoleEnum_MANAGER_description")
   MANAGER, @Description("AccessRoleEnum_CASHIER_description")
   CASHIER, @Description("AccessRoleEnum_STOCKS_description")
   STOCKS, @Description("AccessRoleEnum_SALES_description")
   SALES
}