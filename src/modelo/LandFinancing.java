package modelo;

import java.io.Serial;

public class LandFinancing extends Financing {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String zoneType;

    public LandFinancing(double value, int term, double rate, String zoneType) {
        super(value, term, rate);
        this.zoneType = zoneType;
    }

    @Override
    public double calculateMonthlyPayment() {
        // Adicionar um acrescimo de 2% sobre o valor da parcela por ser terreno
        double base = (this.propertyValue / (this.financingTermYears * 12)) * (1 + (this.annualInterestRate / 100 / 12));
        return base * 1.02;
    }

    @Override
    public String getSpecificDetails() {
        return "TERRENO: Zona " + zoneType;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Tipo de Zona: %s", zoneType);
    }
}