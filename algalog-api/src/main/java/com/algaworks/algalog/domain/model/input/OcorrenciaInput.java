package com.algaworks.algalog.domain.model.input;

import jakarta.validation.constraints.NotBlank;

public class OcorrenciaInput {

	@NotBlank
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
