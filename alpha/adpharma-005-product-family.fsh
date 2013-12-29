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



@/* Entity ProductSubFamily */;
entity --named ProductSubFamily --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Product Sub Family" --text "The product sub family";
description add-class-description  --locale fr --title "Sous Famille Produit" --text "La sous famille produit";

field string --named code;
description add-field-description --onProperty code --title "Code" --text "The code of this product sub family";
description add-field-description --onProperty code --title "Code" --text "Le code de la sous famille du produit" --locale fr;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this product sub family";
description add-field-description --onProperty name --title "Libelle" --text "Le nom de la sous famille produit" --locale fr;

field string --named shortName;
description add-field-description --onProperty shortName --title "Short Name" --text "The short name of this product sub family";
description add-field-description --onProperty shortName --title "Libelle Court" --text "Abréviation du nom de la sous famille produit." --locale fr;

field string --named note;
constraint Size --onProperty note --max 256;
description add-field-description --onProperty note --title "Short Name" --text "The description of this product sub family";
description add-field-description --onProperty note --title "Libelle Court" --text "La description de la sous famille produit." --locale fr;

field manyToOne --named family --fieldType ~.jpa.ProductFamily;
description add-field-description --onProperty family --title "Family" --text "Specifies the product family enclosing this sub family.";
description add-field-description --onProperty family --title "Famille" --text "Spécifie la sous famille de produit à laquelle appartient cette sous famille" --locale fr;
constraint NotNull --onProperty family;

