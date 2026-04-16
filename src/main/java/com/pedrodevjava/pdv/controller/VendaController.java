package com.pedrodevjava.pdv.controller;

import com.pedrodevjava.pdv.dto.ItemVendaDTO;
import com.pedrodevjava.pdv.dto.PagamentoDTO;
import com.pedrodevjava.pdv.entity.Venda;
import com.pedrodevjava.pdv.repository.VendaRepository;
import com.pedrodevjava.pdv.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @Autowired
    private VendaRepository vendaRepository;

    // ✅ CRIAR VENDA
    @PostMapping
    public Venda criar() {
        return vendaService.criarVenda();
    }

    // ✅ ADICIONAR ITEM (AGORA COM DTO)
    @PostMapping("/{id}/itens")
    public Venda adicionarItem(@PathVariable Long id, @RequestBody ItemVendaDTO dto) {
        return vendaService.adicionarItem(id, dto);
    }

    // ✅ FINALIZAR
    @PutMapping("/{id}/finalizar")
    public Venda finalizar(@PathVariable Long id) {
        return vendaService.finalizarVenda(id);
    }

    // ✅ PAGAR
    @PostMapping("/{id}/pagar")
    public Venda pagar(@PathVariable Long id, @RequestBody PagamentoDTO dto) {
        return vendaService.pagarVenda(id, dto);
    }

    // ✅ BUSCAR POR ID
    @GetMapping("/{id}")
    public Venda buscar(@PathVariable Long id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));
    }

    // ✅ LISTAR TODAS
    @GetMapping
    public List<Venda> listar() {
        return vendaRepository.findAll();
    }

    // ✅ RELATÓRIO
    @GetMapping("/relatorio")
    public Double relatorio() {
        return vendaService.totalVendasFinalizadas();
    }
}