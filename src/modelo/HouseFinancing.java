package modelo;

import modelo.excecao.LimitedAdditionException;

import java.io.Serial;

public class HouseFinancing extends Financing {
    @Serial
    private static final long serialVersionUID = 1L;
    private final double builtArea;
    private final double landArea;

    public HouseFinancing(double value, int term, double rate, double builtArea, double landArea) {
        super(value, term, rate);
        this.builtArea = builtArea;
        this.landArea = landArea;
    }
     // Calcular o pagamento mensal da casa.
     // Se o acrescimo de seguro for abusivo (juros > 50%),
     // o sistema ajusta automaticamente para o teto permitido.
    @Override
    public double calculateMonthlyPayment() {
        // Calcular dos juros mensais (Valor do Imóvel * Taxa Mensal)
        double monthlyInterest = (this.propertyValue * (this.annualInterestRate / 100 / 12));
        double surcharge = 80.0;

        try {
            // Validação para disparar a exceção
            checkSurchargeRule(surcharge, monthlyInterest);
        } catch (LimitedAdditionException e) {
            // Logica de Resiliencia: O sistema ajusta o valor
            surcharge = monthlyInterest;
            System.err.println("\n[LOG T-ANALYTICS] Alerta de Compliance: " + e.getMessage());
            System.err.println("-> Ajustando acréscimo para o teto permitido: R$ " + String.format("%.2f", surcharge));
        }

        // Retornar (Amortização Simples) + Juros + Acréscimo (Seguro)
        return (this.propertyValue / (this.financingTermYears * 12)) + monthlyInterest + surcharge;
    }

    // Validar se o acréscimo excede 50% do valor dos juros da parcela.
    private void checkSurchargeRule(double surcharge, double interest) throws LimitedAdditionException {
        if (surcharge > (interest / 2)) {
            throw new LimitedAdditionException("O acréscimo de R$ 80,00 excede 50% do valor dos juros mensais.");
        }
    }

    @Override
    public String getSpecificDetails() {
        return String.format("CASA: Área Construída: %.2fm² | Terreno: %.2fm²", builtArea, landArea);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Área Construída: %.2fm² | Terreno: %.2fm²", builtArea, landArea);
    }
}