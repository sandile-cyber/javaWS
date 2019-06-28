package jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the Quotes database table.
 * 
 */

@Entity
@Table(name="Quotes")
@NamedQuery(name="Quote.findAll", query="SELECT q FROM Quote q")
public class Quote implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column
	private double adjustmentCurrencyRate;

	@Column(name="client_id")
	private int clientId;

	@Column(name="source_amount")
	private double sourceAmount;

	@Column(name="source_currency")
	private String sourceCurrency;

	@Column(name="source_currency_rate")
	private double sourceCurrencyRate;

	@Column(name="target_currency")
	private String targetCurrency;

	@Column(name="target_currency_rate")
	private double targetCurrencyRate;
	
	@Id
	@Column
	private String uuid;
	
	

	public Quote(double adjustmentCurrencyRate, int clientId, double sourceAmount, String sourceCurrency,
			double sourceCurrencyRate, String targetCurrency, double targetCurrencyRate, String uuid) {
		super();
		
		this.adjustmentCurrencyRate = adjustmentCurrencyRate;
		this.clientId = clientId;
		this.sourceAmount = sourceAmount;
		this.sourceCurrency = sourceCurrency;
		this.sourceCurrencyRate = sourceCurrencyRate;
		this.targetCurrency = targetCurrency;
		this.targetCurrencyRate = targetCurrencyRate;
		this.uuid = uuid;
	}

	public Quote() {
	}

	public double getAdjustmentCurrencyRate() {
		return this.adjustmentCurrencyRate;
	}

	public void setAdjustmentCurrencyRate(double adjustmentCurrencyRate) {
		this.adjustmentCurrencyRate = adjustmentCurrencyRate;
	}

	public int getClientId() {
		return this.clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public double getSourceAmount() {
		return this.sourceAmount;
	}

	public void setSourceAmount(double sourceAmount) {
		this.sourceAmount = sourceAmount;
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

	public double getTargetCurrencyRate() {
		return this.targetCurrencyRate;
	}

	public void setTargetCurrencyRate(double targetCurrencyRate) {
		this.targetCurrencyRate = targetCurrencyRate;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}