package main;

import modelo.*;
import util.UserInterface;
import util.FileUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.text.NumberFormat;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));

        boolean continueSimulation = true;

        while (continueSimulation) {
            List<Financing> list = new ArrayList<>();

            try {
                // Coletar os dados iniciais
                int profile = ui.askSimulationProfile();
                double baseValue = ui.askPropertyValue();
                int term = ui.askTerm();

                double defaultRate = switch (profile) {
                    case 1 -> UserInterface.RATE_HOUSE;
                    case 2 -> UserInterface.RATE_APARTMENT;
                    case 3 -> UserInterface.RATE_LAND;
                    default -> 10.0;
                };
                double userRate = ui.askRate(defaultRate);
                
                // Perguntar ao usuário a zona apenas se for Terreno
                String zoneChoice = (profile == 3) ? ui.askZone() : "";

                // Geração do relatorio
                // Criar o bloco 'try/catch' para capturar possíveis falhas
                generateBatch(list, profile, baseValue, term, userRate, zoneChoice);

                // Relatorio Resumido
                printHeader("RESUMO DO FINANCIAMENTO");
                double totalProp = 0, totalFin = 0;

                for (int i = 0; i < list.size(); i++) {
                    Financing f = list.get(i);
                    totalProp += f.getPropertyValue();
                    totalFin += f.calculateTotalPayment();
                    
                    // Encapsulamento para tradução
                    String typeName = f.getFriendlyTypeName();

                    // Se for terreno, adicionar a zona no resumo
                    if (f instanceof LandFinancing) {
                        typeName += " (" + f.getSpecificDetails().replace("TERRENO: Zona ", "") + ")";
                    }

                    System.out.printf("Item %d: %-20s | V. Imóvel: %12s | V. Total: %s%n",
                            (i + 1),
                            typeName,
                            nf.format(f.getPropertyValue()),
                            nf.format(f.calculateTotalPayment()));
                }

                System.out.println("-".repeat(80));
                System.out.printf("VALOR TOTAL DOS BENS (ORIGINAL): %s%n", nf.format(totalProp));
                System.out.printf("VALOR TOTAL FINANCIADO (JUROS + CAPITAL): %s%n", nf.format(totalFin));
                System.out.printf("TOTAL DO JUROS: %s%n", nf.format(totalFin - totalProp));
                System.out.println("=".repeat(80));

                // Detalhamento Opcional para o usuario
                if (ui.askDetailReport()) {
                    printHeader("DETALHAMENTO DO FINANCIAMENTO");
                    for (Financing f : list) {
                        System.out.println("[" + f.getFriendlyTypeName().toUpperCase() + "] " + f.getSpecificDetails());
                        System.out.printf("Prazo: %d meses | Taxa: %.1f%% a.a.%n", f.getTermInMonths(), f.getAnnualInterestRate());
                        System.out.printf("Parcela Mensal: %s%n", nf.format(f.calculateMonthlyPayment()));
                        System.out.printf("Custo Total:    %s (Juros: %s)%n", 
                                nf.format(f.calculateTotalPayment()), 
                                nf.format(f.calculateTotalPayment() - f.getPropertyValue()));
                        System.out.println("---------------------------------------------------------");
                    }
                }

                // Persistência
                FileUtils.salvarComoTexto(list);
                FileUtils.salvarComoObjeto(list);

                ui.showMessage("Simulação concluída! Dados salvos na pasta 'outputs'.");

            } catch (ArithmeticException e) {
                // Capturar erros matemáticos, como divisão por zero
                System.err.println("ERRO: Cálculo impossível: " + e.getMessage());
            } catch (RuntimeException e) {
                // Capturar erros inesperados que podem ocorrer durante a execução
                System.err.println("ERRO INESPERADO: " + e.getLocalizedMessage());
            }

            // Retornar ao ‘Loop’ par a uma Nova Simulação
            System.out.println("\n---------------------------------------------------------");
            continueSimulation = ui.askNewSimulation();
            
            if (!continueSimulation) {
                ui.showMessage("Obrigado por utilizar o Sistema T-Analytics! Encerrando...");
                ui.closeScanner();
            }
        }
    }

    private static void generateBatch(List<Financing> list, int profile, double baseValue, int term, double userRate, String zoneChoice) {
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            double variation = 0.9 + (rand.nextDouble() * 0.2);
            double currentVal = baseValue * variation;
            // Lógica para perfil Misto
            int currentType = (profile == 4) ? (i < 2 ? 1 : i < 4 ? 2 : 3) : profile;

            double appliedRate = (userRate > 0) ? userRate : switch (currentType) {
                case 1 -> UserInterface.RATE_HOUSE;
                case 2 -> UserInterface.RATE_APARTMENT;
                default -> UserInterface.RATE_LAND;
            };

            switch (currentType) {
                case 1 -> list.add(new HouseFinancing(currentVal, term, appliedRate, 120 * variation, 250 * variation));
                case 2 -> list.add(new ApartmentFinancing(currentVal, term, appliedRate, i + 1, 60 * variation));
                case 3 -> {
                    String finalZone = profile == 3 ? (zoneChoice.isEmpty() ? (i < 3 ? "Urbano" : "Rural") : zoneChoice) : (rand.nextBoolean() ? "Urbano" : "Rural");
                    list.add(new LandFinancing(currentVal, term, appliedRate, finalZone));
                }
            }
        }
    }

    private static void printHeader(String title) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println(" ".repeat((80 - title.length()) / 2) + title);
        System.out.println("=".repeat(80));
    }
}
