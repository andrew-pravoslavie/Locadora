package br.com.locadora.filme;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FilmeService {
	@Autowired
	private FilmeRepository filmeRepository;

	@Autowired
	private FilmeMapper filmeMapper;

	public Filme salvarOuAtualizar(DadosAtualizacaoFilme dto) {
		if (dto.id() != null) {
			// atualizando — Busca existente e atualiza
			Filme existente = filmeRepository.findById(dto.id())
				.orElseThrow(() -> new EntityNotFoundException("Filme não encontrado com ID: " + dto.id()));
			filmeMapper.updateEntityFromDto(dto, existente);
			return filmeRepository.save(existente);
		} else {
			// criando — Novo filme
			Filme novoFilme = filmeMapper.toEntityFromAtualizacao(dto);
			return filmeRepository.save(novoFilme);
		}
	}
	
	public List<Filme> getAllFilme() {
		return filmeRepository.findAll(Sort.by("titulo").ascending());
	}
	public Filme getFilmeById(Long id) {
		return filmeRepository.getReferenceById(id);
	}
	public List<Filme> findAllById(List<Long> filmesIds) {
		return filmeRepository.findAllById(filmesIds);
	}

	public List<Filme> buscarLancamentosDoAno() {
        int anoAtual = Year.now().getValue();
        return filmeRepository.findByDataLancamento(anoAtual);
    }
	
	public List<Filme> buscarFilmesDestaque() {
        // Implementar lógica específica para filmes em destaque
        // Exemplo: 5 filmes mais recentes ou mais avaliados
        return filmeRepository.findTop5ByOrderByDataLancamentoDesc();
    }
	public List<Filme> buscarFilmesAtor(Long id){
		return filmeRepository.findByIdWithFilmes(id);
	}
}
