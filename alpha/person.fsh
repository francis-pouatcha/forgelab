
set ACCEPT_DEFAULTS true;

new-project --named person --topLevelPackage org.adorsys.person --finalName person;

as7 setup;
persistence setup --provider HIBERNATE --container JBOSS_AS7;

validation setup;

description setup;

set ACCEPT_DEFAULTS false;

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
