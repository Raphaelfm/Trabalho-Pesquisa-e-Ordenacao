package ordenacao;

import dados.Item;

public class MetodosOrdenacao {
    private Item[] vetor;

    public MetodosOrdenacao(Item[] vetor){
        this.vetor = vetor;
    }

    // Métodos de ordenação
    public void ordenacao(int quant, String nomeMetodo) {
        if (vetor != null) { // Adicionado para verificar se o vetor está inicializado

            switch (nomeMetodo){
                case "quickSort":
                    quickSort(0, quant - 1);
                    break;
                case "shellSort":
                    shellSort(quant);
                    break;
                default:
                    System.out.println("Tipo de ordenação não encontrado, tente novamente");
                    break;
            }
        } else {
            System.out.println("Vetor não inicializado. Certifique-se de ler o arquivo antes de chamar o quicksort.");
        }
    }

    private void quickSort(int esq, int dir) {
        int i = esq, j = dir;
        Item temp;
        String pivo = vetor[(i + j) / 2].getChaveComposta(); // Alterado para String
        do {
            while (vetor[i].getChaveComposta().compareTo(pivo) < 0) // Usando compareTo para comparar strings
                i++;
            while (vetor[j].getChaveComposta().compareTo(pivo) > 0) // Usando compareTo para comparar strings
                j--;
            if (i <= j) {
                temp = vetor[i];
                vetor[i] = vetor[j];
                vetor[j] = temp;
                i++;
                j--;
            }
        } while (i <= j);
        if (esq < j)
            quickSort(esq, j);
        if (dir > i)
            quickSort(i, dir);
    }

    public void shellSort(int quant) {
        int i, j, h;
        Item temp;
        h = 1;
        do {
            h = 3 * h + 1;
        } while (h < quant);
        do {
            h = h / 3;
            for (i = h; i < quant; i++) {
                temp = this.vetor[i];
                j = i;
                while (this.vetor[j - h].getChaveComposta().compareTo(temp.getChave()) > 0) {
                    this.vetor[j] = this.vetor[j - h];
                    j -= h;
                    if (j < h) {
                        break;
                    }
                }
                this.vetor[j] = temp;
            }
        } while (h != 1);
    }
}
