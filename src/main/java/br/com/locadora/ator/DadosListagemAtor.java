package br.com.locadora.ator;

public record DadosListagemAtor(
	Long id,
	String nome,
	String pais) {
	public DadosListagemAtor (Ator ator) {
	    this(ator.getId(),
	    		ator.getNome(),
	    		ator.getPais()); 
	}
}
