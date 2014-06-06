package org.adorsys.adpharma.client.events;

/**
 * Used to dispatch event without having to link packages with each other.
 * 
 * @author francis pouatcha
 *
 */
public abstract class DomainObjectId {

	public final Long id;

	protected DomainObjectId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
