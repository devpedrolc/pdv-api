package com.pedrodevjava.pdv.controller;

import com.pedrodevjava.pdv.entity.Produto;
import com.pedrodevjava.pdv.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    // ✅ CRIAR PRODUTO
    @PostMapping
    public Produto criar(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    // ✅ LISTAR
    @GetMapping
    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    // ✅ BUSCAR POR ID
    @GetMapping("/{id}")
    public Produto buscar(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    // ✅ ATUALIZAR
    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @RequestBody Produto produto) {

        Produto p = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        p.setNome(produto.getNome());
        p.setCodigoBarras(produto.getCodigoBarras());
        p.setPreco(produto.getPreco());
        p.setEstoque(produto.getEstoque());

        return produtoRepository.save(p);
    }
}