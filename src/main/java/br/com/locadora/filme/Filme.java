package br.com.locadora.filme;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.com.locadora.ator.Ator;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name = "filme")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class Filme implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String titulo;
	private String ano;
	private String classificacao;
	private LocalDate dataLancamento;
	private String duracao;
	private String genero;
	private String nomeDiretor;
	private String resumo;
	private String pais;
	private String lingua;
	private String poster;
	private boolean desabilitado;
	private String imdbId;
	@Transient //  não persiste no banco
	private String dataFormatada;
	
	@ManyToMany( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(
		     name = "ator_filme", // Nome da tabela de junção
		     joinColumns = @JoinColumn(name = "filme_id"), // Coluna deste lado (ator)
		     inverseJoinColumns = @JoinColumn(name = "ator_id") // Coluna do outro lado (Filme)
		)
	private List<Ator> atores = new ArrayList<Ator>();
	
	public String getDataFormatada() {
		if (this.dataLancamento != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.dataFormatada = this.dataLancamento.format(formatter);
        }
        return this.dataFormatada;
	}

	public Filme(DadosCadastroFilme dados) {
		this.titulo = dados.titulo();
		this.nomeDiretor = dados.nomeDiretor();
		
	}

	public void atualizarInformacoes(DadosAtualizacaoFilme dados) {
		if (dados.titulo() != null )
			this.titulo = dados.titulo();
		if (dados.nomeDiretor() != null)
			this.nomeDiretor =dados.nomeDiretor();
	}
}
