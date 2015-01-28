package org.adorsys.adpharma.server.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.adorsys.javaext.format.DateFormatPattern;

@Entity
public class DeliveryItemArticleEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;
	
	private Long itemId ;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date created = new Date();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	public DeliveryItemArticleEvent() {
		// TODO Auto-generated constructor stub
	}
	
	public DeliveryItemArticleEvent(DeliveryItem deliveryItem) {
		this.itemId = deliveryItem.getId();
	}

	
}
