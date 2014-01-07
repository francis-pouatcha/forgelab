@/* Entity VAT */;
entity --named VAT --package ~.jpa --idStrategy AUTO;
description add-class-description --title "VAT" --text "The value added tax";
description add-class-description  --locale fr --title "TVA" --text "Taxe sur valeure ajoutée";

field string --named code;
description add-field-description --onProperty code --title "Code" --text "The code of this VAT";
description add-field-description --onProperty code --title "Code" --text "Le code de la TVA" --locale fr;

field number --named rate --type java.math.BigDecimal;
description add-field-description --onProperty rate --title "Rate" --text "The VAT rate";
description add-field-description --onProperty rate --title "Taux" --text "Taux de la TVA" --locale fr;

field boolean --named valid;
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

field boolean --named valid;
description add-field-description --onProperty valid --title "Valid" --text "Says if this margin rate is valide or not";
description add-field-description --onProperty valid --title "Valide" --text "Indique si ce taux de marge est valide ou pas." --locale fr;

