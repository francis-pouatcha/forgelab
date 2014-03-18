field temporal --type TIMESTAMP --named recordingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty recordingDate --title "Recording Date" --text "The recording date ";
description add-field-description --onProperty recordingDate --title "Date de Saisie" --text "La date de saisie ." --locale fr;
format add-date-pattern --onProperty recordingDate --pattern "dd-MM-yyyy HH:mm"; 



field string --named invoiceNumber;
description add-field-description --onProperty invoiceNumber --title "Invoice Number" --text "The number of the invoice.";
description add-field-description --onProperty invoiceNumber --title "Numéro Facture" --text "Le numéro de cette facture." --locale fr;
display add-toString-field --field invoiceNumber;
display add-list-field --field invoiceNumber;


field number --named amountVAT --type java.math.BigDecimal;
description add-field-description --onProperty amountVAT --title "Amount VAT" --text "Total amount VAT for this sales order.";
description add-field-description --onProperty amountVAT --title "Montant TVA" --text "Montant total TVA pour cette commande client." --locale fr;
format add-number-type --onProperty amountVAT --type CURRENCY ;
@/* Default=0, montant_tva */;
display add-list-field --field amountVAT;


field manyToOne --named equivalentArticle --fieldType ~.jpa.Article;
description add-field-description --onProperty equivalentArticle --title "Equivalent Article" --text "The Equivalent Article";
description add-field-description --onProperty equivalentArticle --title "Article Equivalent" --text "Article equivalent." --locale fr;
association set-selection-mode --onProperty equivalentArticle --selectionMode COMBOBOX;
association set-type --onProperty equivalentArticle --type AGGREGATION --targetEntity ~.jpa.Article;
display add-toString-field --field equivalentArticle.articleName;
display add-list-field --field equivalentArticle.articleName;


field custom --named paymentMode --type ~.jpa.PaymentMode;
description add-field-description --onProperty paymentMode --title "Payment Mode" --text "The Mode of this payment.";
description add-field-description --onProperty paymentMode --title "Mode de Paiement" --text "Le Mode de paiement." --locale fr;
enum enumerated-field --onProperty paymentMode ;
display add-list-field --field paymentMode;

constraint NotNull --onProperty agency;
description add-notNull-message --onProperty agency --title "The agency  is required" --text "The agency  is required";
description add-notNull-message --onProperty agency --title "Le Agence est réquis" --text "l agence est requis" --locale fr;