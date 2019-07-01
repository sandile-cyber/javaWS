package za.co.yakka.model;


import javax.ejb.Stateless;

@Stateless
public class ResponseModel {

    private double exchangeRate;
    private double targetAmount;
    private double sourceCurrency;

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public double getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(double sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

}