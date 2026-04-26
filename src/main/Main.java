package main;

import modelo.*;
import util.UserInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.text.NumberFormat;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));
        Random rand = new Random();

        boolean continueSimulation = true;

        while (continueSimulation) {
            List<Financing> list = new ArrayList<>();

            // Inputs Iniciais
            int profile = ui.askSimulationProfile();
            double baseValue = ui.askPropertyValue();
            int term = ui.askTerm();

            // Determinar a taxa padrão para mostrar no input
            double defaultRate = switch (profile) {
                case 1 -> UserInterface.RATE_HOUSE;
                case 2 -> UserInterface.RATE_APARTMENT;
                case 3 -> UserInterface.RATE_LAND;
                // Se Misto utilizar o padrão de 10.0
                default -> 10.0;
            };
            double userRate = ui.askRate(defaultRate);
            
            // Perguntar ao usuario da zona apenas se for Terreno (Tipo 3)
            String zoneChoice = "";
            if (profile == 3) {
                zoneChoice = ui.askZone();
            }

            // RELATORIOS
            // Gerar 5 exemplos
            for (int i = 0; i < 5; i++) {
                double variation = 0.9 + (rand.nextDouble() * 0.2);
                double currentVal = baseValue * variation;
                
                int currentType = profile;
                // Logica Mista
                if (profile == 4) {
                    if (i < 2) currentType = 1;
                    else if (i < 4) currentType = 2;
                    else currentType = 3;
                }

                double appliedRate = (userRate > 0) ? userRate : switch (currentType) {
                    case 1 -> UserInterface.RATE_HOUSE;
                    case 2 -> UserInterface.RATE_APARTMENT;
                    default -> UserInterface.RATE_LAND;
                };

                switch (currentType) {
                    case 1 -> list.add(new HouseFinancing(currentVal, term, appliedRate, 120 * variation, 250 * variation));
                    case 2 -> list.add(new ApartmentFinancing(currentVal, term, appliedRate, 1, i + 1, 60 * variation));
                    case 3 -> {
                        String finalZone;
                        if (profile == 3) {
                            if (zoneChoice.isEmpty()) {
                                finalZone = (i < 3) ? "Urbano" : "Rural";
                            } else {
                                finalZone = zoneChoice;
                            }
                        } else {
                            // Sortear a zona se o usuario escolher misto
                            finalZone = rand.nextBoolean() ? "Urbano" : "Rural";
                        }
                        list.add(new LandFinancing(currentVal, term, appliedRate, finalZone));
                    }
                }
            }

            // Relatorio Resumido
            printHeader("RESUMO DO INVESTIMENTO");
            double totalProp = 0, totalFin = 0;

            for (int i = 0; i < list.size(); i++) {
                Financing f = list.get(i);
                totalProp += f.getPropertyValue();
                totalFin += f.calculateTotalPayment();
                
                String typeName = f.getClass().getSimpleName().replace("Financiamento", "");
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

            // Relatorio detalhado
            if (ui.askDetailReport()) {
                printHeader("DETALHAMENTO DO FINANCIAMENTO");
                for (Financing f : list) {
                    System.out.println(f.getSpecificDetails());
                    System.out.printf("Prazo: %d meses | Taxa: %.1f%% a.a.%n", f.getTermInMonths(), f.getAnnualInterestRate());
                    System.out.printf("Parcela Mensal: %s%n", nf.format(f.calculateMonthlyPayment()));
                    System.out.printf("Custo Total:    %s (Juros: %s)%n", 
                            nf.format(f.calculateTotalPayment()), 
                            nf.format(f.calculateTotalPayment() - f.getPropertyValue()));
                    System.out.println("---------------------------------------------------------");
                }
            }

            // Retornar ao Loop par a uma Nova Simulação
            continueSimulation = ui.askNewSimulation();
            if (!continueSimulation) {
                System.out.println("\nObrigado por utilizar o Sistema T-Analytics! Encerrando...");
            }
        }
    }

    private static void printHeader(String title) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println(" ".repeat((80 - title.length()) / 2) + title);
        System.out.println("=".repeat(80));
    }
}
