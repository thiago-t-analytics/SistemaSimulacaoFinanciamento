package modelo;

public class HouseFinancing extends Financing {
    private final double builtArea;
    private final double landArea;

    public HouseFinancing(double value, int term, double rate, double builtArea, double landArea) {
        super(value, term, rate);
        this.builtArea = builtArea;
        this.landArea = landArea;
    }

    @Override
    public double calculateMonthlyPayment() {
        // Aplicar juros simples e somar a taxa de seguro de R$ 80,00
        return (this.propertyValue / (this.financingTermYears * 12)) * (1 + (this.annualInterestRate / 100 / 12)) + 80;
    }

    @Override
    public String getSpecificDetails() {
        return String.format("Área da Casa: %.2fm² | Terreno: %.2fm²", builtArea, landArea);

    }
}