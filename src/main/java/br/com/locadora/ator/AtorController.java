package br.com.locadora.ator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.locadora.filme.Filme;
import br.com.locadora.filme.FilmeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/ator")
@Tag(name = "Atores", description = "API para gerenciamento de atores")
public class AtorController {
	
	@Autowired
	private AtorRepository atorRepository;
	
	@Autowired
	private FilmeService filmeService;
	
	@GetMapping ("/formulario")
	@Operation(summary = "Formulário para novo Ator")
	public String carregaPaginaFormulario (Long id, Model model){ 
		model.addAttribute("filmes", filmeService.getAllFilme());
		if(id != null) {
	        var ator = atorRepository.getReferenceById(id);
	        model.addAttribute("ator", ator);
	    }
	    return "ator/formulario";              
	}   
	
	// Para editar filme com ID
		@GetMapping ("/formulario/{id}")
		@Operation(summary = "Editar Ator específico")
		public String carregaPaginaFormulario (@PathVariable("id") Long id, 
				HttpSession session,
				RedirectAttributes redirectAttributes, 
				Model model){ 

			try {
				Ator ator = atorRepository.findById(id)
						.orElseThrow(() -> new EntityNotFoundException("Ator não encontrado"));
				model.addAttribute("filmes", filmeService.getAllFilme());
				model.addAttribute("filmesAtor", filmeService.buscarFilmesAtor(id));
				model.addAttribute("ator", ator);
				return "ator/formulario";

			} catch (EntityNotFoundException e) {
				redirectAttributes.addFlashAttribute("error", e.getMessage());
				return "redirect:/ator";
			}
		}   
	
	@GetMapping
	@Operation(summary = "Listar todos os atores")
	public String carregaPaginaListagem (Model model){    
		
		//model.addAttributes("filmeAtor", );
	    model.addAttribute("lista", atorRepository.findAll(Sort.by("nome").ascending()));
	    return "ator/listagem";                         
	} 

	@PostMapping
	@Transactional
	@Operation(summary = "Cadastrar novo Ator")
	public String cadastrar ( @Valid DadosCadastroAtor dados, @RequestParam("filmesIds") List<Long> filmesIds) {
		Ator ator = new Ator(dados);

		// Busca os Filmes pelos IDs cria uma lista de Filmes 
		List<Filme> filmes = filmeService.findAllById(filmesIds);
	
//		for (Filme filme : filmes) {
//			//System.out.println("Filme " + filme.getTitulo());
//		} 
		ator.setFilmes(filmes);
		atorRepository.save(ator);
		return   "redirect:ator/formulario";      
	}
	
	@PutMapping
	@Transactional
	@Operation(summary = "Atualizar Ator")
	public String atualizar (DadosAtualizacaoAtor dados) {
		var ator = atorRepository.getReferenceById(dados.id());
		ator.atualizarInformacoes(dados);
		return "redirect:ator";  
	}
	
	@DeleteMapping
	@Transactional
	@Operation(summary = "Remover Ator")
	public String removeAtor (Long id) {
		atorRepository.deleteById (id);
		return "redirect:ator";  
	}
	
}
