package org.adorsys.adpharma.server.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.list.ListField;

@Entity
@Description("SalesOrder_description")
@ListField({ "cashDrawer.cashDrawerNumber", "soNumber", "customer.fullName",
	"insurance.customer.fullName", "insurance.insurer.fullName",
	"vat.rate", "salesAgent.fullName", "agency.name", "salesOrderStatus",
	"cashed", "amountBeforeTax", "amountVAT", "amountDiscount",
	"totalReturnAmount", "amountAfterTax", "salesOrderType" })
@ToStringField("soNumber")
public class SalesOrder implements Serializable
{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;

	@ManyToOne
	@Description("SalesOrder_cashDrawer_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = CashDrawer.class)
	private CashDrawer cashDrawer;

	@Column
	@Description("SalesOrder_soNumber_description")
	private String soNumber;
	
	@Column
	@Description("SalesOrder_patientMatricle_description")
	private String patientMatricle;

	@Transient
	@Description("SalesOrder_salesKey_description")
	private String salesKey;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("SalesOrder_creationDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date creationDate = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	@Description("SalesOrder_cancelationDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date cancelationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("SalesOrder_restorationDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date restorationDate;

	@ManyToOne
	@Description("SalesOrder_customer_description")
	@Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Customer.class)
	@NotNull(message = "SalesOrder_customer_NotNull_validation")
	private Customer customer;

	@ManyToOne
	@Description("SalesOrder_insurance_description")
	@Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Insurrance.class)
	private Insurrance insurance;

	@ManyToOne
	@Description("SalesOrder_vat_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = VAT.class)
	private VAT vat;

	@ManyToOne
	@Description("SalesOrder_salesAgent_description")
	@Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
	@NotNull(message = "SalesOrder_salesAgent_NotNull_validation")
	private Login salesAgent;

	@ManyToOne
	@Description("SalesOrder_agency_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
	@NotNull(message = "SalesOrder_agency_NotNull_validation")
	private Agency agency;

	@Column
	@Description("SalesOrder_salesOrderStatus_description")
	@Enumerated
	private DocumentProcessingState salesOrderStatus = DocumentProcessingState.ONGOING;

	@Column
	@Description("SalesOrder_cashed_description")
	private Boolean cashed =Boolean.FALSE;

	@Column
	@Description("SalesOrder_amountBeforeTax_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal amountBeforeTax =BigDecimal.ZERO;

	@Column
	@Description("SalesOrder_amountVAT_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal amountVAT =BigDecimal.ZERO;

	@Column
	@Description("SalesOrder_amountDiscount_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal amountDiscount =BigDecimal.ZERO;

	@Column
	@Description("SalesOrder_totalReturnAmount_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal totalReturnAmount =BigDecimal.ZERO;

	@Column
	@Description("SalesOrder_amountAfterTax_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal amountAfterTax =BigDecimal.ZERO;

	@Column
	@Description("SalesOrder_salesOrderType_description")
	@Enumerated
	private SalesOrderType salesOrderType=SalesOrderType.CASH_SALE;

	@OneToMany(mappedBy = "salesOrder", cascade = CascadeType.ALL)
	@Description("SalesOrder_salesOrderItems_description")
	@Association(associationType = AssociationType.COMPOSITION, targetEntity = SalesOrderItem.class, selectionMode = SelectionMode.TABLE)
	private Set<SalesOrderItem> salesOrderItems = new HashSet<SalesOrderItem>();

	@Column
	@Description("SalesOrder_alreadyReturned_description")
	private Boolean alreadyReturned = Boolean.FALSE;

	@Column
	@Description("SalesOrder_discountRate_description")
	@NumberFormatType(NumberType.PERCENTAGE)
	private BigDecimal discountRate =BigDecimal.ZERO;
	
	@PostPersist
	public void prePersist(){
		creationDate = new Date();
	}

	public void calculateTotalReturnAmount(){
		totalReturnAmount =BigDecimal.ZERO;
		for (SalesOrderItem item : salesOrderItems) {
			if(item.hasReturnArticle()){
				totalReturnAmount = totalReturnAmount.add(item.getReturnedQty().multiply(item.getSalesPricePU()));
			}

		}
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
			return id.equals(((SalesOrder) that).id);
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

	public CashDrawer getCashDrawer()
	{
		return this.cashDrawer;
	}

	public void setCashDrawer(final CashDrawer cashDrawer)
	{
		this.cashDrawer = cashDrawer;
	}

	public String getSoNumber()
	{
		return this.soNumber;
	}

	public void setSoNumber(final String soNumber)
	{
		this.soNumber = soNumber;
	}

	public Date getCreationDate()
	{
		return this.creationDate;
	}

	public void setCreationDate(final Date creationDate)
	{
		this.creationDate = creationDate;
	}

	public Date getCancelationDate()
	{
		return this.cancelationDate;
	}

	public void setCancelationDate(final Date cancelationDate)
	{
		this.cancelationDate = cancelationDate;
	}

	public Date getRestorationDate()
	{
		return this.restorationDate;
	}

	public void setRestorationDate(final Date restorationDate)
	{
		this.restorationDate = restorationDate;
	}

	public Customer getCustomer()
	{
		return this.customer;
	}

	public void setCustomer(final Customer customer)
	{
		this.customer = customer;
	}

	public Insurrance getInsurance()
	{
		return this.insurance;
	}

	public void setInsurance(final Insurrance insurance)
	{
		this.insurance = insurance;
	}

	public VAT getVat()
	{
		return this.vat;
	}

	public void setVat(final VAT vat)
	{
		this.vat = vat;
	}

	public Login getSalesAgent()
	{
		return this.salesAgent;
	}

	public void setSalesAgent(final Login salesAgent)
	{
		this.salesAgent = salesAgent;
	}

	public Agency getAgency()
	{
		return this.agency;
	}

	public void setAgency(final Agency agency)
	{
		this.agency = agency;
	}

	public String getSalesKey() {
		return salesKey;
	}

	public void setSalesKey(String salesKey) {
		this.salesKey = salesKey;
	}

	public DocumentProcessingState getSalesOrderStatus()
	{
		return this.salesOrderStatus;
	}

	public void setSalesOrderStatus(final DocumentProcessingState salesOrderStatus)
	{
		this.salesOrderStatus = salesOrderStatus;
	}

	public Boolean getCashed()
	{
		return this.cashed;
	}

	public void setCashed(final Boolean cashed)
	{
		this.cashed = cashed;
	}

	public BigDecimal getAmountBeforeTax()
	{
		return this.amountBeforeTax;
	}

	public void setAmountBeforeTax(final BigDecimal amountBeforeTax)
	{
		this.amountBeforeTax = amountBeforeTax;
	}

	public BigDecimal getAmountVAT()
	{
		return this.amountVAT;
	}

	public void setAmountVAT(final BigDecimal amountVAT)
	{
		this.amountVAT = amountVAT;
	}

	public BigDecimal getAmountDiscount()
	{
		return this.amountDiscount;
	}

	public void setAmountDiscount(final BigDecimal amountDiscount)
	{
		this.amountDiscount = amountDiscount;
	}

	public BigDecimal getTotalReturnAmount()
	{
		return this.totalReturnAmount;
	}

	public void setTotalReturnAmount(final BigDecimal totalReturnAmount)
	{
		this.totalReturnAmount = totalReturnAmount;
	}

	public BigDecimal getAmountAfterTax()
	{
		return this.amountAfterTax;
	}

	public void setAmountAfterTax(final BigDecimal amountAfterTax)
	{
		this.amountAfterTax = amountAfterTax;
	}

	public SalesOrderType getSalesOrderType()
	{
		return this.salesOrderType;
	}

	public void setSalesOrderType(final SalesOrderType salesOrderType)
	{
		this.salesOrderType = salesOrderType;
	}

	public Boolean getAlreadyReturned() {
		return alreadyReturned;
	}

	public void setAlreadyReturned(Boolean alreadyReturned) {
		this.alreadyReturned = alreadyReturned;
	}

	@Override
	public String toString()
	{
		String result = getClass().getSimpleName() + " ";
		if (soNumber != null && !soNumber.trim().isEmpty())
			result += "soNumber: " + soNumber;
		if (cashed != null)
			result += ", cashed: " + cashed;
		return result;
	}

	public Set<SalesOrderItem> getSalesOrderItems()
	{
		return this.salesOrderItems;
	}

	public void setSalesOrderItems(final Set<SalesOrderItem> salesOrderItems)
	{
		this.salesOrderItems = salesOrderItems;
	}

	public BigDecimal getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}
	
	public BigDecimal getNetToPay(){
		return getAmountAfterTax();
	}
	public void setNetToPay(BigDecimal netToPay){}
	
	public BigDecimal getCustomerRestTopay(){
		return getAmountAfterTax();
	}
	public void setCustomerRestTopay(BigDecimal customerRestToPay){}
	
	public BigDecimal getInsurranceRestTopay(){
		return BigDecimal.ZERO;
	}
	public void setInsurranceRestTopay(BigDecimal insurranceRestToPay){}	
	
	public BigDecimal getAdvancePayment(){
		return BigDecimal.ZERO;
	}
	public void setAdvancePayment(BigDecimal advancePayment){}
	
	public BigDecimal getTotalRestToPay(){
		return getAmountAfterTax();
	}
	public void setTotalRestToPay(BigDecimal totalRestToPay){}

	public String getPatientMatricle() {
		return patientMatricle;
	}

	public void setPatientMatricle(String patientMatricle) {
		this.patientMatricle = patientMatricle;
	}
	
}