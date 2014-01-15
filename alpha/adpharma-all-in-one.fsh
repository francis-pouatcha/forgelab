
@/* Download and install jboss-eap-6.1 */;
@/* Install these plugins if not done yet. */;
@/* forge install-plugin arquillian */;
@/* forge install-plugin jboss-as-7 */;
@/* forge install-plugin angularjs */;
@/* git clone https://github.com/clovisgakam/forge-envers-plugin.git */;
@/* forge source-plugin forge-envers-plugin */;
@/* git clone https://github.com/clovisgakam/forge-enumeration-plugin.git */;
@/* forge source-plugin forge-enumeration-plugin */;
@/* git clone https://github.com/francis-pouatcha/forge-repository-plugin.git */;
@/* forge source-plugin forge-repository-plugin */;
@/* git clone https://github.com/francis-pouatcha/javaext.description.git */;
@/* cd javaext.description | mvn clean install | cd .. */;
@/* git clone https://github.com/francis-pouatcha/forge-description-plugin.git */;
@/* forge source-plugin forge-description-plugin */;

set ACCEPT_DEFAULTS true;

new-project --named adph.server --topLevelPackage org.adorsys.adph.server --finalName adph.server;

as7 setup;
persistence setup --provider HIBERNATE --container JBOSS_AS7;

validation setup;

description setup;

enum setup ;

envers setup ;


@/* ============================= */;
@/* Locations */;

@/* Entity Site */;
entity --named Site --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Site" --text "The site of this pharmacy";
description add-class-description  --locale fr --title "Site" --text "Le site de cette pharmacie";

field string --named displayName;
description add-field-description --onProperty displayName --title "Name" --text "The site name";
description add-field-description --onProperty displayName --title "Nom" --text "Nom du site" --locale fr;
constraint NotNull --onProperty displayName;

field string --named address;
description add-field-description --onProperty address --title "Address" --text "The site address";
description add-field-description --onProperty address --title "Adresse" --text "Adresse du site" --locale fr;
constraint Size --onProperty address --max 256;

field string --named city;
description add-field-description --onProperty city --title "City" --text "The site city";
description add-field-description --onProperty city --title "Ville" --text "Ville du site" --locale fr;

field string --named country;
description add-field-description --onProperty country --title "Country" --text "The country site";
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

field long --named barCodePerLine --primitive false;
description add-field-description --onProperty barCodePerLine --title "Bar Code" --text "The bar code";
description add-field-description --onProperty barCodePerLine --title "Code Bar" --text "Le code bar" --locale fr;

cd ~~;


@/* Entity Gender */;
java new-enum-type --named Gender --package ~.jpa ;
enum add-enum-class-description --title "Gender" --text "The gender of a user.";
enum add-enum-class-description  --locale fr --title "Genre" --text "Le genre de cet utilisateur.";

java new-enum-const Masculin ;
java new-enum-const Feminin ;
java new-enum-const Enfant ;
java new-enum-const Docteur ;
java new-enum-const Neutre ;
enum generate-description-keys --locale fr ;
enum generate-description-keys ;

@/* Entity RoleName */;
entity --named RoleName --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Roles" --text "Names of roles assignable to users";
description add-class-description  --locale fr --title "Roles" --text "Nom de role attribuable aux utilisateurs.";

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this role.";
description add-field-description --onProperty name --title "Intitule" --text "Intitule de ce role" --locale fr;

field string --named resourceKey;
description add-field-description --onProperty resourceKey --title "Resource Key" --text "The key use to fetch the language resource from the database.";
description add-field-description --onProperty resourceKey --title "Clef Ressource" --text "La clef utilise pour lire les valeure literaires de la base de donnee." --locale fr;

@/* Entity PharmaUser */;
entity --named PharmaUser --package ~.jpa --idStrategy AUTO;
description add-class-description --title "User" --text "A user of this application";
description add-class-description  --locale fr --title "Utilisateur" --text "Un utilisateur de cette application.";

field string --named userNumber;
description add-field-description --onProperty userNumber --title "User Number" --text "The number of this user.";
description add-field-description --onProperty userNumber --title "Numéro de cet Utilisateur" --text "Le numéro de cet utilisateur." --locale fr;

field custom --named gender --type ~.jpa.Gender;
description add-field-description --onProperty gender --title "Gender" --text "The gender of this user";
description add-field-description --onProperty gender --title "Genre" --text "Le genre de cet utilisateur" --locale fr;
enum enumerated-field --onProperty gender ;

field string --named userName;
description add-field-description --onProperty userName --title "User Name" --text "The name used by the user to login.";
description add-field-description --onProperty userName --title "Nom de cet Utilisateur" --text "Le nom de connexion de cet utilisateur." --locale fr;
constraint NotNull --onProperty userName;
@/* Unique=true */;

field string --named firstName;
description add-field-description --onProperty firstName --title "First Name" --text "The first name of the user.";
description add-field-description --onProperty firstName --title "Prénom" --text "Le prénom de cet utilisateur." --locale fr;

field string --named lastName;
description add-field-description --onProperty lastName --title "Last Name" --text "The last name of the user.";
description add-field-description --onProperty lastName --title "Nom" --text "Le nom de cet utilisateur." --locale fr;
constraint NotNull --onProperty lastName;

field string --named fullName;
description add-field-description --onProperty fullName --title "Full Name" --text "The full name of the user.";
description add-field-description --onProperty fullName --title "Nom Complet" --text "Le nom complet dede cet ilisateur." --locale fr;
constraint NotNull --onProperty fullName;
@/* Nom complet de lutilisateur(nom+prenom) */;

field string --named password;
description add-field-description --onProperty password --title "Password" --text "The password of the user.";
description add-field-description --onProperty password --title "Mot de Passe" --text "Mot de passe de cet utilisateur." --locale fr;

field manyToMany --named roleNames --fieldType ~.jpa.RoleName;
description add-field-description --onProperty roleNames --title "Role Names" --text "The names of roles assigned to this user.";
description add-field-description --onProperty roleNames --title "Nom des Roles" --text "Les nom de role attribués a de cet utilisateur" --locale fr;

field string --named phoneNumber;
description add-field-description --onProperty phoneNumber --title "Phone Number" --text "The phone number of this user.";
description add-field-description --onProperty phoneNumber --title "Numéro de Telephone" --text "Numéro de téléphone de cet utilisateur." --locale fr;

field boolean --named disableLogin --primitive false;
@/*  default=true */;
description add-field-description --onProperty disableLogin --title "Disable Login" --text "Indicates whether the user login is disabled.";
description add-field-description --onProperty disableLogin --title "Login Desactivé" --text "Indique si le login de cet utilisateur est desactivé ou non." --locale fr;

field boolean --named accountLocked --primitive false;
@/*  default=false */;
description add-field-description --onProperty accountLocked --title "Disable Login" --text "Indicates whether the user account is locked.";
description add-field-description --onProperty accountLocked --title "Login Desactivé" --text "Indique si le compte(login+password) est bloqué ou pas." --locale fr;

field temporal --type TIMESTAMP --named credentialExpiration; 
@/* pattern= dd-MM-yyyy HH:mm  Default = +50 ans   */;
description add-field-description --onProperty credentialExpiration --title "Password Expiration" --text "Deta of expiration of the user password.";
description add-field-description --onProperty credentialExpiration --title "Expiration Mot de Passe" --text "Date expiration du certificat utilisateur." --locale fr;

field temporal --type TIMESTAMP --named accountExpiration; 
@/* pattern=dd-MM-yyyy HH:mm  Default = +50 ans */;
description add-field-description --onProperty accountExpiration --title "Last Out of Stock Date" --text "Date of expiration of the user account.";
description add-field-description --onProperty accountExpiration --title "Date Derniere Rupture de stock" --text "Date expiration du compte utilisateur" --locale fr;

field string --named passwordSalt;
description add-field-description --onProperty passwordSalt --title "Password Salt" --text "The salt used to encrypt the user password.";
description add-field-description --onProperty passwordSalt --title "Clé de hachage du Mot de Passe" --text "La clé de hachage pour encryptage des mots de passe en MD5" --locale fr;
@/* Default= ace6b4f53 */;

field string --named address;
description add-field-description --onProperty address --title "Address" --text "The address of this user.";
description add-field-description --onProperty address --title "Adresse" --text "Adresse de cet utilisateur" --locale fr;

field string --named email;
description add-field-description --onProperty email --title "Email" --text "The email address of this user..";
description add-field-description --onProperty email --title "Email" --text "Adresse email de cet utilisateur" --locale fr;

field manyToOne --named office --fieldType ~.jpa.Site;
description add-field-description --onProperty office --title "Office" --text "The site in which the user is registered.";
description add-field-description --onProperty office --title "Site" --text "Site dans lequel cet utilisateur est enregistré." --locale fr;

field number --named discountRate --type java.math.BigDecimal;
description add-field-description --onProperty discountRate --title "Discount Rate" --text "The discount rate this user can give to clients.";
description add-field-description --onProperty discountRate --title "Taux Remise" --text "Taux de remise que cet utilisateur peut accorder aux clients." --locale fr;
@/* Default= 15% */;

field string --named saleKey;
description add-field-description --onProperty saleKey --title "Sale Key" --text "The sales key of saller for a sales session open to all users. ";
description add-field-description --onProperty saleKey --title "Sale Key" --text "Clé de vente du vendeur pour la session vente ouverte à tous les utilisateurs." --locale fr;

cd ~~;


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

field boolean --named active --primitive false;
description add-field-description --onProperty active --title "Active" --text "Says if this agency is active or not";
description add-field-description --onProperty active --title "Actif" --text "Indique si la filiale est active ou non" --locale fr;


@/* Entity Currency */;
entity --named Currency --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Currency" --text "The currency";
description add-class-description  --locale fr --title "Devise" --text "La devise";

field string --named currencyNumber;
description add-field-description --onProperty currencyNumber --title "Currency Number" --text "The number of this Currency";
description add-field-description --onProperty currencyNumber --title "Numéro Devise" --text "Le numéro de cette devise" --locale fr;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this currency.";
description add-field-description --onProperty name --title "Libelle" --text "Le nom de cette devise." --locale fr;
constraint NotNull --onProperty name;

field string --named shortName;
description add-field-description --onProperty shortName --title "Short Name" --text "The short name of this currency.";
description add-field-description --onProperty shortName --title "Libelle Court" --text "Abréviation du nom de cette devise." --locale fr;
constraint NotNull --onProperty shortName;

field string --named cfaEquivalent;
description add-field-description --onProperty cfaEquivalent --title "CFA Equivalent" --text "Corresponding CFA value for conversions.";
description add-field-description --onProperty cfaEquivalent --title "Equivalent CFA" --text "Valeur equivalant cfa pour faire les conversions." --locale fr;

cd ~~ ;



@/* Entity Rayon */;
entity --named Section --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Section" --text "A section in the storage of this pharmacie.";
description add-class-description  --locale fr --title "Rayon" --text "Un rayon dans le magasin de cette pharmacie";

field string --named sectionCode;
description add-field-description --onProperty sectionCode --title "Section Code" --text "The code of this section";
description add-field-description --onProperty sectionCode --title "Code Rayon" --text "Le code du rayon" --locale fr;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this section";
description add-field-description --onProperty name --title "Nom" --text "Le nom du rayon" --locale fr;
constraint NotNull --onProperty name;

field string --named displayName;
description add-field-description --onProperty displayName --title "Display Name" --text "Field to display the name of this section";
description add-field-description --onProperty displayName --title "Nom Affiche" --text "Champ affichant le nom du rayon du nom du rayon" --locale fr;

field string --named position;
description add-field-description --onProperty position --title "Position" --text "Code to identify the position of his section";
description add-field-description --onProperty position --title "Position" --text "Code identifiant la position du rayon." --locale fr;

field string --named geoCode;
description add-field-description --onProperty geoCode --title "Geographic Code" --text "Geographic code for the identification of the position of this article in the pharmacie";
description add-field-description --onProperty geoCode --title "Code Géographique" --text "Code géographique identifiant physiquement un produit dans la pharmacie" --locale fr;

field string --named note;
description add-field-description --onProperty note --title "Note" --text "Description of the section";
description add-field-description --onProperty note --title "Note" --text "Description du rayon" --locale fr;
constraint Size --onProperty note --max 256;

field manyToOne --named site --fieldType ~.jpa.Site;
constraint NotNull --onProperty site;
description add-field-description --onProperty site --title "Site" --text "Site in which the section is located";
description add-field-description --onProperty site --title "Site" --text "Site dans lequel le rayon se trouve." --locale fr;

cd ~~ ;

@/* ================================== */;
@/* Product */;

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

field manyToOne --named parentFamilly --fieldType ~.jpa.ProductFamily.java 
description add-field-description --onProperty parentFamilly --title "Parent Familly" --text "The parent familly";
description add-field-description --onProperty parentFamilly --title "Note" --text "La famille parent du produit " --locale fr;

cd ~~ ;

@/* Entity VAT */;
entity --named VAT --package ~.jpa --idStrategy AUTO;
description add-class-description --title "VAT" --text "The value added tax";
description add-class-description  --locale fr --title "TVA" --text "Taxe sur la valeure ajoutée";

field string --named code;
description add-field-description --onProperty code --title "Code" --text "The code of this VAT";
description add-field-description --onProperty code --title "Code" --text "Le code de la TVA" --locale fr;

field number --named rate --type java.math.BigDecimal;
description add-field-description --onProperty rate --title "Rate" --text "The VAT rate";
description add-field-description --onProperty rate --title "Taux" --text "Taux de la TVA" --locale fr;

field boolean --named valid --primitive false;
description add-field-description --onProperty valid --title "Valid" --text "Says if this VAT is valide or not";
description add-field-description --onProperty valid --title "Valide" --text "Indique si la TVA est valide ou pas." --locale fr;

@/* Entity SalesMargin */;
entity --named SalesMargin --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Sales Margin" --text "The sales margin on this product.";
description add-class-description  --locale fr --title "Taux de Marge" --text "Le taux de marge du produit.";

field string --named code;
description add-field-description --onProperty code --title "Code" --text "The code of this margin";
description add-field-description --onProperty code --title "Code" --text "Numéro de la marge" --locale fr;

field number --named rate --type java.math.BigDecimal;
description add-field-description --onProperty rate --title "Rate" --text "The rate of this margin.";
description add-field-description --onProperty rate --title "Taux" --text "Taux de la marge." --locale fr;

field boolean --named valid --primitive false;
description add-field-description --onProperty valid --title "Valid" --text "Says if this margin rate is valide or not";
description add-field-description --onProperty valid --title "Valide" --text "Indique si ce taux de marge est valide ou pas." --locale fr;
cd ~~ ;


@/* Enum type DocumentState State */;
java new-enum-type --named DocumentProcessingState --package ~.jpa ;
enum add-enum-class-description --title "Document State" --text "The state of a documents.";
enum add-enum-class-description --title "Document State" --text "The state of a documents." --locale fr;
java new-enum-const SUSPENDED ;
java new-enum-const ONGOING ;
enum add-enum-constant-description --onConstant SUSPENDED --title "SUSPENDED" --text "The document is suspended.";
enum add-enum-constant-description --onConstant SUSPENDED --title "SUSPENDED" --text "Ce document est suspendu." --locale fr;
enum add-enum-constant-description --onConstant ONGOING  --title "ONGOING" --text "This document is ongoing." ;
enum add-enum-constant-description --onConstant ONGOING  --title "ONGOING" --text "Ce document est courant." --locale fr;


@/* Entity Clearance Config */;
entity --named ClearanceConfig --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Clearance Configuration" --text "Configuration for the clearance of a product.";
description add-class-description --title "Configuration Solde" --text "Permet de créer une configuration du solde pour un produit." --locale fr;

field temporal --type TIMESTAMP --named startDate; 
@/*  pattern=dd-MM-yyyy Date */;
description add-field-description --onProperty startDate --title "Clearance Start Date" --text "Start date for this clearance";
description add-field-description --onProperty startDate --title "Date Debut Solde" --text "Date de debut du solde" --locale fr;
constraint NotNull --onProperty startDate --message "Veuillez entrer la date de debut du solde";

field temporal --type TIMESTAMP --named endDate; 
@/*  pattern=dd-MM-yyyy Date*/;
description add-field-description --onProperty endDate --title "Clearance End Date" --text "End date for this clearance";
description add-field-description --onProperty endDate --title "Date Fin Solde" --text "Date de fin du solde." --locale fr;
constraint NotNull --onProperty endDate;

field number --named discountRate --type java.math.BigDecimal;
@/*   Default=5 */;
description add-field-description --onProperty discountRate --title "Discount Rate" --text "Discount rate for this clearance.";
description add-field-description --onProperty discountRate --title "Taux Remise" --text "Taux de remise pour ce solde." --locale fr;
constraint NotNull --onProperty discountRate --message "Veuillez entrer le taux de solde de ce produit";
constraint DecimalMin --onProperty discountRate --min 1.0 --message "Le taux de solde ne doit pas etre inferieur a 1";
constraint DecimalMax --onProperty discountRate --max 100.0 --message "Le taux de solde ne doit pas etre superieur a 100";

field custom --named clearanceState --type ~.jpa.DocumentProcessingState.java ;
@/* default EtatSolde.EN_COURS */;
description add-field-description --onProperty clearanceState --title "Clearance State" --text "The clearance state";
description add-field-description --onProperty clearanceState --title "État du Solde" --text "État du solde" --locale fr;
enum enumerated-field --onProperty clearanceState ;
field boolean --named active --primitive false;
description add-field-description --onProperty active --title "Active" --text "Says if this clearance configuration is active or not";
description add-field-description --onProperty active --title "Actif" --text "Indique si cette configuration solde est active ou non" --locale fr;
@/* default=Boolean.TRUE */;

cd ~~;


@/* Entity PackagingMode, ModeConditionnement */;
entity --named PackagingMode --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Packaging Mode" --text "THe product packaging mode";
description add-class-description --locale fr --title "Mode de Conditionement" --text "Le mode de conditionnement du produit";

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this packaging mode";
description add-field-description --onProperty name --title "Libelle" --text "Nom du mode de conditionnement" --locale fr;

field string --named shortName;
description add-field-description --onProperty shortName --title "Short Name" --text "The short name of this packaging mode";
description add-field-description --onProperty shortName --title "Libelle Court" --text "Abréviation du nom du mode de conditionnement du produit" --locale fr;

@/* Entity Article */;
entity --named Article --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Article" --text "An article or any oder drug sold in the pharmacy";
description add-class-description  --locale fr --title "Produit" --text "Un produit ou medicament en vente dans cette pharmacie";

field string --named articleNumber;
description add-field-description --onProperty articleNumber --title "Article Number" --text "The number of this product automatically generated by the system";
description add-field-description --onProperty articleNumber --title "Numéro du Produit" --text "Le numéro du produit genere automatiquement par le système" --locale fr;

field string --named articleName;
description add-field-description --onProperty articleName --title "Name" --text "The name of this article";
description add-field-description --onProperty articleName --title "Nom" --text "Le nom du produit" --locale fr;

field string --named manufacturer;
description add-field-description --onProperty manufacturer --title "Manufacturer name" --text "The name of this article";
description add-field-description --onProperty manufacturer --title "Fabricant" --text "Le nom du fabricant du produit" --locale fr;

field manyToOne --named section --fieldType ~.jpa.Section;
description add-field-description --onProperty section --title "Section" --text "The section in which the product is stored";
description add-field-description --onProperty section --title "Rayon" --text "Le rayon dans lequel le produit est classé." --locale fr;

field boolean --named active --primitive false;
description add-field-description --onProperty active --title "Active" --text "Says if this article is active or not";
description add-field-description --onProperty active --title "Actif" --text "Indique si le produit est active ou non" --locale fr;

field manyToOne --named family --fieldType ~.jpa.ProductFamily;
description add-field-description --onProperty family --title "Family" --text "Specifies the product family of this article.";
description add-field-description --onProperty family --title "Famille" --text "Spécifie la famille de produit à laquelle appartient le produit." --locale fr;

field long --named qtyInStock --primitive false;
description add-field-description --onProperty qtyInStock --title "Quantity in Stock" --text "The quantity of this article in stock.";
description add-field-description --onProperty qtyInStock --title "Quantité en Stock" --text "Quantité reelle de produits dans le stock." --locale fr;

field number --named pppu --type java.math.BigDecimal;
description add-field-description --onProperty pppu --title "Purchase Price per Unit" --text "Purchase price per unit.";
description add-field-description --onProperty pppu --title "Prix Unitaire Achat" --text "Prix achat unitaire du produit." --locale fr;

field number --named sppu --type java.math.BigDecimal;
description add-field-description --onProperty sppu --title "Sales Price per Unit" --text "Sales price per unit.";
description add-field-description --onProperty sppu --title "Prix de Vente Unitaire" --text "Prix de vente unitaire du produit" --locale fr;

field long --named maxQtyPerPO --primitive false;
description add-field-description --onProperty maxQtyPerPO --title "Max Quantity per PO" --text "Maximal quantity per purchase order";
description add-field-description --onProperty maxQtyPerPO --title "Quantite Maximale par Commande" --text "Quantite maximale de produits commandable" --locale fr;

field number --named maxDiscountRate --type java.math.BigDecimal;
@/* Default=5 */;
description add-field-description --onProperty maxDiscountRate --title "Max Discount Rate" --text "Maximal discount rate given to buyers of this product.";
description add-field-description --onProperty maxDiscountRate --title "Taux Maximal Remise" --text "Taux de remise max en % accordes aux utilisateurs sur le produit" --locale fr;

field number --named totalStockPrice --type java.math.BigDecimal; 
@/* Default=0, calcul=  Somme(prix vente  des lignes approvisionnement du produit*qte de chaque ligne)*/;
description add-field-description --onProperty totalStockPrice --title "Total Stock Price" --text "Total value of products in stock";
description add-field-description --onProperty totalStockPrice --title "Valeure Total du Stock" --text "Valeure totale des produits en stocks" --locale fr;

field temporal --type TIMESTAMP --named lastStockEntry; 
@/* Pattern= dd-MM-yyy */;
description add-field-description --onProperty lastStockEntry --title "Last Delivery Date" --text "Last purchase delivery date for this article";
description add-field-description --onProperty lastStockEntry --title "Date Derniere Livraison" --text "Date de la derniere livraison achat pour ce produit" --locale fr;

field temporal --type TIMESTAMP --named lastOutOfStock; 
@/*pattern= dd-MM-yyyy  (qtéStock=0) */;
description add-field-description --onProperty lastOutOfStock --title "Last Out of Stock Date" --text "Date of last out of stock for this article";
description add-field-description --onProperty lastOutOfStock --title "Date Derniere Rupture de stock" --text "Date de la derniere rupture de stock pour ce produit" --locale fr;

field string --named dosage; 
@/*La posologie du medicament  observation  MaxSize=256 */;
description add-field-description --onProperty dosage --title "Dosage" --text "The dosage of this medecine";
description add-field-description --onProperty dosage --title "Posologie" --text "La posologie du medicament" --locale fr;

field manyToOne --named vat --fieldType ~.jpa.VAT;
description add-field-description --onProperty vat --title "VAT" --text "The value added tax";
description add-field-description --onProperty vat --title "TVA" --text "La taxe sur la valeur ajoute" --locale fr;

field manyToOne --named salesMargin --fieldType ~.jpa.SalesMargin; 
description add-field-description --onProperty salesMargin --title "Sales Margin" --text "The sales margin on this product.";
description add-field-description --onProperty salesMargin --title "Taux de Marge" --text "Le taux de marge du produit." --locale fr;

field string --named pic; 
@/* Unique */;
description add-field-description --onProperty pic --title "Product Identification Code" --text "The standard product identification code of this product.";
description add-field-description --onProperty pic --title "Code Identifiant Prouit" --text "Le Code identifiant standard du produit." --locale fr;

field manyToOne --named packagingMode --fieldType ~.jpa.PackagingMode; 
description add-field-description --onProperty packagingMode --title "Packaging Mode" --text "THe product packaging mode";
description add-field-description --onProperty packagingMode --title "Mode de Conditionement" --text "Le mode de conditionnement du produit" --locale fr;

field boolean --named authorizedSale --primitive false; 
@/*  vente_autorisee  default=true  */;
description add-field-description --onProperty authorizedSale --title "Product Identification Code" --text "Allows to release a prouct for sale.";
description add-field-description --onProperty authorizedSale --title "Code Identifiant Prouit" --text "Autorise ou non un produit à la vente" --locale fr;

field boolean --named approvedOrder --primitive false; 
description add-field-description --onProperty approvedOrder --title "Approved Order" --text "Document if the next order of this product is approved.";
description add-field-description --onProperty approvedOrder --title "Commande Autorisée" --text "Autorise ou non le produit à la commande." --locale fr;

field long --named maxStockQty --primitive false;
description add-field-description --onProperty maxStockQty --title "Max Stock Quantity" --text "Sets the standard max stock quantity for this product.";
description add-field-description --onProperty maxStockQty --title "Quantité Plafond" --text "Permet de fixer le max de qté en stock de produit." --locale fr;

field manyToOne --named agency --fieldType ~.jpa.Agency;
description add-field-description --onProperty agency --title "Agency" --text "The agency hosting this product.";
description add-field-description --onProperty agency --title "Filiale" --text "La filiale à laquelle le produit appartient." --locale fr;

field manyToOne --named clearanceConfig --fieldType ~.jpa.ClearanceConfig;
description add-field-description --onProperty clearanceConfig --title "Clearance Configuration" --text "Configuration for the clearance of this product.";
description add-field-description --onProperty clearanceConfig --title "Configuration Solde" --text "Permet de créer une configuration du solde pour ce produit." --locale fr;

field string --named activeIngredients; 
constraint Size --onProperty activeIngredients --max 256;
description add-field-description --onProperty activeIngredients --title "Active Ingredients" --text "Describes the active ingredients of this drug.";
description add-field-description --onProperty activeIngredients --title "Principes Actifs" --text "Decrit les principes actifs de ce médicament." --locale fr;

field boolean --named mixedDrug --primitive false;
@/*   produit_compose  default=false  */;
description add-field-description --onProperty mixedDrug --title "Mixed Drug" --text "Indicates whether this product is mixed.";
description add-field-description --onProperty mixedDrug --title "Prouit Composé" --text "Précise si un produit est décomposable ou non." --locale fr;
cd ~~;


@/* Entity Supplier */;
entity --named Supplier --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Supplier" --text "The supplier";
description add-class-description  --locale fr --title "Fournisseur" --text "Le fournisseur";

field string --named supplierNumber;
description add-field-description --onProperty supplierNumber --title "Supplier Number" --text "The number identifying this supplier.";
description add-field-description --onProperty supplierNumber --title "Numéro du Fournisseur" --text "Le numéro identification de ce fournisseur." --locale fr;


field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of the supplier.";
description add-field-description --onProperty name --title "Nom" --text "Le nom du fournisseur." --locale fr;

field string --named shortName;
description add-field-description --onProperty shortName --title "Short Name" --text "The short name of the supplier";
description add-field-description --onProperty shortName --title "Libelle Court" --text "Abréviation du nom du fournisseur." --locale fr;

field string --named phone;
description add-field-description --onProperty phone --title "Phone" --text "The phone number of the supplier";
description add-field-description --onProperty phone --title "Téléphone" --text "Le téléphone du fournisseur" --locale fr;

field string --named mobile;
description add-field-description --onProperty mobile --title "Mobile" --text "The mobile phone number of the supplier";
description add-field-description --onProperty mobile --title "Mobile" --text "Le téléphone mobile du fournisseur" --locale fr;

field string --named fax;
description add-field-description --onProperty fax --title "Fax" --text "The fax number of the supplier";
description add-field-description --onProperty fax --title "Fax" --text "Le fax du fournisseur" --locale fr;

field string --named email;
description add-field-description --onProperty email --title "Email" --text "The email of the supplier";
description add-field-description --onProperty email --title "Email" --text "Le email du fournisseur" --locale fr;

field string --named adresse;
description add-field-description --onProperty adresse --title "Address" --text "The addresse of the supplier";
description add-field-description --onProperty adresse --title "Adresse" --text "Adresse du fournisseur" --locale fr;

field string --named internetSite;
description add-field-description --onProperty internetSite --title "Web Site" --text "The web site of the supplier";
description add-field-description --onProperty internetSite --title "Site Internet" --text "Le site internet du fournisseur" --locale fr;

field string --named responsable;
description add-field-description --onProperty responsable --title "Person In Charge" --text "The person in charge at the supplier side.";
description add-field-description --onProperty responsable --title "Responsable" --text "Le responsable chez le fournisseur." --locale fr;

field string --named revenue;
description add-field-description --onProperty revenue --title "Revenue" --text "The revenue of this supplier side.";
description add-field-description --onProperty revenue --title "Chiffre d Affaires" --text "Le chiffre d affaires de ce fournisseur." --locale fr;

field string --named city;
description add-field-description --onProperty city --title "City" --text "The city of this supplier";
description add-field-description --onProperty city --title "Ville" --text "La ville du fournisseur." --locale fr;

field string --named country;
description add-field-description --onProperty country --title "Country" --text "The country of this supplier";
description add-field-description --onProperty country --title "Pays" --text "Le pays de ce fournisseur" --locale fr;

field string --named zipCode;
description add-field-description --onProperty zipCode --title "Zip Code" --text "The zip code of this supplier";
description add-field-description --onProperty zipCode --title "Code Postal" --text "Le code postal de ce fournisseur" --locale fr;

field string --named taxIdNumber;
description add-field-description --onProperty taxIdNumber --title "Tax Id Number" --text "The tax id number of this supplier";
description add-field-description --onProperty taxIdNumber --title "Numéro du Contribuable" --text "Le numéro du contribuable de ce fournisseur" --locale fr;

field string --named commRegNumber;
description add-field-description --onProperty commRegNumber --title "Commercial Register Number" --text "The commercial register number of this supplier";
description add-field-description --onProperty commRegNumber --title "Numéro Registre du Commerce" --text "Le numéro du registre de commerce de ce fournisseur" --locale fr;

field string --named note;
description add-field-description --onProperty note --title "Note" --text "Description of this supplier";
description add-field-description --onProperty note --title "Note" --text "Description de ce fournisseur" --locale fr;
constraint Size --onProperty note --max 256;

@/* Entity ProcurementOrderItem */;
entity --named ProcurementOrderItem --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Procurement Order Item" --text "Procurement order item";
description add-class-description  --locale fr --title "Ligne Commande Fournisseur" --text "Ligne de commande fournisseur";

field string --named indexLine; 
description add-field-description --onProperty indexLine --title "Line Index" --text "Index for searching through purchase order items";
description add-field-description --onProperty indexLine --title "Index de Ligne" --text "Index permettant de rechercher la ligne de commande fournisseur" --locale fr;

field string --named pic; 
description add-field-description --onProperty pic --title "Product Identification Code" --text "The code identifying the product in this order item.";
description add-field-description --onProperty pic --title "Code Identifiant Prouit" --text "Le code identifiant du produit de la ligne de commande." --locale fr;

field manyToOne --named article --fieldType ~.jpa.Article;
constraint NotNull --onProperty article;
description add-field-description --onProperty article --title "Article" --text "The article fo this lot";
description add-field-description --onProperty article --title "Produit" --text "Le produit du lot." --locale fr;

field temporal --type TIMESTAMP --named recCreated; 
@/* Pattern=dd-MM-yyy  */;
description add-field-description --onProperty recCreated --title "Record Created" --text "Order item record creation date";
description add-field-description --onProperty recCreated --title "Date de Saisie" --text "Date à laquelle la ligne de commande a été saisi(crée)" --locale fr;

field number --named quantity --type java.math.BigDecimal;
description add-field-description --onProperty quantity --title "Quantity Ordered" --text "The quantity ordered in this lot.";
description add-field-description --onProperty quantity --title "Quantité Commandée" --text "La quantité de produits commandés dans le lot." --locale fr;
@/* Default=0 */;

field number --named availableQty --type java.math.BigDecimal;
description add-field-description --onProperty availableQty --title "Available Quantity" --text "The quantity available at the supplier.";
description add-field-description --onProperty availableQty --title "Quantité Disponible" --text "La quantité de produits disponible chez le fournisseur." --locale fr;
@/* Default=0 quantité_fournie*/;

field manyToOne --named creatingUser --fieldType ~.jpa.PharmaUser;
description add-field-description --onProperty creatingUser --title "Creating User" --text "The user creating this procurement order item.";
description add-field-description --onProperty creatingUser --title "Agent Créateur" --text "Utilisateur ayant crée cet ligne de commande." --locale fr;

field string --named designation; 
description add-field-description --onProperty designation --title "Designation" --text "The name the product in this order item.";
description add-field-description --onProperty designation --title "Designation" --text "Le nom du produit de la ligne de commande." --locale fr;

field boolean --named valid --primitive false;
description add-field-description --onProperty valid --title "Valid" --text "Determines if the order item is valid or not according to the expectations of the supplier.";
description add-field-description --onProperty valid --title "Valide" --text "Détermine si la ligne de commande est valide ou pas selon les attentes du fournisseur." --locale fr;

field number --named minPurchasePriceSug --type java.math.BigDecimal;
description add-field-description --onProperty minPurchasePriceSug --title "Minimum Purchase Price Suggested" --text "Minimum suggested purchase price suggested for this purchase order item.";
description add-field-description --onProperty minPurchasePriceSug --title "Prix d Achat Minimum Proposé" --text "Prix d achat minimum proposé d un produit de la ligne de commande Fournisseur." --locale fr;
@/* Default=0 */;

field number --named minPurchasePriceSupl --type java.math.BigDecimal;
description add-field-description --onProperty minPurchasePriceSupl --title "Minimum Purchase Price Supplier" --text "Minimum supplier purchase price suggested for this purchase order item.";
description add-field-description --onProperty minPurchasePriceSupl --title "Prix d Achat Minimum Fournisseur" --text "Prix fournisseur minimum pour achat pour un produit de la ligne de commande Fournisseur." --locale fr;
@/* Default=0 */;

field number --named minSalesPrice --type java.math.BigDecimal;
description add-field-description --onProperty minSalesPrice --title "Minimum Sales Price" --text "Minimum sales price for the product of this procurement order item.";
description add-field-description --onProperty minSalesPrice --title "Prix de Vente Minimum" --text "Prix de vente minimum pour les produits de cette ligne de commande fournisseur." --locale fr;
@/* Default=0 */;

field number --named totalPurchasePrice --type java.math.BigDecimal;
description add-field-description --onProperty totalPurchasePrice --title "Total Purchase Price" --text "Total purchase price for this procurement order item.";
description add-field-description --onProperty totalPurchasePrice --title "Prix d Achat Totale" --text "Prix achat totale pour cette ligne de commande fournisseur." --locale fr;
@/* Default=0, Formule=prix_achat_min*quantité_commandée */;

@/* Entity ProcurementOrderType */;
entity --named ProcurementOrderType --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Procurement Order Type" --text "Procurement order type";
description add-class-description  --locale fr --title "Type Commande Fournisseur" --text "Type commande fournisseur";
@/* Enumération{NORMAL,PACKAGED,SPECIAL} */;


field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this procurement order type.";
description add-field-description --onProperty name --title "Nom" --text "Le nom de ce type commande fournisseur." --locale fr;

@/* Enum ProcmtOrderConversationState */;
java new-enum-type  --named ProcmtOrderConversationState --package ~.jpa ;
enum add-enum-class-description --title "Procurement Order Coversation State" --text "Procurement order conversation state.";
enum add-enum-class-description  --locale fr --title "Etat d Echange de Commande Fournisseur" --text "Etat de conversation pour cette commande fournisseur.";
java new-enum-const SUBMIT;
java new-enum-const VALIDATE;
java new-enum-const CANCELLED;
java new-enum-const PROVIDER;
java new-enum-const PROCESSING;
java new-enum-const DRUGSTORE_PROCESSING;
enum generate-description-keys ;
enum generate-description-keys --locale fr ;

@/* Enum  ProcmtOrderTriggerMode */;

java new-enum-type --named ProcmtOrderTriggerMode --package ~.jpa ;
enum add-enum-class-description --title "Procurement Order Trigger Mode" --text "Procurement order trigger mode.";
enum add-enum-class-description  --locale fr --title "Criteres de Preparation de Commande Fournisseur" --text "Criteres de preparation de commande fournisseur.";
java new-enum-const MANUELLE;
java new-enum-const RUPTURE_STOCK;
java new-enum-const PLUS_VENDU;
enum generate-description-keys ;
enum generate-description-keys --locale fr ;

@/* Entity ProcurementOrder */;
entity --named ProcurementOrder --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Procurement Order" --text "Procurement order";
description add-class-description  --locale fr --title "Commande Fournisseur" --text "Commande fournisseur";

field string --named procurementNumber;
description add-field-description --onProperty procurementNumber --title "Order Number" --text "The number of this procurement order";
description add-field-description --onProperty procurementNumber --title "Numéro de Commande" --text "Le numéro de cette commande" --locale fr;

field temporal --type TIMESTAMP --named submissionDate; 
@/* pattern=dd-MM-yyyy HH:mm */;
description add-field-description --onProperty submissionDate --title "Submission DAte" --text "Date of submission of the order to Ubipharm created from the module Ubipharm";
description add-field-description --onProperty submissionDate --title "Date de Soumission" --text "Date de soumission de la commande à Ubipharm crée à partir du module d Ubipharm" --locale fr;

field temporal --type TIMESTAMP --named processingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty processingDate --title "Processing Date" --text "The processing date";
description add-field-description --onProperty processingDate --title "Date de Traitement" --text "The processing date" --locale fr;
cd ~~;

@/* Entity PurchaseOrder */;
entity --named PurchaseOrder --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Purchase Order" --text "The purchase order";
description add-class-description  --locale fr --title "Approvisionnement" --text "Un approvisionement (une Commande)";

field string --named poNumber;
description add-field-description --onProperty poNumber --title "PO Number" --text "The number of the purchase order.";
description add-field-description --onProperty poNumber --title "Numéro Appr." --text "Le numéro de l approvisionement" --locale fr;

field string --named deliverySlipNumber;
constraint NotNull --onProperty deliverySlipNumber;
description add-field-description --onProperty deliverySlipNumber --title "Delivery Slip Number" --text "The delivery slip number (generaly available on the delivery slip)";
description add-field-description --onProperty deliverySlipNumber --title "Numéro Bordereau de Livraison" --text "Numéro de bordereau de l approvisionement(mentionné géneralement sur le bordereau de livraison)" --locale fr;

field temporal --type TIMESTAMP --named dateOnDeliverySlip; 
description add-field-description --onProperty dateOnDeliverySlip --title "Date on Delivery Slip" --text "Date as mentioned on delivery slip";
description add-field-description --onProperty dateOnDeliverySlip --title "Date sur Bordereau" --text "Date de livraison mentionée sur le bordereau de livraison de l approvisionement" --locale fr;

field manyToOne --named creatingUser --fieldType ~.jpa.PharmaUser;
description add-field-description --onProperty creatingUser --title "Creating User" --text "The user creating this purchase order.";
description add-field-description --onProperty creatingUser --title "Agent Créateur" --text "L utilisateur ayant crée l approvisionement." --locale fr;
constraint NotNull --onProperty creatingUser;

field manyToOne --named site --fieldType ~.jpa.Site;
constraint NotNull --onProperty site;
description add-field-description --onProperty site --title "Site" --text "The site in which the purchase order was made.";
description add-field-description --onProperty site --title "Site" --text "Le site dans lequel l approvisionement a été éffectuée." --locale fr;

field manyToOne --named currency --fieldType ~.jpa.Currency;
description add-field-description --onProperty currency --title "Currency" --text "The currency used for the conversion of the currency stated on the delivery note in local currency (FCFA).";
description add-field-description --onProperty currency --title "Devise" --text "La devise utilisée pour la conversion de la monnaie mentionnée sur le bordereau de livraison en monnaie locale(FCFA)." --locale fr;

field temporal --type TIMESTAMP --named orderDate; 
description add-field-description --onProperty orderDate --title "Order Date" --text "Order date.";
description add-field-description --onProperty orderDate --title "Date de Commande" --text "Date de commande." --locale fr;

field temporal --type TIMESTAMP --named deliveryDate; 
description add-field-description --onProperty deliveryDate --title "Delivery Date" --text "Date on which the products where effectively delivered products.";
description add-field-description --onProperty deliveryDate --title "Date de Livraison" --text "Date à laquelle a été livrée les produits qui entrent en stock.." --locale fr;

field manyToOne --named supplier --fieldType ~.jpa.Supplier;
description add-field-description --onProperty supplier --title "Supplier" --text "The supplier mentioned on the delivery slip while products are being delivered.";
description add-field-description --onProperty supplier --title "Fournisseur" --text "Le fournisseur mentionné sur le bordereau de livraison des produits qui entrent en stock." --locale fr;

field temporal --type TIMESTAMP --named paymentDate; 
description add-field-description --onProperty paymentDate --title "Payment Date" --text "Date of settlement of this order.";
description add-field-description --onProperty paymentDate --title "Date de Règlement" --text "Date à laquelle l approvisionement a été doit être reglé auprès du fournisseur. Prévu pour recapituler les dettes fournisseur." --locale fr;

field number --named amountBeforeTax --type java.math.BigDecimal;
description add-field-description --onProperty amountBeforeTax --title "Amount Before Tax" --text "Total amount before tax for this purchase order.";
description add-field-description --onProperty amountBeforeTax --title "Montant hors Taxes" --text "Montant total hors Taxes pour cette approvisionement." --locale fr;
constraint NotNull --onProperty amountBeforeTax;

field number --named amountAfterTax --type java.math.BigDecimal;
description add-field-description --onProperty amountAfterTax --title "Amount after Tax" --text "Total amount after tax for this purchase order.";
description add-field-description --onProperty amountAfterTax --title "Montant TTC" --text "Montant total TTC pour cette approvisionement." --locale fr;

field number --named amountDiscount --type java.math.BigDecimal;
description add-field-description --onProperty amountDiscount --title "Discount Amount" --text "Discount amount for this purchase order.";
description add-field-description --onProperty amountDiscount --title "Montant Remise" --text "Montant de la remise de l approvisionement." --locale fr;

field number --named netAmountToPay --type java.math.BigDecimal;
description add-field-description --onProperty netAmountToPay --title "Net Amount to Pay" --text "Teh net amount to pay.";
description add-field-description --onProperty netAmountToPay --title "Montant net a payer" --text "Montant de la remise de l approvisionement." --locale fr;

field manyToOne --named vat --fieldType ~.jpa.VAT;
description add-field-description --onProperty vat --title "VAT" --text "The value added tax";
description add-field-description --onProperty vat --title "TVA" --text "La taxe sur la valeur ajoute" --locale fr;

field temporal --type TIMESTAMP --named creationDate; 
description add-field-description --onProperty creationDate --title "Creation Date" --text "The creation date of this order.";
description add-field-description --onProperty creationDate --title "Date de Création" --text "La date de création de cette commande." --locale fr;

field boolean --named claims --primitive false ;
description add-field-description --onProperty claims --title "Claims" --text "Specifies whether the purchase contains claims or not.";
description add-field-description --onProperty claims --title "Réclamations" --text "Précise si l approvisionement contient des réclamations ou non." --locale fr;
@/* default=false */;

field boolean --named emergency --primitive false ;
description add-field-description --onProperty emergency --title "Emergency" --text "Emergency";
description add-field-description --onProperty emergency --title "Urgence" --text "Urgence." --locale fr;
@/* default=false */;

field boolean --named closed --primitive false ;
description add-field-description --onProperty closed --title "Closed" --text "Specifies whether the purchase was closed or not.";
description add-field-description --onProperty closed --title "Cloturé" --text "Précise si l approvisionement a été cloturé ou  non." --locale fr;

field custom --named orderProcessingState --type ~.jpa.DocumentProcessingState.java;
description add-field-description --onProperty orderProcessingState --title "Order Processing State" --text "The purchase order processing state.";
description add-field-description --onProperty orderProcessingState --title "Etat de traitement Commande" --text "L etat de traitement de cette approvisionement." --locale fr;
enum enumerated-field --onProperty orderProcessingState ;

field manyToOne --named receivingAgency --fieldType ~.jpa.Agency;
description add-field-description --onProperty receivingAgency --title "Agency" --text "Name of the agency in which the product was delivered.";
description add-field-description --onProperty receivingAgency --title "Filiale" --text "Nom de la filiale dans laquelle l entree en stock s effectue" --locale fr;




@/* Entity PurchaseOrderItem */;
entity --named PurchaseOrderItem --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Purchase Order Item" --text "Purchase order item";
description add-class-description  --locale fr --title "Ligne Approvisionnement" --text "la ligne d approvisionement";

field manyToOne --named purchaseOrder --fieldType ~.jpa.PurchaseOrder;
description add-field-description --onProperty purchaseOrder --title "Purchase Order" --text "The purchase order enclosing this item";
description add-field-description --onProperty purchaseOrder --title "Approvisionnement" --text "L approvisionement contenant cette ligne d approvisionement." --locale fr;

field string --named poItemNumber;
description add-field-description --onProperty poItemNumber --title "PO Item Number" --text "The number of this PO Item";
description add-field-description --onProperty poItemNumber --title "Numéro LAP" --text "Le numéro de cette ligne d approvisionement" --locale fr;

field string --named lineIndex;
description add-field-description --onProperty lineIndex --title "Line Index" --text "The index of a line of this item in the PO";
description add-field-description --onProperty lineIndex --title "Index de ligne" --text "L index de la ligne de cette LAP" --locale fr;

field string --named pic; 
description add-field-description --onProperty pic --title "Product Identification Code" --text "The standard product identification code of this product.";
description add-field-description --onProperty pic --title "Code Identifiant Prouit" --text "Le Code identifiant standard du produit." --locale fr;

field string --named localPic; 
description add-field-description --onProperty localPic --title "Local PIC" --text "The internal product identification code used to identify lots during sales.";
description add-field-description --onProperty localPic --title "CIP Maison" --text "Le code identifiant produit maison, utilisé pour identifier les lots de produits lors de la vente." --locale fr;
constraint Size --onProperty localPic --min 7;

field manyToOne --named article --fieldType ~.jpa.Article
constraint NotNull --onProperty article;
description add-field-description --onProperty article --title "Article" --text "The article fo this lot";
description add-field-description --onProperty article --title "Produit" --text "Le produit du lot." --locale fr;

field string --named designation;
description add-field-description --onProperty designation --title "Designation" --text "The designation of the product in thos lot";
description add-field-description --onProperty designation --title "Designation" --text "La designation du produit dans le lot" --locale fr;

field temporal --type TIMESTAMP --named productionDate; 
@/* Pattern= dd-MM-yyy  */;
description add-field-description --onProperty productionDate --title "Production Date" --text "Production date fo the article in this lot";
description add-field-description --onProperty productionDate --title "Date de Fabrication" --text "Date de fabrication du produit dans ce lot" --locale fr;

field temporal --type TIMESTAMP --named expirationDate; 
@/* Pattern=dd-MM-yyy  */;
description add-field-description --onProperty expirationDate --title "Expiration Date" --text "Expiration date for the article in this lot";
description add-field-description --onProperty expirationDate --title "Date de Peremption" --text "Date de peremption du produit dans le lot" --locale fr;

field string --named modifyingUser; 
description add-field-description --onProperty modifyingUser --title "Modifying User" --text "The user editing this PO";
description add-field-description --onProperty modifyingUser --title "Agent de Saisie" --text "Agent de saisie du produit" --locale fr;

field temporal --type TIMESTAMP --named productRecCreated; 
@/* Pattern= dd-MM-yyy  */;
description add-field-description --onProperty productRecCreated --title "Product Record Created" --text "Product record creation date";
description add-field-description --onProperty productRecCreated --title "Date de Saisie du Produit" --text "Date à laquelle le produit a été saisi(crée)" --locale fr;

field number --named quantity --type java.math.BigDecimal;
description add-field-description --onProperty quantity --title "Quantity" --text "The quantity purchase in this lot.";
description add-field-description --onProperty quantity --title "Quantité" --text "La quantité de produits approvisionnée dans le lot." --locale fr;

field number --named freeQuantity --type java.math.BigDecimal;
description add-field-description --onProperty freeQuantity --title "Free Quantity" --text "The auntity of products given by the supplier free of charge during purchasing. These articles are a value aded for the products in stock.";
description add-field-description --onProperty freeQuantity --title "Quantité Gratuite" --text "La quantité de produits fournis gratuitement par le fournisseur lors de l approvisionnement. Ces produits sont une plus value pour les produits dans le stock" --locale fr;

field number --named soldQuantity --type java.math.BigDecimal;
description add-field-description --onProperty soldQuantity --title "Sold Quantity" --text "The quantity of articles sold by the suplier";
description add-field-description --onProperty soldQuantity --title "Quantité Vendue" --text "La quantité de produits vendus par le fournisseur" --locale fr;
@/*  Default=0 */; 

field number --named claimedQuantity --type java.math.BigDecimal;
description add-field-description --onProperty claimedQuantity --title "Claimed Quantity" --text "The quantity of products claimed";
description add-field-description --onProperty claimedQuantity --title "Quantité reclamée" --text "Quantité de produits reclamés" --locale fr;
@/*  Default=0 */; 

field number --named stockQuantity --type java.math.BigDecimal;
description add-field-description --onProperty stockQuantity --title "Stock Quantity" --text "The quantity of products claimed";
description add-field-description --onProperty stockQuantity --title "Quantité en Stock" --text "Quantité de produits du lot." --locale fr;
@/*  Default=0 */; 

field number --named releasedQuantity --type java.math.BigDecimal;
description add-field-description --onProperty releasedQuantity --title "Released Quantity" --text "The quantity of products released";
description add-field-description --onProperty releasedQuantity --title "Quantité Sortie" --text "Quantité de produits sortis du lot." --locale fr;
@/*  Default=0 */; 

field number --named salesPricePU --type java.math.BigDecimal;
description add-field-description --onProperty salesPricePU --title "Sales Price per Unit" --text "The sales price per unit.";
description add-field-description --onProperty salesPricePU --title "Prix de Vente Unitaire" --text "Prix de vente unitaire." --locale fr;
@/*  Default=0 */; 

field number --named purchasePricePU --type java.math.BigDecimal;
description add-field-description --onProperty purchasePricePU --title "Purchase Price per Unit" --text "The purchase price per unit.";
description add-field-description --onProperty purchasePricePU --title "Prix d Achat Unitaire" --text "Prix d achat unitaire." --locale fr;
@/*  Default=0 */; 

field number --named totalPurchasePrice --type java.math.BigDecimal;
description add-field-description --onProperty totalPurchasePrice --title "Total Purchase Price" --text "The total purchase price.";
description add-field-description --onProperty totalPurchasePrice --title "Prix d Achat Total" --text "Prix d achat totale." --locale fr;
@/*  Default=0 */; 
@/*Formule= (prix_achat_unitaire*[qte_aprovisionée-qte_unité_gratuite]) */;

field number --named grossMargin --type java.math.BigDecimal;
description add-field-description --onProperty grossMargin --title "Gross Margin" --text "The gross margin.";
description add-field-description --onProperty grossMargin --title "Marge Brute" --text "La marge brute." --locale fr;
@/* Formule= prix_vente_unitaire – prix_achat_unitaire */; 
@/* C est le benefice net pour chaque produit du lot */;

field boolean --named approvedForSale --primitive false ; 
description add-field-description --onProperty approvedForSale --title "Approved For Sale" --text "Used to approved the sale of products in this lot.";
description add-field-description --onProperty approvedForSale --title "Vente Autorisée" --text "Permet d autoriser ou non la vente d un lot de produits." --locale fr;
@/* default=true */;

field number --named maxDiscountRate --type java.math.BigDecimal;
@/*  remise_max, Default=0 */;
description add-field-description --onProperty maxDiscountRate --title "Max Discount Rate" --text "Maximal discount rate for product of this lot.";
description add-field-description --onProperty maxDiscountRate --title "Taux Maximal Remise" --text "Taux de remise max pour les produits de ce lot." --locale fr;

field boolean --named discountApproved --primitive false ; 
description add-field-description --onProperty discountApproved --title "Discount Approved" --text "Used to approved the discount on products in this lot.";
description add-field-description --onProperty discountApproved --title "Remise Autorisée" --text "Permet d autoriser ou non la remise sur vent d un produit de ce lot." --locale fr;
@/* default=true */;

cd ~~ ;

@/* ==================================== */;
@/* Movement Stock*/;

@/* Type Movement Stock*/;
java new-enum-type --named StockMovementType --package ~.jpa ;
enum add-enum-class-description --title "Stock Movement Type" --text "Type of movement made ​​in the stock";
enum add-enum-class-description  --locale fr --title "Type Mouvement Stock" --text "Type de mouvement effectué dans le stock";
java new-enum-const OUT;
enum add-enum-constant-description --onConstant OUT --title "Outbound" --text "Stock receip.";
enum add-enum-constant-description --locale fr --onConstant OUT --title "Sortie" --text "Stock delivery.";
java new-enum-const IN;
enum add-enum-constant-description --onConstant IN --title "Inbound" --text "Sortie de stock.";
enum add-enum-constant-description --locale fr --onConstant IN --title "Entrée" --text "Entrée de stock.";

@/* Movement Stock Endpoint*/;
java new-enum-type --named StockMovementEndpoint --package ~.jpa ;
enum add-enum-class-description --title "Stock Movement Endpoint" --text "An origin or the destination of a stock movement.";
enum add-enum-class-description  --locale fr --title "Origine ou Destination du Mouvement Stock" --text "Origine ou la destination du movement de stock.";
java new-enum-const WAREHOUSE;
enum add-enum-constant-description --onConstant WAREHOUSE --title "Wharehouse" --text "Warehouse";
enum add-enum-constant-description --locale fr --onConstant WAREHOUSE --title "Magasin" --text "Magasin.";
java new-enum-const SUPPLIER;
enum add-enum-constant-description --onConstant SUPPLIER --title "Supplier" --text "Supplier";
enum add-enum-constant-description --locale fr --onConstant SUPPLIER --title "Fournisseur" --text "Forunisseur";
java new-enum-const CUSTOMER;
enum add-enum-constant-description --onConstant CUSTOMER --title "Customer" --text "Customer";
enum add-enum-constant-description --locale fr --onConstant CUSTOMER --title "Client" --text "Client";
@/* Enumeration{MAGASIN, FOURNISSEUR, CLIENT} */;

@/* Mouvement de stock */;
entity --named StockMovement --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Purchase Order" --text "It saves the traces of all the movements that take place in the stock (inputs, outputs, inventory, returns processing, etc ...)";
description add-class-description  --locale fr --title "Approvisionnement" --text "Elle permet de sauvegarder les traces de  tous les mouvements qui prennen place dans le stock(entrees, sorties, inventaires, retours, transformation, etc...)";

field string --named mvtNumber;
description add-field-description --onProperty mvtNumber --title "Movement Number" --text "The movement number.";
description add-field-description --onProperty mvtNumber --title "Numéro du Movement" --text "Le numero du mouvement." --locale fr;

field temporal --type TIMESTAMP --named creationDate; 
description add-field-description --onProperty creationDate --title "Creation Date" --text "The creation date of this stock movement.";
description add-field-description --onProperty creationDate --title "Date de Création" --text "La date de création de cette movement de stock." --locale fr;

field manyToOne --named creatingUser --fieldType  ~.jpa.PharmaUser.java ;
description add-field-description --onProperty creatingUser --title "Creating User" --text "The user creating this stock movement.";
description add-field-description --onProperty creatingUser --title "Agent Créateur" --text "Utilisateur originaire du mouvement." --locale fr;
constraint NotNull --onProperty creatingUser;

field number --named movedQty --type java.math.BigDecimal;
description add-field-description --onProperty movedQty --title "Quantity Moved" --text "The quantity moved during this stockage operation.";
description add-field-description --onProperty movedQty --title "Quantité Deplacés" --text "La quantité de produits deplacés lors de cette operation de stockage." --locale fr;
@/* Default=0 */;

field custom --named movementType --type ~.jpa.StockMovementType;
constraint NotNull --onProperty movementType;
description add-field-description --onProperty movementType --title "Movement Type" --text "The type of this stock movement.";
description add-field-description --onProperty movementType --title "Type de Mouvement" --text "Le type de ce mouvement de stock." --locale fr;
enum enumerated-field --onProperty movementType ;

field manyToOne --named site --fieldType ~.jpa.Site;
constraint NotNull --onProperty site;
description add-field-description --onProperty site --title "Site" --text "The site in which the movement occured";
description add-field-description --onProperty site --title "Magasin" --text "Le site dans lequel le mouvement prend place" --locale fr;

field custom --named movementOrigin --type ~.jpa.StockMovementType;
description add-field-description --onProperty movementOrigin --title "Movement Origin" --text "The starting point of the movement.";
description add-field-description --onProperty movementOrigin --title "Origine du Mouvement" --text "Le point de depart du mouvement." --locale fr;
enum enumerated-field --onProperty movementOrigin ;
@/* Enumeration{MAGASIN, FOURNISSEUR, CLIENT} */;

field custom --named movementDestination --type ~.jpa.StockMovementType;
description add-field-description --onProperty movementDestination --title "Movement Destination" --text "Point of arrival of the movement.";
description add-field-description --onProperty movementDestination --title "Destination du Mouvement" --text "Point arrivée du mouvement." --locale fr;
enum enumerated-field --onProperty movementDestination ;
@/* Enumeration{MAGASIN, FOURNISSEUR, CLIENT} */;

field string --named soNumber;
description add-field-description --onProperty soNumber --title "Sales Order Number" --text "The sales order number.";
description add-field-description --onProperty soNumber --title "Numéro de Commande Client" --text "Le numéro de commande client." --locale fr;
@/* numero_ticket, Numero de la commande client pour un mouvement de type vente */;

field string --named deliverySlipNumber;
description add-field-description --onProperty deliverySlipNumber --title "Delivery Slip Number" --text "The delivery slip number for purchase order (in case of a purchase)";
description add-field-description --onProperty deliverySlipNumber --title "Numéro Bordereau de Livraison" --text "Numéro de bordereau de cet approvisionement (pour un mouvement de type approvisionement)" --locale fr;

field string --named pic; 
description add-field-description --onProperty pic --title "Product Identification Code" --text "The standard product identification code for a product in stock.";
description add-field-description --onProperty pic --title "Code Identifiant Prouit" --text "Le Code identifiant standard pour un mouvement de produit dans le stock." --locale fr;

field string --named picm; 
description add-field-description --onProperty picm --title "Product Identification Code PO" --text "The standard product identification code for a product (in case of a purchase order).";
description add-field-description --onProperty picm --title "Code Identifiant Prouit PO" --text "Le Code identifiant standard pour un mouvement de produit (cas approvisionent)." --locale fr;

field manyToOne --named agency --fieldType ~.jpa.Agency;
description add-field-description --onProperty agency --title "Agency" --text "Name of the agency in which the movement takes place.";
description add-field-description --onProperty agency --title "Filiale" --text "Nom de la filiale dans laquelle le mouvement a lieu." --locale fr;

field string --named designation; 
description add-field-description --onProperty designation --title "Designation" --text "Product designation for product movement.";
description add-field-description --onProperty designation --title "Designation" --text "Designation du produit pour un mouvement de produit." --locale fr;

field number --named initialQty --type java.math.BigDecimal;
description add-field-description --onProperty initialQty --title "Initial Quantity" --text "The quantity in stock before the movement.";
description add-field-description --onProperty initialQty --title "Quantité Initiale" --text "La quantité de produits dans le stock avant le mouvement." --locale fr;
@/* Default=0 */;

field number --named finalQty --type java.math.BigDecimal;
description add-field-description --onProperty finalQty --title "Final Quantity" --text "The quantity in stock after the movement.";
description add-field-description --onProperty finalQty --title "Quantité Finale" --text "La quantité de produits dans le stock apres le mouvement." --locale fr;
@/* Default=0 */;

field string --named cashDrawerNumber;
description add-field-description --onProperty cashDrawerNumber --title "Cash Drawer Number" --text "The cash drawer number for cash movements.";
description add-field-description --onProperty cashDrawerNumber --title "Numéro de Caisse" --text "Le numéro de caisse pour les mouvement de caise." --locale fr;

field number --named totalPurchasingPrice --type java.math.BigDecimal;
description add-field-description --onProperty totalPurchasingPrice --title "Total Purchasing Price" --text "Total purchasing price for movement of type purchase.";
description add-field-description --onProperty totalPurchasingPrice --title "Prix Achat Total" --text "Prix total achat pour les mouvements de type approvisionement." --locale fr;
constraint NotNull --onProperty totalPurchasingPrice;

field number --named totalDiscount --type java.math.BigDecimal;
description add-field-description --onProperty totalDiscount --title "Total Discount" --text "Total discount of the purchase.";
description add-field-description --onProperty totalDiscount --title "Remise Totale" --text "Remise totale sur cet approvisionement." --locale fr;

field number --named totalSalesPrice --type java.math.BigDecimal;
description add-field-description --onProperty totalSalesPrice --title "Total Sales Price" --text "Total sale price for the movement type sales";
description add-field-description --onProperty totalSalesPrice --title "Prix de Vente Totale" --text "Prix de vente total pour les mouvements de type vente." --locale fr;

field string --named note;
description add-field-description --onProperty note --title "Note" --text "Description of this stock movement.";
description add-field-description --onProperty note --title "Note" --text "Description de ce mouvement de stock." --locale fr;
constraint Size --onProperty note --max 256;

field boolean --named canceled --primitive false; 
description add-field-description --onProperty canceled --title "Canceled" --text "Sates if the stock movement was canceled or not.";
description add-field-description --onProperty canceled --title "Annulé" --text "Precise si le mouvement de tock a été annulée ou non." --locale fr;
@/* default=false */;

cd ~~ ;



@/* ==================================== */;
@/* Client */;


@/* Type Client*/;
java new-enum-type --named ClientType --package ~.jpa ;
enum add-enum-class-description --title "Client Type" --text "Type of client.";
enum add-enum-class-description  --locale fr --title "Type of Client" --text "Type de client.";
java new-enum-const INDIVIDUAL;
enum add-enum-constant-description --onConstant INDIVIDUAL --title "Individual" --text "Individual person";
enum add-enum-constant-description --locale fr --onConstant INDIVIDUAL --title "Physique" --text "Personnalié physique";
java new-enum-const LEGAL;
enum add-enum-constant-description --onConstant LEGAL --title "Legal" --text "Legal entity";
enum add-enum-constant-description --locale fr --onConstant LEGAL --title "Morale" --text "Personnalité juridique";
@/* enumeration{PHYSIQUE, MORAL} */;

@/* Entité CategorieClient */;
entity --named ClientCategory --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Client Category" --text "The client categories.";
description add-class-description  --locale fr --title "Categorie Client" --text "Les categorie de client.";

field string --named categoryNumber;
description add-field-description --onProperty categoryNumber --title "Client Category Number" --text "The client category number.";
description add-field-description --onProperty categoryNumber --title "Numéro de Categorie Client" --text "Le numéro de categorie client." --locale fr;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this client category.";
description add-field-description --onProperty name --title "Libelle" --text "Le nom de cette categorie client." --locale fr;

field string --named note;
description add-field-description --onProperty note --title "Note" --text "Description of this client category.";
description add-field-description --onProperty note --title "Note" --text "Description de cette categorie client." --locale fr;
constraint Size --onProperty note --max 256;

field number --named discountRate --type java.math.BigDecimal;
description add-field-description --onProperty discountRate --title "Discount Rate" --text "Discount rate for this client category.";
description add-field-description --onProperty discountRate --title "Taux Remise" --text "Taux de remise pour cette categorie client." --locale fr;





@/* Client */;
entity --named Client --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Client" --text "The client.";
description add-class-description  --locale fr --title "Categorie" --text "Un client.";

field string --named clientNumber;
description add-field-description --onProperty clientNumber --title "Client Number" --text "The client number.";
description add-field-description --onProperty clientNumber --title "Numéro du Client" --text "Le numéro du client." --locale fr;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this client.";
description add-field-description --onProperty name --title "Nom" --text "Le nom de ce client." --locale fr;

field string --named firstName;
description add-field-description --onProperty firstName --title "First Name" --text "The first name of this client.";
description add-field-description --onProperty firstName --title "Prénom" --text "Le prénom de ce client." --locale fr;

field string --named fullName;
description add-field-description --onProperty fullName --title "Full Name" --text "The full name of this client.";
description add-field-description --onProperty fullName --title "Nom Complet" --text "Le nom complet de ce client." --locale fr;
@/* Le nom complet du client(nom+prenom) */;

field string --named landLinePhone;
description add-field-description --onProperty landLinePhone --title "Land Line Phone" --text "The client land line phone number";
description add-field-description --onProperty landLinePhone --title "Téléphone Fixe" --text "Téléphone fixe du client" --locale fr;

field string --named mobile;
description add-field-description --onProperty mobile --title "Mobile Phone" --text "The mobile phone of the client";
description add-field-description --onProperty mobile --title "Téléphone Mobile" --text "Téléphone Mobile du client" --locale fr;

field string --named fax;
description add-field-description --onProperty fax --title "Fax" --text "The fax number of the client";
description add-field-description --onProperty fax --title "Fax" --text "Fax du client" --locale fr;

field string --named email;
description add-field-description --onProperty email --title "Email" --text "The email address of the client";
description add-field-description --onProperty email --title "Email" --text "Email du client" --locale fr;

field boolean --named creditAuthorized --primitive false;
description add-field-description --onProperty creditAuthorized --title "Credit Authorized" --text "Whether or not the customer can purchase on credit";
description add-field-description --onProperty creditAuthorized --title "Crédit Autorisé" --text "Autorise ou non le crédit au client" --locale fr;

field boolean --named discountAuthorized --primitive false;
description add-field-description --onProperty discountAuthorized --title "Discount Authorized" --text "Whether or not the customer can be given discount";
description add-field-description --onProperty discountAuthorized --title "Remise Autorisée" --text "Autorise ou non la remise globale sur les produits au client" --locale fr;
@/* default=true */;

field number --named totalCreditLine --type java.math.BigDecimal;
description add-field-description --onProperty totalCreditLine --title "Total Purchasing Price" --text "Total credit line for this customer..";
description add-field-description --onProperty totalCreditLine --title "Prix d'Achat Total" --text "Le montant max de credit qu'on peut accorder au client." --locale fr;
@/* Default=0 */;

field string --named employer;
description add-field-description --onProperty employer --title "Employer" --text "The client's employer.";
description add-field-description --onProperty employer --title "Employeur" --text "L'employeur du client." --locale fr;

field temporal --type TIMESTAMP --named birthDate; 
@/* pattern= dd-MM-yyyy */;
description add-field-description --onProperty birthDate --title "Birth Date" --text "The birth date of this client";
description add-field-description --onProperty birthDate --title "Date de Naissance" --text "La date de naissance du client" --locale fr;

field custom --named gender --type ~.jpa.Gender;
description add-field-description --onProperty gender --title "Gender" --text "The gender of this client.";
description add-field-description --onProperty gender --title "Genre" --text "Le genre de ce client." --locale fr;
enum enumerated-field --onProperty gender ;

field number --named coverageRate --type java.math.BigDecimal;
@/* default=100% */;
description add-field-description --onProperty coverageRate --title "Coverage Rate" --text "The coverage rate for this client.";
description add-field-description --onProperty coverageRate --title "Taux Couverture" --text "Taux de couverture pour ce client." --locale fr;

field string --named note;
description add-field-description --onProperty note --title "Note" --text "Description of this client.";
description add-field-description --onProperty note --title "Note" --text "Description de cette client." --locale fr;
constraint Size --onProperty note --max 256;

field manyToOne --named payingClient --fieldType ~.jpa.Client;
description add-field-description --onProperty payingClient --title "Paying Client" --text "The paying client.";
description add-field-description --onProperty payingClient --title "Client Payeur" --text "Le client payeur." --locale fr;

field manyToOne --named clientCategory --fieldType ~.jpa.Client;
description add-field-description --onProperty clientCategory --title "Client Category" --text "The category this client belongs to.";
description add-field-description --onProperty clientCategory --title "Category Client" --text "La categorie de client à laquelle appartient le client." --locale fr;

field number --named totalDebt --type java.math.BigDecimal;
description add-field-description --onProperty totalDebt --title "Total Debt" --text "Total debts of this customer.";
description add-field-description --onProperty totalDebt --title "Dette Total" --text "Montant total des dettes du client." --locale fr;
@/* Default=0 */;

field custom --named clientType --type ~.jpa.ClientType;
description add-field-description --onProperty clientType --title "Client Type" --text "The client type.";
description add-field-description --onProperty clientType --title "Type Client" --text "Le type de client." --locale fr;
enum enumerated-field --onProperty clientType ;
@/* enumeration{PHYSIQUE, MORAL} */;

field string --named serialNumber;
description add-field-description --onProperty serialNumber --title "Serial Number" --text "The serial number of this client.";
description add-field-description --onProperty serialNumber --title "Matricule Client" --text "Le numéro matricule de ce client." --locale fr;

cd ~~ ;



@/* ==================================== */;
@/* Commande Client */;

@/* SalesOrderType */;
java new-enum-type --named SalesOrderType --package ~.jpa ;
enum add-enum-class-description --title "Sales Order Type" --text "The type of this sales order.";
enum add-enum-class-description  --locale fr --title "Type de Commande Client" --text "Le type d'une commande client.";
java new-enum-const CASH_SALE;
enum add-enum-constant-description --onConstant CASH_SALE --title "Cash Sale" --text "Cash Sale";
enum add-enum-constant-description --locale fr --onConstant CASH_SALE --title "Vente Cash" --text "Vente en espece";
java new-enum-const CREDIT_SALE;
enum add-enum-constant-description --onConstant CREDIT_SALE --title "Credit Sale" --text "Credit sale";
enum add-enum-constant-description --locale fr --onConstant CREDIT_SALE --title "Vente à Crédit" --text "Vente à crédit";
java new-enum-const PROFORMA_SALE;
enum add-enum-constant-description --onConstant PROFORMA_SALE --title "Pro Forma Sale" --text "Pro forma sale";
enum add-enum-constant-description --locale fr --onConstant PROFORMA_SALE --title "Vente Pro Forma" --text "Vente pro forma";
@/* Enumeration{VENTE_AU_PUBLIC, VENTE_A_CREDIT, VENTE_PROFORMAT} */;


@/* Sales Order */;
entity --named SalesOrder --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Sales Order" --text "A sales order.";
description add-class-description  --locale fr --title "Commande Client" --text "Une commande client.";

field string --named soNumber;
description add-field-description --onProperty soNumber --title "Sales Order Number" --text "The sales order number.";
description add-field-description --onProperty soNumber --title "Numéro de Commande Client" --text "Le numéro de la commande client." --locale fr;

field temporal --type TIMESTAMP --named creationDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty creationDate --title "Creation Date" --text "The creation date of this order.";
description add-field-description --onProperty creationDate --title "Date de Création" --text "La date de création de cette commande." --locale fr;

field temporal --type TIMESTAMP --named cancelationDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty cancelationDate --title "Cancelation Date" --text "The cancelation date of this order.";
description add-field-description --onProperty cancelationDate --title "Date d'Annulation" --text "La date d'annulation de cette commande." --locale fr;

field temporal --type TIMESTAMP --named restorationDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty restorationDate --title "Restoration Date" --text "The restoration date of this order.";
description add-field-description --onProperty restorationDate --title "Date de Restauration" --text "La date de restauration de cette commande." --locale fr;

field manyToOne --named client --fieldType ~.jpa.Client;
description add-field-description --onProperty client --title "Client" --text "The client ordering.";
description add-field-description --onProperty client --title "Client" --text "Le client  qui passe la commande." --locale fr;

field manyToOne --named salesStaff --fieldType ~.jpa.PharmaUser;
description add-field-description --onProperty salesStaff --title "Sales Staff" --text "The user making this sale.";
description add-field-description --onProperty salesStaff --title "Vendeur" --text "L'utilisateur qui effectue la vente." --locale fr;

field custom --named salesOrderStatus --type ~.jpa.DocumentProcessingState.java;
description add-field-description --onProperty salesOrderStatus --title "Status" --text "The status of this sales order.";
description add-field-description --onProperty salesOrderStatus --title "Status" --text "L'etat de cette commande client." --locale fr;
enum enumerated-field --onProperty salesOrderStatus ;

field boolean --named cashed --primitive false ;
description add-field-description --onProperty cashed --title "Cashed" --text "Indicates whether the order has been cashed or not.";
description add-field-description --onProperty cashed --title "Encaissé" --text "Indique si la commande a été encaissée ou non." --locale fr;
@/* default=false */;

field boolean --named canceled --primitive false ; 
description add-field-description --onProperty canceled --title "Canceled" --text "Sates if the order was canceled or not.";
description add-field-description --onProperty canceled --title "Annulé" --text "Indique si la commande a été annulée ou pas." --locale fr;
@/* default=false */;

field number --named amountBeforeTax --type java.math.BigDecimal;
description add-field-description --onProperty amountBeforeTax --title "Amount Before Tax" --text "Total amount before tax for this sales order.";
description add-field-description --onProperty amountBeforeTax --title "Montant hors Taxes" --text "Montant total hors Taxes pour cette commande client." --locale fr;
@/* Default=0, montant_ht */;

field number --named amountVAT --type java.math.BigDecimal;
description add-field-description --onProperty amountVAT --title "Amount VAT" --text "Total amount VAT for this sales order.";
description add-field-description --onProperty amountVAT --title "Montant TVA" --text "Montant total TVA pour cette commande client." --locale fr;
@/* Default=0, montant_tva */;

field number --named amountDiscount --type java.math.BigDecimal;
description add-field-description --onProperty amountDiscount --title "Discount Amount" --text "Discount amount for this sales order. The sum of all discounts.";
description add-field-description --onProperty amountDiscount --title "Montant Remise" --text "Remise totale de la commande, c'est-à-dire la somme des remise totales des  lignes de commande." --locale fr;
@/* Default=0, remise */;

field number --named amountAfterTax --type java.math.BigDecimal;
description add-field-description --onProperty amountAfterTax --title "Amount after Tax" --text "Total amount after tax for this sales order.";
description add-field-description --onProperty amountAfterTax --title "Montant TTC" --text "Montant total TTC pour cette commande client." --locale fr;
@/* Default=0, Formule=(montant_HT - remise) */;

field number --named otherDiscount --type java.math.BigDecimal;
description add-field-description --onProperty otherDiscount --title "Other Discount" --text "Other form of discount.";
description add-field-description --onProperty otherDiscount --title "Autre Remise" --text "Autre forme de remise sur la commande." --locale fr;
@/* Default=0, other_remise */;

field long --named invoiceId --primitive false;
description add-field-description --onProperty invoiceId --title "Invoice Identifier" --text "The identifier of the invoice associated with this sales order.";
description add-field-description --onProperty invoiceId --title "Identifiant Facture" --text "Idenfiant de la facture liée à cette commande." --locale fr;

field custom --named salesOrderType --type ~.jpa.SalesOrderType;
description add-field-description --onProperty salesOrderType --title "Type" --text "The type of this sales order.";
description add-field-description --onProperty salesOrderType --title "Type" --text "Le type de cette commande client." --locale fr;
enum enumerated-field --onProperty salesOrderType ;
@/* Enumeration{VENTE_AU_PUBLIC, VENTE_A_CREDIT, VENTE_PROFORMAT} */;

field manyToOne --named payingClient --fieldType ~.jpa.Client;
description add-field-description --onProperty payingClient --title "Paying Client" --text "The customer in charge for full or partial payment of the invoice of this order.";
description add-field-description --onProperty payingClient --title "Client Payeur" --text "Le client qui est en charge du payment(total ou partiel) de la facture de la commande." --locale fr;
constraint NotNull --onProperty payingClient; 

field number --named coverageRate --type java.math.BigDecimal;
@/* default=100% */;
description add-field-description --onProperty coverageRate --title "Coverage Rate" --text "The coverage rate (including tax) for this order.";
description add-field-description --onProperty coverageRate --title "Taux Couverture" --text "Taux de couverture TTC pour cette commande." --locale fr;
@/* taux_couverture, Default=100% */;

field boolean --named partialSales --primitive false ; 
description add-field-description --onProperty canceled --title "Partial Sales" --text "Sates if the current order is a partial sales.";
description add-field-description --onProperty canceled --title "VVente Partielle" --text "Indique si la presente commande represente un vente partielle." --locale fr;
@/* default=false */;

field string --named voucherNumber;
description add-field-description --onProperty voucherNumber --title "Voucher Number" --text "The number of the client voucher associated with this order.";
description add-field-description --onProperty voucherNumber --title "Numéro Avoir" --text "Le numéro  de l'avoir client lié à la commande." --locale fr;

field string --named couponNumber;
description add-field-description --onProperty couponNumber --title "Coupon Number" --text "The number of the client coupon associated with this order.";
description add-field-description --onProperty couponNumber --title "Numéro Bon" --text "Le numéro bu bon client lié à la commande." --locale fr;





@/* Sales Order Item */;
entity --named SalesOrderItem --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Sales Order Item" --text "The sales order item.";
description add-class-description  --locale fr --title "Ligne Commande Client" --text "La ligne de command client.";

field string --named soItemNumber;
description add-field-description --onProperty soItemNumber --title "Item Number" --text "The sales order item number.";
description add-field-description --onProperty soItemNumber --title "Numéro de Ligne" --text "Le numéro de categorie client." --locale fr;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this client category.";
description add-field-description --onProperty name --title "Libelle" --text "Le nom de cette categorie client." --locale fr;

field number --named orderedQty --type java.math.BigDecimal;
description add-field-description --onProperty orderedQty --title "Quantity Ordered" --text "The quantity ordered in this line.";
description add-field-description --onProperty orderedQty --title "Quantité Commandés" --text "La quantité commandée dans cette ligne." --locale fr;
@/* Default=0 */;

field number --named returnedQty --type java.math.BigDecimal;
description add-field-description --onProperty returnedQty --title "Quantity Returned" --text "The quantity returned in this line.";
description add-field-description --onProperty returnedQty --title "Quantité Retournée" --text "La quantité retournée dans cette ligne." --locale fr;
@/* Default=0 */;

field temporal --type TIMESTAMP --named recordDate; 
@/* Pattern=d-MM-yyy HH:MM  */;
description add-field-description --onProperty recordDate --title "Record Date" --text "Creation date for this line.";
description add-field-description --onProperty recordDate --title "Date de Saisie" --text "Date de saisie de cette ligne." --locale fr;

field number --named salesPricePU --type java.math.BigDecimal;
description add-field-description --onProperty salesPricePU --title "Sales Price per Unit" --text "The sales price per unit.";
description add-field-description --onProperty salesPricePU --title "Prix de Vente Unitaire" --text "Prix de vente unitaire." --locale fr;
@/*  Default=0 */; 

field number --named unitDiscount --type java.math.BigDecimal;
description add-field-description --onProperty unitDiscount --title "Unit Discount" --text "Discount for one unit of the product in this line.";
description add-field-description --onProperty unitDiscount --title "Remise Unitaire" --text "Remise unitaire pour un produit de cette ligne." --locale fr;

field number --named totalDiscount --type java.math.BigDecimal;
description add-field-description --onProperty totalDiscount --title "Total Discount" --text "Total discount of this line.";
description add-field-description --onProperty totalDiscount --title "Remise Totale" --text "Remise totale pour cette ligne." --locale fr;
@/* Default=0., Formule=(remise * qté_commande) */;

field string --named pic; 
description add-field-description --onProperty pic --title "Product Identification Code" --text "The standard product identification code for a product in this line.";
description add-field-description --onProperty pic --title "Code Identifiant Prouit" --text "Le Code identifiant standard pour un produit dans cette ligne." --locale fr;

field string --named picm; 
description add-field-description --onProperty picm --title "Product Identification Code PO" --text "The standard product identification code for a product in this line.";
description add-field-description --onProperty picm --title "Code Identifiant Prouit PO" --text "Le Code identifiant standard pour un de produit dans cette ligne." --locale fr;

field string --named designation; 
description add-field-description --onProperty designation --title "Designation" --text "The name the product in this order item.";
description add-field-description --onProperty designation --title "Designation" --text "Le nom du produit de la ligne de commande." --locale fr;

field string --named modifyingUser; 
description add-field-description --onProperty modifyingUser --title "Modifying User" --text "The user editing this order item.";
description add-field-description --onProperty modifyingUser --title "Agent de Saisie" --text "Agent de saisie de cette ligne." --locale fr;

@/* Question: Why do we reference the purchase order item instead of referencing the product. */;
@/* Answer: because we need  to manage product expiration date and lots. */;
field manyToOne --named purchaseOrderItem --fieldType ~.jpa.PurchaseOrderItem;
description add-field-description --onProperty purchaseOrderItem --title "Purchase Order Item" --text "The purchase order item of the product to be sold.";
description add-field-description --onProperty purchaseOrderItem --title "Ligne d'Approvisionement" --text "La ligne d'approvisionnement contenant le produit que l'on veut vendre." --locale fr;

field manyToOne --named salesOrder --fieldType ~.jpa.SalesOrder;
description add-field-description --onProperty salesOrder --title "Sales Order" --text "The sales order conatining this line.";
description add-field-description --onProperty salesOrder --title "Commande Client" --text "La commande client contenant cet ligne." --locale fr;

cd ~~;



@/* ======================== */;

@/* Entité Caisse */;
entity --named CashDrawer --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Cash Drawer" --text "A cash drawer.";
description add-class-description  --locale fr --title "Caisse" --text "Une caisse.";

field string --named cashDrawerNumber;
description add-field-description --onProperty cashDrawerNumber --title "Cash Drawer Number" --text "The number of this cash drawer.";
description add-field-description --onProperty cashDrawerNumber --title "Numéro de Caisse" --text "Le numéro de cette caisse." --locale fr;

field manyToOne --named cashier --fieldType ~.jpa.PharmaUser;
description add-field-description --onProperty cashier --title "Cashier" --text "The user collecting the payment on this drawer.";
description add-field-description --onProperty cashier --title "Caissier" --text "Utilisateur percevant le paiement surcette caisse." --locale fr;

field manyToOne --named closedBy --fieldType ~.jpa.PharmaUser;
description add-field-description --onProperty closedBy --title "Closed By" --text "The user who closed this cash drawer.";
description add-field-description --onProperty closedBy --title "Fermé Par" --text "Utilisateur ayant fermé la caisse." --locale fr;

field manyToOne --named site --fieldType ~.jpa.Site ;
constraint NotNull --onProperty site;
description add-field-description --onProperty site --title "Site" --text "Site in which this drawer resides.";
description add-field-description --onProperty site --title "Site" --text "Site dans lequel la caisse est gerée." --locale fr;

field temporal --type TIMESTAMP --named openingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty openingDate --title "Opening Date" --text "The opening date of this drawer.";
description add-field-description --onProperty openingDate --title "Date d'Ouverture" --text "La date d'ouverture de cette caisse." --locale fr;

field temporal --type TIMESTAMP --named closingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty closingDate --title "Closing Date" --text "The closing date of this drawer.";
description add-field-description --onProperty closingDate --title "Date de Fermeture" --text "La date de fermeture de cette caisse." --locale fr;

field number --named initialAmount --type java.math.BigDecimal;
description add-field-description --onProperty initialAmount --title "Initial Amount" --text "The initial amount.";
description add-field-description --onProperty initialAmount --title "Fond de Caisse" --text "Le fond initial de la caisse." --locale fr;
@/* Default=0 */;

field boolean --named opened --primitive false;
description add-field-description --onProperty opened --title "Open" --text "Indicates whether the cash drawer is open.";
description add-field-description --onProperty opened --title "Ouverte" --text "Indique si la caisse est ouverte." --locale fr;
@/* default=true */;

field number --named totalCashIn --type java.math.BigDecimal;
description add-field-description --onProperty totalCashIn --title "Total Cash In" --text "The total cash in.";
description add-field-description --onProperty totalCashIn --title "Total Encaissement" --text "L'encaissement totale." --locale fr;
@/* Default=0 */;

field number --named totalCashOut --type java.math.BigDecimal;
description add-field-description --onProperty totalCashOut --title "Total Cash Out" --text "Total withdrawal from this drawer.";
description add-field-description --onProperty totalCashOut --title "Total Retrait" --text "Total des decaissements éffectués en caisse." --locale fr;
@/* Default=0 */;

field number --named totalCash --type java.math.BigDecimal;
description add-field-description --onProperty totalCash --title "Total Cash" --text "Total cash in this drawer.";
description add-field-description --onProperty totalCash --title "Total Cash" --text "Total cash dans cette caisse." --locale fr;
@/* Default=0 */;

field number --named totalCreditSales --type java.math.BigDecimal;
description add-field-description --onProperty totalCreditSales --title "Total Credit Sales" --text "Total credit sales in this drawer.";
description add-field-description --onProperty totalCreditSales --title "Total Credit" --text "Total credit vendu par cette caisse." --locale fr;
@/* Default=0 */;

field number --named totalCheck --type java.math.BigDecimal;
description add-field-description --onProperty totalCheck --title "Total Checks" --text "Total checks in this drawer.";
description add-field-description --onProperty totalCheck --title "Total Chèque" --text "Total chèque dans cette caisse." --locale fr;
@/* Default=0 */;

field number --named totalCreditCard --type java.math.BigDecimal;
description add-field-description --onProperty totalCreditCard --title "Total Credit Card" --text "Total credit cards by this drawer.";
description add-field-description --onProperty totalCreditCard --title "Total Carte Credit" --text "Total carte de credit par cette caisse." --locale fr;
@/* Default=0 */;

field number --named totalCompanyVoucher --type java.math.BigDecimal;
description add-field-description --onProperty totalCompanyVoucher --title "Total Company Vouchera" --text "Total voucher (from company or hospital) in this drawer.";
description add-field-description --onProperty totalCompanyVoucher --title "Total Avoir Companie" --text "Totale des bons (de sociéte ou d'hopital) qu'il ya en caisse." --locale fr;
@/* Default=0 */;

field number --named totalClientVoucher --type java.math.BigDecimal;
description add-field-description --onProperty totalClientVoucher --title "Total Client Voucher" --text "Total voucher (from client) in this drawer.";
description add-field-description --onProperty totalClientVoucher --title "Total Avoir Client" --text "Totale des bons (client) qu'il ya en caisse." --locale fr;
@/* Default=0 */;

field number --named totalCashDebt --type java.math.BigDecimal;
description add-field-description --onProperty totalCashDebt --title "Total Cash Debt" --text "Total cash from client debts in the drawer.";
description add-field-description --onProperty totalCashDebt --title "Total Cash Dette" --text "Totale cash des dettes client dans cette caisse." --locale fr;
@/* Default=0 */;

@/* Invoice */;

@/* Type Facture*/;
java new-enum-type --named InvoiceType --package ~.jpa ;
enum add-enum-class-description --title "Invoice Type" --text "Type of invoice.";
enum add-enum-class-description  --locale fr --title "Type Facture" --text "Type de facture.";
@/* Enumeration{CAISSE, PROFORMAT} */;
java new-enum-const CASH ;
enum add-enum-constant-description --onConstant CASH --title "Cash" --text "Cash invoice";
enum add-enum-constant-description --locale fr --onConstant CASH --title "Cash" --text "Facture caisse";
java new-enum-const PROFORMA ;
enum add-enum-constant-description --onConstant PROFORMA --title "Pro Forma" --text "Pro forma invoice";
enum add-enum-constant-description --locale fr --onConstant PROFORMA --title "Pro Forma" --text "Facture pro forma";

@/* Entite Ligne Facture */;
entity --named InvoiceItem --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Invoice Item" --text "An invoice item.";
description add-class-description  --locale fr --title "Ligne Facture" --text "Une ligne facture.";

field int --named indexLine; 
description add-field-description --onProperty indexLine --title "Line Index" --text "Index for searching through invoice items";
description add-field-description --onProperty indexLine --title "Index de Ligne" --text "Index permettant de rechercher la ligne de facture" --locale fr;

field string --named pic; 
description add-field-description --onProperty pic --title "Product Identification Code" --text "The standard product identification code for this invoice line.";
description add-field-description --onProperty pic --title "Code Identifiant Prouit" --text "Le Code identifiant standard pour cette ligne de facture." --locale fr;

field string --named picm; 
description add-field-description --onProperty picm --title "Product Identification Code PO" --text "The standard product identification code for this invoice item.";
description add-field-description --onProperty picm --title "Code Identifiant Prouit PO" --text "Le code identifiant standard pour cette ligne de facture." --locale fr;

field string --named designation; 
description add-field-description --onProperty designation --title "Designation" --text "The name the product in this invoice item.";
description add-field-description --onProperty designation --title "Designation" --text "Le nom du produit de la ligne de facture." --locale fr;

field number --named purchasedQty --type java.math.BigDecimal;
description add-field-description --onProperty purchasedQty --title "Quantity Purchased" --text "The quantity purchased in this line.";
description add-field-description --onProperty purchasedQty --title "Quantité Achetée" --text "La quantité achetée dans cette ligne." --locale fr;
@/* Default=0 */;

field number --named returnedQty --type java.math.BigDecimal;
description add-field-description --onProperty returnedQty --title "Quantity Returned" --text "The quantity returned in this line.";
description add-field-description --onProperty returnedQty --title "Quantité Retournée" --text "La quantité retournée dans cette ligne." --locale fr;
@/* Default=0 */;

field number --named salesPricePU --type java.math.BigDecimal;
description add-field-description --onProperty salesPricePU --title "Sales Price per Unit" --text "The sales price per unit for product of this line.";
description add-field-description --onProperty salesPricePU --title "Prix de Vente Unitaire" --text "Prix unitaire du produit de la ligne de facture" --locale fr;
@/*  Default=0 */; 

field number --named discountAmount --type java.math.BigDecimal;
description add-field-description --onProperty discountAmount --title "Discount Amount" --text "Discount amount for this invoice item.";
description add-field-description --onProperty discountAmount --title "Montant Remise" --text "Remise totale de la ligne de facture." --locale fr;
@/* Default=0, montant_remise */;

field number --named totalSalesPrice --type java.math.BigDecimal;
description add-field-description --onProperty totalSalesPrice --title "Total Sales Price" --text "The total sales price for product of this line.";
description add-field-description --onProperty totalSalesPrice --title "Prix de Vente Total" --text "Prix total du produit de la ligne de facture" --locale fr;
@/*  Default=0 */; 



@/* Entité Facture */;
entity --named Invoice --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Invoice" --text "An invoice.";
description add-class-description  --locale fr --title "Facture" --text "Une facture.";

field string --named invoiceNumber;
description add-field-description --onProperty invoiceNumber --title "Invoice Number" --text "The number of the invoice.";
description add-field-description --onProperty invoiceNumber --title "Numéro Facture" --text "Le numéro de cette facture." --locale fr;

field temporal --type TIMESTAMP --named creationDate; 
@/* pattern= dd-MM-yyyy HH:mm*/;
description add-field-description --onProperty creationDate --title "Creation Date" --text "The creation date of this invoicd.";
description add-field-description --onProperty creationDate --title "Date de Création" --text "La date de création de cette facture." --locale fr;

field manyToOne --named client --fieldType ~.jpa.Client;
description add-field-description --onProperty client --title "Client" --text "The client referenced by this invoice.";
description add-field-description --onProperty client --title "Client" --text "Le client mentionné sur la facture." --locale fr;

field manyToOne --named cashDrawer --fieldType ~.jpa.CashDrawer;
description add-field-description --onProperty cashDrawer --title "Cash Drawer" --text "Cash drawer in which the invoice is stored while waiting for payment.";
description add-field-description --onProperty cashDrawer --title "Caisse" --text "Caisse dans laquelle la facture est deposée en attente de paiement." --locale fr;

field manyToOne --named salesStaff --fieldType ~.jpa.PharmaUser;
description add-field-description --onProperty salesStaff --title "Sales Staff" --text "The user creating this invoice.";
description add-field-description --onProperty salesStaff --title "Vendeur" --text "Utilisateur vendeur ayant generé la facture" --locale fr;

field manyToOne --named site --fieldType ~.jpa.Site
description add-field-description --onProperty site --title "Site" --text "Site in which this invoice is generated.";
description add-field-description --onProperty site --title "Site" --text "Site dans lequel la caisse est gerée." --locale fr;

field manyToOne --named salesOrder --fieldType ~.jpa.SalesOrder;
description add-field-description --onProperty client --title "Sales Order" --text "The sales order generating of this invoice.";
description add-field-description --onProperty client --title "Commande Client" --text "Commande client à l'origine de la facture" --locale fr;

field number --named totalSalesPrice --type java.math.BigDecimal;
description add-field-description --onProperty totalSalesPrice --title "Total Sales Price" --text "The total sales price for this invoice.";
description add-field-description --onProperty totalSalesPrice --title "Prix de Vente Total" --text "Prix total de la facture." --locale fr;
@/*  Default=0 */; 

field number --named discountAmount --type java.math.BigDecimal;
description add-field-description --onProperty discountAmount --title "Discount Amount" --text "Discount amount for this invoice.";
description add-field-description --onProperty discountAmount --title "Montant Remise" --text "Remise totale de la facture." --locale fr;
@/* Default=0, montant_remise */;

field number --named netToPay --type java.math.BigDecimal;
description add-field-description --onProperty netToPay --title "Net a Payer" --text "The net amount to pay.";
description add-field-description --onProperty netToPay --title "Net a Payer" --text "Le montant net à payer." --locale fr;
@/*  Default=0 */; 

field boolean --named settled --primitive false; 
description add-field-description --onProperty settled --title "Settled" --text "Sates if the invoice is settled.";
description add-field-description --onProperty settled --title "Soldée" --text "Indique si la facture est soldée ou pas." --locale fr;
@/* default=false */;

field boolean --named cashed --primitive false; 
description add-field-description --onProperty cashed --title "Cashed" --text "Sates if the invoice is cashed.";
description add-field-description --onProperty cashed --title "encaisseé" --text "Indique si la facture est encaissée ou pas." --locale fr;
@/* default=false */;

field number --named advancePayment --type java.math.BigDecimal;
description add-field-description --onProperty advancePayment --title "Advance Payment" --text "The advance payment.";
description add-field-description --onProperty advancePayment --title "Net a Payer" --text "L'avance sur paiement." --locale fr;
@/*  Default=0 */; 

field number --named restToPay --type java.math.BigDecimal;
description add-field-description --onProperty restToPay --title "Ret to Pay" --text "The rest to pay.";
description add-field-description --onProperty restToPay --title "Reste a Payer" --text "Le reste a payer." --locale fr;
@/*  Default=0 */; 


field oneToMany --named invoiceItems --fieldType ~.jpa.InvoiceItem --inverseFieldName invoice --fetchType EAGER  --cascade;
description add-field-description --onProperty invoiceItems --title "Invoice Items" --text "The invoice items";
description add-field-description --onProperty invoiceItems --title "Lignes Facture" --text "Les ligne facture" --locale fr;
@/* OneToMany(cascadeType=All) */;

field custom --named invoiceType --type ~.jpa.InvoiceType;
description add-field-description --onProperty invoiceType --title "Type" --text "The type of this invoice.";
description add-field-description --onProperty invoiceType --title "Type" --text "Le type de cette facture." --locale fr;
enum enumerated-field --onProperty invoiceType ;
@/* Enumeration{CAISSE, PROFORMAT} */;

cd ~~ ;
 


@/* =============================== */;

@/* Type Paiement*/;
java new-enum-type  --named PaymentType --package ~.jpa ;
enum add-enum-class-description --title "Payment Type" --text "Type of payment.";
enum add-enum-class-description  --locale fr --title "Type Paiement" --text "Type de paiement.";
java new-enum-const CASH;
enum add-enum-constant-description --onConstant CASH --title "Cash" --text "Cash Payment";
enum add-enum-constant-description --locale fr --onConstant CASH --title "Caisse" --text "Paiement Caisse";
java new-enum-const CHECK;
enum add-enum-constant-description --onConstant CHECK --title "Check" --text "Check Payement";
enum add-enum-constant-description --locale fr --onConstant CHECK --title "Cheque" --text "Paiement Cheque";
java new-enum-const CREDIT_CARD;
enum add-enum-constant-description --onConstant CREDIT_CARD --title "Credit Card" --text "Credit card payment";
enum add-enum-constant-description --locale fr --onConstant CREDIT_CARD --title "Carte de crédit" --text "Paiement par cartes de crédit.";
java new-enum-const VOUCHER;
enum add-enum-constant-description --onConstant VOUCHER --title "Client Voucher" --text "Client voucher";
enum add-enum-constant-description --locale fr --onConstant VOUCHER --title "Avoir Client" --text "Avoir client";

@/* Entité  paiement */;
entity --named Payment --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Payment" --text "A payment.";
description add-class-description  --locale fr --title "Paiement" --text "Un paiement.";

field string --named paymentNumber;
description add-field-description --onProperty paymentNumber --title "Payment Number" --text "The paiment number.";
description add-field-description --onProperty paymentNumber --title "Numéro du Paiement" --text "Le numéro du paiement." --locale fr;

field temporal --type TIMESTAMP --named paymentDate; 
@/* pattern=dd-MM-yyyy HH:mm*/;
description add-field-description --onProperty paymentDate --title "Payment Date" --text "The payment date.";
description add-field-description --onProperty paymentDate --title "Date de Paiement" --text "La date de paiement." --locale fr;

field temporal --type TIMESTAMP --named recordDate; 
@/* pattern=dd-MM-yyyy HH:mm*/;
description add-field-description --onProperty recordDate --title "Record Date" --text "The record date for this paiement.";
description add-field-description --onProperty recordDate --title "Date de Saisie" --text "La date de saisie du paiement." --locale fr;

field number --named amount --type java.math.BigDecimal;
description add-field-description --onProperty amount --title "Payment Amount" --text "The payment amount.";
description add-field-description --onProperty amount --title "Montant du Paiement" --text "Le montant du paiement." --locale fr;

field number --named receivedAmount --type java.math.BigDecimal;
description add-field-description --onProperty receivedAmount --title "Received Amount" --text "The amount received from the payment.";
description add-field-description --onProperty receivedAmount --title "Montant Reçue" --text "Le montant reçue du paiement." --locale fr;

field number --named difference --type java.math.BigDecimal;
description add-field-description --onProperty difference --title "Difference" --text "The difference (amount returned to payer).";
description add-field-description --onProperty difference --title "Différence" --text "La différence (montant retourné au client)." --locale fr;

field manyToOne --named site --fieldType ~.jpa.Site
constraint NotNull --onProperty site;
description add-field-description --onProperty site --title "Site" --text "Site in which the payment occurs.";
description add-field-description --onProperty site --title "Magasin" --text "Site dans lequel s'effectue le paiement." --locale fr;

field manyToOne --named cashier --fieldType ~.jpa.PharmaUser;
description add-field-description --onProperty cashier --title "Cashier" --text "The user collecting the payment.";
description add-field-description --onProperty cashier --title "Caissier" --text "L'utilisateur percevant le paiement." --locale fr;

field manyToOne --named cashDrawer --fieldType ~.jpa.CashDrawer;
description add-field-description --onProperty cashDrawer --title "Cash Drawer" --text "The cash drawer in use.";
description add-field-description --onProperty cashDrawer --title "Caisse" --text "La caisse utilisé." --locale fr;

cd ../Invoice.java;

field oneToMany --named payments --fieldType ~.jpa.Payment --inverseFieldName invoice --fetchType EAGER  --cascade;
description add-field-description --onProperty payments --title "Payments" --text "Payments associated with this invoice.";
description add-field-description --onProperty payments --title "Piements" --text "Payements associe avec cette facture. " --locale fr;

cd ../Payment.java;

field custom --named paymentType --type ~.jpa.PaymentType;
description add-field-description --onProperty paymentType --title "Payment Type" --text "The type of this payment.";
description add-field-description --onProperty paymentType --title "Type de Paiement" --text "La type de ce paiement." --locale fr;
enum enumerated-field --onProperty paymentType ;

field boolean --named paymentReceiptPrinted --primitive false;
description add-field-description --onProperty paymentReceiptPrinted --title "Open" --text "Indicates whether the payment receipt is printed or not.";
description add-field-description --onProperty paymentReceiptPrinted --title "Ouverte" --text "Indique si le reçu de paiement est imprimé ou pas." --locale fr;
@/* default=false */;

field string --named paidBy;
description add-field-description --onProperty paidBy --title "Paid By" --text "name of the person performing this payment.";
description add-field-description --onProperty paidBy --title "Payé Par" --text "Nom de la personne efectuant ce paiement." --locale fr;

field string --named clientName;
description add-field-description --onProperty clientName --title "Client Name" --text "Name of the client performing this payment.";
description add-field-description --onProperty clientName --title "Nom du Client" --text "Nom du client efectuant ce paiement." --locale fr;

field string --named note;
description add-field-description --onProperty note --title "Note" --text "Description of the payment";
description add-field-description --onProperty note --title "Note" --text "Description du paiement" --locale fr;
constraint Size --onProperty note --max 256;
@/* informations */;

field string --named voucherNumber;
description add-field-description --onProperty voucherNumber --title "Voucher Number" --text "Number of the voucher performing this payment.";
description add-field-description --onProperty voucherNumber --title "Numéro du Bon" --text "Numéro de bon effectuant le paiement." --locale fr;


field number --named voucherAmount --type java.math.BigDecimal;
description add-field-description --onProperty voucherAmount --title "Voucher Amount" --text "The voucher amount for this payment.";
description add-field-description --onProperty voucherAmount --title "Montant du Bon" --text "Le montant du bon pour ce paiement." --locale fr;

field boolean --named compensation --primitive false;
description add-field-description --onProperty compensation --title "Compensation" --text "Indicates whether this payment is a compensation (coupon, voucher).";
description add-field-description --onProperty compensation --title "Compensation" --text "Indique si c est un paiement par compensation de bon avoir ou non." --locale fr;
@/* default=false */;
cd ~~ ;


@/* ================================ */;
@/* Inventories */;

@/* Entite Ligne Inventaire */;
entity --named InventoryItem --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Inventory Item" --text "An inventory item";
description add-class-description  --locale fr --title "Ligne Inventaire" --text "Une ligne inventaire.";

field string --named iiNumber;
description add-field-description --onProperty iiNumber --title "Item Number" --text "The inventory item number.";
description add-field-description --onProperty iiNumber --title "Numéro de Ligne" --text "Le numéro de la ligne d inventaire." --locale fr;

field long --named expectedQty --primitive false;
description add-field-description --onProperty expectedQty --title "Expected Quantity in Stock" --text "The quantity of this article expected to be in stock.";
description add-field-description --onProperty expectedQty --title "Quantité Attendue en Stock" --text "Quantité de produits supposé en le stock." --locale fr;

field long --named asseccedQty --primitive false;
description add-field-description --onProperty asseccedQty --title "Real Quantity" --text "Actual quantity of products physically counted.";
description add-field-description --onProperty asseccedQty --title "Real Quantité" --text "Quantité réelle de produits de la ligne comptés physiquement." --locale fr;

field long --named gap --primitive false;
description add-field-description --onProperty gap --title "Gap" --text "Deviation of access and expected quantity.";
description add-field-description --onProperty gap --title "Écart" --text "Écart de stock de la ligne d inventaire." --locale fr;
@/* formule=(qte_reel - qte_en_stock) */;

field number --named lastSalesPricePU --type java.math.BigDecimal;
description add-field-description --onProperty lastSalesPricePU --title "Last Sales Price per Unit" --text "The last sales price per unit.";
description add-field-description --onProperty lastSalesPricePU --title "Dernier Prix de Vente Unitaire" --text "Le dernier prix de vente unitaire." --locale fr;
@/*  Default=0 */; 

field number --named totalPrice --type java.math.BigDecimal;
description add-field-description --onProperty totalPrice --title "Total Price" --text "The total price.";
description add-field-description --onProperty totalPrice --title "Prix Total" --text "Le prix total." --locale fr;
@/*  Default=0, formule=(prix_unitaire*ecart) */;

field manyToOne --named recordingUser --fieldType ~.jpa.PharmaUser;
description add-field-description --onProperty recordingUser --title "Recording User" --text "The user recording this inventory item.";
description add-field-description --onProperty recordingUser --title "Agent Saisie" --text "Responsable de la saisie de la ligne d inventaire" --locale fr;

field temporal --type TIMESTAMP --named recordingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty recordingDate --title "Recording Date" --text "The recording date of this inventory item.";
description add-field-description --onProperty recordingDate --title "Date de Saisie" --text "La date de saisie de cette ligne inventaire." --locale fr;

field manyToOne --named article --fieldType ~.jpa.Article
constraint NotNull --onProperty article
description add-field-description --onProperty article --title "Article" --text "The product associated with this inventory line.";
description add-field-description --onProperty article --title "Produit" --text "Le produit attaché à la ligne d inventaire." --locale fr;


@/* Entite Inventaire */;
entity --named Inventory --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Inventory" --text "An inventory";
description add-class-description  --locale fr --title "Inventaire" --text "Un inventaire.";

field string --named inventoryNumber;
description add-field-description --onProperty inventoryNumber --title "Inventory Number" --text "The inventory number.";
description add-field-description --onProperty inventoryNumber --title "Numéro d Inventaire" --text "Le numéro d inventaire." --locale fr;

field string --named purchaseOrderNumber;
description add-field-description --onProperty purchaseOrderNumber --title "Purchase Order Number" --text "Identifier of a purchase order following an inventory.";
description add-field-description --onProperty purchaseOrderNumber --title "Numéro d Approvisionement" --text "Identifiant de l approvisionement suite à un inventaire" --locale fr;

field manyToOne --named recordingUser --fieldType ~.jpa.PharmaUser;
description add-field-description --onProperty recordingUser --title "Recording User" --text "The user recording this inventory.";
description add-field-description --onProperty recordingUser --title "Agent Saisie" --text "L utilisateur saisissant cet inventaire." --locale fr;

field number --named amount --type java.math.BigDecimal;
description add-field-description --onProperty amount --title "Amount" --text "The amount of this inventory.";
description add-field-description --onProperty amount --title "Montant" --text "Le montant de cet inventaire." --locale fr;

field custom --named inventoryStatus --type ~.jpa.DocumentProcessingState.java;
description add-field-description --onProperty inventoryStatus --title "Inventory Status" --text "The status of this inventory.";
description add-field-description --onProperty inventoryStatus --title "État d Inventaire" --text "L état de cet inventaire." --locale fr;
enum enumerated-field --onProperty inventoryStatus ;

field string --named note;
description add-field-description --onProperty note --title "Note" --text "Description of this inventory.";
description add-field-description --onProperty note --title "Note" --text "Description de cet inventaire." --locale fr;
constraint Size --onProperty note --max 256;

field temporal --type TIMESTAMP --named inventoryDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty inventoryDate --title "Inventory Date" --text "The date of this inventory.";
description add-field-description --onProperty inventoryDate --title "Date d Inventaire" --text "La date de cet inventaire." --locale fr;

field manyToOne --named site --fieldType ~.jpa.Site;
constraint NotNull --onProperty site;
description add-field-description --onProperty site --title "Site" --text "The site of this inventory.";
description add-field-description --onProperty site --title "Magasin" --text "Le site de cet inventaire." --locale fr;

cd ../Inventory.java;

field oneToMany --named inventoryItems --fieldType ~.jpa.InventoryItem --inverseFieldName inventory --fetchType EAGER  --cascade;
description add-field-description --onProperty inventoryItems --title "Inventory Items" --text "The inventory items";
description add-field-description --onProperty inventoryItems --title "Lignes Inventaire" --text "Les ligne d inventaire" --locale fr;


@/* Entité TransformationProduit */;
entity --named ProductTransformationConfig --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Product Transformation" --text "Product transformation";
description add-class-description  --locale fr --title "Transformation Produit" --text "Transformation produit";

field string --named ptNumber;
description add-field-description --onProperty ptNumber --title "Product Transformation Number" --text "The product transformation number.";
description add-field-description --onProperty ptNumber --title "Numéro Transformation Produit" --text "Le numéro de transformation produit." --locale fr;

field oneToOne --named source --fieldType ~.jpa.Article;
constraint NotNull --onProperty source;
description add-field-description --onProperty source --title "Source Product" --text "The compound product to be decompose.";
description add-field-description --onProperty source --title "Produit Origine" --text "Le produit Composant que l on veut decomposer." --locale fr;

field oneToOne --named target --fieldType ~.jpa.Article;
constraint NotNull --onProperty target;
description add-field-description --onProperty target --title "Target Product" --text "The compound product generated from the decomposition.";
description add-field-description --onProperty target --title "Produit Cible" --text "Le produit composé generé à partir de la décomposition." --locale fr;

field number --named targetQuantity --type java.math.BigDecimal;
description add-field-description --onProperty targetQuantity --title "Target Quantity" --text "Quantity of the target product";
description add-field-description --onProperty targetQuantity --title "Quantité Cible" --text "Quantité du produit cible.." --locale fr;
constraint NotNull --onProperty targetQuantity;

field number --named salesPrice --type java.math.BigDecimal;
description add-field-description --onProperty salesPrice --title "Sales Price" --text "Sales price.";
description add-field-description --onProperty salesPrice --title "Prix de Vente" --text "Prix de vente." --locale fr;
constraint NotNull --onProperty salesPrice;

field boolean --named active --primitive false;
description add-field-description --onProperty active --title "Active" --text "Alows activation or deactivation of this transformation.";
description add-field-description --onProperty active --title "Actif" --text "Permet d activer ou de desactiver la transformation.." --locale fr;
@/* Default= true */;

cd ~~ ;


@/* ====================================== */;
@/* Accounts Receivable */;

@/* VoucherType */;
java new-enum-type --named VoucherType --package ~.jpa ;
enum add-enum-class-description --title "Voucher Type" --text "The type of a voucher.";
enum add-enum-class-description  --locale fr --title "Type de Bon" --text "Le type d un avoir.";
java new-enum-const CREATION;
enum add-enum-constant-description --onConstant CREATION --title "Creation" --text "Creation";
enum add-enum-constant-description --locale fr --onConstant CREATION --title "Création" --text "Création";
java new-enum-const RETURN;
enum add-enum-constant-description --onConstant RETURN --title "Return" --text "Return";
enum add-enum-constant-description --locale fr --onConstant RETURN --title "Retour" --text "Retour";

@/* Entité AvoirClient */;
entity --named ClientVoucher --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Client Voucher" --text "Client voucher";
description add-class-description  --locale fr --title "Avoir Client" --text "Avoir client";

field string --named voucherNumber;
description add-field-description --onProperty voucherNumber --title "Voucher Number" --text "The client voucher number.";
description add-field-description --onProperty voucherNumber --title "Numéro de l Avoir" --text "Le numéro de l avoir du client." --locale fr;

field temporal --type TIMESTAMP --named modifDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty modifDate --title "Modified" --text "Modification date.";
description add-field-description --onProperty modifDate --title "Modifié" --text "Date d edition de l avoir." --locale fr;

field number --named amount --type java.math.BigDecimal;
description add-field-description --onProperty amount --title "Amount" --text "Voucher amount.";
description add-field-description --onProperty amount --title "Montant" --text "Montant de l avoir" --locale fr;
constraint NotNull --onProperty amount;

field string --named clientName;
description add-field-description --onProperty clientName --title "Client Name" --text "The name of the client holding this voucher.";
description add-field-description --onProperty clientName --title "Nom Client" --text "Nom du client qui possède l avoir." --locale fr;

field string --named clientNumber;
description add-field-description --onProperty clientNumber --title "Client Number" --text "The number of the client holding this voucher.";
description add-field-description --onProperty clientNumber --title "Numéro Client" --text "Le numéro du client qui possède l avoir." --locale fr;

field boolean --named canceled --primitive false; 
description add-field-description --onProperty canceled --title "Canceled" --text "Sates if the voucher was canceled or not.";
description add-field-description --onProperty canceled --title "Annulé" --text "Indique si l avoir  a été annulé ou non." --locale fr;
@/* default=false */;

@/* Question: why no relationshitp to pharma user */;
field string --named recordingUser;
description add-field-description --onProperty recordingUser --title "User" --text "The user modifying this voucher.";
description add-field-description --onProperty recordingUser --title "Agent" --text "L agent de saisie ayant édité cet avoir." --locale fr;
@/* default=getUserName() */;

field temporal --type TIMESTAMP --named modifiedDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty modifiedDate --title "Last Modified" --text "The last modification date.";
description add-field-description --onProperty modifiedDate --title "Derniere Edition" --text "La data de derniere edition de l avoir." --locale fr;

field custom --named voucherType --type ~.jpa.VoucherType;
description add-field-description --onProperty voucherType --title "Voucher Type" --text "The type of this voucher.";
description add-field-description --onProperty voucherType --title "Type de Bon Avoir" --text "Le type de ce bon avoir." --locale fr;
enum enumerated-field --onProperty voucherType ;

field number --named amountUsed --type java.math.BigDecimal;
description add-field-description --onProperty amountUsed --title "Amount Used" --text "Amount used.";
description add-field-description --onProperty amountUsed --title "Montant Utilisé" --text "Montant utilisé." --locale fr;
@/* Default=0 */;

field boolean --named settled --primitive false; 
description add-field-description --onProperty settled --title "Settled" --text "Sates if the voucher is settled or not.";
description add-field-description --onProperty settled --title "Soldé" --text "Indique si l avoir est soldé ou non." --locale fr;
@/* default=false */;

field number --named restAmount --type java.math.BigDecimal;
description add-field-description --onProperty restAmount --title "Rest Amount" --text "The remaining credit on this voucher.";
description add-field-description --onProperty restAmount --title "Montant Restant" --text "Rest de l avoir client" --locale fr;
@/* Default=0 */;

field boolean --named voucherPrinted --primitive false;
description add-field-description --onProperty voucherPrinted --title "Printed" --text "Indicates whether the voucher is printed or not.";
description add-field-description --onProperty voucherPrinted --title "Imprimé" --text "Indique si l avoir est imprimé ou pas." --locale fr;
@/* default=false */;


@/* Entité DetteClient */;
entity --named ClientDebt --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Client Debt" --text "Client debt";
description add-class-description  --locale fr --title "Dette Client" --text "Dette client";

@/* Question: why not the reference to the invoice */;
field manyToOne --named invoice --fieldType ~.jpa.Invoice.java ;
description add-field-description --onProperty invoice --title "Invoice " --text "the invoice associated with this debt.";
description add-field-description --onProperty invoice --title "Facture" --text "la facture client associe avec cette dette." --locale fr;

@/* Question: why not the reference to the client */;
field string --named clientNumber;
field manyToOne --named client --fieldType ~.jpa.Client.java ;
description add-field-description --onProperty client --title "Client " --text "the client associated with this debt.";
description add-field-description --onProperty client --title "Numéro " --text "Le client associe avec cette dette." --locale fr;

field string --named clientName;
description add-field-description --onProperty clientName --title "Client Name" --text "The name of the client associated with this debt.";
description add-field-description --onProperty clientName --title "Nom du Client" --text "Le nom du client associe avec cette dette." --locale fr;

field temporal --type TIMESTAMP --named creationDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty creationDate --title "Creation Date" --text "The creation date of this debt.";
description add-field-description --onProperty creationDate --title "Date de Creation" --text "La date de creation de cette dette." --locale fr;

field temporal --type TIMESTAMP --named endDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty endDate --title "End Date" --text "The end date for this debt";
description add-field-description --onProperty endDate --title "Date de Fin" --text "La date de fin de la dette." --locale fr;

field number --named initialAmount --type java.math.BigDecimal;
description add-field-description --onProperty initialAmount --title "Initial Amount" --text "Initial amount of this debt.";
description add-field-description --onProperty initialAmount --title "Montant Initial" --text "Montant initial de la dette." --locale fr;

field number --named advancePayment --type java.math.BigDecimal;
description add-field-description --onProperty advancePayment --title "Advance Payment" --text "Advance payment.";
description add-field-description --onProperty advancePayment --title "Montant Avancé" --text "Montant avancé." --locale fr;

field number --named restAmount --type java.math.BigDecimal;
description add-field-description --onProperty restAmount --title "Rest Amount" --text "The remaining amount of this debt.";
description add-field-description --onProperty restAmount --title "Montant Restant" --text "Le montant restant a payer pour cette dette." --locale fr;
@/* Default=0 */;

field boolean --named settled --primitive false; 
description add-field-description --onProperty settled --title "Settled" --text "Sates if the debt is settled or not.";
description add-field-description --onProperty settled --title "Soldé" --text "Indique si la dette est soldé ou non." --locale fr;
@/* default=false */;

field temporal --type TIMESTAMP --named lastPaymentDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty lastPaymentDate --title "Last Payment Date" --text "The last payment date.";
description add-field-description --onProperty lastPaymentDate --title "Date Dernier Versement " --text "La date du dernier versement." --locale fr;

field boolean --named canceled --primitive false; 
description add-field-description --onProperty canceled --title "Canceled" --text "Sates if the debt is canceled or not.";
description add-field-description --onProperty canceled --title "Annulée" --text "Indique si la dette est annulée ou non." --locale fr;
@/* default=false */;

field temporal --type TIMESTAMP --named paymentDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty paymentDate --title "Payment Date" --text "Date the debt was paid";
description add-field-description --onProperty paymentDate --title "Date Paiement" --text "Date à laquelle la dette a été payée" --locale fr;

@/* Entité EtatCredits */;
entity --named DebtStatement --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Debt Statement" --text "The sum of all the debts of a client";
description add-class-description  --locale fr --title "Etat Credits" --text "Le cumul de toutes les dettes d un client";

field string --named statementNumber;
description add-field-description --onProperty statementNumber --title "Statement Number" --text "The number identifying this statement.";
description add-field-description --onProperty statementNumber --title "Numéro de l État" --text "Le numéro identifiant cet état." --locale fr;

field manyToOne --named client --fieldType ~.jpa.Client;
description add-field-description --onProperty client --title "Client" --text "The client carrying this debt.";
description add-field-description --onProperty client --title "Client" --text "Le client portant cette dette." --locale fr;

field temporal --type TIMESTAMP --named modifDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty modifDate --title "Modified" --text "Modification date.";
description add-field-description --onProperty modifDate --title "Modifié" --text "Date d edition de cet état." --locale fr;

field temporal --type TIMESTAMP --named paymentDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty paymentDate --title "Payment Date" --text "Date these debts was paid.";
description add-field-description --onProperty paymentDate --title "Date Paiement" --text "Date à laquelle ces dettes ont été payées." --locale fr;

field number --named initialAmount --type java.math.BigDecimal;
description add-field-description --onProperty initialAmount --title "Initial Amount" --text "Initial amount of this statement.";
description add-field-description --onProperty initialAmount --title "Montant Initial" --text "Montant initial de cet état." --locale fr;

field number --named advancePayment --type java.math.BigDecimal;
description add-field-description --onProperty advancePayment --title "Advance Payment" --text "Advance payment.";
description add-field-description --onProperty advancePayment --title "Montant Avancé" --text "Montant avancé." --locale fr;

field number --named restAmount --type java.math.BigDecimal;
description add-field-description --onProperty restAmount --title "Rest Amount" --text "The remaining amount of this debt.";
description add-field-description --onProperty restAmount --title "Montant Restant" --text "Le montant restant a payer pour cette dette." --locale fr;
@/* Default=0 */;

field boolean --named settled --primitive false; 
description add-field-description --onProperty settled --title "Settled" --text "Sates if the statement is settled or not.";
description add-field-description --onProperty settled --title "Soldé" --text "Indique si cet état est soldé ou non." --locale fr;
@/* default=false */;

field number --named amountFromVouchers --type java.math.BigDecimal;
description add-field-description --onProperty amountFromVouchers --title "Rest Amount" --text "Amount of of vouchers if the customer uses vouchers to pay these debts.";
description add-field-description --onProperty amountFromVouchers --title "Montant Restant" --text "Montant de l avoir si le client en possède utilisé pour rembourser les dettes." --locale fr;
@/* Default=0 */;

field boolean --named canceled --primitive false; 
description add-field-description --onProperty canceled --title "Canceled" --text "Sates if the statement is canceled or not.";
description add-field-description --onProperty canceled --title "Annulée" --text "Precise si l états a été annulée ou non." --locale fr;
@/* default=false */;

field boolean --named useVoucher --primitive false; 
description add-field-description --onProperty useVoucher --title "Use Vouchers" --text "Specifies whether the client can use his voucher to pay its debts.";
description add-field-description --onProperty useVoucher --title "Consommer Avoir" --text "Indique si le client peut consommer ou non ses avoirs pour payer ses dettes." --locale fr;
@/* default=false */;

cd ../ClientDebt.java;

field manyToOne --named debtStatement --fieldType ~.jpa.DebtStatement;
description add-field-description --onProperty debtStatement --title "Debt Statement" --text "The debt statement containing this debt.";
description add-field-description --onProperty debtStatement --title "Etat Credits" --text "L état crédit contenant cette dette." --locale fr;
cd ~~;
 

@/* ================================== */;
@/* Prescriptions */;

@/* Entité Ordonnanciers */;
entity --named PrescriptionBook --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Prescription Blook" --text "Prescription book";
description add-class-description  --locale fr --title "Ordonnancier" --text "Ordonnancier";

field string --named prescriber;
description add-field-description --onProperty prescriber --title "Prescriber" --text "The doctor who prescribed the order.";
description add-field-description --onProperty prescriber --title "Prescripteur" --text "Le medecin ayant prescrit l ordonnance." --locale fr;
constraint NotNull --onProperty prescriber;

field string --named hospital;
description add-field-description --onProperty hospital --title "Hospital" --text "The hospital subjet of this prescription.";
description add-field-description --onProperty hospital --title "Hopital" --text "L hopital ayant prescrit l ordonnance." --locale fr;
constraint NotNull --onProperty hospital;

field string --named patientName;
description add-field-description --onProperty patientName --title "Patient Name" --text "The patient who received the prescription.";
description add-field-description --onProperty patientName --title "patient Name" --text "Le patient ayant reçu l ordonnance." --locale fr;

@/* Question: why don t we have a foreign key relationship to pharmaUser */;
@/* Answer : you have raison  */;

field manyToOne --named recordingAgent --fieldType ~.jpa.PharmaUser.java;
description add-field-description --onProperty recordingAgent --title "Recording Agent" --text "The user who recorded this prescription.";
description add-field-description --onProperty recordingAgent --title "Agent Saisie" --text "L utilisateur saisiessant cet ordonnance." --locale fr;

field string --named prescriptionNumber;
description add-field-description --onProperty prescriptionNumber --title "Prescription Number" --text "The prescription number.";
description add-field-description --onProperty prescriptionNumber --title "Numéro de l Ordonnance" --text "Le numéro de l ordonnance." --locale fr;

field oneToOne --named salesOrder --fieldType ~.jpa.SalesOrder;
constraint NotNull --onProperty salesOrder;
description add-field-description --onProperty salesOrder --title "Sales Order" --text "The sales order containing this prescription.";
description add-field-description --onProperty salesOrder --title "Commande Client" --text "La commandeclient qui contient l ordonnance." --locale fr;

field temporal --type TIMESTAMP --named prescriptionDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty prescriptionDate --title "Prescription Date" --text "The prescription date.";
description add-field-description --onProperty prescriptionDate --title "Date de Prescription" --text "La date de prescription." --locale fr;

field temporal --type TIMESTAMP --named recordingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty recordingDate --title "Recording Date" --text "The recording date.";
description add-field-description --onProperty recordingDate --title "Date de Saisie" --text "La date de saisie." --locale fr;
cd ~~;

envers audit-package --jpaPackage src/main/java/org/adorsys/adph/server/jpa/ ;
cd ~~ ;
repogen setup ;
repogen new-repository --jpaPackage src/main/java/org/adorsys/adph/server/jpa/;
reporest setup --activatorType APP_CLASS ;
reporest endpoint-from-entity --jpaPackage src/main/java/org/adorsys/adph/server/jpa/ --overrride --contentType application/xml ;
 

cd ~~;

mvn clean install -DskipTests;




 






















