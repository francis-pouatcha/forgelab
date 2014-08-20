package org.adorsys.adpharma.client.events;

public class ProcurementOrderId  extends DomainObjectId {

	private boolean onlyRupture = false ;
	public ProcurementOrderId(Long id) {
		super(id);
	}
	public boolean isOnlyRupture() {
		return onlyRupture;
	}
	public void setOnlyRupture(boolean onlyRupture) {
		this.onlyRupture = onlyRupture;
	}

}
