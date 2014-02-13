
set ACCEPT_DEFAULTS true;

new-project --named adph.server --topLevelPackage org.adorsys.adph.server --finalName adph.server --projectFolder adph.server;

as7 setup;
persistence setup --provider HIBERNATE --container JBOSS_AS7;

validation setup;

description setup;

set ACCEPT_DEFAULTS false;

enum setup;

repogen setup;

association setup;

java new-enum-type --named Gender --package ~.jpa;
enum add-enum-class-description --title "Gender" --text "The gender of  a person";
enum add-enum-class-description --locale fr --title "Genre" --text "Le genre d une personne";
java new-enum-const MALE;
enum add-enum-constant-description --onConstant MALE --title "Male" --text "Gender type male.";
enum add-enum-constant-description --locale fr --onConstant MALE --title "Masculin" --text "Le genre masculin.";
java new-enum-const FEMALE;
enum add-enum-constant-description --onConstant FEMALE  --title "Female" --text "Gender type female" ;
enum add-enum-constant-description --locale fr --onConstant FEMALE  --title "Feminin" --text "Le genre feminin" ;

@/* Entity Address */;
entity --named Address --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Address" --text "An address";
description add-class-description  --locale fr --title "Adresse" --text "Une adresse";

field string --named street;
description add-field-description --onProperty street --title "Street" --text "The name of the street";
description add-field-description --onProperty street --title "Rue" --text "Nom de la rue" --locale fr;
display add-toString-field --field street;
display add-list-field --field street;

field string --named zipCode;
description add-field-description --onProperty zipCode --title "Zip Code" --text "The zip code oif this address";
description add-field-description --onProperty zipCode --title "Code Postale" --text "Le code poastale de cette adresse" --locale fr;
display add-toString-field --field zipCode;
display add-list-field --field zipCode;

field string --named city;
description add-field-description --onProperty city --title "City" --text "The city of this address";
description add-field-description --onProperty city --title "Ville" --text "La localite de cette adresse" --locale fr;
display add-toString-field --field city;
display add-list-field --field city;

field string --named country;
description add-field-description --onProperty country --title "Country" --text "The zip code oif this address";
description add-field-description --onProperty country --title "Pays" --text "Le pays de cette adresse" --locale fr;
display add-toString-field --field country;
display add-list-field --field country;

cd ~~;

@/* Entity RoleName */;
entity --named RoleName --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Roles" --text "Names of roles assignable to users";
description add-class-description  --locale fr --title "Roles" --text "Nom de role attribuable aux utilisateurs.";

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this role.";
description add-field-description --onProperty name --title "Intitule" --text "Intitule de ce role" --locale fr;
display add-toString-field --field name;
display add-list-field --field name;

cd ~~;

@/* Entity Person */;
entity --named Person --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Person" --text "A person";
description add-class-description  --locale fr --title "Personne" --text "Une personne";

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of the person";
description add-field-description --onProperty name --title "Nom" --text "Nom de la personne" --locale fr;
constraint NotNull --onProperty name;
display add-list-field --field name;
display add-toString-field --field name;

field custom --named gender --type ~.jpa.Gender;
description add-field-description --onProperty gender --title "Gender" --text "The gender of this person.";
description add-field-description --onProperty gender --title "Genre" --text "Le genre de cette personne." --locale fr;
enum enumerated-field --onProperty gender ;
display add-list-field --field gender;
display add-toString-field --field gender;

field string --named notes;
description add-field-description --onProperty notes --title "Notes" --text "The description of this person";
description add-field-description --onProperty notes --title "Notes" --text "La description de cette personne" --locale fr;
constraint Size --onProperty notes --max 256;

field number --named hourlyWage --type java.math.BigDecimal;
description add-field-description --onProperty hourlyWage --title "Hourly Wage" --text "The person s hourly wage";
description add-field-description --onProperty hourlyWage --title "Salaire Horaire" --text "Le salaire horaire de cette personne" --locale fr;
format add-number-type --onProperty hourlyWage --type CURRENCY;
display add-list-field --field hourlyWage;

field temporal --type TIMESTAMP --named birthDate; 
description add-field-description --onProperty birthDate --title "Birth Date" --text "Date of birth";
description add-field-description --onProperty birthDate --title "Date de Naissance" --text "Date de naissance." --locale fr;
display add-list-field --field birthDate;
display add-toString-field --field birthDate;

field boolean --named active --primitive false;
description add-field-description --onProperty active --title "Active" --text "The active of roles assigned";
description add-field-description --onProperty active --title "Active" --text "Les active de role attribués a de cet utilisateur" --locale fr;

field  oneToMany --named superviseds --fieldType ~.jpa.Person --inverseFieldName supervisor;
description add-field-description --onProperty superviseds --title "Supervisor" --text "The supervisor of this person";
description add-field-description --onProperty superviseds --title "Superviseur" --text "Le superviseur de cette personne" --locale fr;
association set-type --onProperty superviseds --type AGGREGATION --targetEntity ~.jpa.Person.java;
association display-field --field superviseds.name;
association display-field --field superviseds.gender;
association display-field --field superviseds.birthDate;
association display-field --field superviseds.hourlyWage;

description add-field-description --onProperty supervisor --title "Supervisor" --text "The supervisor of this person";
description add-field-description --onProperty supervisor --title "Superviseur" --text "Le superviseur de cette personne" --locale fr;
association set-type --onProperty supervisor --type AGGREGATION --targetEntity ~.jpa.Person.java;
association set-selection-mode --onProperty supervisor --selectionMode TABLE;
association display-field --field supervisor.name;
association display-field --field supervisor.gender;
association display-field --field supervisor.birthDate;
association display-field --field supervisor.hourlyWage;

field oneToMany --named addresses --fieldType ~.jpa.Address --inverseFieldName person --cascade ALL;
description add-field-description --onProperty addresses --title "Addresses" --text "The addresses of this person";
description add-field-description --onProperty addresses --title "Adresses" --text "Les adresses de cette personne" --locale fr;
association set-type --onProperty addresses --type COMPOSITION --targetEntity ~.jpa.Address.java;
association set-selection-mode --onProperty addresses --selectionMode TABLE;
association display-field --field addresses.zipCode;
association display-field --field addresses.street;
association display-field --field addresses.city;
association display-field --field addresses.country;

cd ../Address.java;
description add-field-description --onProperty person --title "Person" --text "The person owning this address";
description add-field-description --onProperty person --title "Personne" --text "La personne proprietaire de cette addresse" --locale fr;
association set-type --onProperty person --type COMPOSITION --targetEntity ~.jpa.Person.java;
association display-field --field person.name;
association display-field --field person.gender;
association display-field --field person.birthDate;
association display-field --field person.hourlyWage;


relationship add --sourceEntity ~.jpa.Person --sourceQualifier roleNames --targetEntity ~.jpa.RoleName;
cd ../Person.java;
description add-field-description --onProperty roleNames --title "Roles" --text "The names of roles assigned to this user.";
description add-field-description --onProperty roleNames --title "Roles" --text "Les nom de role attribués a de cet utilisateur" --locale fr;
association set-type --onProperty roleNames --type AGGREGATION --targetEntity ~.jpa.RoleName;

relationship add --sourceEntity ~.jpa.Person --sourceQualifier staffMembers --targetEntity ~.jpa.Person --targetQualifier managers;
cd ../Person.java;
description add-field-description --onProperty staffMembers --title "Staff Members" --text "The staff members of this person";
description add-field-description --onProperty staffMembers --title "Equipage" --text "Equipage de cette persone" --locale fr;
association set-type --onProperty staffMembers --type AGGREGATION --targetEntity ~.jpa.Person;
association display-field --field staffMembers.name;
association display-field --field staffMembers.gender;
association display-field --field staffMembers.birthDate;
association display-field --field staffMembers.hourlyWage;

cd ../Person.java;
description add-field-description --onProperty managers --title "Managers" --text "The managers of this person";
description add-field-description --onProperty managers --title "Manageurs" --text "Les manageurs de cette persone" --locale fr;
association set-type --onProperty managers --type AGGREGATION --targetEntity ~.jpa.Person;
association set-selection-mode --onProperty managers --selectionMode TABLE;
association display-field --field managers.name;
association display-field --field managers.gender;
association display-field --field managers.birthDate;
association display-field --field managers.hourlyWage;

cd ~~;

envers setup;

envers audit-package --jpaPackage src/main/java/org/adorsys/;

cd ~~;

repogen new-repository --jpaPackage src/main/java/org/adorsys;

cd ~~;

reporest setup --activatorType APP_CLASS;

reporest endpoint-from-entity --jpaPackage src/main/java/org/adorsys;

cd ~~;

repotest setup;

repotest create-test --packages src/main/java/org/adorsys/;

cd ~~;


