package br.com.locadora.ator;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroAtor(
		
		@NotBlank
		String nome,
		String pais,
		List<Long> filmesIds) {

}
