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
	   SUPER_SALLER_PERM,@Description("AccessRoleEnum_SUPER_SALLER_PERM_description")
	   SALLE_BY_ARTICLENAME_PERM
	   ,@Description("AccessRoleEnum_SALLE_BY_ARTICLENAME_PERM_description")
	   ARTICLE_LOT_PERM
	   ,@Description("AccessRoleEnum_INVENTORY_PERM_description")
	   INVENTORY_PERM,
	   @Description("AccessRoleEnum_PUSH_ARTICLE_OUT_PERM_description")
	   PUSH_ARTICLE_OUT_PERM,
	   @Description("AccessRoleEnum_RETURN_SALES_PERM_description")
	   RETURN_SALES_PERM,
	   @Description("AccessRoleEnum_DEBTSTATEMENT_PERM_description")
	   DEBTSTATEMENT_PERM,
	   @Description("AccessRoleEnum_CASHDRAWER_REPPORT_PERM_description")
	   CASHDRAWER_REPPORT_PERM,
	   @Description("AccessRoleEnum_TRANSFORM_ARTICLE_PERM_description")
	   TRANSFORM_ARTICLE_PERM,
	   @Description("AccessRoleEnum_PERIODICAL_SALES_REPPORT_PERM_description")
	   PERIODICAL_SALES_REPPORT_PERM,
	   @Description("AccessRoleEnum_PERIODICAL_DELIVERY_REPPORT_PERM_description")
	   PERIODICAL_DELIVERY_REPPORT_PERM,
	   @Description("AccessRoleEnum_UPDATE_DELIVERY_HEAD_PERM_description")
	   UPDATE_DELIVERY_HEAD_PERM
	   
}