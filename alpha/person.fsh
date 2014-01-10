
set ACCEPT_DEFAULTS true;

new-project --named person --topLevelPackage org.adorsys.person --finalName person;

as7 setup;
persistence setup --provider HIBERNATE --container JBOSS_AS7;

validation setup;

description setup;

set ACCEPT_DEFAULTS false;

enum setup;

java new-enum-type --named Gender --package ~.jpa ;
enum add-enum-class-description --title "Gender" --text "The gender of  a person";
enum add-enum-class-description --locale fr --title "Genre" --text "Le genre d une personne";
java new-enum-const MALE;
enum add-enum-constant-description --onConstant MALE --title "Male" --text "Gender type male.";
enum add-enum-constant-description --locale fr --onConstant MALE --title "Masculin" --text "Le genre masculin.";
java new-enum-const FEMALE;
enum add-enum-constant-description --onConstant FEMALE  --title "Female" --text "Gender type female" ;
enum add-enum-constant-description --locale fr --onConstant FEMALE  --title "Feminin" --text "Le genre feminin" ;


@/* Entity Site */;
entity --named Person --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Person" --text "A person";
description add-class-description  --locale fr --title "Personne" --text "Une personne";

field string --named name;
description add-field-description --onProperty name --title "Name" --text "Nom de la personne" --locale fr;
description add-field-description --onProperty name --title "Nom" --text "The name of the person";
constraint NotNull --onProperty name;

field string --named notes;
description add-field-description --onProperty notes --title "Notes" --text "The description of this person";
description add-field-description --onProperty notes --title "Notes" --text "La description de cette personne" --locale fr;
constraint Size --onProperty notes --max 256;

field custom --named gender --type ~.jpa.Gender;
description add-field-description --onProperty gender --title "Gender" --text "The gender of this person.";
description add-field-description --onProperty gender --title "Genre" --text "Le gnere de cette personne" --locale fr;
enum enumerated-field --onProperty gender ;

cd ~~;

envers setup;

envers audit-package --jpaPackage src/main/java/org/adorsys/person/jpa/;

cd ~~;

repogen setup;

repogen new-repository --jpaPackage src/main/java/org/adorsys/person/jpa/;

cd ~~;

reporest setup --activatorType APP_CLASS;

reporest endpoint-from-entity --jpaPackage src/main/java/org/adorsys/person/jpa/;

cd ~~;

repotest setup;

repotest create-test --packages src/main/java/org/adorsys/person/repo/;

repotest create-test --packages src/main/java/org/adorsys/person/rest/;

cd ~~;

mvn clean install -DskipTests;
