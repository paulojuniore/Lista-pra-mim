package com.projeto.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.projeto.comparadores.ComparaCategoria;
import com.projeto.comparadores.ComparaNome;
import com.projeto.excecoes.CampoInvalidoException;
import com.projeto.excecoes.CompraNaoCadastrada;
import com.projeto.util.SistemaMensagens;
import com.projeto.util.ValidadorSistema;

public class ListaDeCompra implements Comparable<ListaDeCompra>{

	/**
	 * Representa o descritor da lista de compras.
	 */
	private String descritor;

	private String dataCriacao;

	private Map<Integer, Compra> compras;

	private boolean finalizada;

	private String local;

	private int valorFinal;

	/**
	 * Constroi uma listaDeCompras a partir de um descritor.
	 * 
	 * @param descritor
	 *            String que representa o descritor da lista de compras.
	 */
	public ListaDeCompra(String descritor) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		this.dataCriacao = dateFormat.format(new Date());
		this.descritor = descritor;
		compras = new HashMap<>();
		this.finalizada = false;
	}

	/**
	 * Metodo que adiciona uma compra na listaDeCompras, caso o item ainda nao tenha
	 * sido adicionado em alguma compra.
	 * 
	 * @param quantidade
	 *            quantidade de itens.
	 * @param item
	 *            instancia de Item.
	 */
	public void adicionaCompraALista(int quantidade, Item item) {
		if (this.compras.keySet().contains(item.getId())) {
			throw new CampoInvalidoException("Item ja cadastrado");
		}
		Compra compra = new Compra(item, quantidade);
		this.compras.put(item.getId(), compra);
	}

	/**
	 * Metodo que atualiza a quantidade de itens de uma compra na lista de compras.
	 * Remove um produto da lista de compras se a quantidade for menor que 1.
	 * 
	 * @param idItem
	 *            id do produto.
	 * @param operacao
	 *            operacao a ser realizada: "adiciona" ou "diminui".
	 * @param quantidade
	 *            qtd a ser removida ou adicionada no atributo quantidade.
	 */
	public void atualizaCompraDeLista(Integer idItem, String operacao, int quantidade) {
		try {
			if (ValidadorSistema.validaOperacao(operacao)) {
				if (!this.compras.containsKey(idItem)) {
					throw new CompraNaoCadastrada(SistemaMensagens.MSG_EXCECAO_ATUALIZA_COMPRA.get());
				}
			}
		} catch (CampoInvalidoException e) {
			throw new CampoInvalidoException(SistemaMensagens.MSG_EXCECAO_ATUALIZA_COMPRA.get() + e.getMessage());
		}

		this.compras.get(idItem).atualizar(operacao, quantidade);
		if (this.compras.get(idItem).getQuantidade() <= 0)
			this.compras.remove(idItem);

	}

	/**
	 * Retorna a representacao visual de uma compra de uma lista de compras
	 * 
	 * @param posicao
	 * @return
	 */
	public String getItemLista(int posicao) {
		List<Compra> listaOrdenada = new ArrayList<>();

		listaOrdenada.addAll(this.adicionaListaComprasPorCategoria("higiene pessoal"));
		listaOrdenada.addAll(this.adicionaListaComprasPorCategoria("limpeza"));
		listaOrdenada.addAll(this.adicionaListaComprasPorCategoria("alimento industrializado"));
		listaOrdenada.addAll(this.adicionaListaComprasPorCategoria("alimento nao industrializado"));

		if (posicao < listaOrdenada.size()) {
			return listaOrdenada.get(posicao).toString();
		}
		return "";
	}

	/**
	 * Metodo privado que auxilia o metodo getItemLista. Retorna uma lista de
	 * compras de uma listaDeCompras com determinada categoria.
	 * 
	 * @param categoria
	 *            categoria desejada.
	 * @param lista
	 *            lista de compras.
	 * @return lista de compras com apenas uma categoria (a categoria inserida).
	 */
	private List<Compra> adicionaListaComprasPorCategoria(String categoria) {
		List<Compra> listaCategoria = new ArrayList<>();
		for (Compra compra : this.compras.values()) {
			if (compra.getItem().getCategoria().equals(categoria)) {
				listaCategoria.add(compra);
			}
		}
		Collections.sort(listaCategoria, new ComparaNome());
		return listaCategoria;
	}

	private List<Item> buscatodosItens() {
		List<Item> itens = new ArrayList<Item>();
		for (Compra compra : this.compras.values()) {
			itens.add(compra.getItem());
		}
		return itens;
	}

	public String pesquisaCompraEmLista(Integer idItem) {
		if(!this.compras.containsKey(idItem)) {
			throw new CompraNaoCadastrada(SistemaMensagens.MSG_EXCECAO_PESQUISA_COMPRA.get());
		}
		return this.compras.get(idItem).getDescricao();
	}

	public void finalizar(String local, int valorFinal) {
		this.local = local;
		this.finalizada = true;
		this.valorFinal = valorFinal;
	}

	/**
	 * Metodo que verifica se uma listaDeCompras foi finalizada.
	 * 
	 * @return valor boleano indicando se foi finalidada ou nao.
	 */
	public boolean isFinalizada() {
		return this.finalizada;
	}

	public void deletaCompraDeLista(Integer idItem) {
		if (!this.compras.containsKey(idItem)) {
			throw new CompraNaoCadastrada(SistemaMensagens.MSG_EXCECAO_EXCLUSAO_COMPRA.get());
		}
		this.compras.remove(idItem);
	}

	public String getData() {
		return this.dataCriacao;
	}

	public String getDescritor() {
		return this.descritor;
	}

	@Override
	public int compareTo(ListaDeCompra o) {
		return this.descritor.toLowerCase().compareTo(o.getDescritor().toLowerCase());
	}
	
	@Override
	public String toString() {
		return this.descritor;
	}

	public Compra getItem(Integer idItem) {
		return this.compras.get(idItem);
	}

	public boolean possuiCompra(int idItem) {
		for (Compra compra : this.compras.values()) {
			if(compra.getItem().getId()==idItem) {
				return true;
			}
		}
		return false;
	}
}
