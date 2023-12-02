package ordenacao;

import dados.Item;

import java.io.*;

public class TrabalhaArquivos {
    private String nomeMetodo = "";
    private Item[] vetor;
    private int quant;

    // Método para gerar arquivo ordenado
    public void gerarArquivoOrdenado(String nomeEntrada, String nomeSaida, String nomeMetodo) {
        try {
            lerArquivo(nomeEntrada);

            MetodosOrdenacao ordenacao = new MetodosOrdenacao(vetor);
            this.nomeMetodo = nomeMetodo;

            if (quant > 0) { // Verifica se há itens para ordenar
                ordenacao.ordenacao(quant, nomeMetodo);

                escreverArquivo(nomeSaida);
                System.out.printf("Arquivos ordenados usando %s!\n", nomeMetodo);
            } else {
                System.out.println("Nenhum dado encontrado no arquivo. Não há nada para ordenar.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Métodos de leitura e escrita
    public void lerArquivo(String nomeArquivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(nomeArquivo));

        // Inicializa o vetor com um tamanho razoável
        vetor = new Item[1000];
        quant = 0;

        String linha;

        //br.readLine(); // Ignora a primeira linha (cabeçalho)

        while ((linha = br.readLine()) != null) {
            // Divida a linha em campos usando ponto e vírgula como separador
            String[] campos = linha.split(";");

            if (campos.length >= 4) { // Certifica-se de que há pelo menos 4 campos (índice 3 é o CPF)
                // Certifique-se de que a classe Item tenha um construtor que aceite os campos necessários
                Item item = new Item(campos[0], campos[1], Double.parseDouble(campos[2]), campos[3]); // Adaptar conforme seus campos

                // Se o vetor estiver cheio, aumente seu tamanho
                if (quant == vetor.length) {
                    vetor = redimensionarVetor(vetor, vetor.length * 2);
                }

                vetor[quant++] = item;
            }
        }

        // Fiz dessa forma para redimencionar o vetor
        vetor = redimensionarVetor(vetor, quant);

        br.close();
    }

    // Método para redimensionar o vetor
    private Item[] redimensionarVetor(Item[] vetorAntigo, int novoTamanho) {
        Item[] novoVetor = new Item[novoTamanho];
        System.arraycopy(vetorAntigo, 0, novoVetor, 0, Math.min(vetorAntigo.length, novoTamanho));
        return novoVetor;
    }

    // Método auxiliar para contar linhas no arquivo
    private int contaLinhas(String nomeArquivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            int linhas = 0;

            while (br.readLine() != null) {
                linhas++;
            }

            return linhas;
        }
    }

    public void escreverArquivo(String nomeArquivo) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo));
        for (int i = 0; i < quant; i++) {
            // Lógica para escrever os dados do vetor ordenado no arquivo
            bw.write(vetor[i].toString());
            bw.newLine();
        }
        bw.close();
    }
}
