package dados;

import java.util.LinkedList;

public class NoABB {
    private LinkedList<Item> itens;
    private NoABB esq, dir;

    public NoABB(Item item) {
        this.itens = new LinkedList<>();
        this.itens.add(item);
        this.esq = this.dir = null;
    }


    public LinkedList<Item> getItens() {
        return itens;
    }

    public NoABB getEsq() {
        return esq;
    }

    public void setEsq(NoABB esq) {
        this.esq = esq;
    }

    public NoABB getDir() {
        return dir;
    }

    public void setDir(NoABB dir) {
        this.dir = dir;
    }
}
