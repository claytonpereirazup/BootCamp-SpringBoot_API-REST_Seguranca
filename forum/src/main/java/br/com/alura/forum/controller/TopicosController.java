package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.dto.TopicoPorIdDto;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.controller.form.TopicoFormAtalizacao;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	@Autowired
	private CursoRepository coursoRepository;
	
	@GetMapping
	public List<TopicoDto> listar(String nomeCurso) {
		//// lista em memória
		//Topico topico = new Topico("Dúvida", "Dúvida com Spring", new Curso("Spring", "Programação"));
		//return Topico.converter(Arrays.asList(topico, topico, topico));
		
		//topicos?nomeCurso=HTML%205
		if (nomeCurso == null) {
			List<Topico> topicos = topicoRepository.findAll();
			return Topico.converter(topicos);
		} else {
			List<Topico> topicos = topicoRepository.carregarPorNomeDoCurso(nomeCurso);
			return Topico.converter(topicos);
		}
	}
	
	@Transactional
	@PostMapping
	public ResponseEntity<TopicoDto> cadastar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(coursoRepository);
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TopicoPorIdDto> listarPorId(@PathVariable Long id) {
		//Optional<Topico> topico = topicoRepository.findById(id);
		return topicoRepository.findById(id)
                .map(TopicoPorIdDto::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
	}
	
	@Transactional
	@PutMapping("/{id}")
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid TopicoFormAtalizacao form) {
		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			Topico topico = form.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDto(topico));
		}
		return ResponseEntity.notFound().build();
	}
	
	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
}
