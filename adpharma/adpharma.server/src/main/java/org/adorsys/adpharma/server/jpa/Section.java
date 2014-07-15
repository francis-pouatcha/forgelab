package org.adorsys.adpharma.server.jpa;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;

import java.lang.Override;

import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

import javax.validation.constraints.NotNull;

import org.adorsys.adpharma.server.jpa.Agency;

import javax.persistence.ManyToOne;

import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;

@Entity
@Description("Section_description")
@ToStringField("sectionCode")
@ListField({ "sectionCode", "name", "position", "agency.name" })
public class Section implements Serializable
{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;

	@Column
	@Description("Section_sectionCode_description")
	private String sectionCode;

	@Column
	@Description("Section_name_description")
	@NotNull(message = "Section_name_NotNull_validation")
	private String name;

	@Column
	@Description("Section_position_description")
	private String position;

	@Column
	@Description("Section_geoCode_description")
	private String geoCode;

	@ManyToOne
	@Description("Section_agency_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
	@NotNull(message = "Section_agency_NotNull_validation")
	private Agency agency;

	@Column
	@Description("Article_wareHouse_description")
	private Boolean wareHouse = Boolean.FALSE;

	public Long getId()
	{
		return this.id;
	}

	public void setId(final Long id)
	{
		this.id = id;
	}

	public int getVersion()
	{
		return this.version;
	}

	public void setVersion(final int version)
	{
		this.version = version;
	}

	@Override
	public boolean equals(Object that)
	{
		if (this == that)
		{
			return true;
		}
		if (that == null)
		{
			return false;
		}
		if (getClass() != that.getClass())
		{
			return false;
		}
		if (id != null)
		{
			return id.equals(((Section) that).id);
		}
		return super.equals(that);
	}

	@Override
	public int hashCode()
	{
		if (id != null)
		{
			return id.hashCode();
		}
		return super.hashCode();
	}

	public String getSectionCode()
	{
		return this.sectionCode;
	}

	public void setSectionCode(final String sectionCode)
	{
		this.sectionCode = sectionCode;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getPosition()
	{
		return this.position;
	}

	public void setPosition(final String position)
	{
		this.position = position;
	}

	public String getGeoCode()
	{
		return this.geoCode;
	}

	public void setGeoCode(final String geoCode)
	{
		this.geoCode = geoCode;
	}

	public Boolean getWareHouse() {
		return wareHouse;
	}

	public void setWareHouse(Boolean salable) {
		this.wareHouse = wareHouse;
	}

	@Override
	public String toString()
	{
		String result = getClass().getSimpleName() + " ";
		if (sectionCode != null && !sectionCode.trim().isEmpty())
			result += "sectionCode: " + sectionCode;
		if (name != null && !name.trim().isEmpty())
			result += ", name: " + name;
		if (position != null && !position.trim().isEmpty())
			result += ", position: " + position;
		if (geoCode != null && !geoCode.trim().isEmpty())
			result += ", geoCode: " + geoCode;
		return result;
	}

	public Agency getAgency()
	{
		return this.agency;
	}

	public void setAgency(final Agency agency)
	{
		this.agency = agency;
	}
}