package services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import database.EntregadorDAO;
import database.ItemPedidoDAO;
import database.PedidoDAO;
import entities.Cliente;
import entities.Entregador;
import entities.ItemPedido;
import entities.Pedido;
import entities.Restaurante;
import view.ItemPedidoView;
import view.ProdutoRestauranteView;
import database.ProdutoRestauranteDAO;

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
	private Connection conn;
	private PedidoDAO pedidoDAO;
	private ProdutoRestauranteDAO produtoRestauranteDAO;
	private EntregadorDAO entregadorDAO;
	private ItemPedidoDAO itemPedidoDAO;


	public PedidoService(Connection conn) {
		this.pedidoDAO = new PedidoDAO();
		this.produtoRestauranteDAO = new ProdutoRestauranteDAO();
		this.itemPedidoDAO = new ItemPedidoDAO();
		this.entregadorDAO = new EntregadorDAO();
		this.conn = conn;
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
	
	
	/**
	 * cadastra um pedido no sistema e todos os items desse pedido
	 * também diminui do estoque do restaurante os produtos que foram comprados
	 * @param r objeto restaurante
	 * @param c objeto cliente
	 * @param carrinhoCompras do cliente
	 */
	public void cadastrarPedido(
		Restaurante r, 
		Cliente c, 
		ArrayList<ItemPedidoView> carrinhoCompras
	) {
		//pedidoDAO local sem argumentos que usará a conexão com autoCommit desativado
		PedidoDAO pedidoDAO = new PedidoDAO();
		
		try {
			//desativa o salvamento automático do banco de dados
			conn.setAutoCommit(false);
			
			//cadastra o pedido e pega a chave primária gerada para cadastrar items
			int idPedido = pedidoDAO.inserirPedido(conn, r, c);
			
			for (ItemPedidoView item: carrinhoCompras) {
				
				//diminui a quantidade comprada do estoque e armazena o resultado para checar
				boolean estoqueAtualizado = produtoRestauranteDAO.diminuirEstoque(conn, r.getCnpj(), item);
				
				if (!estoqueAtualizado) {
					throw new RuntimeException("Estoque insuficiente");
				}
				
				//instancia um ItemPedido que será inserido no N:N e vincula os parametros
				ItemPedido ip = new ItemPedido();
				ip.setNumeroPedido(idPedido);
				ip.setCodigoProduto(item.getCodigoProduto());
				ip.setQuantidade(item.getQuantidade());
				
				itemPedidoDAO.inserirItemPedido(conn, ip);
				
				conn.commit();
			}
			
		} catch (Exception e) {
			
			try {
				//em caso de algum erro, desfaz todas as inserções feitas no banco de dados
	            conn.rollback();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
			
			throw new RuntimeException(e);
			
		} finally {
			
			try {
				//reativa o auto commit da conexão para outras operações
	            conn.setAutoCommit(true);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
			
		}
	}
	
	/**
	 * Retorna todos os pedidos associados ao restaurante em sessão, filtrando eles por status de preparo
	 * @param cnpj do restaurante
	 * @param status do pedido
	 * @return ArrayList do tipo pedido
	 */
	public ArrayList<Pedido> retornarPedidosRestaurante(String cnpj, String status){
		return pedidoDAO.listarPedidosPorRestaurante(conn, cnpj, status);
	}
	
	/**
	 * Retorna todos os pedidos que determinado cliente em sessão já comprou no sistema
	 * @param cpf do cliente
	 * @return ArrayList do tipo pedido
	 */
	public ArrayList<Pedido> retornarPedidosCliente(String cpf){
		return pedidoDAO.listarPedidosPorCliente(conn, cpf);
	}
	
	/**
	 * Retorna todos os itens associados a um pedido no sistema
	 * @param codigoPedido
	 * @return ArrayList do tipo ItemPedidoView com todos os itens de um pedido
	 */
	public ArrayList<ItemPedidoView> retornarItensPedido(int codigoPedido){
		return itemPedidoDAO.retornarItensPedido(conn, codigoPedido);
	}
	
	/**
	 * Atualiza as informações de entrega de um pedido e a disponibilidade do entregador
	 * utilizando transação para garantir consistência no banco de dados.
	 * @param p pedido que terá os dados de entrega atualizados
	 * @param e entregador responsável pela entrega
	 * @param eDisp nova disponibilidade do entregador
	 * @param statusPedido novo status do pedido
	 * @return boolean
	 */
	public boolean atualizarEntregaPedido(Pedido p, Entregador e, short eDisp, String statusPedido) {

	    try {

	    	//salvamento do banco de dados é desativado
	        conn.setAutoCommit(false);

	        //atualiza os atributos do pedido
	        p.setCpfEntregador(e.getCpf());
	        p.setStatus(statusPedido);

	        //executa e verifica se não houve erros na atualização
	        if (!pedidoDAO.atualizarPedido(conn, p)) {
	        	//desfaz alterações e não salva nada se houve erro
	            conn.rollback(); 
	            return false;
	        }

	        //atualiza os atributos do entregador
	        e.setDisponibilidade(eDisp);

	      //executa e verifica se não houve erros na atualização
	        if (!entregadorDAO.atualizarEntregador(conn, e)) {
	        	//desfaz alterações e não salva nada se houve erro
	            conn.rollback();
	            return false;
	        }

	        //se tudo executou de maneira correta confirma as mudanças e retorna êxito
	        conn.commit();
	        return true;

	    } catch (Exception ex) {

	        try {
	            conn.rollback();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }

	        ex.printStackTrace();
	        return false;

	    } finally {

	        try {
	            conn.setAutoCommit(true);
	        } catch (SQLException e2) {
	            e2.printStackTrace();
	        }
	    }
	}
	
	/**
	 * retorna o valor total da compra acrescido da taxa de entrega do restaurante
	 * @param valorTotal da compra
	 * @return double
	 */
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
