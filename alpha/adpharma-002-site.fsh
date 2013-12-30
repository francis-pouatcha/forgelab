
@/* ============================= */;
@/* Locations */;

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

field string --named siteNumber;
@/* add unique constraint generator here */;
description add-field-description --onProperty siteNumber --title "Number" --text "The site number";
description add-field-description --onProperty siteNumber --title "Numéro" --text "Numéro du site" --locale fr;

field string --named phone;
description add-field-description --onProperty phone --title "Phone" --text "The site phone number";
description add-field-description --onProperty phone --title "Téléphone" --text "Téléphone du site" --locale fr;

field string --named siteManager;
description add-field-description --onProperty siteManager --title "Manager" --text "The name of the site manager";
description add-field-description --onProperty siteManager --title "Manager" --text "Le nom du manager du site" --locale fr;

field string --named email;
description add-field-description --onProperty email --title "Email" --text "The email address of the site";
description add-field-description --onProperty email --title "Email" --text "Email du site" --locale fr;

field string --named siteInternet;
description add-field-description --onProperty siteInternet --title "Web Site" --text "The web site of the site";
description add-field-description --onProperty siteInternet --title "Sit Internet" --text "Site internet du site" --locale fr;

field string --named mobile;
description add-field-description --onProperty mobile --title "Mobile Phone" --text "The mobile phone of the site";
description add-field-description --onProperty mobile --title "Téléphone Mobile" --text "Téléphone Mobile du site" --locale fr;

field string --named fax;
description add-field-description --onProperty fax --title "Fax" --text "The fax number of the site";
description add-field-description --onProperty fax --title "Fax" --text "Fax du site" --locale fr;

field string --named registerNumber;
description add-field-description --onProperty registerNumber --title "Register Number" --text "The register number of the site";
description add-field-description --onProperty registerNumber --title "Numéro de Registre" --text "Numéro de registre du site" --locale fr;

field string --named messageTicker;
description add-field-description --onProperty messageTicker --title "Slogan Message" --text "The slogan message for this site";
description add-field-description --onProperty messageTicker --title "Message Slogan" --text "Slogan du site" --locale fr;

field long --named barCodePerLine;
description add-field-description --onProperty barCodePerLine --title "Bar Code" --text "The bar code";
description add-field-description --onProperty barCodePerLine --title "Code Bar" --text "Le code bar" --locale fr;

cd ~~;
