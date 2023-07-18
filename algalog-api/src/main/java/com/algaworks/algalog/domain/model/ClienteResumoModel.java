package com.algaworks.algalog.domain.model;

public class ClienteResumoModel {

	private Long id;
	private String nome;
	public ClienteResumoModel(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public ClienteResumoModel() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
//esse metodo serve para nao mostrar todos os dados do cliente que estao no entregaModel e mostrar somente os que estão colocados nessa classe de resumo