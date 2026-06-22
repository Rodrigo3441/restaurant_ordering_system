package entities;

/**
 * Entidade: Produto
 *
 * Descrição:
 * Representa um produto cadastrado no sistema.
 *
 * Responsabilidades:
 * - Armazenar informações do produto
 * - Manter descrição e identificação do produto
 * - Associar o produto a uma categoria
 *
 * @author Rodrigo
 * @since 20-04-2026
 */

public class Produto {
	private Integer codigo;
	private String nome;
	private String descricao;
	private Integer idCategoria;
	
	/**
	 * Construtor sem argumentos
	 */
	public Produto() {
		
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

}
