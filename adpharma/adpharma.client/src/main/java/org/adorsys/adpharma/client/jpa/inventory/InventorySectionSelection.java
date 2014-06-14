package org.adorsys.adpharma.client.jpa.inventory;

import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.section.Section;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class InventorySectionSelection extends AbstractSelection<Inventory, Section>
{

   private ComboBox<InventorySection> section;

   @Inject
   @Bundle({ CrudKeys.class, Section.class, Inventory.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      section = viewBuilder.addComboBox("Inventory_section_description.title", "section", resourceBundle, false);

      section.setCellFactory(new Callback<ListView<InventorySection>, ListCell<InventorySection>>()
      {
         @Override
         public ListCell<InventorySection> call(ListView<InventorySection> listView)
         {
            return new InventorySectionListCell();
         }
      });
      section.setButtonCell(new InventorySectionListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Inventory model)
   {
	   section.valueProperty().bindBidirectional(model.sectionProperty());
   }

   public ComboBox<InventorySection> getSection()
   {
      return section;
   }
}
