package arvores;

import dados.Item;
public class VetorItem {
    private Item[] vetor;
    private int tamanho;

    public VetorItem(int capacidade) {
        this.vetor = new Item[capacidade];
        this.tamanho = 0;
    }

    public void inserir(Item item) {
        if (tamanho < vetor.length) {
            vetor[tamanho++] = item;
        } else {
            // Tratar o caso em que o vetor está cheio (pode redimensionar, por exemplo)
        }
    }

    public Item get(int indice) {
        if (indice >= 0 && indice < tamanho) {
            return vetor[indice];
        } else {
            // Tratar o caso de índice inválido
            return null;
        }
    }

    public int getTamanho() {
        return tamanho;
    }
}

