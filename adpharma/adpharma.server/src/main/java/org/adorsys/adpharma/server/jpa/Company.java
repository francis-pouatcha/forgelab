package org.adorsys.adpharma.server.jpa;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.PostPersist;
import javax.persistence.Version;

import java.lang.Override;

import org.adorsys.adpharma.server.utils.SequenceGenerator;
import org.adorsys.javaext.description.Description;

import javax.validation.constraints.NotNull;

import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.adorsys.javaext.format.DateFormatPattern;

@Entity
@Description("Company_description")
@ToStringField("displayName")
@ListField({ "displayName", "phone", "fax", "siteManager", "email" })
public class Company implements Serializable
{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;

	@Column
	@Description("Company_displayName_description")
	@NotNull(message = "Company_displayName_NotNull_validation")
	private String displayName;

	@Column
	@Description("Company_phone_description")
	private String phone;

	@Column
	@Description("Company_fax_description")
	private String fax;

	@Column
	@Description("Company_siteManager_description")
	private String siteManager;

	@Column
	@Description("Company_email_description")
	private String email;

	@Column
	@Description("Company_street_description")
	private String street;

	@Column
	@Description("Company_zipCode_description")
	private String zipCode;

	@Column
	@Description("Company_city_description")
	private String city;

	@Column
	@Description("Company_country_description")
	private String country;

	@Column
	@Description("Company_siteInternet_description")
	private String siteInternet;

	@Column
	@Description("Company_mobile_description")
	private String mobile;

	@Column
	@Description("Company_registerNumber_description")
	private String registerNumber;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("Company_recordingDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date recordingDate;
	
	public Company() {
		// TODO Auto-generated constructor stub
	}
	

	public Company(String displayName) {
		super();
		this.displayName = displayName;
	}


	@PostPersist
	public void onPostPersist(){
		recordingDate = new Date();
	}

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
			return id.equals(((Company) that).id);
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

	public String getDisplayName()
	{
		return this.displayName;
	}

	public void setDisplayName(final String displayName)
	{
		this.displayName = displayName;
	}

	public String getPhone()
	{
		return this.phone;
	}

	public void setPhone(final String phone)
	{
		this.phone = phone;
	}

	public String getFax()
	{
		return this.fax;
	}

	public void setFax(final String fax)
	{
		this.fax = fax;
	}

	public String getSiteManager()
	{
		return this.siteManager;
	}

	public void setSiteManager(final String siteManager)
	{
		this.siteManager = siteManager;
	}

	public String getEmail()
	{
		return this.email;
	}

	public void setEmail(final String email)
	{
		this.email = email;
	}

	public String getStreet()
	{
		return this.street;
	}

	public void setStreet(final String street)
	{
		this.street = street;
	}

	public String getZipCode()
	{
		return this.zipCode;
	}

	public void setZipCode(final String zipCode)
	{
		this.zipCode = zipCode;
	}

	public String getCity()
	{
		return this.city;
	}

	public void setCity(final String city)
	{
		this.city = city;
	}

	public String getCountry()
	{
		return this.country;
	}

	public void setCountry(final String country)
	{
		this.country = country;
	}

	public String getSiteInternet()
	{
		return this.siteInternet;
	}

	public void setSiteInternet(final String siteInternet)
	{
		this.siteInternet = siteInternet;
	}

	public String getMobile()
	{
		return this.mobile;
	}

	public void setMobile(final String mobile)
	{
		this.mobile = mobile;
	}

	public String getRegisterNumber()
	{
		return this.registerNumber;
	}

	public void setRegisterNumber(final String registerNumber)
	{
		this.registerNumber = registerNumber;
	}

	public Date getRecordingDate()
	{
		return this.recordingDate;
	}

	public void setRecordingDate(final Date recordingDate)
	{
		this.recordingDate = recordingDate;
	}

	@Override
	public String toString()
	{
		String result = getClass().getSimpleName() + " ";
		if (displayName != null && !displayName.trim().isEmpty())
			result += "displayName: " + displayName;
		if (phone != null && !phone.trim().isEmpty())
			result += ", phone: " + phone;
		if (fax != null && !fax.trim().isEmpty())
			result += ", fax: " + fax;
		if (siteManager != null && !siteManager.trim().isEmpty())
			result += ", siteManager: " + siteManager;
		if (email != null && !email.trim().isEmpty())
			result += ", email: " + email;
		if (street != null && !street.trim().isEmpty())
			result += ", street: " + street;
		if (zipCode != null && !zipCode.trim().isEmpty())
			result += ", zipCode: " + zipCode;
		if (city != null && !city.trim().isEmpty())
			result += ", city: " + city;
		if (country != null && !country.trim().isEmpty())
			result += ", country: " + country;
		if (siteInternet != null && !siteInternet.trim().isEmpty())
			result += ", siteInternet: " + siteInternet;
		if (mobile != null && !mobile.trim().isEmpty())
			result += ", mobile: " + mobile;
		if (registerNumber != null && !registerNumber.trim().isEmpty())
			result += ", registerNumber: " + registerNumber;
		return result;
	}
}