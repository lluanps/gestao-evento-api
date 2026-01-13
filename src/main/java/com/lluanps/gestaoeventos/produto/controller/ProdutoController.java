package com.lluanps.gestaoeventos.produto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lluanps.gestaoeventos.produto.dto.ProdutoDTO;
import com.lluanps.gestaoeventos.produto.model.Produto;
import com.lluanps.gestaoeventos.produto.service.ProdutoService;

@RestController
@RequestMapping("produto")
public class ProdutoController {

	@Autowired
	private ProdutoService service;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Produto cadastrarProduto(@RequestBody ProdutoDTO dto) {
		return service.cadastrarProduto(dto);
	}
	
	@GetMapping("/{produtoId}")
	public Produto buscarProdutoPorId(@PathVariable Integer produtoId) {
		return service.buscarProdutoById(produtoId);
	}
	
	@PostMapping("/venda/{id}")
	public ProdutoDTO vender(
	        @PathVariable Integer id,
	        @RequestBody ProdutoDTO dto) {

	    return service.cadastrarNovaVenda(id, dto);
	}
	
	@PutMapping("/{produtoId}")
	public ProdutoDTO atualizarProduto(
	        @PathVariable Integer produtoId,
	        @RequestBody ProdutoDTO dto) {

	    return service.atualizarProduto(produtoId, dto);
	}
	
}
