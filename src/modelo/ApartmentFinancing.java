package modelo;

import java.io.Serial;

public class ApartmentFinancing extends Financing {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int floor;
    private final double area;

    public ApartmentFinancing(double value, int term, double rate, int floor, double area) {
        super(value, term, rate);
        this.floor = floor;
        this.area = area;
    }

    @Override
    public double calculateMonthlyPayment() {
        // Calcular o juros compostos para apartamentos
        double mRate = (this.annualInterestRate / 100) / 12;
        double months = this.financingTermYears * 12;
        return (this.propertyValue * Math.pow(1 + mRate, months)) / months;
    }

    @Override
    public String getSpecificDetails() {
        return String.format("APTO: %.2fm² e %dº andar", area, floor);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Andar: %2d | Área Privativa: %.2fm²", floor, area);
    }
}