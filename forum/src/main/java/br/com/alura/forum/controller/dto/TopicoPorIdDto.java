package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.forum.modelo.StatusTopico;
import br.com.alura.forum.modelo.Topico;

public class TopicoPorIdDto {
	
	private Long id;
	private String titulo;
	private String mensagem;
	private String nomeAutor;
	private StatusTopico status;
	private LocalDateTime dataCriacao = LocalDateTime.now();
	private List<RespostaDto> resposta;
	
	public TopicoPorIdDto(Topico topico) {
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.nomeAutor = topico.getAutor().getNome();
		this.status = topico.getStatus();
		this.dataCriacao = topico.getDataCriacao();
		this.resposta = new ArrayList<RespostaDto>();
		this.resposta.addAll(topico.getRespostas().stream().map(x -> new RespostaDto(x)).collect(Collectors.toList()));
	}

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public String getNomeAutor() {
		return nomeAutor;
	}

	public StatusTopico getStatus() {
		return status;
	}

	public List<RespostaDto> getResposta() {
		return resposta;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

}
