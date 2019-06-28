package jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the QuoteInfo database table.
 * 
 */
@Entity
@NamedQuery(name="QuoteInfo.findAll", query="SELECT q FROM QuoteInfo q")
public class QuoteInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="adjustment_rate")
	private double adjustmentRate;

	@Column(name="amount")
	private double amount;

	@Column(name="client_id")
	private int clientId;

	@Column(name="source_currency")
	private String sourceCurrency;

	@Column(name="source_currency_rate")
	private double sourceCurrencyRate;

	@Column(name="target_currency")
	private String targetCurrency;

	@Column(name="target_current_rate")
	private double targetCurrentRate;

	@Id
	@Column(name="uuid")
	private String uuid;

	public QuoteInfo() {
	}

	public double getAdjustmentRate() {
		return this.adjustmentRate;
	}

	public void setAdjustmentRate(double adjustmentRate) {
		this.adjustmentRate = adjustmentRate;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getClientId() {
		return this.clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getSourceCurrency() {
		return this.sourceCurrency;
	}

	public void setSourceCurrency(String sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}

	public double getSourceCurrencyRate() {
		return this.sourceCurrencyRate;
	}

	public void setSourceCurrencyRate(double sourceCurrencyRate) {
		this.sourceCurrencyRate = sourceCurrencyRate;
	}

	public String getTargetCurrency() {
		return this.targetCurrency;
	}

	public void setTargetCurrency(String targetCurrency) {
		this.targetCurrency = targetCurrency;
	}

	public double getTargetCurrentRate() {
		return this.targetCurrentRate;
	}

	public void setTargetCurrentRate(double targetCurrentRate) {
		this.targetCurrentRate = targetCurrentRate;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}