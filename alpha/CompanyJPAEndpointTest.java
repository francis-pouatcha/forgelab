package org.adorsys.tsheet.rest;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import org.adorsys.tsheet.jpa.CompanyJPA;
import org.adorsys.tsheet.jpa.CompanyJPARepository;
import org.adorsys.tsheet.jpa.CompanyJPA_;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CompanyJPAEndpointTest
{
   @Inject
   private CompanyJPAEndpoint companyjpaendpoint;
   
   @Inject
   private CompanyJPARepository companyJPARepository;

   @Deployment
   public static WebArchive createDeployment()
   {

	   File[] libs = Maven.resolver()
			   .loadPomFromFile("pom.xml").importRuntimeDependencies()
			   .resolve().withTransitivity().asFile();
	    
	   return ShrinkWrap.create(WebArchive.class, "test.war")
	   		.addPackage(CompanyJPA.class.getPackage())
	   		.addPackage(CompanyJPAEndpoint.class.getPackage())
	   		.addAsResource("META-INF/persistence.xml")
	   		.addAsResource("META-INF/validation.xml")
	   		.addAsDirectory("src/main/webapp")
	   		.addAsLibraries(libs)
	   		.merge(ShrinkWrap.create(GenericArchive.class)
	   				.as(ExplodedImporter.class)
	   				.importDirectory("src/main/webapp").as(GenericArchive.class), "/",Filters.includeAll());
   }

   @Test
   public void testIsDeployed()
   {
      Assert.assertNotNull(companyjpaendpoint);
   }

   @Test(expected=javax.ejb.EJBTransactionRolledbackException.class)
   public void testSaveCompanyJPAExpectException() throws Throwable
   {
      CompanyJPA entity = new CompanyJPA();
      companyjpaendpoint.create(entity);
   }

   @Test
   public void testSaveCompanyJPA() throws Throwable
   {
      CompanyJPA entity = new CompanyJPA();
      entity.setAdviserName("Francis Pouatcha");
      entity.setCity("Nuremberg");
      entity.setCompanyId("ADO");
      entity.setCountry("Germany");
      entity.setEmail("info@adorsys.com");
      entity.setFax("345324523452345");
      entity.setName("Adorsys GmbH");
      entity.setPhone("231423412341234");
      entity.setStreet("Bartholomaues str.");
      entity.setZip("90489");
      
      companyjpaendpoint.create(entity);
   }
   
   @Test
   public void testSelectLike(){
	      CompanyJPA entity = new CompanyJPA();
	      entity.setAdviserName("Francis Pouatcha");
	      entity.setCity("Nuremberg");
	      entity.setCompanyId("ADO");
	      entity.setCountry("Germany");
	      entity.setEmail("info@adorsys.com");
	      entity.setFax("345324523452345");
	      entity.setName("Adorsys GmbH");
	      entity.setPhone("231423412341234");
	      entity.setStreet("Bartholomaues str.");
	      entity.setZip("90489");

	      entity = new CompanyJPA();
	      entity.setAdviserName("Nadege Pouatcha");
	      entity.setCity("Dultuh");
	      entity.setCompanyId("PMO");
	      entity.setCountry("USA");
	      entity.setEmail("npa@adorsys.com");
	      entity.setFax("475674567456");
	      entity.setName("Pearly's mom LLC");
	      entity.setPhone("231423412341234");
	      entity.setStreet("1245 BUford drive");
	      entity.setZip("30092");
	      
	      companyjpaendpoint.create(entity);
	   
	   CompanyJPA example = new CompanyJPA();
	   example.setZip("90489");
	List<CompanyJPA> like = companyJPARepository.findByLike(example, CompanyJPA_.zip);
	Assert.assertEquals(1, like.size());
   }
}
