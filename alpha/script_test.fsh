
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


@/* Entity Company */;
entity --named Company --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Company" --text "The Company";
description add-class-description  --locale fr --title "Compagnie" --text "La compagnie";

field string --named displayName;
description add-field-description --onProperty displayName --title "Name" --text "The site name";
description add-field-description --onProperty displayName --title "Nom" --text "Nom du site" --locale fr;
constraint NotNull --onProperty displayName;

field manyToOne --named address --fieldType ~.jpa.Address;
description add-field-description --onProperty address --title "Address" --text "The site address";
description add-field-description --onProperty address --title "Adresse" --text "Adresse du site" --locale fr;
constraint Size --onProperty address --max 256;

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
description add-field-description --onProperty siteInternet --title "Site Internet" --text "Site internet du site" --locale fr;

field string --named mobile;
description add-field-description --onProperty mobile --title "Mobile Phone" --text "The mobile phone of the site";
description add-field-description --onProperty mobile --title "Téléphone Mobile" --text "Téléphone Mobile du site" --locale fr;

field string --named fax;
description add-field-description --onProperty fax --title "Fax" --text "The fax number of the site";
description add-field-description --onProperty fax --title "Fax" --text "Fax du site" --locale fr;

field string --named registerNumber;
description add-field-description --onProperty registerNumber --title "Register Number" --text "The register number of the site";
description add-field-description --onProperty registerNumber --title "Numéro de Registre" --text "Numéro de registre du site" --locale fr;

cd ~~;



@/* Entity Gender */;
java new-enum-type --named Gender --package ~.jpa ;
enum add-enum-class-description --title "Gender" --text "The gender of a user.";
enum add-enum-class-description  --locale fr --title "Genre" --text "Le genre de cet utilisateur.";

java new-enum-const Masculin ;
java new-enum-const Feminin ;
java new-enum-const Neutre ;
enum generate-description-keys --locale fr ;
enum generate-description-keys ;



@/* Entity Filiale */;
entity --named Agency --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Agency" --text "An agency of this pharmacie";
description add-class-description  --locale fr --title "Filiale" --text "une filiale de cette pharmacie";

field string --named agencyNumber;
description add-field-description --onProperty agencyNumber --title "Agency Number" --text "The number of this agency";
description add-field-description --onProperty agencyNumber --title "Numéro Agence" --text "Numéro de la filiale" --locale fr;
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

field manyToOne --named address --fieldType ~.jpa.Address;
description add-field-description --onProperty address --title "Address" --text "The address of this Agency.";
description add-field-description --onProperty address --title "Adresse" --text "Adresse de cette agence" --locale fr;

field manyToOne --named company --fieldType ~.jpa.Company;
description add-field-description --onProperty company --title "Company" --text "The company to which this Agency depend.";
description add-field-description --onProperty company --title "Adresse" --text "la company a laquelle cette agence depend" --locale fr;

field string --named ticketMessage;
description add-field-description --onProperty ticketMessage --title "Ticket Message" --text "The  message for ticker";
description add-field-description --onProperty ticketMessage --title "Message Ticket" --text "le message du ticket" --locale fr;


field string --named invoiceMessage;
description add-field-description --onProperty invoiceMessage --title "Message Facture" --text "The message for invoice";
description add-field-description --onProperty invoiceMessage --title "Invoice Message" --text "Le message Pour la facture" --locale fr;


@/* Entity Currency */;
entity --named Currency --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Currency" --text "The currency";
description add-class-description  --locale fr --title "Devise" --text "La devise";

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this currency.";
description add-field-description --onProperty name --title "Libelle" --text "Le nom de cette devise." --locale fr;
constraint NotNull --onProperty name;

field string --named shortName;
description add-field-description --onProperty shortName --title "Short Name" --text "The short name of this currency.";
description add-field-description --onProperty shortName --title "Libelle Court" --text "Abréviation du nom de cette devise." --locale fr;
constraint NotNull --onProperty shortName;

field number --named cfaEquivalent --type java.math.BigDecimal;
description add-field-description --onProperty cfaEquivalent --title "CFA Equivalent" --text "Corresponding CFA value for conversions.";
description add-field-description --onProperty cfaEquivalent --title "Equivalent CFA" --text "Valeur equivalant cfa pour faire les conversions." --locale fr;

cd ~~ ;

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

@/* Entity Users */;
entity --named Users --package ~.jpa --idStrategy AUTO;
description add-class-description --title "User" --text "A user of this application";
description add-class-description  --locale fr --title "Utilisateur" --text "Un utilisateur de cette application.";

field custom --named gender --type ~.jpa.Gender;
description add-field-description --onProperty gender --title "Gender" --text "The gender of this user";
description add-field-description --onProperty gender --title "Genre" --text "Le genre de cet utilisateur" --locale fr;
enum enumerated-field --onProperty gender ;

field string --named userName;
description add-field-description --onProperty userName --title "User Name" --text "The name used by the user to login.";
description add-field-description --onProperty userName --title "Nom de Connection" --text "Le nom de connexion de cet utilisateur." --locale fr;
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
description add-field-description --onProperty roleNames --title "Roles" --text "Les nom de role attribués a de cet utilisateur" --locale fr;

field string --named phoneNumber;
description add-field-description --onProperty phoneNumber --title "Phone Number" --text "The phone number of this user.";
description add-field-description --onProperty phoneNumber --title "Numéro de Telephone" --text "Numéro de téléphone de cet utilisateur." --locale fr;

field boolean --named disableLogin --primitive false;
@/*  default=true */;
description add-field-description --onProperty disableLogin --title "Disable Login" --text "Indicates whether the user login is disabled.";
description add-field-description --onProperty disableLogin --title "Login Desactivé" --text "Indique si le login de cet utilisateur est desactivé ou non." --locale fr;

field boolean --named accountLocked --primitive false;
@/*  default=false */;
description add-field-description --onProperty accountLocked --title "Account Locked" --text "Indicates whether the user account is locked.";
description add-field-description --onProperty accountLocked --title "Compte Bloqué" --text "Indique si le compte(login+password) est bloqué ou pas." --locale fr;

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

field manyToOne --named address --fieldType ~.jpa.Address;
description add-field-description --onProperty address --title "Address" --text "The address of this user.";
description add-field-description --onProperty address --title "Adresse" --text "Adresse de cet utilisateur" --locale fr;

field string --named email;
description add-field-description --onProperty email --title "Email" --text "The email address of this user..";
description add-field-description --onProperty email --title "Email" --text "Adresse email de cet utilisateur" --locale fr;

field manyToMany --named agency --fieldType ~.jpa.Agency;
description add-field-description --onProperty agency --title "Agency" --text "The agency in which the user is registered.";
description add-field-description --onProperty agency --title "Agence" --text "l Agence dans lequel cet utilisateur est enregistré." --locale fr;

field number --named discountRate --type java.math.BigDecimal;
description add-field-description --onProperty discountRate --title "Discount Rate" --text "The discount rate this user can give to clients.";
description add-field-description --onProperty discountRate --title "Taux Remise" --text "Taux de remise que cet utilisateur peut accorder aux clients." --locale fr;
@/* Default= 15% */;

field string --named saleKey;
description add-field-description --onProperty saleKey --title "Sale Key" --text "The sales key of saller for a sales session open to all users. ";
description add-field-description --onProperty saleKey --title "Sale Key" --text "Clé de vente du vendeur pour la session vente ouverte à tous les utilisateurs." --locale fr;

cd ~~;

@/* Entity Section */;
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

field string --named description;
description add-field-description --onProperty description --title "Description" --text "Description of the section";
description add-field-description --onProperty description --title "Description" --text "Description du rayon" --locale fr;
constraint Size --onProperty description --max 256;

field manyToOne --named agency --fieldType ~.jpa.Agency;
constraint NotNull --onProperty agency;
description add-field-description --onProperty agency --title "Agency" --text "Agency in which the section is located";
description add-field-description --onProperty agency --title "Agency" --text "Agency dans lequel le rayon se trouve." --locale fr;

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

field string --named description;
constraint Size --onProperty description --max 256;
description add-field-description --onProperty description --title "Description" --text "The description of this product family";
description add-field-description --onProperty description --title "Description" --text "La description de la famille produit." --locale fr;

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

field boolean --named active --primitive false;
description add-field-description --onProperty active --title "Active" --text "Says if this VAT is active or not";
description add-field-description --onProperty active --title "Actif" --text "Indique si la TVA est Actif ou pas." --locale fr;

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

field boolean --named active --primitive false;
description add-field-description --onProperty active --title "Active" --text "Says if this margin rate is Active or not";
description add-field-description --onProperty active --title "Actif" --text "Indique si ce taux de marge est actif ou pas." --locale fr;
cd ~~ ;

