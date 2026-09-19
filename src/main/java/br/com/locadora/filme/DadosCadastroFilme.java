package br.com.locadora.filme;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroFilme(
		
		@NotBlank
		String titulo,
		String ano,
		String classificacao,
		LocalDate dataLancamento,
		String duracao,
		String genero,
		String nomeDiretor,
		String resumo,
		String pais,
		String lingua
		) {

}
