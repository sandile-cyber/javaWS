package jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CurrencyAdjustments database table.
 * 
 */
@Entity
@Table(name="CurrencyAdjustments")
@NamedQuery(name="CurrencyAdjustment.findAll", query="SELECT c FROM CurrencyAdjustment c")
public class CurrencyAdjustment implements Serializable {
	private static final long serialVersionUID = 1L;

	private float adjustmentPercentage;
	
	@Id
	@Column(name="curr_code")
	private String currCode;

	public CurrencyAdjustment() {
	}

	public float getAdjustmentPercentage() {
		return this.adjustmentPercentage;
	}

	public void setAdjustmentPercentage(float adjustmentPercentage) {
		this.adjustmentPercentage = adjustmentPercentage;
	}

	public String getCurrCode() {
		return this.currCode;
	}

	public void setCurrCode(String currCode) {
		this.currCode = currCode;
	}

}