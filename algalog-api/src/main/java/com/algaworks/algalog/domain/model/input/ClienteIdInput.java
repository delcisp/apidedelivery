package com.algaworks.algalog.domain.model.input;

import jakarta.validation.constraints.NotNull;

public class ClienteIdInput {
   @NotNull
	private Long id;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}
	
	
}
