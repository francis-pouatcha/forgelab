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

import org.apache.commons.lang3.StringUtils;

@Entity
public class ArticleLotSequence {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;

	private String mainPic;

	private Long lotNumber = 0l;

	private String internalPic;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lotDate;

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

	public String getMainPic() {
		return mainPic;
	}

	public void setMainPic(String mainPic) {
		this.mainPic = mainPic;
	}

	public Long getLotNumber() {
		return lotNumber;
	}

	public void setLotNumber(Long lotNumber) {
		this.lotNumber = lotNumber;
	}

	public String getInternalPic() {
		return internalPic;
	}

	public void setInternalPic(String internalPic) {
		this.internalPic = internalPic;
	}

	public void newLot() {
		lotNumber += 1;
		internalPic = mainPic + StringUtils.leftPad("" + lotNumber, 2, '0');
		lotDate = new Date();
	}
}
