package modelo;

import java.io.Serializable;

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

    // Retornar o nome da classe em portugues
    public String getFriendlyTypeName() {
        return switch (this.getClass().getSimpleName()) {
            case "HouseFinancing" -> "Casa";
            case "ApartmentFinancing" -> "Apartamento";
            case "LandFinancing" -> "Terreno";
            default -> "Financiamento";
        };
    }

    public double getPropertyValue() { return propertyValue; }
    public int getFinancingTermYears() { return financingTermYears; }
    public double getAnnualInterestRate() { return annualInterestRate; }

    // Metodo para que cada imovel descreva a suas particularidades
    public abstract String getSpecificDetails();

    public int getTermInMonths() {
        return this.financingTermYears * 12;
    }
}