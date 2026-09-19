package br.com.locadora.ator;

import java.util.List;

import br.com.locadora.filme.Filme;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ator")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class Ator {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	private String pais;
	private String imagem;
	
	@ManyToMany(mappedBy = "atores",  fetch = FetchType.LAZY)
	private List<Filme> filmes;
	
	public Ator(DadosCadastroAtor dados) {
		this.nome = dados.nome();
		this.pais = dados.pais();
	}
	
	public Ator(String nome ) {
		this.nome = nome;
	}
	
	public void atualizarInformacoes(DadosAtualizacaoAtor dados) {
		if (dados.nome() != null)
			this.nome = dados.nome();
		if (dados.pais() != null)
			this.pais = dados.pais();
	}
}