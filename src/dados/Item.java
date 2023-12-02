package dados;

public class Item {
	private String agencia;
	private String numero;
	private double saldo;
	private String cpf;

	// Construtor de objetos da classe Item
	public Item(String agencia, String numero, double saldo, String cpf) {
		this.agencia = agencia;
		this.numero = numero;
		this.saldo = saldo;
		this.cpf = cpf;
	}

	// Método para obter a chave (neste caso, o CPF)
	public String getChave() {
		return cpf;
	}

	public String getChaveComposta() {
		return cpf + agencia + numero; // Concatena CPF, agência e número da conta
	}

	// Métodos para obter e definir valores
	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	@Override
	public String toString() {
		return agencia + ";" + numero + ";" + saldo + ";" + cpf;
	}
}
