import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import arvoreABB.TrabalhaArvores;
import arvoreAVL.ArvoreAVL;
import arvoreAVL.NoAVL;
import arvoreAVL.TrabalhaArvoresAVL;
import dados.Item;
import dados.NoABB;
import hashing.TabelaHash;
import ordenacao.TrabalhaArquivos;
import arvoreABB.ArvoreABB;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        TrabalhaArquivos ordenacao = new TrabalhaArquivos();
        TrabalhaArvores trabalhaArvores = new TrabalhaArvores();
        ArvoreABB arvoreABB = new ArvoreABB();
        TrabalhaArvoresAVL trabalhaArvoresAVL = new TrabalhaArvoresAVL();
        TabelaHash tabelaHash;

        String pastaEntrada = "src/arquivos";
        String pastaSaidaQuickSort = "src/arquivosOrdenados/quickSort";
        String pastaSaidaShellSort = "src/arquivosOrdenados/shellSort";
        String pastaSaidaABB = "src/arquivosArvores/ABB"; //
        String pastaSaidaAVL = "src/arquivosArvores/AVL";
        String pastaSaidaHash = "src/arquivosHash";

//        ArvoreABB arvore = criarArvoreABB(trabalhaArvores, "D:\\Downloads\\ED_3SC1 - Copy (5)\\Trabalho\\src\\arquivos\\conta1000.txt");
//        arvore.imprimirEmOrdem();

        System.out.println("Bem-vindo ao programa de ordenação e operações com árvores!");

        int opcao;
        do {
            exibirMenu();
            System.out.print("Escolha a operação (1 para QuickSort, 2 para ShellSort, 3 para Árvore ABB, 0 para sair): ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    processarArquivos(ordenacao, pastaEntrada, pastaSaidaQuickSort, "quickSort");
                    System.out.println("Arquivos ordenados usando QuickSort!");
                    break;
                case 2:
                    processarArquivos(ordenacao, pastaEntrada, pastaSaidaShellSort, "shellSort");
                    System.out.println("Arquivos ordenados usando ShellSort!");
                    break;
                case 3:
                    processarArvoreABB(trabalhaArvores, pastaEntrada, pastaSaidaABB, "arvoreABB");
                    System.out.println("Operações com Árvore ABB concluídas!");
                    break;
                case 4:
                    processarArvoreAVL(trabalhaArvoresAVL, pastaEntrada, pastaSaidaAVL, "arvoreAVL");
                    System.out.println("Operações com Árvore ABB concluídas!");
                    break;
                case 5:
                    processarTabelaHash(pastaEntrada, pastaSaidaHash, "tabelaHash");
                    System.out.println("Operações com Tabela Hash concluídas!");
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
        System.out.println("\n----- Menu de Operações -----");
        System.out.println("1. QuickSort");
        System.out.println("2. ShellSort");
        System.out.println("3. Operações com Árvore ABB");
        System.out.println("4. Operações com Árvore AVL");
        System.out.println("5. Operações com Hashing Encadeado");
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

    private static void processarArvoreABB(TrabalhaArvores trabalhaArvores, String pastaEntrada, String pastaSaida, String nomeMetodo) throws IOException {
        File diretorioEntrada = new File(pastaEntrada);
        File diretorioSaida = new File(pastaSaida);

        if (!diretorioSaida.exists()) {
            diretorioSaida.mkdirs();
        }

        File[] arquivos = diretorioEntrada.listFiles();

        if (arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isFile() && arquivo.getName().endsWith(".txt")) {
                    String nomeArquivo = arquivo.getName();
                    String caminhoArquivoEntrada = diretorioEntrada.getPath() + File.separator + nomeArquivo;
                    String nomeArquivoSaida = diretorioSaida.getPath() + File.separator + arquivo.getName().replace(".txt", "_abb.txt");

                    ArvoreABB arvoreABB = criarArvoreABB(trabalhaArvores, caminhoArquivoEntrada);

                    try {
                        trabalhaArvores.gerarArquivoArvoreABB(caminhoArquivoEntrada, nomeArquivoSaida);
                        System.out.println("Arquivo ABB gerado com sucesso!");
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Erro ao gerar arquivo ABB.");
                    }
                }
            }
        } else {
            System.out.println("Nenhum arquivo encontrado na pasta de entrada.");
        }
    }

    private static ArvoreABB criarArvoreABB(TrabalhaArvores trabalhaArvores, String nomeArquivo) throws IOException {
        ArvoreABB arvoreABB = new ArvoreABB();
        trabalhaArvores.lerArquivo(nomeArquivo);

        // Adapte essa lógica conforme necessário
        for (Item item : trabalhaArvores.getVetor()) {
            NoABB no = arvoreABB.pesquisar(item.getCpf());
            if (no == null) {
                arvoreABB.inserir(item);
            }
        }

        arvoreABB.balancear();

        return arvoreABB;
    }

    private static void processarArvoreAVL(TrabalhaArvoresAVL trabalhaArvoresAVL, String pastaEntrada, String pastaSaida, String nomeMetodo) throws IOException {
        File diretorioEntrada = new File(pastaEntrada);
        File diretorioSaida = new File(pastaSaida);

        if (!diretorioSaida.exists()) {
            diretorioSaida.mkdirs();
        }

        File[] arquivos = diretorioEntrada.listFiles();

        if (arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isFile() && arquivo.getName().endsWith(".txt")) {
                    String nomeArquivo = arquivo.getName();
                    String caminhoArquivoEntrada = diretorioEntrada.getPath() + File.separator + nomeArquivo;
                    String nomeArquivoSaida = diretorioSaida.getPath() + File.separator + arquivo.getName().replace(".txt", "_avl.txt");

                    ArvoreAVL arvoreAVL = criarArvoreAVL(trabalhaArvoresAVL, caminhoArquivoEntrada);

                    try {
                        trabalhaArvoresAVL.gerarArquivoArvoreAVL(caminhoArquivoEntrada, nomeArquivoSaida);
                        System.out.println("Arquivo AVL gerado com sucesso!");
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Erro ao gerar arquivo AVL.");
                    }
                }
            }
        } else {
            System.out.println("Nenhum arquivo encontrado na pasta de entrada.");
        }
    }

    private static ArvoreAVL criarArvoreAVL(TrabalhaArvoresAVL trabalhaArvoresAVL, String nomeArquivo) throws IOException {
        ArvoreAVL arvoreAVL = new ArvoreAVL();
        trabalhaArvoresAVL.lerArquivo(nomeArquivo);

        // Adapte essa lógica conforme necessário
        for (Item item : trabalhaArvoresAVL.getVetor()) {
            NoAVL no = arvoreAVL.pesquisar(item.getCpf());
            if (no == null) {
                arvoreAVL.inserir(item);
            }
        }

        return arvoreAVL;
    }

    private static void processarTabelaHash(String pastaEntrada, String pastaSaida, String nomeMetodo) throws IOException {
        File diretorioEntrada = new File(pastaEntrada);
        File diretorioSaida = new File(pastaSaida);

        if (!diretorioSaida.exists()) {
            diretorioSaida.mkdir();
        }

        File[] arquivos = diretorioEntrada.listFiles();

        if (arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isFile() && arquivo.getName().endsWith(".txt")) {
                    String nomeArquivo = arquivo.getName();
                    String caminhoArquivoEntrada = diretorioEntrada.getPath() + File.separator + nomeArquivo;

                    int tamanhoTabela = TabelaHash.calcularTamanhoTabela(caminhoArquivoEntrada);
                    TabelaHash tabelaHash = new TabelaHash(tamanhoTabela);

                    // Lê o arquivo e insere os itens na tabela hash
                    tabelaHash.lerArquivo(caminhoArquivoEntrada);

                    // Gera o arquivo da tabela hash
                    tabelaHash.geraArquivo(pastaSaida, nomeArquivo);
                }
            }
        } else {
            System.out.println("Nenhum arquivo encontrado na pasta de entrada.");
        }
    }


}
