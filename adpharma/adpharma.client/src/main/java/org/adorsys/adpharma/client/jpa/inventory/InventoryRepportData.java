package org.adorsys.adpharma.client.jpa.inventory;

public class InventoryRepportData {
	private Inventory inventory = new Inventory();

	private boolean isCountRepport = true ;

	public InventoryRepportData() {
		// TODO Auto-generated constructor stub
	}

	public InventoryRepportData(Inventory inventory) {
		this.inventory = inventory;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public boolean isCountRepport() {
		return isCountRepport;
	}

	public void setCountRepport(boolean isCountRepport) {
		this.isCountRepport = isCountRepport;
	}


}
