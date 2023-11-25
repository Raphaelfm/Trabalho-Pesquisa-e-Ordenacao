// Main.java
import java.io.File;
import java.util.Scanner;

import ordenacao.TrabalhaArquivos;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TrabalhaArquivos ordenacao = new TrabalhaArquivos(); // Ajuste o nome da classe aqui

        String pastaEntrada = "src/arquivos";
        String pastaSaidaQuickSort = "src/arquivosOrdenados/quickSort";
        String pastaSaidaShellSort= "src/arquivosOrdenados/shellSort";

        System.out.println("Bem-vindo ao programa de ordenação!");

        int opcao;
        do {
            exibirMenu();
            System.out.print("Escolha o método de ordenação (1 para quickSort, " +
                    "2 para shellSort, 0 para sair): ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    processarArquivos(ordenacao, pastaEntrada, pastaSaidaQuickSort, "quickSort");
                    System.out.println("Arquivos ordenados usando quickSort!");
                    break;
                case 2:
                    processarArquivos(ordenacao, pastaEntrada, pastaSaidaShellSort, "shellSort");
                    System.out.println("Arquivos ordenados usando shellSort!");
                    break;
                case 0:
                    System.out.println("Saindo do programa. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n----- Menu de Opções -----");
        System.out.println("1. QuickSort");
        System.out.println("0. Sair");
    }

    private static void processarArquivos(TrabalhaArquivos ordenacao, String pastaEntrada, String pastaSaida, String nomeMetodo) {
        File diretorioEntrada = new File(pastaEntrada);
        File diretorioSaida = new File(pastaSaida);

        if (!diretorioEntrada.exists()) {
            System.out.println("A pasta de entrada não existe.");
            return;
        }

        if (!diretorioSaida.exists()) {
            diretorioSaida.mkdir();
        }

        File[] arquivos = diretorioEntrada.listFiles();

        if (arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isFile()) {
                    String nomeArquivo = arquivo.getName();
                    String caminhoArquivoEntrada = diretorioEntrada.getPath() + File.separator + nomeArquivo;
                    String caminhoArquivoSaida = diretorioSaida.getPath() + File.separator + nomeArquivo.replace(".txt", "_ordenado.txt");


                    ordenacao.gerarArquivoOrdenado(caminhoArquivoEntrada, caminhoArquivoSaida, nomeMetodo);
                }
            }
        } else {
            System.out.println("Nenhum arquivo encontrado na pasta de entrada.");
        }
    }

}
