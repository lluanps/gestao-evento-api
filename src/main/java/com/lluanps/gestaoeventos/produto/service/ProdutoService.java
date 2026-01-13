package com.lluanps.gestaoeventos.produto.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lluanps.gestaoeventos.evento.model.Evento;
import com.lluanps.gestaoeventos.evento.service.EventoService;
import com.lluanps.gestaoeventos.produto.dto.ProdutoDTO;
import com.lluanps.gestaoeventos.produto.model.Produto;
import com.lluanps.gestaoeventos.produto.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repository;
	
	@Autowired
	private EventoService eventoService;

	public Produto cadastrarProduto(ProdutoDTO dto) {
		Produto produto = new Produto();
		
		Evento evento = eventoService.buscarPorId(dto.getEventoId());
		
		produto.setEvento(evento);
		produto.setNome(dto.getNome());
		produto.setValorProduto(dto.getValorProduto());
		produto.setQuantidadeInicial(dto.getQuantidadeInicial());
		produto.setQuantidadeVendida(0);
		
		return repository.save(produto);
	}

	public Produto buscarProdutoById(Integer produtoId) {
		return repository.findById(produtoId)
	            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
	}
	
	public ProdutoDTO cadastrarNovaVenda(Integer produtoId, ProdutoDTO dto) {

	    Produto produto = buscarProdutoById(produtoId);
	    Evento evento = eventoService.buscarPorId(dto.getEventoId());

	    if (produto.getQuantidadeVendida() == null)
	        produto.setQuantidadeVendida(0);

	    if (produto.getTotalVendido() == null)
	        produto.setTotalVendido(BigDecimal.ZERO);

	    BigDecimal vendaAtual =
	        produto.getValorProduto()
	            .multiply(BigDecimal.valueOf(dto.getQuantidadeVendida()));

	    produto.setTotalVendido(produto.getTotalVendido().add(vendaAtual));
	    produto.setQuantidadeVendida(
	        produto.getQuantidadeVendida() + dto.getQuantidadeVendida()
	    );

	    produto.setEvento(evento);

	    Produto salvo = repository.save(produto);

	    return toDTO(salvo);
	}
	
	private ProdutoDTO toDTO(Produto produto) {
	    ProdutoDTO dto = new ProdutoDTO();
	    dto.setId(produto.getId());
	    dto.setNome(produto.getNome());
	    dto.setQuantidadeInicial(produto.getQuantidadeInicial());
	    dto.setQuantidadeVendida(produto.getQuantidadeVendida());
	    dto.setValorProduto(produto.getValorProduto());
	    dto.setEventoId(
	        produto.getEvento() != null ? produto.getEvento().getId() : null
	    );
	    return dto;
	}
	
	public ProdutoDTO atualizarProduto(Integer produtoId, ProdutoDTO dto) {

	    Produto produto = buscarProdutoById(produtoId);

	    if (dto.getEventoId() != null) {
	        Evento evento = eventoService.buscarPorId(dto.getEventoId());
	        produto.setEvento(evento);
	    }

	    if (dto.getNome() != null) {
	        produto.setNome(dto.getNome());
	    }

	    if (dto.getValorProduto() != null) {
	        produto.setValorProduto(dto.getValorProduto());
	    }

	    if (dto.getQuantidadeInicial() != null) {
	        produto.setQuantidadeInicial(dto.getQuantidadeInicial());
	    }

	    Produto salvo = repository.save(produto);
	    return toDTO(salvo);
	}

}
