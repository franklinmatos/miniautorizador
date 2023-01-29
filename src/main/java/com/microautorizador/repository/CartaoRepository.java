package com.microautorizador.repository;

import com.microautorizador.entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartaoRepository extends JpaRepository<Cartao, UUID> {
}
