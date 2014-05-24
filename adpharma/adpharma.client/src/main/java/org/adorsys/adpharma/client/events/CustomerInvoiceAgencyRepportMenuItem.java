package org.adorsys.adpharma.client.events;

import javafx.scene.control.MenuItem;

public class CustomerInvoiceAgencyRepportMenuItem {
	private final MenuItem menuItem;

	public CustomerInvoiceAgencyRepportMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((menuItem == null) ? 0 : menuItem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerInvoiceAgencyRepportMenuItem other = (CustomerInvoiceAgencyRepportMenuItem) obj;
		if (menuItem == null) {
			if (other.menuItem != null)
				return false;
		} else if (!menuItem.equals(other.menuItem))
			return false;
		return true;
	}
}
