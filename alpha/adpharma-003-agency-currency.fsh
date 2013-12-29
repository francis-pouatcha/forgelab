
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
