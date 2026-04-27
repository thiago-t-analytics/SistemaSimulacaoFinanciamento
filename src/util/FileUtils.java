package util;

import modelo.Financing;
import java.io.*;
import java.util.List;

public class FileUtils {

    private static final String DIR_NAME = "outputs";
    private static final String FILE_TXT = DIR_NAME + File.separator + "simulacoes.txt";
    private static final String FILE_OBJ = DIR_NAME + File.separator + "simulacoes.ser";

    // Gerar o diretório de saída.
    private static void checkDirectory() {
        File directory = new File(DIR_NAME);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    // Salvar a lista de financiamentos no arquivo de texto dentro da pasta
    public static void salvarComoTexto(List<Financing> lista) {
        checkDirectory();
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(FILE_TXT, true)))) {
            writer.println("\n--- NOVA SIMULAÇÃO REALIZADA EM: " + new java.util.Date() + " ---");
            writer.println("========================================================================================================================");
            for (Financing f : lista) {
                String line = f.toString().replace("\u00A0", " ");
                writer.println(line);
                writer.println(String.format("   >> Detalhamento: %s | Total Pago: R$ %.2f (Juros: R$ %.2f)", 
                        f.getSpecificDetails(), f.calculateTotalPayment(), (f.calculateTotalPayment() - f.getPropertyValue())));
                writer.println("-".repeat(120));
            }
            writer.flush();
            System.out.println("[LOG] Dados acumulados no relatório: " + FILE_TXT);
        } catch (IOException e) {
            System.err.println("ERRO ao salvar relatório de texto: " + e.getMessage());
        }
    }

    public static void lerDoTexto() {
        System.out.println("\n--- Recuperando Histórico Completo (TXT) ---");
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_TXT))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de histórico não encontrado em " + FILE_TXT);
        } catch (IOException e) {
            System.err.println("ERRO ao ler histórico: " + e.getMessage());
        }
    }

    public static void salvarComoObjeto(List<Financing> lista) {
        checkDirectory();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_OBJ))) {
            oos.writeObject(lista);
            oos.flush();
            System.out.println("[LOG] Objetos serializados salvos em: " + FILE_OBJ);
        } catch (IOException e) {
            System.err.println("ERRO ao salvar objetos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Financing> lerDoObjeto() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_OBJ))) {
            return (List<Financing>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new java.util.ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("ERRO ao carregar objetos: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }
}
