package entities;

/**
 * Entidade: Entregador
 *
 * Descrição:
 * Representa um entregador cadastrado no sistema.
 *
 * Responsabilidades:
 * - Armazenar informações pessoais do entregador
 * - Manter dados do veículo utilizado para entregas
 * - Controlar a disponibilidade do entregador
 * - Exibir informações formatadas do entregador
 *
 * @author Rodrigo
 * @since 20-04-2026
 */

public class Entregador extends Usuario {
	private String veiculo;
	private Short disponibilidade;
		
	/**
	 * Construtor sem argumentos
	 */
	public Entregador() {
		super();
	}

	public String getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(String veiculo) {
		this.veiculo = veiculo;
	}

	public short getDisponibilidade() {
		return disponibilidade;
	}	

	/**
	 * Retorna uma mensagem de disponibilidade do entregador baseado no índice de disponibilidade
	 * @return uma string com os dois possíveis status de disponibilidade
	 */
	public String getDisponibilidadeString() {
		return (this.disponibilidade == 0) ? "Livre" : "Ocupado";
	}

	public void setDisponibilidade(Short disponibilidade) {
		this.disponibilidade = disponibilidade;
	}
	
	@Override
	public String toString() {
		return String.format("Nome Completo: %s %s %s | "
							+ "CPF: %s | "
							+ "Telefone: %s | "
							+ "Veículo: %s | "
							+ "Disponibilidade: %s", 
							primeiroNome, 
							nomeMeio, 
							ultimoNome, 
							cpf,
							telefone, 
							veiculo, 
							this.getDisponibilidadeString());
		
	}
	
	
	
}
