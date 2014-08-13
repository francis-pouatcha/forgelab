package org.adorsys.adpharma.client.jpa.accessroleenum;

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
   SALES,@Description("AccessRoleEnum_SUPER_SALLER_description")
   SUPER_SALLER,@Description("AccessRoleEnum_SALLE_BY_ARTICLENAME_description")
   SALLE_BY_ARTICLENAME
}