package modelo;

import java.io.Serial;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;


public abstract class Financing implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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

    public abstract String getSpecificDetails();

    public double getPropertyValue() { return propertyValue; }
    public int getFinancingTermYears() { return financingTermYears; }
    public double getAnnualInterestRate() { return annualInterestRate; }
    public int getTermInMonths() { return this.financingTermYears * 12; }

    // Formatar valores para moeda brasileira
    protected String formatCurrency(double value) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));
        return nf.format(value).replace("\u00A0", " ");
    }

    @Override
    public String toString() {
        double totalInterest = calculateTotalPayment() - propertyValue;
        return String.format("Tipo: %-12s | V. Original: %12s | V. Total: %12s | Juros: %12s | Prazo: %2d anos | Taxa: %4.1f%% a.a.", 
                getFriendlyTypeName(), 
                formatCurrency(propertyValue), 
                formatCurrency(calculateTotalPayment()),
                formatCurrency(totalInterest),
                financingTermYears, 
                annualInterestRate);
    }
}