package util;

import java.util.Scanner;
import java.util.Locale;

public class UserInterface {
    private final Scanner scanner;

    // Taxas de Juros Padrões para cada tipo de imovel
    public static final double RATE_HOUSE = 9.5;
    public static final double RATE_APARTMENT = 10.5;
    public static final double RATE_LAND = 11.0;

    public UserInterface() {
        this.scanner = new Scanner(System.in).useLocale(Locale.US);
    }

    public int askSimulationProfile() {
        System.out.println("\n=========================================================");
        System.out.println("   SISTEMA DE SIMULAÇÃO DE FINANCIAMENTOS");
        System.out.println("=========================================================");
        System.out.println("Escolha o tipo de imóvel para simular:");
        System.out.println("(1) Casa");
        System.out.println("(2) Apartamento");
        System.out.println("(3) Terreno");
        System.out.println("(4) Misto (Vários tipos)");
        return (int) readDoubleWithRetry("Opção: ");
    }

    public double askPropertyValue() {
        return readDoubleWithRetry("Digite o valor base do imóvel: ");
    }

    public int askTerm() {
        return (int) readDoubleWithRetry("Prazo em anos: ");
    }

    public double askRate(double defaultRate) {
        System.out.printf("Taxa anual sugerida é: %.1f%%%n", defaultRate);
        return readDoubleWithRetry("Digite a taxa anual (%) ou 0 para usar a sugerida: ");
    }

    public String askZone() {
        System.out.print("O terreno é Rural ou Urbano? (Deixe vazio para aleatório): ");
        return scanner.nextLine().trim();
    }

    public boolean askDetailReport() {
        System.out.print("\nDeseja visualizar o relatório detalhado? (S/N): ");
        String resp = scanner.nextLine().trim().toUpperCase();
        return resp.startsWith("S");
    }

    public boolean askNewSimulation() {
        System.out.print("\nDeseja realizar uma nova simulação? (S/N): ");
        String resp = scanner.nextLine().trim().toUpperCase();
        return resp.startsWith("S");
    }

    private double readDoubleWithRetry(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim().replace(",", ".");
                if (input.isEmpty()) return 0;
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Erro: Entrada inválida. Digite apenas números.");
            }
        }
    }
}