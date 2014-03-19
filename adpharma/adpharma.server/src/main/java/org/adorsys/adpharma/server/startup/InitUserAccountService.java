package org.adorsys.adpharma.server.startup;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.jpa.AccessRoleEnum;
import org.adorsys.adpharma.server.jpa.Agency;
import org.adorsys.adpharma.server.jpa.Company;
import org.adorsys.adpharma.server.jpa.RoleName;
import org.adorsys.adpharma.server.jpa.RoleName_;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.LoginRoleNameAssoc;
import org.adorsys.adpharma.server.jpa.PermissionActionEnum;
import org.adorsys.adpharma.server.jpa.PermissionName;
import org.adorsys.adpharma.server.jpa.PermissionName_;
import org.adorsys.adpharma.server.jpa.RoleNamePermissionNameAssoc;
import org.adorsys.adpharma.server.rest.AgencyEJB;
import org.adorsys.adpharma.server.rest.CompanyEJB;
import org.adorsys.adpharma.server.rest.RoleNameEJB;
import org.adorsys.adpharma.server.rest.LoginEJB;
import org.adorsys.adpharma.server.rest.PermissionNameEJB;
import org.adorsys.adpharma.server.rest.LoginRoleNameAssocEJB;
import org.adorsys.adpharma.server.rest.RoleNamePermissionNameAssocEJB;
import org.apache.commons.lang3.StringUtils;

@Singleton
@Startup
public class InitUserAccountService
{

	@Inject
	private LoginEJB loginEJB;

	@Inject
	private RoleNameEJB roleEJB;

	@Inject
	private LoginRoleNameAssocEJB assocEJB;

	@Inject
	private PermissionNameEJB permissionEJB;

	@Inject 
	private CompanyEJB companyEJB;

	@Inject
	private AgencyEJB agencyEJB;

	Agency agency ;

	@Inject
	private RoleNamePermissionNameAssocEJB roleNamePermissionNameAssocEJB;

	@PostConstruct
	protected void postConstruct()
	{
		
		if(companyEJB.count()<1){
			Company company = companyEJB.create(new Company("Adorsys SA"));
			if(agencyEJB.count()<1){
				agency =agencyEJB.create(new Agency("Douala", company));
			}else {
				agency = agencyEJB.findById(Long.valueOf(1));
			}
		}

		Properties logins = new Properties();
		Properties roles = new Properties();
		Properties permissions = new Properties();
		InputStream loginStream = this.getClass().getClassLoader().getResourceAsStream("logins.properties");
		if (loginStream != null)
		{
			try
			{
				logins.load(loginStream);
				InputStream roleStream = this.getClass().getClassLoader().getResourceAsStream("roles.properties");
				if (roleStream != null)
				{
					roles.load(roleStream);
				}
				InputStream permissionStream = this.getClass().getClassLoader()
						.getResourceAsStream("permissions.properties");
				if (permissionStream != null)
				{
					permissions.load(permissionStream);
				}
			}
			catch (IOException e)
			{
				throw new IllegalStateException(e);
			}
		}

		createPermission("org.adorsys.adpharma.server.jpa.StockMovement");
		createPermission("org.adorsys.adpharma.server.jpa.CashDrawer");
		createPermission("org.adorsys.adpharma.server.jpa.Agency");
		createPermission("org.adorsys.adpharma.server.jpa.DebtStatement");
		createPermission("org.adorsys.adpharma.server.jpa.PaymentCustomerInvoiceAssoc");
		createPermission("org.adorsys.adpharma.server.jpa.Hospital");
		createPermission("org.adorsys.adpharma.server.jpa.ProductDetailConfig");
		createPermission("org.adorsys.adpharma.server.jpa.Company");
		createPermission("org.adorsys.adpharma.server.jpa.Login");
		createPermission("org.adorsys.adpharma.server.jpa.ArticleEquivalence");
		createPermission("org.adorsys.adpharma.server.jpa.SalesOrder");
		createPermission("org.adorsys.adpharma.server.jpa.ProductFamily");
		createPermission("org.adorsys.adpharma.server.jpa.Prescriber");
		createPermission("org.adorsys.adpharma.server.jpa.SupplierInvoiceItem");
		createPermission("org.adorsys.adpharma.server.jpa.DeliveryItem");
		createPermission("org.adorsys.adpharma.server.jpa.CustomerCategory");
		createPermission("org.adorsys.adpharma.server.jpa.Customer");
		createPermission("org.adorsys.adpharma.server.jpa.ProcurementOrderItem");
		createPermission("org.adorsys.adpharma.server.jpa.SalesOrderItem");
		createPermission("org.adorsys.adpharma.server.jpa.Supplier");
		createPermission("org.adorsys.adpharma.server.jpa.CustomerInvoiceItem");
		createPermission("org.adorsys.adpharma.server.jpa.PermissionName");
		createPermission("org.adorsys.adpharma.server.jpa.Employer");
		createPermission("org.adorsys.adpharma.server.jpa.PackagingMode");
		createPermission("org.adorsys.adpharma.server.jpa.ProcurementOrder");
		createPermission("org.adorsys.adpharma.server.jpa.PrescriptionBook");
		createPermission("org.adorsys.adpharma.server.jpa.ClearanceConfig");
		createPermission("org.adorsys.adpharma.server.jpa.Payment");
		createPermission("org.adorsys.adpharma.server.jpa.ArticleLot");
		createPermission("org.adorsys.adpharma.server.jpa.LoginRoleNameAssoc");
		createPermission("org.adorsys.adpharma.server.jpa.SupplierInvoice");
		createPermission("org.adorsys.adpharma.server.jpa.Article");
		createPermission("org.adorsys.adpharma.server.jpa.DebtStatementCustomerInvoiceAssoc");
		createPermission("org.adorsys.adpharma.server.jpa.RoleName");
		createPermission("org.adorsys.adpharma.server.jpa.SalesMargin");
		createPermission("org.adorsys.adpharma.server.jpa.Inventory");
		createPermission("org.adorsys.adpharma.server.jpa.RoleNamePermissionNameAssoc");
		createPermission("org.adorsys.adpharma.server.jpa.Currency");
		createPermission("org.adorsys.adpharma.server.jpa.Delivery");
		createPermission("org.adorsys.adpharma.server.jpa.CustomerInvoice");
		createPermission("org.adorsys.adpharma.server.jpa.Section");
		createPermission("org.adorsys.adpharma.server.jpa.VAT");
		createPermission("org.adorsys.adpharma.server.jpa.CustomerVoucher");
		createPermission("org.adorsys.adpharma.server.jpa.InventoryItem");
		createPermission("org.adorsys.adpharma.server.jpa.Insurrance");

		AccessRoleEnum[] roleEnums = AccessRoleEnum.values();
		for (AccessRoleEnum roleEnum : roleEnums)
		{
			RoleName role = new RoleName();
			role.setName(roleEnum.name());
			role = roleEJB.create(role);

			String permissionString = (String) permissions.get(role.getName());
			if (StringUtils.isBlank(permissionString))
				continue;
			String[] perms = permissionString.split(",");
			for (String perm : perms)
			{
				String permName = StringUtils.substringBefore(perm, "(");
				if (StringUtils.isBlank(permName))
					continue;
				String action = null;
				if (perm.contains("("))
				{
					action = StringUtils.substringAfter(perm, "(");
					action = StringUtils.substringBefore(action, ")");
				}

				addPermission(role.getName(), permName, action);
			}

		}

		Set<Entry<Object, Object>> loginSet = logins.entrySet();
		for (Entry<Object, Object> entry : loginSet)
		{
			String loginName = (String) entry.getKey();
			String password = (String) entry.getValue();
			Login login = new Login();
			login.setLoginName(loginName);
			login.setFullName(loginName);
			login.setPassword(password);
			login.setAgency(agency);
			login = loginEJB.create(login);
			String roleNames = roles.getProperty(loginName);
			if (StringUtils.isBlank(roleNames))
				continue;
			String[] split = roleNames.split(",");
			for (String roleName : split)
			{
				roleName = roleName.trim();
				RoleName searchRole = new RoleName();
				searchRole.setName(roleName);
				List<RoleName> found = roleEJB.findBy(searchRole, 0, 1, new SingularAttribute[] { RoleName_.name });
				if (found.isEmpty())
					throw new IllegalStateException("RoleName with name " + roleName + " not found.");
				RoleName role = found.iterator().next();

				LoginRoleNameAssoc roleAssoc = new LoginRoleNameAssoc();
				roleAssoc.setSource(login);
				roleAssoc.setTarget(role);
				roleAssoc.setSourceQualifier("roleNames");
				roleAssoc.setTargetQualifier("source");
				roleAssoc = assocEJB.create(roleAssoc);
			}
		}
	}

	private void createPermission(String entityFqn)
	{
		PermissionActionEnum[] values = PermissionActionEnum.values();
		for (PermissionActionEnum action : values)
		{
			createPermission(entityFqn, action);
			createPermission(entityFqn, action);
			createPermission(entityFqn, action);
		}
	}

	private void createPermission(String entityFqn, PermissionActionEnum action)
	{
		PermissionName entity = new PermissionName();
		entity.setAction(action);
		entity.setName(entityFqn);
		permissionEJB.create(entity);
	}

	private void addPermission(String roleName, String permName, String action)
	{
		PermissionActionEnum permissionActionEnum = PermissionActionEnum.valueOf(action);
		PermissionName entity = new PermissionName();
		entity.setAction(permissionActionEnum);
		entity.setName(permName);

		List<PermissionName> permFound = permissionEJB.findBy(entity, 0, 1, new SingularAttribute[] { PermissionName_.name, PermissionName_.action });
		if (permFound.isEmpty())
			throw new IllegalStateException("Permission with name " + permName + " and action " + action + " not found");
		PermissionName permissionName = permFound.iterator().next();

		RoleName searchRole = new RoleName();
		searchRole.setName(roleName);
		List<RoleName> roleFound = roleEJB.findBy(searchRole, 0, 1,
				new SingularAttribute[] { RoleName_.name });
		if (roleFound.isEmpty())
			throw new IllegalStateException("RoleName with name "
					+ roleName + " not found.");
		RoleName role = roleFound.iterator().next();
		RoleNamePermissionNameAssoc roleNamePermissionNameAssoc = new RoleNamePermissionNameAssoc();
		roleNamePermissionNameAssoc.setSource(role);
		roleNamePermissionNameAssoc.setTarget(permissionName);
		roleNamePermissionNameAssoc.setSourceQualifier("permissions");
		roleNamePermissionNameAssoc.setTargetQualifier("source");
		roleNamePermissionNameAssocEJB.create(roleNamePermissionNameAssoc);
	}

}
