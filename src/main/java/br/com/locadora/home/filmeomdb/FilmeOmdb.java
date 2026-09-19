package br.com.locadora.home.filmeomdb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.locadora.ator.Ator;

public class FilmeOmdb implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String titulo;
	private String imdbID;
	private String ano;
	private String poster;
	
	private List<Ator> atores = new ArrayList<Ator>();
	
	
	public FilmeOmdb(DadosListagemOmdb dados) {
		this.titulo = dados.titulo();
		this.imdbID = dados.imdbID();
	}


	public FilmeOmdb() {
		super();
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getImdbID() {
		return imdbID;
	}


	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}


	public String getAno() {
		return ano;
	}


	public void setAno(String ano) {
		this.ano = ano;
	}


	public String getPoster() {
		return poster;
	}


	public void setPoster(String poster) {
		this.poster = poster;
	}


	public List<Ator> getAtores() {
		return atores;
	}


	public void setAtores(List<Ator> atores) {
		this.atores = atores;
	}
	

	
}
