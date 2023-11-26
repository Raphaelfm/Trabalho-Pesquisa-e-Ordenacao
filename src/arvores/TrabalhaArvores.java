package arvores;

import dados.Item;
import dados.NoABB;

import java.io.*;

public class TrabalhaArvores {
    private ArvoreABB arvoreABB;
    private Item[] vetor;
    private int quant;

    public Item[] getVetor() {
        return vetor;
    }
    public void gerarArquivoArvoreABB(String nomeEntrada, String nomeSaida) throws IOException {
        arvoreABB = new ArvoreABB();

        BufferedReader brl = new BufferedReader(new FileReader(nomeEntrada));

        int numLinhas = 0;
        while (brl.readLine() != null) {
            numLinhas++;
        }

        // Reiniciar o leitor para ler o arquivo novamente
        brl.close();

        vetor = new Item[numLinhas];
        quant = 0;

        lerArquivo(nomeEntrada);

        for (int i = 0; i < quant; i++) {
            arvoreABB.inserir(vetor[i]);
        }

        arvoreABB.balancear();

        // Agora, para cada CPF do arquivo de CPFs, faça a pesquisa e escreva o resultado no arquivo de saída
        // Suponha que o arquivo de CPFs seja "src/CPFs/CPF.txt"
        File arquivoCPFs = new File("src/CPFs/CPF.txt");

        BufferedReader br = new BufferedReader(new FileReader(arquivoCPFs));
        BufferedWriter bw = new BufferedWriter(new FileWriter(nomeSaida));

        String cpf;
        while ((cpf = br.readLine()) != null) {
            NoABB noEncontrado = arvoreABB.pesquisar(cpf);

            if (noEncontrado != null) {
                Item item = noEncontrado.getItem();

                if (item != null) {
                    escreverDadosNoArquivo(bw, item);
                } else {
                    // Lidere com a situação em que o item é nulo
                    bw.write("CPF " + cpf + ": ITEM NULO");
                    bw.newLine();
                }
            } else {
                bw.write("CPF " + cpf + ": INEXISTENTE");
                bw.newLine();
            }
        }

        System.out.println("Arquivo ABB gerado com sucesso!");
    }

    public void lerArquivo(String nomeArquivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(nomeArquivo));

        // Contar a quantidade de linhas no arquivo
        int numLinhas = 0;
        while (br.readLine() != null) {
            numLinhas++;
        }

        // Reiniciar o leitor para ler o arquivo novamente
        br.close();
        br = new BufferedReader(new FileReader(nomeArquivo));

        vetor = new Item[numLinhas]; // Definir o tamanho do vetor com base na quantidade de linhas
        quant = 0;
        String linha;

        while ((linha = br.readLine()) != null) {
            String[] campos = linha.split(";");

            if (campos.length >= 4) {
                Item item = new Item(campos[0], campos[1], Double.parseDouble(campos[2]), campos[3]);
                vetor[quant++] = item;
            }
        }

        br.close();
    }


    private void escreverDadosNoArquivo(BufferedWriter bw, Item item) throws IOException {
        bw.write("CPF " + item.getChave() + ":");
        bw.newLine();
        bw.write("Agencia " + item.getAgencia() + " Conta " + item.getNumero() + " Saldo: " + item.getSaldo());
        bw.newLine();
        // Continue escrevendo os outros dados conforme necessário
        // Lembre-se de calcular o saldo total corretamente
        // Por exemplo, crie uma variável para acumular o saldo total e some a cada conta do mesmo CPF
        double saldoTotal = item.getSaldo(); // Inicializa com o saldo da primeira conta
        for (int i = 0; i < quant; i++) {
            if (vetor[i].getChave().equals(item.getChave())) {
                saldoTotal += vetor[i].getSaldo();
            }
        }
        bw.write("Saldo total: " + saldoTotal);
        bw.newLine();
    }

    // Adicione outros métodos da árvore que você possa precisar nesta classe
    // Exemplo: métodos para inserção, pesquisa, balanceamento, etc.
}
