package br.com.locadora.filme;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoFilme(
	@NotNull
	Long id,
	String titulo,
	String nomeDiretor) {
}
