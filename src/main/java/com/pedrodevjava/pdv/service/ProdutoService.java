package com.pedrodevjava.pdv.service;

import com.pedrodevjava.pdv.entity.Produto;
import com.pedrodevjava.pdv.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public Produto buscarPorCodigo(String codigo) {
        return repository.findByCodigoBarras(codigo)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }
}