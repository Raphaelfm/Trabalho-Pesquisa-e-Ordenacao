package arvoreABB;

import dados.Item;
import dados.NoABB;

import java.io.*;
import java.util.LinkedList;

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
                LinkedList<Item> itens = noEncontrado.getItens();

                if (itens != null && !itens.isEmpty()) {
                    for (Item item : itens) {
                        escreverDadosNoArquivo(bw, noEncontrado, cpf);
                    }

                    //arvoreABB.imprimirArvore("01682240980");
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

    private void escreverDadosNoArquivo(BufferedWriter bw, NoABB no, String cpfProcurado) throws IOException {
        if (no != null) {
            if (no.getItens().get(0).getCpf().equals(cpfProcurado)) {
                bw.write("CPF " + no.getItens().get(0).getCpf() + ":");
                bw.newLine();

                double saldoTotal = percorrerArvore(bw, no, cpfProcurado);

                bw.write("Saldo total: " + saldoTotal);
                bw.newLine();
            } else {
                // Se o CPF não for encontrado, gravar a informação correspondente
                bw.write("CPF " + cpfProcurado + ":");
                bw.newLine();
                bw.write("CPF não encontrado.");
                bw.newLine();
            }

            bw.flush();
        }
    }

    private double percorrerArvore(BufferedWriter bw, NoABB no, String cpfProcurado) throws IOException {
        double saldoTotal = 0;

        if (no != null) {
            if (no.getItens().get(0).getCpf().equals(cpfProcurado)) {
                for (Item conta : no.getItens()) {
                    bw.write("Agencia " + conta.getAgencia() + " Conta " + conta.getNumero() + " Saldo: " + conta.getSaldo());
                    bw.newLine();
                    saldoTotal += conta.getSaldo();
                }
            }

            // Percorre os nós à esquerda e à direita e acumula os saldos
            saldoTotal += percorrerArvore(bw, no.getEsq(), cpfProcurado);
            saldoTotal += percorrerArvore(bw, no.getDir(), cpfProcurado);
        }

        return saldoTotal;
    }
}
