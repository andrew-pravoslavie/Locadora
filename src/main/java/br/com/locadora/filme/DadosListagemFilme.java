package br.com.locadora.filme;

import java.time.LocalDate;

public record DadosListagemFilme(
	Long id,
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
	public DadosListagemFilme (Filme filme) {
	    this(
	    	filme.getId(),
	    	filme.getTitulo(),
	    	filme.getAno(),
	    	filme.getClassificacao(),
	    	filme.getDataLancamento(),
	    	filme.getDuracao(),
	    	filme.getGenero(),
	    	filme.getNomeDiretor(),
	    	filme.getResumo(),
	    	filme.getResumo(),
	    	filme.getLingua()
	    ); 
	}
}
