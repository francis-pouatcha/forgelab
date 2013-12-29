
@/* Download and install jboss-eap-6.1 */;
@/* Install these plugins if not done yet. */;
@/* forge install-plugin arquillian */;
@/* forge install-plugin jboss-as-7 */;
@/* forge install-plugin angularjs */;
@/* git clone https://github.com/clovisgakam/forge-envers-plugin.git */;
@/* forge source-plugin forge-envers-plugin */;
@/* git clone https://github.com/francis-pouatcha/forge-repository-plugin.git */;
@/* forge source-plugin forge-repository-plugin */;
@/* git clone https://github.com/francis-pouatcha/javaext.description.git */;
@/* cd javaext.description | mvn clean install | cd .. */;
@/* git clone https://github.com/francis-pouatcha/forge-description-plugin.git */;
@/* forge source-plugin forge-description-plugin */;
@/* run ../adpharma.fsh */;

set ACCEPT_DEFAULTS true;

new-project --named adph --topLevelPackage org.adorsys.adph --finalName adph;

project add-dependency org.hibernate:hibernate-jpamodelgen:1.3.0.Final:provided;
project add-managed-dependency org.jboss.shrinkwrap.resolver:shrinkwrap-resolver-bom:2.0.1:import:pom;
project add-dependency org.jboss.shrinkwrap.resolver:shrinkwrap-resolver-depchain:2.0.1:test:pom;

as7 setup;
persistence setup --provider HIBERNATE --container JBOSS_AS7;

validation setup;

description setup;

project add-dependency junit:junit:4.11:test;

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

@/* Entity Filiale */;
entity --named Agency --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Agency" --text "An agency of this pharmacie";
description add-class-description  --locale fr --title "Filiale" --text "une filiale de cette pharmacie";

field string --named agencyNumber;
description add-field-description --onProperty agencyNumber --title "Agency Number" --text "The number of this agency";
description add-field-description --onProperty agencyNumber --title "Numéro de la Filiale" --text "Numéro de la filiale" --locale fr;
@/* add unique constraint generator here */;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this agency";
description add-field-description --onProperty name --title "Nom" --text "Le nom de la filiale" --locale fr;

field string --named description;
description add-field-description --onProperty description --title "Description" --text "The description of this agency";
description add-field-description --onProperty description --title "Description" --text "Description de la filiale" --locale fr;

field boolean --named active;
description add-field-description --onProperty active --title "Active" --text "Says if this agency is active or not";
description add-field-description --onProperty active --title "Actif" --text "Indique si la filiale est active ou non" --locale fr;

@/* Entity Rayon */;
entity --named Section --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Section" --text "A section in the storage of this pharmacie.";
description add-class-description  --locale fr --title "Rayon" --text "un rayon dans le magasin de cette pharmacie";

field string --named sectionCode;
description add-field-description --onProperty sectionCode --title "Section Code" --text "The code of this section";
description add-field-description --onProperty sectionCode --title "Code Rayon" --text "Le code du rayon" --locale fr;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this section";
description add-field-description --onProperty name --title "Nom" --text "Le nom du rayon" --locale fr;
constraint NotNull --onProperty name;





















field string --named displayName;
description add-field-description --onProperty displayName --title "Display Name" --text "Field to display the name of this section";
description add-field-description --onProperty displayName --title "Nom Affiche" --text "Champ d'affichage du nom du rayon" --locale fr;
echo "1"
field string --named position;
description add-field-description --onProperty position --title "Position" --text "Code to identify the position of his section";
description add-field-description --onProperty position --title "Position" --text "Code permettant d'identifier la position du rayon." --locale fr;
echo "2"
field string --named geoCode;
description add-field-description --onProperty geoCode --title "Geographic Code" --text "Geographic code for the identification of the position of this article in the pharmacie";
description add-field-description --onProperty geoCode --title "Code Géographique" --text "Code géographique permettant d'identifier physiquement un produit dans la pharmacie" --locale fr;
echo "3"
field string --named note;
description add-field-description --onProperty note --title "Note" --text "Description of the section";
description add-field-description --onProperty note --title "Note" --text "Description du rayon" --locale fr;
constraint Size --onProperty note --max 256;
echo "4"
field manyToOne --named site --fieldType ~.jpa.Site;
constraint NotNull --onProperty site;
description add-field-description --onProperty site --title "Site" --text "Site in which the section is located";
description add-field-description --onProperty site --title "Site" --text "Site dans lequel le rayon se trouve." --locale fr;
echo "5"
@/* ================================== */;
@/* Product */;
echo "6"
@/* Entity ProductFamily */;
entity --named ProductFamily --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Product Family" --text "The product family";
description add-class-description  --locale fr --title "Famille Produit" --text "La famille produit";

field string --named code;
description add-field-description --onProperty code --title "Code" --text "The code of this product family";
description add-field-description --onProperty code --title "Code" --text "Le code de la famille produit" --locale fr;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this product family";
description add-field-description --onProperty name --title "Libelle" --text "Le nom de la famille produit" --locale fr;

field string --named shortName;
description add-field-description --onProperty shortName --title "Short Name" --text "The short name of this product family";
description add-field-description --onProperty shortName --title "Libelle Court" --text "Abréviation du nom de la famille produit." --locale fr;

field string --named note;
constraint Size --onProperty note --max 256;
description add-field-description --onProperty note --title "Note" --text "The description of this product family";
description add-field-description --onProperty note --title "Note" --text "La description de la famille produit." --locale fr;

