package arvoreAVL;

import dados.Item;

public class ArvoreAVL {
    private NoAVL raiz;
    private boolean h;
    private int quant;

    public ArvoreAVL() {
        this.raiz = null;
        this.quant = 0;
    }

    public NoAVL getRaiz() {
        return raiz;
    }

    public int getQuant() {
        return quant;
    }

    public void inserir(Item item) {
        this.raiz = inserir(item, this.raiz);
    }

    private NoAVL inserir(Item item, NoAVL no) {
        if (no == null) {
            NoAVL novo = new NoAVL(item);
            this.h = true;
            return novo;
        } else {
            if (item.getCpf().compareTo(no.getItens().get(0).getCpf()) < 0) {
                no.setEsq(inserir(item, no.getEsq()));
                no = balancearEsq(no);
                return no;
            } else if (item.getCpf().compareTo(no.getItens().get(0).getCpf()) > 0) {
                no.setDir(inserir(item, no.getDir()));
                no = balancearDir(no);
                return no;
            } else {
                no.getItens().add(item);
                return no;
            }
        }
    }

    private NoAVL balancearEsq(NoAVL no) {
        if (this.h) {
            switch (no.getFatorBalanceamento()) {
                case -1:
                    no.setFatorBalanceamento(0);
                    this.h = false;
                    break;
                case 0:
                    no.setFatorBalanceamento(1);
                    break;
                case 1:
                    no = rotacaoDireita(no);
                    break;
            }
        }
        return no;
    }

    private NoAVL balancearDir(NoAVL no) {
        if (this.h) {
            switch (no.getFatorBalanceamento()) {
                case 1:
                    no.setFatorBalanceamento(0);
                    this.h = false;
                    break;
                case 0:
                    no.setFatorBalanceamento(-1);
                    break;
                case -1:
                    no = rotacaoEsquerda(no);
                    break;
            }
        }
        return no;
    }

    private NoAVL rotacaoDireita(NoAVL no) {
        NoAVL temp1, temp2;
        temp1 = no.getEsq();

        if (temp1 == null) {
            return no; // Não há rotação se temp1 for nulo
        }

        if (temp1.getFatorBalanceamento() == -1) { // Faz RD
            no.setEsq(temp1.getDir());
            temp1.setDir(no);
            no.setFatorBalanceamento(0);
            no = temp1;
        } else { // Faz RDD
            temp2 = temp1.getDir();

            if (temp2 != null) { // Verifica se temp2 não é nulo
                temp1.setDir(temp2.getEsq());
                temp2.setEsq(temp1);
                no.setEsq(temp2.getDir());
                temp2.setDir(no);

                // Recalcula o FB do nó à direita na nova árvore
                if (temp2.getFatorBalanceamento() == -1) {
                    no.setFatorBalanceamento(1);
                } else {
                    no.setFatorBalanceamento(0);
                }

                // Recalcula o FB do nó à esquerda na nova árvore
                if (temp2.getFatorBalanceamento() == 1) {
                    temp1.setFatorBalanceamento(-1);
                } else {
                    temp1.setFatorBalanceamento(0);
                }

                no = temp2;
            }
        }

        no.setFatorBalanceamento(0);
        this.h = false;
        return no;
    }


    private NoAVL rotacaoEsquerda(NoAVL no) {
        NoAVL temp1, temp2;
        temp1 = no.getDir();

        if (temp1 == null) {
            return no; // Não há rotação se temp1 for nulo
        }

        if (temp1.getFatorBalanceamento() == 1) {
            no.setDir(temp1.getEsq());
            temp1.setEsq(no);
            no.setFatorBalanceamento(0);
            no = temp1;
        } else {
            temp2 = temp1.getEsq();

            if (temp2 != null) {
                temp1.setEsq(temp2.getDir());
                temp2.setDir(temp1);
                no.setDir(temp2.getEsq());
                temp2.setEsq(no);

                if (temp2.getFatorBalanceamento() == 1) {
                    no.setFatorBalanceamento(-1);
                } else {
                    no.setFatorBalanceamento(0);
                }

                if (temp2.getFatorBalanceamento() == -1) {
                    temp1.setFatorBalanceamento(1);
                } else {
                    temp1.setFatorBalanceamento(0);
                }

                no = temp2;
            }
        }

        no.setFatorBalanceamento(0);
        this.h = false;
        return no;
    }


    // Outros métodos necessários

    // Método para pesquisar por CPF
    public NoAVL pesquisar(String cpf) {
        return pesquisar(cpf, this.raiz);
    }

    private NoAVL pesquisar(String cpf, NoAVL no) {
        if (no == null) {
            return null;
        } else if (cpf.equals(no.getItens().get(0).getCpf())) {
            return no;
        } else if (cpf.compareTo(no.getItens().get(0).getCpf()) < 0) {
            return pesquisar(cpf, no.getEsq());
        } else {
            return pesquisar(cpf, no.getDir());
        }
    }
}

