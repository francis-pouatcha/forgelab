
@/* Download and install jboss-eap-6.1 */;
@/* Install these plugins if not done yet. */;
@/* forge install-plugin arquillian */;
@/* forge install-plugin jboss-as-7 */;
@/* forge install-plugin angularjs */;


set ACCEPT_DEFAULTS true;

new-project --named tsheet --topLevelPackage org.adorsys.tsheet --finalName tsheet;

project add-dependency org.hibernate:hibernate-jpamodelgen:1.3.0.Final:provided;
project add-managed-dependency org.jboss.shrinkwrap.resolver:shrinkwrap-resolver-bom:2.0.1:import:pom;
project add-dependency org.jboss.shrinkwrap.resolver:shrinkwrap-resolver-depchain:2.0.1:test:pom;
project add-dependency org.apache.deltaspike.modules:deltaspike-data-module-api:0.5:compile;
project add-dependency org.apache.deltaspike.modules:deltaspike-data-module-impl:0.5:runtime;

as7 setup;
persistence setup --provider HIBERNATE --container JBOSS_AS7;
validation setup;
rest setup --activatorType APP_CLASS;

arquillian setup --containerType REMOTE --containerName JBOSS_AS_REMOTE_7.X;
arquillian configure-container --profile arq-jboss_as_remote_7.x;
project add-dependency junit:junit:4.11:test;

entity --named CompanyJPA --idStrategy AUTO --package org.adorsys.tsheet.jpa;

field string --named name;
constraint NotNull --onProperty name --message "companyJpa_company_name_null";
constraint Size --onProperty name --min 2 --message "companyJpa_company_name_size";

field string --named street;
constraint NotNull --onProperty street --message "companyJpa_company_street_null";
constraint Size --onProperty street --min 3 --message "companyJpa_company_street_size";

field string --named zip;
constraint NotNull --onProperty zip --message "companyJpa_company_zip_null";
constraint Size --onProperty zip --min 3 --message "companyJpa_company_zip_size";

field string --named country;
constraint NotNull --onProperty country --message "companyJpa_company_country_null";
constraint Size --onProperty country --min 3 --message "companyJpa_company_country_size";

field string --named city;
constraint NotNull --onProperty city --message "companyJpa_company_city_null";
constraint Size --onProperty city --min 3 --message "companyJpa_company_city_size";

field string --named email;
constraint NotNull --onProperty email --message "companyJpa_company_email_null";
constraint Size --onProperty email --min 6 --message "companyJpa_company_email_size";

field string --named fax;
constraint NotNull --onProperty fax --message "companyJpa_company_fax_null";
constraint Size --onProperty fax --min 6 --message "companyJpa_company_fax_size";

field string --named phone;
constraint NotNull --onProperty phone --message "companyJpa_company_phone_null";
constraint Size --onProperty phone --min 6 --message "companyJpa_company_phone_size";

field string --named companyId;
constraint Size --onProperty companyId --min 3 --max 3 --message "companyJpa_company_id_null";
constraint NotNull --onProperty companyId --message "companyJpa_company_id_size";

field string --named adviserName;
constraint NotNull --onProperty adviserName --message "companyJpa_company_adviderName_null";
constraint Size --onProperty adviserName --min 2 --message "companyJpa_company_adviderName_size";

field temporal --named lastlyModified --type TIMESTAMP;
field temporal --named created --type TIMESTAMP;

rest endpoint-from-entity --contentType application/json org.adorsys.tsheet.jpa.CompanyJPA.java --strategy JPA_ENTITY;

entity --named PersonJPA --idStrategy AUTO --package org.adorsys.tsheet.jpa;
field temporal --named lastlyModified --type TIMESTAMP;
field temporal --named created --type TIMESTAMP;

field string --named name;
constraint NotNull --onProperty name --message "companyJpa_company_name_null";
constraint Size --onProperty name --min 2 --message "companyJpa_company_name_size";

field string --named street;
constraint NotNull --onProperty street --message "companyJpa_company_street_null";
constraint Size --onProperty street --min 3 --message "companyJpa_company_street_size";

rest endpoint-from-entity --contentType application/json org.adorsys.tsheet.jpa.PersonJPA.java --strategy JPA_ENTITY;

cd ~~;
scaffold-x setup --scaffoldType angularjs --targetDir ajs;
scaffold-x from src/main/java/org/adorsys/tsheet/jpa/CompanyJPA.java --targetDir ajs --overwrite;

cd ~~;
scaffold-x from src/main/java/org/adorsys/tsheet/jpa/PersonJPA.java --targetDir ajs --overwrite;

cd ~~;
arquillian create-test --class org.adorsys.tsheet.rest.CompanyJPAEndpoint.java;

cd ~~;
mvn clean compile;

