package com.pedrodevjava.pdv.service;

import com.pedrodevjava.pdv.dto.ItemVendaDTO;
import com.pedrodevjava.pdv.dto.PagamentoDTO;
import com.pedrodevjava.pdv.entity.ItemVenda;
import com.pedrodevjava.pdv.entity.Produto;
import com.pedrodevjava.pdv.entity.Venda;
import com.pedrodevjava.pdv.entity.StatusVenda;
import com.pedrodevjava.pdv.exception.BusinessException;
import com.pedrodevjava.pdv.repository.ProdutoRepository;
import com.pedrodevjava.pdv.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    // ✅ CRIAR VENDA
    public Venda criarVenda() {
        Venda venda = new Venda();
        venda.setData(LocalDateTime.now());
        venda.setTotal(0.0);
        venda.setStatus(StatusVenda.ABERTA);
        venda.setItens(new ArrayList<>());

        return vendaRepository.save(venda);
    }

    // ✅ ADICIONAR ITEM (COM DTO)
    @Transactional
    public Venda adicionarItem(Long idVenda, ItemVendaDTO dto) {

        Venda venda = vendaRepository.findById(idVenda)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        if (venda.getStatus() != StatusVenda.ABERTA) {
            throw new RuntimeException("Venda não está aberta");
        }

        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (produto.getEstoque() < dto.getQuantidade()) {
            throw new RuntimeException("Estoque insuficiente");
        }

        if (venda.getItens() == null) {
            venda.setItens(new ArrayList<>());
        }

        ItemVenda item = new ItemVenda();
        item.setVenda(venda);
        item.setProduto(produto);
        item.setQuantidade(dto.getQuantidade());
        item.setPreco(produto.getPreco());
        item.setSubtotal(dto.getQuantidade() * produto.getPreco());

        venda.getItens().add(item);

        produto.setEstoque(produto.getEstoque() - dto.getQuantidade());
        produtoRepository.save(produto);

        double total = venda.getItens().stream()
                .mapToDouble(ItemVenda::getSubtotal)
                .sum();

        venda.setTotal(total);

        return vendaRepository.save(venda);
    }

    // ✅ FINALIZAR
    @Transactional
    public Venda finalizarVenda(Long idVenda) {

        Venda venda = vendaRepository.findById(idVenda)
                .orElseThrow(() -> new BusinessException("Venda não encontrada"));

        if (venda.getStatus() != StatusVenda.ABERTA) {
            throw new BusinessException("Venda já finalizada ou cancelada");
        }

        if (venda.getItens().isEmpty()) {
            throw new BusinessException("Venda sem itens");
        }

        venda.setStatus(StatusVenda.FINALIZADA);

        return vendaRepository.save(venda);
    }

    // ✅ PAGAR
    @Transactional
    public Venda pagarVenda(Long id, PagamentoDTO dto) {

        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Venda não encontrada"));

        if (venda.getStatus() != StatusVenda.FINALIZADA) {
            throw new BusinessException("Venda precisa estar FINALIZADA");
        }

        if (dto.getValorPago() < venda.getTotal()) {
            throw new BusinessException("Valor insuficiente");
        }

        venda.setTipoPagamento(dto.getTipoPagamento());
        venda.setValorPago(dto.getValorPago());
        venda.setTroco(dto.getValorPago() - venda.getTotal());

        return vendaRepository.save(venda);
    }

    // ✅ RELATÓRIO
    public Double totalVendasFinalizadas() {
        return vendaRepository.findAll().stream()
                .filter(v -> v.getStatus() == StatusVenda.FINALIZADA)
                .mapToDouble(Venda::getTotal)
                .sum();
    }
}