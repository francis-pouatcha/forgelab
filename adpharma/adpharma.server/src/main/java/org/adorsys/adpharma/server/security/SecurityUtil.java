package org.adorsys.adpharma.server.security;

import java.security.Principal;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import javax.security.auth.Subject;

import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.LoginSearchInput;
import org.adorsys.adpharma.server.jpa.LoginSearchResult;
import org.adorsys.adpharma.server.repo.LoginRepository;
import org.adorsys.adpharma.server.rest.LoginEJB;
import org.adorsys.adpharma.server.rest.LoginEndpoint;
@Stateless
public class SecurityUtil {

	@Resource
	private SessionContext sessionContext;
	
	@EJB
	LoginEndpoint loginEndpoint;

	public String getUserName(){
		Principal callerPrincipal = sessionContext.getCallerPrincipal();
		return callerPrincipal.getName();
	}

	public Login getConnectedUser(){
		LoginSearchInput searchInput = new LoginSearchInput();
		searchInput.getEntity().setLoginName(getUserName());
		List<Login> resultList = loginEndpoint.findBy(searchInput).getResultList();
		if(!resultList.isEmpty()) return resultList.iterator().next();
		return null;
	}
}