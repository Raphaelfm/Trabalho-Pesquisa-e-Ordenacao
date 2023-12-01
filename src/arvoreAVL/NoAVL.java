package arvoreAVL;

import dados.Item;

import java.util.LinkedList;

public class NoAVL {
    private LinkedList<Item> itens;
    private NoAVL esq, dir;
    private int fatorBalanceamento;

    public NoAVL(Item item) {
        this.itens = new LinkedList<>();
        this.itens.add(item);
        this.fatorBalanceamento = 0;
        this.esq = null;
        this.dir = null;
    }

    public LinkedList<Item> getItens() {
        return itens;
    }

    public NoAVL getEsq() {
        return esq;
    }

    public void setEsq(NoAVL esq) {
        this.esq = esq;
    }

    public NoAVL getDir() {
        return dir;
    }

    public void setDir(NoAVL dir) {
        this.dir = dir;
    }

    public int getFatorBalanceamento() {
        return fatorBalanceamento;
    }

    public void setFatorBalanceamento(int fatorBalanceamento) {
        this.fatorBalanceamento = fatorBalanceamento;
    }

    // Outros métodos necessários

    @Override
    public String toString() {
        return "NoAvl{" +
                "itens=" + itens +
                ", fatorBalanceamento=" + fatorBalanceamento +
                '}';
    }
}
