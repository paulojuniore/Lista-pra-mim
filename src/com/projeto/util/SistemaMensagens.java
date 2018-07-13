package com.projeto.util;

/**
 * Classe responsavel por armazenar algumas mensagens exibidas pelo sistema.
 *
 */
public enum  SistemaMensagens {
	
	/**
	 * Mensagem exibida quando ocorre uma excecao no cadastro de itens.
	 */
	MSG_EXCECAO_CADASTRO("Erro no cadastro de item: "),
	
	/**
	 * Mensagem exibida quando ocorre um erro na listagem de itens.
	 */
	MSG_EXCECAO_LISTA_ITEM("Erro na listagem de item: "),
	
	/**
	 * Mensagem exibida quando ocorre um erro na atualizacao de itens.
	 */
	MSG_EXCECAO_ATUALIZA_ITEM("Erro na atualizacao de item: "),
	
	/**
	 * Mensagem exibida quando ocorre um erro no cadastro de precos de um item.
	 */
	MSG_EXCECAO_CADASTO_PRECO("Erro no cadastro de preco: ");
	
	String valor;
	
	private SistemaMensagens(String valor) {
		this.valor = valor;
	}
	
	public String get() {
		return this.valor;
	}

}
