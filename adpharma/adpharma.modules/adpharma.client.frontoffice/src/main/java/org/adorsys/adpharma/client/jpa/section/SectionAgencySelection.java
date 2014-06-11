package org.adorsys.adpharma.client.jpa.section;

import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class SectionAgencySelection extends AbstractSelection<Section, Agency>
{

   private ComboBox<SectionAgency> agency;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class, Section.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      agency = viewBuilder.addComboBox("Section_agency_description.title", "agency", resourceBundle, false);

      agency.setCellFactory(new Callback<ListView<SectionAgency>, ListCell<SectionAgency>>()
      {
         @Override
         public ListCell<SectionAgency> call(ListView<SectionAgency> listView)
         {
            return new SectionAgencyListCell();
         }
      });
      agency.setButtonCell(new SectionAgencyListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Section model)
   {
      agency.valueProperty().bindBidirectional(model.agencyProperty());
   }

   public ComboBox<SectionAgency> getAgency()
   {
      return agency;
   }
}