package services;

import java.sql.Connection;
import java.util.ArrayList;
import database.PedidoDAO;
import entities.Restaurante;
import view.ItemPedidoView;
import view.ProdutoRestauranteView;

/**
 * Classe: PedidoService
 *
 * Descrição:
 * Classe responsável por gerenciar as regras de negócio do pedido
 *
 * Responsabilidades:
 * - oferecer métodos de validação das informações
 * - se comunicar com a camada de dados
 *
 * @author Rodrigo
 * @since 04-05-2026
 */

public class PedidoService {
	//conexão com o banco de dados que será usada em todas as operações
	private PedidoDAO dao;

	
	public PedidoService(Connection conn) {
		this.dao = new PedidoDAO(conn);
	}
	
	/**
	 * método genérico para validar se o índice informado é valido para listas do tipo ArrayList de qualquer valor
	 * @param lista a ser validada
	 * @param index informado
	 */
	public <T> void validarIndex(ArrayList<T> lista, int index) {
		if(!indexValido(lista, index)) {
			throw new IllegalArgumentException("Informe um índice válido");
		}
	}
	
//	/**
//	 * valida se o indice inserido pelo usuário está no intervalo de produtos disponíveis
//	 * @param listaProdutos
//	 * @param index
//	 */
//	public void validarIndexProduto(ArrayList<ProdutoRestauranteView> listaProdutos, int index) {
//		if (!indexValido(listaProdutos, index)) {
//			throw new IllegalArgumentException("Informe um índice válido");
//		}
//	}
	
	/**
	 * valida se a quantidade informada pelo usuário é válida
	 * @param produtoTemp
	 * @param quantidade
	 */
	public void validarQuantidade(ProdutoRestauranteView produtoTemp, int quantidade) {
		if (!quantidadeValida(produtoTemp, quantidade)){
			throw new IllegalArgumentException("Digite uma quantidade válida");
		}
	}
	
	/**
	 * instancia um ItemPedidoView e o retorna para o carrinho de compras do usuário
	 * @param produtoTemp
	 * @param quantidade
	 * @return ItemPedidoView
	 */
	public ItemPedidoView criarItemPedido(ProdutoRestauranteView produtoTemp, int quantidade) {
		ItemPedidoView ip = new ItemPedidoView();
		ip.setNome(produtoTemp.getNomeProduto());
		ip.setPreco(produtoTemp.getPrecoProduto());
		ip.setCodigo(produtoTemp.getCodigoProduto());
		ip.setQuantidade(quantidade);
		return ip;
	}
	
	/**
	 * Busca um produto no carrinho pelo seu código e retorna sua posição na lista.
	 * @param carrinhoCompras lista de compras do usuário
	 * @param codigoProduto código do produto a ser buscado
	 * @return índice do produto na lista
	 */
	public int retornarPosicaoItemCarrinho(
		ArrayList<ItemPedidoView> carrinhoCompras, 
		int codigoProduto
	) {
		for (int i = 0; i < carrinhoCompras.size(); i++) {
			if (carrinhoCompras.get(i).getCodigoProduto() == codigoProduto) {
				return i; //retorna a posição
			}
		}
		
		return -1; //não encontrado
	}
	
	/**
	 * Busca um produto na lista com base no seu código.
	 * @param listaProdutos lista de produtos disponíveis
	 * @param codigoProduto código do produto a ser buscado
	 * @return o produto correspondente ao código informado, ou null caso não seja encontrado
	 */
	public ProdutoRestauranteView retornarProdutoPeloCodigo(
		ArrayList<ProdutoRestauranteView> listaProdutos,
		int codigoProduto
	) {
		for (int i = 0; i < listaProdutos.size(); i++) {
			if (listaProdutos.get(i).getCodigoProduto() == codigoProduto) {
				return listaProdutos.get(i); //retorna a posição
			}
		}
		return null;
	}
	
	/**
	 * calcula o desconto para o valor total do pedido de um cliente
	 * @param valorTotal do pedido do cliente
	 * @return o valor com o desconto aplicado
	 */
	public double calcularDesconto(double valorTotal) {
		if (valorTotal > 300) {
			return valorTotal * 0.85;
			
		} else if (valorTotal > 200){
			return valorTotal * 0.90;
			
		} else if (valorTotal > 100) {
			return valorTotal * 0.95;
		} else {
			return valorTotal;
		}
	}
	
	public double adicionarTaxaEntrega(double valorTotal) {
		return valorTotal + 8.00;
	}
	

	private <T> boolean indexValido(ArrayList<T> lista, int index) {
		return index >= 0 && index < lista.size();
	}
	
	private boolean quantidadeValida(ProdutoRestauranteView produtoTemp, int quantidade) {
		return quantidade > 0 && quantidade <= produtoTemp.getQuantidadeEstoque(); 
	}
}
