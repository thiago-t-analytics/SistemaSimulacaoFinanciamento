package modelo;

import java.io.Serializable;

// Utilizar abstract para definir que esta classe não pode ser instanciada diretamente
public abstract class Financing implements Serializable {
    protected double propertyValue;
    protected int financingTermYears;
    protected double annualInterestRate;

    public Financing(double propertyValue, int financingTermYears, double annualInterestRate) {
        this.propertyValue = propertyValue;
        this.financingTermYears = financingTermYears;
        this.annualInterestRate = annualInterestRate;
    }

    // Cada subclasse implementara sua propria regra de calculo mensal
    public abstract double calculateMonthlyPayment();

    public double calculateTotalPayment() {
        return this.calculateMonthlyPayment() * this.financingTermYears * 12;
    }

    public double getPropertyValue() { return propertyValue; }
    public int getFinancingTermYears() { return financingTermYears; }
    public double getAnnualInterestRate() { return annualInterestRate; }

    // Metodo para que cada imovel descreva suas particularidades
    public abstract String getSpecificDetails();
    public int getTermInMonths() {
        return this.financingTermYears * 12;
    }


}