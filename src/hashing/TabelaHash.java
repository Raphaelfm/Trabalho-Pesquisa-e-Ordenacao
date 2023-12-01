package hashing;

import dados.Item;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class TabelaHash {
    private LinkedList<Item[]>[] tabela; // A matriz de listas encadeadas de arrays de itens
    private int tamanho; // O tamanho da tabela

    // Construtor para criar a tabela hash com um tamanho específico
    public TabelaHash(int tamanho) {
        this.tamanho = tamanho;
        this.tabela = new LinkedList[tamanho];
        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new LinkedList<>();
        }
    }

    // Método para inserir um item na tabela hash
    // Método para inserir um item na tabela hash
    public void insere(Item item) {
        int indice = calcularIndice(item.getCpf());
        LinkedList<Item[]> lista = tabela[indice];

        // Verifique se o CPF já existe na lista
        for (Item[] elementos : lista) {
            if (elementos[0].getCpf().equals(item.getCpf())) {
                // O CPF já existe, adicione o item ao array existente
                Item[] novoArray = new Item[elementos.length + 1];
                for (int i = 0; i < elementos.length; i++) {
                    novoArray[i] = elementos[i];
                }
                novoArray[elementos.length] = item;
                lista.remove(elementos); // Remove o array antigo
                lista.add(novoArray); // Adiciona o novo array
                return;
            }
        }

        // Se o CPF não existe na lista, crie um novo array com um único item e adicione-o à lista
        Item[] novoArray = new Item[1];
        novoArray[0] = item;
        lista.add(novoArray);
    }



    // Método para pesquisar um CPF na tabela hash e retornar todos os itens associados
    public LinkedList<Item> pesquisa(String cpf) {
        int indice = calcularIndice(cpf);
        LinkedList<Item[]> lista = tabela[indice];

        // Pesquise pelo CPF na lista encadeada correspondente
        for (Item[] elementos : lista) {
            if (elementos[0].getCpf().equals(cpf)) {
                // Retorna todos os itens associados ao CPF
                LinkedList<Item> itensAssociados = new LinkedList<>();
                for (Item item : elementos) {
                    itensAssociados.add(item);
                }
                return itensAssociados;
            }
        }

        return null; // CPF não encontrado
    }

    // Método para gerar os arquivos com base nos registros armazenados
    public void geraArquivo(String caminhoSaida, String nomeArquivo) throws IOException {
        String nomeArquivoSaida = caminhoSaida + File.separator + nomeArquivo.replace(".txt", "_hash.txt");
        File diretorioSaida = new File(caminhoSaida);

        if (!diretorioSaida.exists()) {
            diretorioSaida.mkdirs(); // Cria o diretório de saída se não existir
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivoSaida));
        File nomeArquivoEntradaCPF = new File("src/CPFs/CPF.txt");
        // Lê o arquivo de CPFs linha a linha
        BufferedReader brCPF = new BufferedReader(new FileReader(nomeArquivoEntradaCPF));
        String cpf;

        while ((cpf = brCPF.readLine()) != null) {
            LinkedList<Item> itensAssociados = pesquisa(cpf); // Pesquisa o CPF na tabela hash

            if (itensAssociados != null) {
                // CPF encontrado na tabela hash, escreve os detalhes no arquivo de saída
                writer.write("CPF " + cpf + ":");
                writer.newLine();

                double saldoTotal = 0;
                boolean cpfEncontrado = false;

                for (Item item : itensAssociados) {
                    writer.write("Agencia " + item.getAgencia() + " Conta " + item.getNumero() + " Saldo: " + item.getSaldo());
                    writer.newLine();
                    saldoTotal += item.getSaldo();
                    cpfEncontrado = true;
                }

                if (!cpfEncontrado) {
                    writer.write("INEXISTENTE");
                    writer.newLine();
                } else {
                    writer.write("Saldo total: " + saldoTotal);
                    writer.newLine();
                }
            } else {
                // CPF não encontrado na tabela hash, escreve "INEXISTENTE" no arquivo de saída
                writer.write("CPF " + cpf + ":");
                writer.newLine();
                writer.write("INEXISTENTE");
                writer.newLine();
            }
        }

        brCPF.close();
        writer.close();
    }


    public void lerArquivo(String nomeArquivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(nomeArquivo));
        String linha;

        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(";");

            if (campos.length >= 4) {
                String agencia = campos[0];
                String numero = campos[1]; // Linha 32 - Possível causa do erro
                double saldo = Double.parseDouble(campos[2]);
                String cpf = campos[3];

                Item item = new Item(agencia, numero, saldo, cpf);
                insere(item);
            }
        }

        br.close();
    }



    // Método para calcular o índice com base no CPF
    private int calcularIndice(String cpf) {
        // Calcula o índice como a soma dos valores dos caracteres do CPF
        int soma = 0;
        for (char c : cpf.toCharArray()) {
            soma += c;
        }

        // Retorna o índice calculado usando o tamanho da tabela
        return soma % tamanho;
    }

    // Método para calcular o tamanho da tabela com base no arquivo de entrada
    public static int calcularTamanhoTabela(String nomeArquivo) throws IOException {
        Set<String> cpfsUnicos = new HashSet<>();

        BufferedReader br = new BufferedReader(new FileReader(nomeArquivo));
        String linha;
        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(";");
            if (campos.length >= 4) {
                String cpf = campos[3];
                cpfsUnicos.add(cpf);
            }
        }

        br.close();

        // Retorne o tamanho da tabela com base na contagem de CPFs únicos
        return cpfsUnicos.size();
    }

}
