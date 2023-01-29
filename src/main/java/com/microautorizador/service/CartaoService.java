package com.microautorizador.service;

import com.microautorizador.entity.Cartao;
import com.microautorizador.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartaoService {

    @Autowired
    CartaoRepository service;

    public Cartao pesquisarCartao(String numeroCartao) {
        return new Cartao();
    }

    public Optional<Cartao> pesquisarCartaoPorNumeroCartao(String numeroCartao) {
        return Optional.of(new Cartao());
    }

    public ResponseEntity<BigDecimal> getAmount(String numeroCartao) {
        return new ResponseEntity<BigDecimal>(new BigDecimal(500), HttpStatus.OK);
    }
}