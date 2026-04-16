package com.pedrodevjava.pdv.repository;

import com.pedrodevjava.pdv.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}