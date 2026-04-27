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
        // Inicialização do Scanner
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

    // Realizar a leitura dos valores com tratamento de erros
    // Implementar 'Try-Catch' hierarquico para garantir a resiliência do sistema
    private double readDoubleWithRetry(String prompt) {
        while (true) {
            try {
                if (!prompt.isEmpty()) System.out.print(prompt);
                String input = scanner.nextLine().trim().replace(",", ".");
                
                if (input.isEmpty()) return 0;
                
                double value = Double.parseDouble(input);
                
                // Validação de negócio: Não permite valores negativos
                if (value < 0) {
                    throw new IllegalArgumentException("Valores negativos não são permitidos no sistema financeiro.");
                }
                
                return value;
                
            } catch (NumberFormatException e) {
                System.err.println("ERRO: Formato de número inválido. Use apenas números, '.' ou ','.");
            } catch (IllegalArgumentException e) {
                System.err.println("ERRO DE NEGÓCIO: " + e.getMessage());
            } catch (Exception e) {
                // Captura de segurança para erros inesperados
                System.err.println("ERRO CRÍTICO: " + e.getMessage());
                e.printStackTrace(); 
            }
        }
    }

     // Fechar o recurso do scanner para evitar vazamento de memoria
    public void closeScanner() {
        if (this.scanner != null) {
            this.scanner.close();
        }
    }
}