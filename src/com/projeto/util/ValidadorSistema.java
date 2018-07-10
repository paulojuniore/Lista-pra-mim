package com.projeto.util;

import com.projeto.excecoes.CampoInvalidoException;

public class ValidadorSistema {

	public static boolean validaItem(String nome, String categoria) {
		if (nome == null || nome.trim().isEmpty()) {
			throw new CampoInvalidoException("Erro no cadastro de item: nome nao pode ser vazio ou nulo.");
		}
		if (categoria == null || categoria.trim().isEmpty()) {
			throw new CampoInvalidoException("Erro no cadastro de item: categoria nao pode ser vazia ou nula.");
		}
		if (!(categoria.equals("alimento industrializado") || categoria.equals("alimento nao industrializado")
				|| categoria.equals("limpeza") || categoria.equalsIgnoreCase("higiene pessoal"))) {
			throw new CampoInvalidoException("Erro no cadastro de item: categoria nao existe.");
		}
		return true;
	}

	public static boolean validaProdutoNaoIndustrializadoPorQuilo(double quilo, String localCompra, double preco,String nome,String categoria) {
		if (quilo <= 0) {
			throw new CampoInvalidoException("Erro no cadastro de item: valor de quilos nao pode ser menor que zero.");
		}
		if (localCompra == null || localCompra.trim().isEmpty()) {
			throw new CampoInvalidoException("Erro no cadastro de item: local de compra nao pode ser vazio ou nulo.");
		}
		if (preco <= 0) {
			throw new CampoInvalidoException("Erro no cadastro de item: preco de item invalido.");
		}
		validaItem(nome, categoria);
		return true;
	}
	
	public static boolean validaProdutoQuantidadeFixa(int quantidade, String unidadeMedida, String localCompra, double preco) {
		if (quantidade <= 0) {
			throw new CampoInvalidoException("Erro no cadastro de item: valor de quantidade nao pode ser menor que zero.");
		}
		if (unidadeMedida == null || unidadeMedida.trim().isEmpty()) {
			throw new CampoInvalidoException("Erro no cadastro de item: unidade de medida nao pode ser vazia ou nula.");
		}
		if(localCompra == null || localCompra.trim().isEmpty()) {
			throw new CampoInvalidoException("Erro no cadastro de item: local de compra nao pode ser vazio ou nulo.");
		}
		if(preco <= 0) {
			throw new CampoInvalidoException("Erro no cadastro de item: preco de item invalido.");
		}
		return true;
	}

	public static boolean validaProdutoPorUnidade(String nome, int unidade, String categoria, String localCompra, double preco) {
		if(preco <=0) {
			throw new CampoInvalidoException("Erro no cadastro de item: preco de item invalido.");
		}
		if(unidade <= 0) {
			throw new CampoInvalidoException("Erro no cadastro de item: valor de unidade nao pode ser menor que zero.");
		}
		if(localCompra == null || localCompra.trim().isEmpty()) {
			throw new CampoInvalidoException("Erro no cadastro de item: local de compra nao pode ser vazio ou nulo.");
		}
		
		
		validaItem(nome, categoria);
		return true;
	}

	
}