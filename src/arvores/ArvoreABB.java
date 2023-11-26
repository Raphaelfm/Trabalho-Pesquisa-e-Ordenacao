package arvores;


import dados.Item;
import dados.NoABB;

public class ArvoreABB {
    private NoABB raiz;
    private int quant;

    public ArvoreABB() {
        this.quant = 0;
        this.raiz = null;
    }

    public NoABB pesquisar(String cpf) {
        return pesquisar(cpf, this.raiz);
    }

    private NoABB pesquisar(String cpf, NoABB no) {
        if (no == null) {
            return null;
        } else if (cpf.equals(no.getItem().getCpf())) {
            return no;
        } else if (cpf.compareTo(no.getItem().getCpf()) > 0) {
            return pesquisar(cpf, no.getDir());
        } else {
            return pesquisar(cpf, no.getEsq());
        }
    }

    public boolean inserir(Item item) {
        NoABB aux = pesquisar(item.getCpf());
        if (aux != null) {
            return false;
        } else {
            this.raiz = inserir(item, this.raiz);
            return true;
        }
    }

    private NoABB inserir(Item item, NoABB no) {
        if (no == null) {
            no = new NoABB(item);
            this.quant++;
        } else if (item.getCpf().compareTo(no.getItem().getCpf()) > 0) {
            no.setDir(inserir(item, no.getDir()));
        } else {
            no.setEsq(inserir(item, no.getEsq()));
        }
        return no;
    }

    // Métodos de balanceamento
    public VetorItem camCentral() {
        VetorItem vetor = new VetorItem(quant);
        fazCamCentral(this.raiz, vetor);
        return vetor;
    }

    private VetorItem fazCamCentral(NoABB no, VetorItem vetor) {
        if (no != null) {
            vetor = fazCamCentral(no.getEsq(), vetor);
            vetor.inserir(no.getItem());
            vetor = fazCamCentral(no.getDir(), vetor);
        }
        return vetor;
    }

    public void balancear() {
        VetorItem vetor = camCentral();
        this.raiz = balancear(vetor, 0, quant - 1);
    }

    private NoABB balancear(VetorItem vetor, int inicio, int fim) {
        if (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            NoABB no = new NoABB(vetor.get(meio));
            // balancear a parte esquerda do vetor
            no.setEsq(balancear(vetor, inicio, meio - 1));
            // balancear a parte direita do vetor
            no.setDir(balancear(vetor, meio + 1, fim));
            return no;
        }
        return null;
    }

    public void imprimirEmOrdem() {
        imprimirEmOrdem(this.raiz);
    }

    private void imprimirEmOrdem(NoABB no) {
        if (no != null) {
            imprimirEmOrdem(no.getEsq());
            System.out.println(no.getItem().getCpf()); // ou outra informação que você queira imprimir
            imprimirEmOrdem(no.getDir());
        }
    }
}
