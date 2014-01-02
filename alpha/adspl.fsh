
set ACCEPT_DEFAULTS true;

new-project --named adspc --topLevelPackage org.adorsys.adspc --finalName adspc;

as7 setup;
persistence setup --provider HIBERNATE --container JBOSS_AS7;

validation setup;

description setup;

set ACCEPT_DEFAULTS false;

@/* Entity Site */;
entity --named Site --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Site" --text "The site of this pharmacy";
description add-class-description  --locale fr --title "Site" --text "Le site de cette pharmacie";

field string --named displayName;
description add-field-description --onProperty displayName --title "Name" --text "Nom du site" --locale fr;
description add-field-description --onProperty displayName --title "Nom" --text "The site name";
constraint NotNull --onProperty displayName;

field string --named address;
description add-field-description --onProperty address --title "Address" --text "The site address";
description add-field-description --onProperty address --title "Adresse" --text "Adresse du site" --locale fr;
constraint Size --onProperty address --max 256;

field string --named city;
description add-field-description --onProperty city --title "City" --text "The site city";
description add-field-description --onProperty city --title "Ville" --text "Ville du site" --locale fr;

field string --named country;
description add-field-description --onProperty country --title "Country" --text "The cite country";
description add-field-description --onProperty country --title "Pays" --text "Pays du site" --locale fr;

cd ~~;

