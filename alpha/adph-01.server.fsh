
set ACCEPT_DEFAULTS true;

new-project --named adph.server --topLevelPackage org.adorsys.adph.server --finalName adph.server --projectFolder adph.server;

as7 setup;
persistence setup --provider HIBERNATE --container JBOSS_AS7;

validation setup;

description setup;

set ACCEPT_DEFAULTS false;

enum setup;

association setup ;

java new-enum-type --named Gender --package ~.jpa ;
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

field string --named zipCode;
description add-field-description --onProperty zipCode --title "Zip Code" --text "The zip code oif this address";
description add-field-description --onProperty zipCode --title "Code Postale" --text "Le code poastale de cette adresse" --locale fr;

field string --named city;
description add-field-description --onProperty city --title "City" --text "The city of this address";
description add-field-description --onProperty city --title "Ville" --text "La localite de cette adresse" --locale fr;

field string --named country;
description add-field-description --onProperty country --title "Country" --text "The zip code oif this address";
description add-field-description --onProperty country --title "Pays" --text "Le pays de cette adresse" --locale fr;

cd ~~;

@/* Entity RoleName */;
entity --named RoleName --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Roles" --text "Names of roles assignable to users";
description add-class-description  --locale fr --title "Roles" --text "Nom de role attribuable aux utilisateurs.";

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this role.";
description add-field-description --onProperty name --title "Intitule" --text "Intitule de ce role" --locale fr;
display add-toString-field --field name;

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

field string --named notes;
description add-field-description --onProperty notes --title "Notes" --text "The description of this person";
description add-field-description --onProperty notes --title "Notes" --text "La description de cette personne" --locale fr;
constraint Size --onProperty notes --max 256;

field custom --named gender --type ~.jpa.Gender;
description add-field-description --onProperty gender --title "Gender" --text "The gender of this person.";
description add-field-description --onProperty gender --title "Genre" --text "Le gnere de cette personne" --locale fr;
enum enumerated-field --onProperty gender ;
display add-list-field --field gender;

field oneToOne --named address --fieldType ~.jpa.Address --cascade ALL;
description add-field-description --onProperty address --title "Address" --text "The address of this person";
description add-field-description --onProperty address --title "Adresse" --text "Adresse de cette personne" --locale fr;
display add-list-field --field address.street;
display add-list-field --field address.city;
display add-list-field --field address.country;
association set-selection-mode --onProperty address --selectionMode NONE;
association display-field --field address.zipCode;
association display-field --field address.street;
association display-field --field address.city;
association display-field --field address.country;

field manyToOne --named roleNames --fieldType ~.jpa.RoleName;
description add-field-description --onProperty roleNames --title "Roles" --text "The names of roles assigned to this user.";
description add-field-description --onProperty roleNames --title "Roles" --text "Les nom de role attribués a de cet utilisateur" --locale fr;
display add-list-field --field roleNames.name;
association set-selection-mode --onProperty roleNames --selectionMode FORWARD ;

field number --named hourlyWage --type java.math.BigDecimal;
description add-field-description --onProperty hourlyWage --title "Hourly Wage" --text "The person s hourly wage";
description add-field-description --onProperty hourlyWage --title "Salaire Horaire" --text "Le salaire horaire de cette personne" --locale fr;
format add-number-type --onProperty hourlyWage --type CURRENCY;

field temporal --type TIMESTAMP --named birthDate; 
description add-field-description --onProperty birthDate --title "Birth Date" --text "Date of birth";
description add-field-description --onProperty birthDate --title "Date de Naissance" --text "Date de naissance." --locale fr;

field boolean --named active --primitive false ;
description add-field-description --onProperty active --title "Active" --text "The active of roles assigned";
description add-field-description --onProperty active --title "Active" --text "Les active de role attribués a de cet utilisateur" --locale fr;

cd ~~;


envers setup;

envers audit-package --jpaPackage src/main/java/org/adorsys/;

cd ~~;

repogen setup;

repogen new-repository --jpaPackage src/main/java/org/adorsys;

cd ~~;

reporest setup --activatorType APP_CLASS;

reporest endpoint-from-entity --jpaPackage src/main/java/org/adorsys;

cd ~~;

repotest setup;

repotest create-test --packages src/main/java/org/adorsys/adph/;

cd ~~;

mvn clean install -DskipTests;

cd ..;
