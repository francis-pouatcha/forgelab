package org.adorsys.adpharma.server.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.jpa.ArticleLot_;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.Login_;
import org.adorsys.adpharma.server.repo.LoginRepository;
import org.apache.commons.lang3.RandomStringUtils;

@Stateless
public class LoginEJB
{

	@Inject
	private LoginRepository repository;

	@Inject
	private LoginRoleNameAssocMerger loginRoleNameAssocMerger;

	@Inject
	private AgencyMerger agencyMerger;

	public Login create(Login entity)
	{
		entity.setPassword("test");
		return repository.save(attach(entity));
	}

	public Login deleteById(Long id)
	{
		Login entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
		}
		return entity;
	}

	public Login update(Login entity)
	{
		return repository.save(attach(entity));
	}

	public Login findById(Long id)
	{
		return repository.findBy(id);
	}

	public List<Login> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<Login> findBy(Login entity, int start, int max, SingularAttribute<Login, ?>[] attributes)
	{
		Login login = attach(entity);
		return repository.findBy(login, start, max, attributes);
	}

	public Long countBy(Login entity, SingularAttribute<Login, ?>[] attributes)
	{
		Login login = attach(entity);
		return repository.count(login, attributes);
	}

	public List<Login> findByLike(Login entity, int start, int max, SingularAttribute<Login, ?>[] attributes)
	{
		Login login = attach(entity);
		return repository.findByLike(login, start, max, attributes);
	}

	public Long countByLike(Login entity, SingularAttribute<Login, ?>[] attributes)
	{
		Login login = attach(entity);
		return repository.countLike(login, attributes);
	}

	private Login attach(Login entity)
	{
		if (entity == null)
			return null;

		// aggregated
		entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

		// aggregated collection
		loginRoleNameAssocMerger.bindAggregated(entity.getRoleNames());

		return entity;
	}

	public Login resetUserSalesKey(Login login){
		Login original = findById(login.getId());
		String newSalesKey = newSalesKey();
		original.setSaleKey(newSalesKey);
		return update(original) ;
	}


	@SuppressWarnings("unchecked")
	private String newSalesKey(){
		String newSalesKey = RandomStringUtils.randomNumeric(4);
		Login login = new Login();
		login.setSaleKey(newSalesKey);
		List<Login> findBy = findBy(login, 0, 1,  new SingularAttribute[]{Login_.saleKey});
		if(findBy.isEmpty()){
			return newSalesKey;
		}else {
			return newSalesKey() ;
		}
	}
}
