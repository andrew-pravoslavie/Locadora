package br.com.locadora.ator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AtorService {
	@Autowired
	private AtorRepository repository;

	@Autowired
	private AtorMapper atorMapper;

	public Ator salvarOuAtualizar(DadosAtualizacaoAtor dto) {
		if (dto.id() != null) {
			// atualizando — Busca existente e atualiza
			Ator existente = repository.findById(dto.id())
				.orElseThrow(() -> new EntityNotFoundException("Ator não encontrado com ID: " + dto.id()));
			atorMapper.updateEntityFromDto(dto, existente);
			return repository.save(existente);
		} else {
			// criando — Novo ator
			Ator novoAtor = atorMapper.toEntityFromAtualizacao(dto);
			return repository.save(novoAtor);
		}
	}
	
	public List<Ator> getAllAtors() {
		return repository.findAll(Sort.by("nome").ascending());
	}

	public Ator getAtorById (Long id) {
		return repository.getReferenceById(id);
	}
	
	public Ator save(Ator ator) {
		if (ator.getNome() == null || ator.getNome().trim().isEmpty()) {
			return null; // Não salva atores sem nome
		}
		return repository.save(ator);
	}

	public Ator findByNome(String nome) {
		return repository.findByNome(nome);
	}
}
